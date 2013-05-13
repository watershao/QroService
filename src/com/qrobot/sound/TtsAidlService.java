package com.qrobot.sound;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.iflytek.tts.TtsService.AudioData;
import com.iflytek.tts.TtsService.TtsCallback;
import com.ritech.qrobot.MSG_CODE;

public class TtsAidlService extends Service {

	private static final String tag = "TtsAidlService：";


	public static final int ROLE_XIAOYAN = TtsData.ROLE_XIAOYAN;
	public static final int ROLE_DONALD_DUCK = TtsData.ROLE_DONALD_DUCK;
	public static final int ROLE_XU_BABY = TtsData.ROLE_XU_BABY;
	public static final String TTS_IRF_PATH = "/sdcard/qrobot/voiceirf/Resource.irf";

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(tag, "onCreate");
		AudioManager am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		
	//	setVolumeControlStream(AudioManager.STREAM_MUSIC);
		AudioData.init(TTS_IRF_PATH);
		AudioData.setTtsCallback(tts);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(tag, "onDestroy");
		AudioData.stopAudio();
		AudioData.destory();
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

	private TtsCallback tts = new TtsCallback() {

		@Override
		public void speakFinish(int resultCode, int featureCode, String flag) {
			Log.d(tag, "speakFinish: resultCode = " + resultCode
					+ ", featureCode = " + featureCode+",flag="+flag);
			if (resultCode == MSG_CODE.MSG_PLAY_SPEAKING
					&& featureCode == MSG_CODE.NO_CALLBACK) {
				return;
			}
			Intent intent = new Intent(AudioData.getBroadcastKey());
			intent.putExtra(MSG_CODE.RESULT_CODE, resultCode);
			intent.putExtra(MSG_CODE.FEATURE_CODE, featureCode);
			intent.putExtra(MSG_CODE.FLAG_CODE, flag);

			TtsAidlService.this.sendBroadcast(intent);
		}
	};

	public TtsService.Stub stub = new TtsService.Stub() {

		@Override
		public boolean stopAudio() throws RemoteException {
			return AudioData.stopAudio();
		}

		@Override
		public boolean playTts(TtsData data) throws RemoteException {
			return AudioData.playTts(data);
		}

		@Override
		public boolean isAudioFree() throws RemoteException {
			return AudioData.isAudioFree();
		}

		@Override
		public boolean playMedia(String fileName, String broadcastKey)
				throws RemoteException {
			return AudioData.playMedia(fileName, broadcastKey);
		}

		@Override
		public boolean playPraviteMediaForName(String name, String broadcastKey)
				throws RemoteException {
			return AudioData.playMedia(VoiceResouce.getVoiceFileName(name),
					broadcastKey);
		}

		@Override
		public boolean playPraviteMediaForId(int id, String broadcastKey)
				throws RemoteException {
			return AudioData.playMedia(VoiceResouce.getVoiceFileName(id),
					broadcastKey);
		}

	};
}
