package com.iflytek.asr.AsrService;

public interface RecognitionCallback {

	/**
	 * 语音识别回调
	 * @param resultCode 结果码，用于标记开始、识别中、结束、中断等状态，详见讯飞文档
	 * @param result 封装的识别结果
	 */
	public void recognitionFinish(int resultCode, RecognitionResult result);
}
