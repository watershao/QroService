package com.iflytek.asr.AsrService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Process;
import android.util.Log;

/**
 * ASR AudioRecord manager
 * 
 * @author zhangyun
 * 
 */
public class AsrRecord {

	private static final String TAG = "AsrRecord:";
	private static final int BUFF_SIZE = 64 * 320; // Receive data buffer size
//	private static final int BUFF_SIZE = 5464*10; // Receive data buffer size
	private static final int FRAME_BUFF = 16 * 320; // A frame buffer size
	private static final int SAMPLE_RATE = 16000; // Sample rate 44100,22050,11025,8000
	private static final int READ_DELAY = 10; // Read delay time
	private static final long WAIT_TEIMOUT = 20000; // Time out
	private static final int BUFF_IGNORE = 4 * 320; // Ignore audio data when
													// begin record

	private static volatile AudioRecord mRecord = null;
	private static final ReentrantLock ReadThreadLock = new ReentrantLock();

	/**
	 * 云识别开始时间
	 */
	private static long start = 0;
	
	/**
	 * 本地识别次数
	 */
	private static int count = 0;
	
	private static int minBufferSize = 0;
	
	/**
	 * 开始录音识别
	 * @return
	 */
	public static int startRecord() {
		Log.d(TAG, "startRecord");

		stop();

/*		int minBuffSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
		Log.w(TAG, "AudioTrack minBuffsize="+minBuffSize);
		minBufferSize = 10*minBuffSize;
		if (minBufferSize < BUFF_SIZE) {
			minBufferSize = BUFF_SIZE;
		}*/
		mRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, BUFF_SIZE);
//		AudioFormat.ENCODING_PCM_16BIT, minBufferSize);
		if (null == mRecord) {
			return -1;
		}
//		Log.d(TAG, "RECORD STATE = " + mRecord.getState());
		if (mRecord.getState() == AudioRecord.STATE_UNINITIALIZED) {
			Log.d(TAG, "startRecord state uninitialized");
			return -1;
		} else {
			mRecord.startRecording();
			//开启云识别
			if (isCloud && cAsr != null) {
				isCloudSucc = false;
				Log.d(TAG, ">>>cloud reco free:"+cAsr.isCloudRecoFree());
				if (!cAsr.isCloudRecoFree()) {
					cAsr.cancelCloudRec();
				}
				isCloudSucc = cAsr.startAudioInput(null, "sms", null, null);
				Log.d(TAG, "cloud start:"+isCloudSucc);
			}
		}

		class ThreadRecord implements Runnable {

			@Override
			public void run() {
				//设置录音线程为最高优先级
				Process.setThreadPriority(Process.THREAD_PRIORITY_AUDIO);
				boolean isReadRunable = false;
				
				try {
					isReadRunable = ReadThreadLock.tryLock(WAIT_TEIMOUT, TimeUnit.MILLISECONDS);
					count++;
					if (!isReadRunable) {
						//解决overflow问题
						Thread.sleep(6000);
						
						Log.e(TAG, "ThreadRecord tryLock  is unavailable");
						return;
					}
					if (mRecord == null) {
						Log.d(TAG, "ThreadRecord mRecord is null ");
						return;
					}
					byte[] mBuff = new byte[BUFF_SIZE];
//					byte[] mBuff = new byte[minBufferSize];
					mRecord.read(mBuff, 0, BUFF_IGNORE);
					// Log.d(TAG, " ignore audio data ...");
					
					FileOutputStream fos = null;  
					if (isSaveRecord) {
				        try {  
				        	File f = new File(audioDirectory);
				        	if (!f.exists()) {
								f.mkdir();
							}
				            File file = new File(audioName);  
				            if (file.exists()) {  
				                file.delete();  
				            } 
				            File waveFile = new File(audioWav);  

				            fos = new FileOutputStream(file);// 建立一个可存取字节的文件  
				        } catch (Exception e) {  
				            e.printStackTrace();  
				        }  
					}
					
					if(Asr.D){
						Log.d("AsrRecord>>>>>>>", "start appendData time:"+System.currentTimeMillis());
					}
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
							Log.d(TAG, "ThreadRecord mRecord uninitialized or stopped");
							break;
						}
						// Log.d(TAG, "ThreadRecord begin read.......");
						int ret = 0;
						try {
							ret = mRecord.read(mBuff, 0, FRAME_BUFF);
						} catch (Exception e) {
							
						}
						//保存录音到本地
						if (isSaveRecord) {
							if (AudioRecord.ERROR_INVALID_OPERATION != ret && AudioRecord.ERROR_BAD_VALUE !=ret && fos != null) {  
				                try {  
				                    fos.write(mBuff, 0,FRAME_BUFF);  
				                } catch (IOException e) {  
				                    e.printStackTrace();  
				                }  
				            }  
				        }  

						if (ret > 0) {
							//云识别数据长度
							int cloudLength = ret;
							//向识别引擎送入数据,返回0为添加语音数据成功
							ret = Asr.JniAppendData(mBuff, ret);

							//只有当开启云识别成功后才执行
							if(isCloud && isCloudSucc && cAsr != null){
								//云识别数据
								byte[] cloudByte = new byte[cloudLength];
								System.arraycopy(mBuff, 0, cloudByte, 0, cloudLength);
								long end = System.currentTimeMillis();

								long runtime = end -start;
								start = end;

								if (cloudLength == FRAME_BUFF) {
									int standard = FRAME_BUFF*20/640;
									if (runtime<standard) {
										Thread.sleep(standard - runtime);
									}
								}
								cAsr.writeAudio(cloudByte);
							}
							if (0 != ret) {
								Log.e(TAG, "ThreadRecord append data to ASR error!");
								break;
							}
						} else {
							Log.e(TAG, "ThreadRecord read data error!");
							break;
						}
					} // end of while
					
					if(Asr.D){
						Log.d("AsrRecord>>>>>>>>>", "appendData over time:"+System.currentTimeMillis());
					}
					start = 0;
					if (fos != null) {
						try {  
							fos.close();// 关闭写入流  
						} catch (IOException e) {  
							e.printStackTrace();  
						} 
						
						//将裸音频转换成可播放的音频文件
						AudioTool.copyWaveFile(audioName, audioWav);
						isSaveRecord = false;
					}
					
					try {
						Thread.sleep(80);
						Log.d(TAG, "ThreadRecord finish and release");
						if (null != mRecord) {
							if(mRecord.getState() == AudioRecord.STATE_INITIALIZED
									|| AudioRecord.RECORDSTATE_RECORDING == mRecord.getRecordingState()
									 ){
								mRecord.stop();
							}
							mRecord.release();
							mRecord = null;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					mBuff = null;
					
					if (isCloud && cAsr != null) {
						cAsr.stopCloudListening();
						cAsr = null;
						isCloud = false;
					}
				} catch (InterruptedException e1) {
					e1.printStackTrace();
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

	private static void stop() {
		if (null != mRecord) {
			try {
				isSaveRecord = false;
				if (mRecord.getState() == AudioRecord.STATE_INITIALIZED
						|| AudioRecord.RECORDSTATE_RECORDING == mRecord.getRecordingState()) {
					mRecord.stop();
				}
				mRecord.release();
				mRecord = null;
				Log.w(TAG, "stopRecord over");
			} catch (Throwable e) {
			}
		}
	}

	/**
	 * 停止录音
	 */
	public static void stopRecord() {

		Log.d(TAG, "stopRecord");
		stop();
	
	}
	
	/**
	 * 是否保存录音到本地
	 */
	private static volatile boolean isSaveRecord = false;
	//裸音频数据文件  
	private static String audioName = "/mnt/sdcard/qrobot/myvoice/voice.raw";
	//音频目录
	private static String audioDirectory = "/mnt/sdcard/qrobot/myvoice";
	//可播放的音频文件
	private static String audioWav = "/mnt/sdcard/qrobot/myvoice/myvoice.wav";
	
	/**
	 * 保存录音文件到本地
	 * @param isSave
	 * @return
	 */
	public static boolean saveRecord(boolean isSave){
//		Log.d(TAG, "saveRecord:"+isSave);
		isSaveRecord = isSave;
		return isSaveRecord;
	}
	
	/**
	 * 是否开启云识别
	 */
	private static volatile boolean isCloud = false;
	/**
	 * 云识别是否开启成功
	 */
	private static volatile boolean isCloudSucc = false;
	private static CloudAsr cAsr = null;
	/**
	 * 开启云识别
	 * @param context
	 * @return
	 */
	public static boolean startCloudAsr(Context context){
//		Log.d(TAG, "startCloudAsr");
//		return false;
		if (cAsr == null) {
			cAsr = new CloudAsr(context);
		}
		isCloud = true;
		return true;
	}
	
}
