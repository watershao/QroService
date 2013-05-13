package com.qrobot.sound;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.iflytek.asr.AsrService.Asr;
import com.iflytek.asr.AsrService.CloudAsr;
import com.iflytek.asr.AsrService.CloudRecoCallback;
import com.iflytek.asr.AsrService.RecognitionCallback;
import com.iflytek.asr.AsrService.RecognitionResult;
import com.ritech.qrobot.MSG_CODE;

public class AsrAidlService extends Service {

	private static final String tag = "AsrAidlService:";

	public static final int ROLE_XIAOYAN = TtsData.ROLE_XIAOYAN;
	public static final int ROLE_DONALD_DUCK = TtsData.ROLE_DONALD_DUCK;
	public static final int ROLE_XU_BABY = TtsData.ROLE_XU_BABY;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(tag, "onCreate");
		Asr.init();
		Asr.setRecognitionCallback(talk);
		CloudAsr.setCloudCallback(cloud);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(tag, "onDestroy");
		Asr.destroyRecognize();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		Log.d(tag, "onBind");
		return stub;
	}

	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
		Log.d(tag, "onRebind");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d(tag, "onUnbind");
		return super.onUnbind(intent);
	}

	private RecognitionCallback talk = new RecognitionCallback() {

		/**
		 * 语音识别回调
		 * 
		 * @param resultCode 结果码，用于标记开始、识别中、结束、中断等状态
		 * @param result  封装的识别结果
		 */
		@Override
		public void recognitionFinish(int resultCode, RecognitionResult result) {
			Intent intent = new Intent(Asr.getBroadcastKey());
			intent.putExtra(MSG_CODE.MSG_ASR_TYPE, MSG_CODE.MSG_ASR_LOCAL);
			intent.putExtra(MSG_CODE.RESULT_CODE, resultCode);
			intent.putExtra(MSG_CODE.MSG_ASRCOUNT_LOCAL, Asr.getLocalRecoCount());
			
			if (result != null) {
				intent.putExtra(MSG_CODE.RECOGNITION_RESULT, result.getBundle());
			}

			if (isRestart && resultCode == MSG_CODE.MSG_STOP_RECORD) {
				isRestart = false;
				Asr.startRecognize(recoArg.key, recoArg.sceneName);
			}

			AsrAidlService.this.sendBroadcast(intent);
		}
	};

	private CloudRecoCallback cloud = new CloudRecoCallback() {
		
		@Override
		public void recognitionCallback(int resultCode, String result) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Asr.getBroadcastKey());
			intent.putExtra(MSG_CODE.MSG_ASR_TYPE, MSG_CODE.MSG_ASR_CLOUD);
			intent.putExtra(MSG_CODE.RESULT_CODE, resultCode);
			intent.putExtra(MSG_CODE.MSG_ASRCOUNT_CLOUD, CloudAsr.getCloudRecoCount());
			if (result != null) {
				intent.putExtra(MSG_CODE.RECOGNITION_RESULT, result);
			}
			
			AsrAidlService.this.sendBroadcast(intent);
		}
	};
	
	private static final int START_RECO = 1;
	private static final int STOP_RECO = 2;

	private static class StartRecoArgs {

		String key;
		String sceneName;

		public StartRecoArgs(String key, String sceneName) {
			this.key = key;
			this.sceneName = sceneName;
		}
	}

	private volatile boolean isRestart = false;
	private volatile StartRecoArgs recoArg;;
	//点击时间
	private static long timeClick = 0;
	//是否是停止识别
	private static boolean isStopRecognize = false;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			//防止不停触发识别造成的异常
			long current = System.currentTimeMillis();
			long timeGap = 0;
			if (timeClick == 0 || timeGap > 60000) {
				timeClick = current;
			}
			timeGap = current - timeClick;
//			Log.d("timeGap>>>>>", ">>>>>>>>>>>>>timeGap:"+timeGap);
			if (timeGap>0 && timeGap<1000) {
//				Log.d("timeGap:", "<<<:"+timeGap);
				return;
			} else {
				if (!isStopRecognize) {
					timeClick = current;
//					Log.d("time:", "<<<:"+timeClick);
				}else {
					isStopRecognize = false;
				}
			}
			
//			Log.d(">>>>>>", "clickGap<<:"+(System.currentTimeMillis()-current));
			
			switch (msg.what) {
				case START_RECO : {
					
					// if (!Asr.isFree()) {
					//	 Asr.stopRecognize();
					//}					 
					StartRecoArgs args = (StartRecoArgs) msg.obj;
					recoArg = args;
					isRestart = !Asr.startRecognize(args.key, args.sceneName);
					break;
				}
				case STOP_RECO : {
					isRestart = false;
					Asr.stopRecognize();
//					AsrRecord.stopRecord();
					break;
				}
			}
		}
	};

	public AsrService.Stub stub = new AsrService.Stub() {

		/**
		 * 设置用户反应超时时间
		 */
		@Override
		public void setResponseTimeout(int timeout) throws RemoteException {
			Asr.setResponseTimeout(timeout);
		}

		/**
		 * 设置灵敏度
		 */
		@Override
		public void setSensitivity(int mode) throws RemoteException {
			Asr.setSensitivity(mode);
		}

		/**
		 * 设置语法
		 */
		@Override
		public int changScenceFileString(String s) throws RemoteException {
			return Asr.changScenceFile(s);
		}

		/**
		 * 设置语法
		 */
		@Override
		public int changScenceFileByteArray(byte[] buffer)
				throws RemoteException {
			return Asr.changScenceFile(buffer);
		}

		/**
		 * 停止识别
		 */
		@Override
		public void stopRecognize() throws RemoteException {
			// Asr.stopRecognize();
			isStopRecognize = true;
			handler.sendMessage(handler.obtainMessage(STOP_RECO));
		}

		/**
		 * 开始识别
		 */
		@Override
		public boolean startRecognize(String key, String sceneName)
				throws RemoteException {
			handler.sendMessage(handler.obtainMessage(START_RECO,
					new StartRecoArgs(key, sceneName)));
			return true;
		}

		/**
		 * 判断识别模块是否空闲
		 */
		@Override
		public boolean isAsrFree() throws RemoteException {
			return Asr.isFree();
		}

		@Override
		public boolean saveRecord(boolean isSave) throws RemoteException {
			// TODO Auto-generated method stub
			return Asr.saveRecord(isSave);
		}

		@Override
		public boolean startCloudAsr() throws RemoteException {
			// TODO Auto-generated method stub
			return Asr.startCloudAsr(AsrAidlService.this);
		}

		@Override
		public void setSpeechTimeout(int milTimes) throws RemoteException {
			// TODO Auto-generated method stub
			Asr.setSpeechTimeout(milTimes);
		}

		@Override
		public int beginLexicon(String lexiconName, boolean isPersonName)
				throws RemoteException {
			// TODO Auto-generated method stub
			return Asr.beginLexicon(lexiconName, isPersonName);
		}

		@Override
		public int addLexiconItem(String word, int id) throws RemoteException {
			// TODO Auto-generated method stub
			return Asr.addLexiconItem(word, id);
		}

		@Override
		public int endLexicon() throws RemoteException {
			// TODO Auto-generated method stub
			return Asr.endLexicon();
		}

		@Override
		public int changScenceFilePath(String path) throws RemoteException {
			// TODO Auto-generated method stub
			return Asr.changScenceFilePath(path);
		}

		@Override
		public int startFileRecord(String audioFileName) throws RemoteException {
			// TODO Auto-generated method stub
			int ret = Asr.startFileRecord(audioFileName);
			return ret;
		}

		@Override
		public void stopRecord() throws RemoteException {
			// TODO Auto-generated method stub
			Asr.stopRecord();
		}

		@Override
		public int startFileAsr(String sceneName, String audioFileName,
				boolean openCloudAsr) throws RemoteException {
			// TODO Auto-generated method stub
			int ret = Asr.startFileAsr(AsrAidlService.this, sceneName, audioFileName, openCloudAsr);
			return 0;
		}
	};

}
