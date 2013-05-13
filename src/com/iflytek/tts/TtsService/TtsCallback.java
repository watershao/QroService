package com.iflytek.tts.TtsService;

public interface TtsCallback {

	public void speakFinish(int resultCode, int featureCode, String flag);
}
