package com.iflytek.tts.TtsService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Process;
import android.util.Log;

import com.qrobot.sound.TtsData;
import com.qrobot.util.Mp3Decoder;
import com.ritech.qrobot.MSG_CODE;

public class AudioData {

	private static AudioTrack mAudio;
	private static final String TAG = "TtsService(AudioData):";
	private static int mStreamType = AudioManager.STREAM_MUSIC;
	private static int mSampleRate = 16000;
	static int mBuffSize = AudioTrack.getMinBufferSize(44100,
			AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);

	private final static boolean D = true;
	
	static {
		int mBuffSize = AudioTrack.getMinBufferSize(44100,
				AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
		mAudio = new AudioTrack(mStreamType, mSampleRate,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, mBuffSize,
				AudioTrack.MODE_STREAM);
		Log.d(TAG, " AudioTrack create ok");
	}

	public static TtsCallback callback;

	public static void setTtsCallback(TtsCallback tts) {
		AudioData.callback = tts;
	}

	/**
	 * 初始化语音合成服务
	 * @param s
	 */
	public static void init(String s) {
		Tts.JniCreate(s);
	}

	private static volatile boolean isTtsPlaying = false;
	private static volatile boolean isMadiePlaying = false;
	private static volatile boolean isMp3Playing = false;

	private static TtsData data;
	private static final Object SPEAK_LOCK = new Object();
//	private static final byte[] SPEAK_LOCK = new byte[0];
	private static volatile boolean speakNext;
	private static String broadcastKey;

	public static String getBroadcastKey() {
		return broadcastKey;
	}
	
	private static ReentrantLock rLock = new ReentrantLock(true);
	
	private static volatile List<TtsData> ttsDataList = new ArrayList<TtsData>();

	private static volatile boolean isTtsThreadRunning = false;
	/**
	 * 语音合成播放
	 * @param ttsData
	 * @return
	 */
	public static boolean playTts(final TtsData ttsData) {
		Log.d(TAG, "playTts::::isTtsPlaying:"+isTtsPlaying+
				";isMediaPlaying:"+isMadiePlaying+";isMp3Playing:"+isMp3Playing);
		Log.w("Qrob_Test", "playTts:"+System.currentTimeMillis());
		ttsDataList.add(ttsData);
		
		if (ttsDataList.size() > 1) {
			return true;
		}

		TtsThread ttsThread = new TtsThread();
		ttsThread.setPriority(Thread.MAX_PRIORITY);
		ttsThread.start();

		return true;
	}

	/**
	 * tts运行线程
	 * @author v_watershao
	 *
	 */
	static class TtsThread extends Thread {

		TtsThread (){
			
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			isTtsThreadRunning = true;
			if (D) {
				Log.d(TAG, "ttsThread start。"+Thread.currentThread().getName()+"*"+System.currentTimeMillis());
			}
			//设置播放线程为最高优先级
			Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
			speakNext = true;
			for (int t = 0; t < ttsDataList.size(); t++) {
				data = ttsDataList.get(t);
				Log.d(TAG, "data:"+t+"*"+data+"*ttsDataList size:"+ttsDataList.size());
				ttsLock();
			}
			speakNext = false;
			if (D) {
				Log.d(TAG, "ttsThread over。"+Thread.currentThread().getName()+"*"+System.currentTimeMillis());
			}
			
			ttsDataList.clear();
			isTtsThreadRunning = false;
		}
		
		
	}
	
	
	/**
	 * tts锁
	 */
	public static void ttsLock() {
		rLock.lock();
		try {	
//		synchronized (SPEAK_LOCK) {
			
			broadcastKey = data.getBroadcastKey();
//			speakNext = true;
			int count = data.getListSize();
			
			int start = 0;
			int startAppend = count -1;
			//查找添加的播放记忆点
			while (startAppend > 0){
				if (data.getResourceId(startAppend)==4) {
					start = startAppend;
					break;
				}
				startAppend--;
			}
			
			for (int i = start; i < count; i++) {
				if (speakNext) {
					switch (data.getResourceId(i)) {
					// text
					case 0:
						isTtsPlaying = true;
						String flag = data.getFlag(i);
						callback.speakFinish(MSG_CODE.MSG_PLAY_START, 0,flag);
						if (D) {
							Log.w("Qrob_Test", "TTsTime:"+System.currentTimeMillis());
						}

						isFirst = true;
//						String text = data.getText(i);
//						if (text.equalsIgnoreCase("[d]")) {
//							Tts.JniSpeak(text);
//						} else {
//							Tts.JniSpeak(",.,."+text);
//						}
						//playFirst();
						Tts.JniSpeak(data.getText(i));

						if (D) {
						Log.w(TAG, Thread.currentThread().getName()+"*"
								+System.currentTimeMillis()+".tts::::"+data.getText(i));
						}
						callback.speakFinish(
								MSG_CODE.MSG_PLAY_SPEAKING,
								data.getCallbackCode(i),"");
						isTtsPlaying = false;
						callback.speakFinish(MSG_CODE.MSG_PLAY_DONE, 0,flag);
						break;

					//media
					case 1:
						if (D) {
							Log.d(TAG, Thread.currentThread().getName()+".tts media::::"+data.getText(i));
							Log.w("Qrob_Test", "MediaTime:"+System.currentTimeMillis());
						}
//						playMedia(data.getText(i), broadcastKey);
						boolean isSucc = playMp3(data.getText(i), broadcastKey);
						if (D) {
							Log.d(TAG, "playMp3:"+isSucc);
						}
						break;
					
					//start record
					case 2:
						//启动语音识别的标识
						callback.speakFinish(MSG_CODE.MSG_PLAY_RECORD, 0,"");
						break;
						
					default:
						break;
					}
				}
			}
//			speakNext = false;
			} finally {
				if (rLock != null && rLock.isHeldByCurrentThread()) {
					rLock.unlock();
					if (mAudio != null && mAudio.getState() == AudioTrack.STATE_INITIALIZED 
							&& mAudio.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
						mAudio.stop();
					}
				}
			}
	}
	
	/**
	 * 停止播放
	 * @return
	 */
	public static boolean stopAudio() {
		Log.d(TAG, "stopAudio:::isTtsPlaying:"+isTtsPlaying+
				";isMediaPlaying:"+isMadiePlaying+";isMp3Playing:"+isMp3Playing);
//		isTtsThreadRunning = false;
		if (isTtsPlaying) {
			speakNext = false;
			Tts.JniStop();
			Tts.JniDestory();
			
//			if (mp3Audio != null && mp3Audio.getState() == AudioTrack.STATE_INITIALIZED 
//					&& AudioTrack.PLAYSTATE_PLAYING == mp3Audio.getPlayState()) {
//				mp3Audio.stop();
//			}
//			
			isTtsPlaying = false;
			callback.speakFinish(MSG_CODE.MSG_PLAY_STOP, 0,"");
		}
		if (isMadiePlaying) {
			speakNext = false;
			if (myPlayer != null) {
				myPlayer.release();
				myPlayer = null;
			}
			isMadiePlaying = false;
			callback.speakFinish(MSG_CODE.MSG_PLAY_STOP, 0,"");
		}
		
		if(isMp3Playing){
			speakNext = false;
			destoryMp3Audio();
			isMp3Playing = false;
			callback.speakFinish(MSG_CODE.MSG_PLAY_STOP, 0,"");
		}

		ttsDataList.clear();
		int count = 0;
		Log.d(TAG, "stop ttsThread:"+System.currentTimeMillis());
		while(isTtsThreadRunning){
			try {
				Thread.sleep(80);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			count ++;
		}
		Log.d(TAG, "stop ttsThread over:"+System.currentTimeMillis());
		Log.d(TAG, "ttsDataList size:"+ttsDataList.size()+isTtsThreadRunning+count);
		return true;
	}

	/**
	 * 播放是否空闲
	 * @return
	 */
	public static boolean isAudioFree() {
		Log.d(TAG, "isAudioFree:::isTtsPlaying:"+isTtsPlaying+
				";isMediaPlaying:"+isMadiePlaying+";isMp3Playing:"+isMp3Playing+System.currentTimeMillis());
//		return !(isTtsPlaying || isMadiePlaying || isMp3Playing);
		boolean isAudioFree = !(isTtsPlaying || isMadiePlaying || isMp3Playing);
		Log.d(TAG, "isAudioFree:"+isAudioFree+System.currentTimeMillis());
		return isAudioFree;
	}

	private static MediaPlayer myPlayer = new MediaPlayer();
	private static OnCompletionListener mediaCompletion = new OnCompletionListener() {

		@Override
		public void onCompletion(MediaPlayer mp) {
//			myPlayer.release();
//			myPlayer = null;
			myPlayer.reset();
			isMadiePlaying = false;
			callback.speakFinish(MSG_CODE.MSG_PLAY_DONE, 0,"");
		}
	};

	private static OnErrorListener errorListener = new OnErrorListener() {
		
		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {
			// TODO Auto-generated method stub
			myPlayer.release();
			myPlayer = null;
			isMadiePlaying = false;
			callback.speakFinish(MSG_CODE.MSG_PLAY_DONE, 0,"");
			return false;
		}
	};
	
	
	/**
	 * 播放媒体文件
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static boolean playMedia1(String fileName, String key) {
		final String fileNamePath = fileName;
		final String keyStr = key;
		if (isTtsPlaying || isMadiePlaying || isMp3Playing) {
			return false;
		}
		
		Thread mediaThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
//				playMediaPlayer(fileNamePath, keyStr);
			}
		});
		mediaThread.start();
		return true;
	}
	
	/**
	 * 播放媒体文件
	 * @param fileName
	 * @param key
	 * @return
	 */
	public static boolean playMedia(String fileName, String key) {
		Log.d(TAG, "playMedia:::isTtsPlaying:"+isTtsPlaying+";isMediaPlaying:"+isMadiePlaying
				+";isMp3Playing:"+isMp3Playing);
		if (isTtsPlaying || isMadiePlaying || isMp3Playing) {
			return false;
		}
		isMadiePlaying = true;
		broadcastKey = key;

		try {
			File file = new File(fileName);
			if (!file.exists()) {
				return false;
			}
			if (myPlayer == null) {
				if (D) {
					Log.d(TAG, "myPlayer is null" + System.currentTimeMillis());
				}
				myPlayer = new MediaPlayer();
//				FileInputStream fis = new FileInputStream(file);
//				myPlayer.setDataSource(fis.getFD());
				myPlayer.setOnCompletionListener(mediaCompletion);
				myPlayer.setOnErrorListener(errorListener);
				
			}else {
				myPlayer.reset();
				if (D) {
					Log.d(TAG, "myPlayer reset" + System.currentTimeMillis());
				}
			}
			FileInputStream fis = new FileInputStream(file);
			myPlayer.setDataSource(fis.getFD());
			myPlayer.prepare();
			
			if (D) {
				Log.d(TAG, "MediaTime1:"+System.currentTimeMillis());
			}
			myPlayer.start();
			callback.speakFinish(MSG_CODE.MSG_PLAY_START, 0,"");
			
			//音频持续时间
			int duration = myPlayer.getDuration();
			Thread.sleep(duration+1000);
			
			if (D) {
				Log.d(TAG, duration+".sleep:"+System.currentTimeMillis());
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	private static Mp3Decoder mp3Decoder = null;
	private static AudioTrack mp3Audio;
	
	/**
	 * 播放mp3文件
	 * @param filePath 文件路径
	 * @param key 广播key
	 * @return
	 */
	private static boolean playMp3(String filePath, String key){
		File mp3File = new File(filePath);
		if (!mp3File.exists()) {
			Log.d(TAG, "mp3File does not exist.");
			return false;
		}
		
	   	broadcastKey = key;
	   	destoryTtsAudio();
	   	if(mp3Decoder == null){
	   		mp3Decoder = new Mp3Decoder();  
	   	}
    	int ret = mp3Decoder.initAudioDecoder(filePath, 0);
    	if (ret == -1) {
    		Log.d(TAG, "Couldn't open file '" + filePath + "'"); 
    		return false;
		}
    	int sampleRate = mp3Decoder.getAudioSampleRate();
        // 声音文件一秒钟buffer的大小  
        int mAudioMinBufSize = AudioTrack.getMinBufferSize(sampleRate,  
                AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT); 
//        Log.d(TAG, "mAudioMinBufSize:"+mAudioMinBufSize);
    	if (mp3Audio == null) {
    		mp3Audio = new AudioTrack(mStreamType, sampleRate,
					AudioFormat.CHANNEL_CONFIGURATION_MONO,
					AudioFormat.ENCODING_PCM_16BIT, mAudioMinBufSize,
					AudioTrack.MODE_STREAM);
		}
    	isMp3Playing = true;
    	callback.speakFinish(MSG_CODE.MSG_PLAY_START, 0,"");
    	short[] audioBuffer = new short[1024 * 1024];
    	
    	Log.d(TAG, "jieduan:"+System.currentTimeMillis());
    	//处理声音被截断1s的问题
//	       if (mp3Audio != null && mp3Audio.getState() == AudioTrack.STATE_INITIALIZED) {
//	    	   mp3Audio.play();
//	    	   mp3Audio.write(audioBuffer, 0, 2*mAudioMinBufSize);
//	       }
	       Log.w("Qrob_Test", "playMp3:"+System.currentTimeMillis());
	    while(true){
	       int readBuff = mp3Decoder.getAudioBuffer(audioBuffer, mAudioMinBufSize);  
//	       Log.d("2>>>>>>>", readBuff+"*"+mAudioMinBufSize+"*"+System.currentTimeMillis());
	       if(readBuff == 0 || readBuff == -1 || mp3Audio == null ){
	    	   if (readBuff == -1) {
	    		   return false;
	    	   }
	    	   break;
	       }
	       if (mp3Audio != null && mp3Audio.getState() == AudioTrack.STATE_INITIALIZED) {
	    	   mp3Audio.play();
		       mp3Audio.write(audioBuffer, 0, mAudioMinBufSize); 
	       } else {
	    	   Log.d(TAG, "mp3Audio is null or not init.");
	    	   return false;
	       }
	    }
    	mp3Decoder.closeAudioFile();  
    	
    	try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	audioBuffer = null;
    	if (mp3Audio != null && mp3Audio.getState() == AudioTrack.STATE_INITIALIZED
    			&& mp3Audio.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
    		mp3Audio.stop();
		}
    	isMp3Playing = false;
    	callback.speakFinish(MSG_CODE.MSG_PLAY_FINISH, 0,"");
       return true;

	}
	
	/**
	 * 销毁播放mp3的AudioTrack对象 
	 */
	public static void destoryMp3Audio(){
		if (mp3Decoder != null) {
			mp3Decoder.closeAudioFile(); 
		}
		
//		if (mp3Audio != null ) {
//			if (mp3Audio.getState() == AudioTrack.STATE_INITIALIZED || AudioTrack.PLAYSTATE_PLAYING == mp3Audio.getPlayState()) {
//				mp3Audio.stop();
//			}
//			mp3Audio.release();
//			mp3Audio = null;
//		}
	}
	
	/**
	 * 销毁播放tts的AudioTrack对象
	 */
	public static void destoryTtsAudio() {
		if (mAudio != null ) {
			if (mAudio.getState() == AudioTrack.STATE_INITIALIZED && AudioTrack.PLAYSTATE_PLAYING == mAudio.getPlayState()) {
				mAudio.stop();
			}
			mAudio.release();
			mAudio = null;
		}
	}
	
	public static void destory(){
		destoryMp3Audio();
		destoryTtsAudio();
	}
	
	/**
	 * 处理声音被截断1s的问题
	 */
	public static void playFirst(){
		if (null == mAudio) {
			Log.e(TAG, "onJniOutData mAudio is null");
			mAudio = new AudioTrack(mStreamType, mSampleRate,
					AudioFormat.CHANNEL_CONFIGURATION_MONO,
					AudioFormat.ENCODING_PCM_16BIT, mBuffSize,
					AudioTrack.MODE_STREAM);
//			return;
		}
		short[] tempData = new short[1024*1024];
		if (mAudio.getState() == AudioTrack.STATE_INITIALIZED) {
			Log.w(TAG, "playFirst");
			mAudio.play();
			mAudio.write(tempData, 0, mBuffSize);
		}
	}
	
	private static boolean isFirst = false;
	
	/**
	 * For C call
	 */
	public static void onJniOutData(int len, byte[] data) {

		if (null == mAudio) {
			Log.e(TAG, "onJniOutData mAudio is null");
			mAudio = new AudioTrack(mStreamType, mSampleRate,
					AudioFormat.CHANNEL_CONFIGURATION_MONO,
					AudioFormat.ENCODING_PCM_16BIT, mBuffSize,
					AudioTrack.MODE_STREAM);
//			return;
		}
		if (mAudio.getState() != AudioTrack.STATE_INITIALIZED) {
			Log.e(TAG, "onJniOutData mAudio STATE_UNINITIALIZED");
			return;
		}
		if (isFirst) {
			isFirst = false;
			Log.w("Qrob_Test","Tts AudioTrack:"+System.currentTimeMillis());
		}
		
		try {
//			Log.d(TAG, "AudioTrack Time:"+System.currentTimeMillis());
			mAudio.play();
			mAudio.write(data, 0, len);
//			Log.e(TAG, System.currentTimeMillis()+" mAudio is playing,len:"+len+",data size:"+data.length);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}

	/**
	 * For C Watch Call back
	 * 
	 * @param nProcBegin
	 */
	public static void onJniWatchCB(int nProcBegin) {
		Log.d(TAG, "onJniWatchCB  process begin = " + nProcBegin);
	}
}