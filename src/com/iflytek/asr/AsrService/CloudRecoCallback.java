package com.iflytek.asr.AsrService;

/**
 * 云识别回调接口
 * @author v_watershao
 *
 */
public interface CloudRecoCallback {

	/**
	 * 云识别回调
	 * @param resultCode 结果码，用于标记开始、识别中、结束、中断等状态
	 * @param result 封装的识别结果，RecognizerResult的list类型
	 */
//	public void recognitionCallback(int resultCode, ArrayList<RecognizerResult> result);
	
	/**
	 * 云识别回调
	 * @param resultCode 结果码，用于标记开始、识别中、结束、中断等状态
	 * @param result 封装的识别结果,文本类型
	 */
	public void recognitionCallback(int resultCode, String result);
}
