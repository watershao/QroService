package com.qrobot.sound;

import java.io.File;

public class VoiceResouce {
	public static final String[][] VOICE_IDS = {

	{"0924"}, {"0925"}, {"0926"}, {"0927"}, {"0928"}, {"0929"}, {"0930"},
			{"0931"}, {"0932"}, {"0934"}, {"0935"}, {"0936"}, {"0937"},
			{"0938", "0955"}, {"0939"}, {"0940"}, {"0941"}, {"0942"}, {"0943"},
			{"0944"}, {"0946"}, {"0947"}, {"0948"}, {"0949"}, {"0950"},
			{"0951"}, {"0952"}, {"0953"}, {"0954"}, {"0956"}, {"0957"},
			{"0958"}, {"0959"}, {"0960"}, {"0961"}, {"0962"}, {"0963"},
			{"0964"}, {"0965"}, {"0966"}, {"0967"}, {"0968"}, {"0969", "0970"},
			{"0971"}, {"0972"}, {"0973"}, {"0974"}, {"0975"}, {"0976"}};

	public static final String getVoiceId(int keyId) {
		String[] voiceIdArray = VOICE_IDS[keyId];
		if (voiceIdArray.length == 1) {
			return voiceIdArray[0];
		} else {
			int voiceId = (int) (Math.random() * voiceIdArray.length);
			return voiceIdArray[voiceId];
		}
	}

	public static final String getVoiceFileName(int keyId) {
		return getVoiceFileName(getVoiceId(keyId));
	}

	public static final String getVoiceFileName(String voiceId) {
		return getVoiceRoot() + "/lib" + voiceId + ".mp3";
	}

	private static final String CACHE_ROOT_PATH = "/sdcard/qrobot";
	private static final String VOICE_PATH = CACHE_ROOT_PATH + "/voice";

	public static final String getVoiceRoot() {
		mkdirs(VOICE_PATH);
		return VOICE_PATH;
	}

	private static final void mkdirs(String path) {
		File f = new File(path);
		f.mkdirs();
	}
}
