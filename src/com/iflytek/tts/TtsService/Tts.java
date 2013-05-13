package com.iflytek.tts.TtsService;

public final class Tts {

	static {
		System.loadLibrary("Aisound");
	}

	static native int JniGetVersion();
	static native int JniCreate(String resFilename);
	static native int JniDestory();
	static native int JniStop();
	static native int JniSpeak(String text);
	static native int JniSetParam(int paramId, int value);
	static native int JniGetParam(int paramId);
	static native int JniIsPlaying();
	static native boolean JniIsCreated();
}
