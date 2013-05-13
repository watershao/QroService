package com.iflytek.asr.AsrService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.qrobot.sound.AsrAidlService;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Process;
import android.util.Log;

/**
 * 主要用于本地文件的识别
 * @author water
 *
 */
public class FileAsr {

	private static final String RECORED_DIR = "/sdcard/qrobot/record/";
	
	private static final String TAG = "FileRecord:";
	private static final int BUFF_SIZE = 64 * 320; // Receive data buffer size
//	private static final int BUFF_SIZE = 5464*10; // Receive data buffer size
	private static final int FRAME_BUFF = 16 * 320; // A frame buffer size
	private static final int SAMPLE_RATE = 16000; // Sample rate 44100,22050,11025,8000
	private static final int READ_DELAY = 10; // Read delay time
	private static final long WAIT_TEIMOUT = 20000; // Time out
	private static final int BUFF_IGNORE = 4 * 320; // Ignore audio data when
													// begin record

	private static final int STANDARD_INTERVAL = FRAME_BUFF * 20/640;
	
	private static volatile AudioRecord mRecord = null;
	
	private static final ReentrantLock ReadThreadLock = new ReentrantLock();
	
	//裸音频数据文件  
	private static String audioName = "/mnt/sdcard/qrobot/record/voice.pcm";
	//音频目录
	private static String audioDirectory = "/mnt/sdcard/record/";
	//可播放的音频文件
	private static String audioWav = "/mnt/sdcard/qrobot/record/myvoice.wav";
	
	/**
	 * 开始录音，并保存
	 * @param audioFileName 保存的录音文件名称,默认为/sdcard/qrobot/record/voice.pcm
	 * @return
	 */
	public static int startFileRecord(final String audioFileName) {
		stopRecord();
		mRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, BUFF_SIZE);

		if (mRecord == null) {
			return -1;
		}

		if (mRecord.getState() == AudioRecord.STATE_UNINITIALIZED) {
			Log.d(TAG, "startRecord state uninitialized");
			return -1;
		} else {
			mRecord.startRecording();
		}

		class ThreadRecord implements Runnable {

			@Override
			public void run() {
				// 设置录音线程为最高优先级
				Process.setThreadPriority(Process.THREAD_PRIORITY_AUDIO);
				boolean isReadRunable = false;

				try {
					isReadRunable = ReadThreadLock.tryLock(WAIT_TEIMOUT,
							TimeUnit.MILLISECONDS);
					if (!isReadRunable) {
						// 解决overflow问题
						Thread.sleep(6000);
						Log.e(TAG, "ThreadRecord tryLock  is unavailable");
						return;
					}
					if (mRecord == null) {
						Log.d(TAG, "ThreadRecord mRecord is null ");
						return;
					}
					byte[] mBuff = new byte[BUFF_SIZE];
					mRecord.read(mBuff, 0, BUFF_IGNORE);
					// Log.d(TAG, " ignore audio data ...");

					FileOutputStream fos = null;

					File f = new File(RECORED_DIR);
					if (!f.exists()) {
						f.mkdir();
					}
					File recordFile = null;
					if (audioFileName == null || audioFileName.equals("")) {
						recordFile = new File(audioName);
					} else {
						recordFile = new File(audioFileName);
					}
					if (recordFile.exists()) {
						recordFile.delete();
					} else {
						recordFile.createNewFile();
					}
					
					File waveFile = new File(audioWav);
					fos = new FileOutputStream(recordFile);// 建立一个可存取字节的文件

					while (true) {
						try {
							Thread.sleep(READ_DELAY);
						} catch (InterruptedException e) {
							e.printStackTrace();
							break;
						}
						if (mRecord == null) {
							Log.d(TAG, "ThreadRecord mRecord null ");
							break;
						}
						if (mRecord.getState() == AudioRecord.STATE_UNINITIALIZED
								|| AudioRecord.RECORDSTATE_STOPPED == mRecord
										.getRecordingState()) {
							Log.d(TAG,
									"ThreadRecord mRecord uninitialized or stopped");
							break;
						}
						int ret = 0;
						ret = mRecord.read(mBuff, 0, FRAME_BUFF);
						if (ret > 0) {
							// 保存录音到本地

							// if (AudioRecord.ERROR_INVALID_OPERATION != ret &&
							// AudioRecord.ERROR_BAD_VALUE !=ret && fos != null)
							// {
							
							fos.write(mBuff, 0, FRAME_BUFF);
							
							// }
						} else {
							Log.e(TAG, "ThreadRecord read data error!");
							break;
						}
					} // end of while

					if (fos != null) {

						fos.close();// 关闭写入流

						// 将裸音频转换成可播放的音频文件
						AudioTool.copyWaveFile(audioName, audioWav);
					}
					
					Thread.sleep(80);
					Log.d(TAG, "ThreadRecord finish and release");
					if (null != mRecord) {
						if (mRecord.getState() == AudioRecord.STATE_INITIALIZED
								|| AudioRecord.RECORDSTATE_RECORDING == mRecord
										.getRecordingState()) {
							mRecord.stop();
						}
						mRecord.release();
						mRecord = null;
					}
					mBuff = null;
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (isReadRunable) {
						ReadThreadLock.unlock();
					}
				}
			}
		};
		Thread mThreadRecord = new Thread(new ThreadRecord());
		mThreadRecord.setPriority(Thread.MAX_PRIORITY);
		mThreadRecord.start();
		return 0;
	}
	
	private static volatile boolean isCloudSucc = false;
	
	/**
	 * 开始录音文件识别，目前仅能使用pcm文件
	 * @param context 云识别用到的上下文
	 * @param sceneName 场景文件名称
	 * @param audioFileName 录音文件名称
	 * @param openCloudAsr 是否开启云识别
	 * @return 
	 */
	public static int startFileAsr(Context context, String sceneName,
									String audioFileName, boolean openCloudAsr){
		final File asrFile = new File(audioFileName);
		if (!asrFile.exists()) {
			return -1;
		}
		
		if (openCloudAsr) {
			isCloudSucc = startCloudAsr(context);
		}
		
		Asr.JniStart(sceneName);
		
		new Thread(){
			public void run(){
				//设置识别线程为最高优先级
				Process.setThreadPriority(Process.THREAD_PRIORITY_AUDIO);
				Asr.JniRunTask();
			}
		}.start();
		
		new Thread(){
			
			public void run(){
				//设置识别线程为最高优先级
				Process.setThreadPriority(Process.THREAD_PRIORITY_AUDIO);
				
				try {
					FileInputStream fis = new FileInputStream(asrFile);
					byte[] buff = new byte[FRAME_BUFF];
					int count = 0;
					while ((count = fis.read(buff, 0, FRAME_BUFF)) > 0) {
						Thread.sleep(READ_DELAY);
						int ret = Asr.JniAppendData(buff, count);
						//云识别
						if(isCloudSucc){
							cAsr.writeAudio(buff);
							Thread.sleep(STANDARD_INTERVAL);
						}
						
						if (0 != ret) {
							Log.e(TAG, "ThreadRecord append data to ASR error!");
							break;
						}
					}

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (isCloudSucc && cAsr != null) {
						cAsr.stopCloudListening();
						cAsr = null;
						isCloudSucc = false;
					}
					Asr.setAsrRunningState(false);
					Asr.setFileAsrState(false);
				}
				
			}}.start();
		
		return 0;
	}
	
	/**
	 * 停止录音
	 */
	public static void stopRecord() {
		if (null != mRecord) {
			try {
				if (mRecord.getState() == AudioRecord.STATE_INITIALIZED
						|| AudioRecord.RECORDSTATE_RECORDING == mRecord.getRecordingState()) {
					mRecord.stop();
				}
				mRecord.release();
				mRecord = null;
				Log.w(TAG, "stopRecord over");
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}

	private static CloudAsr cAsr = null;
	
	/**
	 * 打开云识别
	 * @param context
	 * @return true为成功，false为失败
	 */
	private static boolean startCloudAsr(Context context){
		Log.d(TAG, "startCloudAsr");
//		return false;
		if (cAsr == null) {
			cAsr = new CloudAsr(context);
		}
		
		//开启云识别
		if (cAsr != null) {
			if (!cAsr.isCloudRecoFree()) {
				cAsr.cancelCloudRec();
			}
			boolean isCloudSucc = cAsr.startAudioInput(null, "sms", null, null);
			Log.w(TAG, "cloud start:"+isCloudSucc);
			return isCloudSucc;
		}
		
		return false;
	}
}
