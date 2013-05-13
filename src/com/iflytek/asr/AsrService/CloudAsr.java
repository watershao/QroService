package com.iflytek.asr.AsrService;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import android.content.Context;
import android.util.Log;

import com.iflytek.resource.MscSetting;
import com.iflytek.speech.RecognizerListener;
import com.iflytek.speech.RecognizerResult;
import com.iflytek.speech.SpeechConfig.RATE;
import com.iflytek.speech.SpeechError;
import com.iflytek.speech.SpeechRecognizer;
import com.ritech.qrobot.MSG_CODE;

/**
 * 云识别
 * @author v_watershao
 *
 */
public class CloudAsr {

//	private final String APP_ID = "4fc5d3bb";
	private final String APP_ID = "4edd7f48";
	private static final String TAG = "CloudAsr:";
	
	private SpeechRecognizer recognizer;
	private String resultText = "";
	
	private static CloudRecoCallback cloudCallback;
	
	private static volatile boolean isCloudRecording = false;
	private static volatile int cloudRecoCount = 0;
	
	private static final boolean D = true;
	
	public static void setCloudCallback(CloudRecoCallback cloudCallback) {
		CloudAsr.cloudCallback = cloudCallback;
	}

	public CloudAsr(Context context){
		//如果反复调用此接口，只会创建一次。
		recognizer = SpeechRecognizer.createRecognizer(context,"appid="+APP_ID);
//		MscSetting.setLogSaved(false);
		MscSetting.setShowLog(false);
	}
	
	/**
	 * 开始进行云识别
	 * <p> 1，调用此接口后启动相应的识别服务，无效的参数会抛出错误信息。
	 * <p> 2，进行命令词识别时需要传入grammar参数，grammar通过UploadDialog接口上传命令词获得。
	 * 
	 * @param listener 识别监听回调对象
	 * @param ent 识别引擎选择，目前支持以下五种 ”sms”：普通文本转写 ；“poi”：地名搜索 ；
	 * 				”vsearch”：热词搜索； ”video”：视频音乐搜索； ”asr”：命令词识别
	 * @param params 附加参数列表，每项中间以逗号分隔，如在地图搜索时可指定搜索区域：”area=安徽省合肥市”，无附加参数传null
	 * @param grammar 自定义命令词识别(ent参数为asr时)需要传入语法 
	 */
	public void startCloudRec(RecognizerListener listener, String ent, 
										String params,String grammar){
		//在已经调用createRecognizer接口的情况下才会返回有效的对象，否则返回null
		recognizer = SpeechRecognizer.getRecognizer();
		if(recognizer != null) {
			recognizer.startListening(mRecoListener, null,null, grammar);
//			isCloudRecording = true;
//			cloudRecoCount = Asr.getLocalRecoCount();
		}
	}
	
	/**
	 * 停止录音，等待服务端返回结果。
	 */
	public void stopCloudListening() {
		recognizer = SpeechRecognizer.getRecognizer();
		if(recognizer != null) {
			recognizer.stopListening();
		}
	}
	
	/**
	 * 取消当前识别，停止录音并断开与服务端的连接
	 */
	public void cancelCloudRec(){
		recognizer = SpeechRecognizer.getRecognizer();
		if(recognizer != null) {
			Log.d(TAG, "recognizer cancel.");
			recognizer.cancel();
			isCloudRecording = false;
		}
	}
	
	/**
	 * 设置录音采样率
	 * 
	 * 调用此接口后在下次识别时生效。
     * <p> Android手机一般只支持8K和16K两种采样率，为了获得更好的识别效果，推荐使用16K。
	 * @param rate 录音采样率，支持rate8k、rate11k、rate16k、rate22k四种，默认为rate16k
	 */
	public void setSampleRate (RATE rate) {
		recognizer = SpeechRecognizer.getRecognizer();
		if(recognizer != null) {
			recognizer.setSampleRate(rate);
		}
	}
	
	/**
	 * 获取上传流量
	 * 
	 * <p>本次交互所产生的流量可以在回调接口的onEnd函数中调用，总流量在任意时刻调用均有效；
	 * @param isTotal true表示获取应用程序启动到当前的上传流量，false表示获取最后一次语音识别的上传流量
	 * @return 与服务器交互所产生的上传流量，单位：字节数（byte）
	 */
	public int getUpflowBytes(boolean isTotal){
		recognizer = SpeechRecognizer.getRecognizer();
		if(recognizer != null) {
			return recognizer.getUpflowBytes(isTotal);
		}
		return 0;
	}
	
	/**
	 * 获取下载流量
	 * @param isTotal true表示获取应用程序启动到当前的下载流量，false表示获取最后一次语音识别的下载流量
	 * @return 与服务器交互所产生的下载流量，单位：字节数（byte）；
	 */
	public int getDownflowBytes(boolean isTotal) {
		recognizer = SpeechRecognizer.getRecognizer();
		if(recognizer != null) {
			return recognizer.getDownflowBytes(isTotal);
		}
		return 0;
	}
	
	/**
	 * 字节流型音频数据识别
	 * 
	 * <p>调用此接口后启动相应的识别服务，无效的参数会抛出错误信息。 
	 * <p>进行命令词识别时需要传入grammar参数，grammar通过UploadDialog接口上传命令词获得。
	 * @param listener 识别监听回调对象
	 * @param buffer 字节流型声音数据
	 * @param ent  识别引擎选择，目前支持以下五种 ”sms”：普通文本转写 ；“poi”：地名搜索 ；
	 * 				”vsearch”：热词搜索； ”video”：视频音乐搜索； ”asr”：命令词识别
	 * @param params 附加参数列表，每项中间以逗号分隔，如在地图搜索时可指定搜索区域：”area=安徽省合肥市”，无附加参数传null
	 * @param grammar  自定义命令词识别(ent参数为asr时)需要传入语法
	 * @return
	 */
	public boolean recognizePcm(RecognizerListener listener, byte[] buffer, 
									String ent, String params, String grammar) {
		recognizer = SpeechRecognizer.getRecognizer();
		if(recognizer != null) {
			isCloudRecording = true;
			return recognizer.recognizePcm(mRecoListener, buffer, ent, params, grammar);
			
		}
		return false;
	}
	
	/**
	 * 并行队列型音频数据识别
	 * 
	 * <p>调用此接口后启动相应的识别服务，无效的参数会抛出错误信息。 
	 * <p>进行命令词识别时需要传入grammar参数，grammar通过UploadDialog接口上传命令词获得。
	 * @param listener 识别监听回调对象
	 * @param buffer 并行队列型声音数据，字节数组单元大小不超过4800byte
	 * @param ent  识别引擎选择，目前支持以下五种 ”sms”：普通文本转写 ；“poi”：地名搜索 ；
	 * 				”vsearch”：热词搜索； ”video”：视频音乐搜索； ”asr”：命令词识别
	 * @param params 附加参数列表，每项中间以逗号分隔，如在地图搜索时可指定搜索区域：”area=安徽省合肥市”，无附加参数传null
	 * @param grammar  自定义命令词识别(ent参数为asr时)需要传入语法
	 * @return
	 */
	public boolean recognizeQueuePcm(RecognizerListener listener, 
			ConcurrentLinkedQueue<byte[]> buffer , String ent, String params, String grammar) {
		recognizer = SpeechRecognizer.getRecognizer();
		if(recognizer != null) {
			isCloudRecording = true;
			return recognizer.recognizePcm(mRecoListener, buffer, ent, params, grammar);
		}
		return false;
	}
	
	/**
	 * 开始识别-分段传音频方式
	 * 
	 * <p>调用此接口后启动相应的识别服务，无效的参数会抛出错误信息。 
	 * <p>进行命令词识别时需要传入grammar参数，grammar通过UploadDialog接口上传命令词获得。
	 * @param listener 识别监听回调对象
	 * @param ent  识别引擎选择，目前支持以下五种 ”sms”：普通文本转写 ；“poi”：地名搜索 ；
	 * 				”vsearch”：热词搜索； ”video”：视频音乐搜索； ”asr”：命令词识别
	 * @param params 附加参数列表，每项中间以逗号分隔，如在地图搜索时可指定搜索区域：”area=安徽省合肥市”，无附加参数传null
	 * @param grammar  自定义命令词识别(ent参数为asr时)需要传入语法
	 * @return
	 */
	public boolean startAudioInput(RecognizerListener listener, String ent, 
												String params, String grammar){
		recognizer = SpeechRecognizer.getRecognizer();
		if(recognizer != null) {
			recognizer.startAudioInput(mRecoListener, ent, params, grammar);
			isCloudRecording = true;
			cloudRecoCount = Asr.getLocalRecoCount();
			return true;
		}
		return false;
	}
	
	/**
	 * 发送音频数据-分段传音频方式
	 * 
	 * <p>需要在调用startAudioInput之后调用本接口，否则返回false且不进行任何操作。
	 * <p>调用writeAudio需按照正常说话的方式来写入音频，16K采样率情况下每写入X字节的音频数据需要相隔X*20/640ms
	 * 再进行下一次写音频操作，8K采样率情况下每写入X字节的音频数据需要相隔X*20/320ms再进行下一次写音频操作。
	 * 
	 * @param buffer 二进制音频数据
	 * @return
	 */
	public boolean writeAudio(byte[] buffer){
		recognizer = SpeechRecognizer.getRecognizer();
		if(recognizer != null) {
			return recognizer.writeAudio(buffer);
		}
		return false;
	}
	
	/**
	 * 判断云识别录音是否空闲
	 * @return
	 */
	public boolean isCloudRecoFree(){
		return !isCloudRecording;
	}
	/**
	 * 识别回调接口
	 */
	private RecognizerListener mRecoListener = new RecognizerListener()
	{
		/**
		 * 识别结果回调
		 * 
		 * <p>SpeechRecognizer采用边录音边发送的方式，可能会多次返回结果，建议用户在此接
		 *	口中只保存结果内容，在onEnd回调中进行下一步的结果处理。
		 *
		 * <p>results 识别结果
		 * <p>isLast true表示最后一次结果，false表示结果未取完
		 * 
		 * <p>说明：识别结果（RecognizerResult）
		 * <p>成员名 String text（识别文本结果），int confidence（结果置信度），
		 * <p>ArrayList<HashMap<String,String>> semanteme 语义结果，由本次识别所选择服务定义
		 * 
		 * <p> 1、 自定义的命令词识别时，text表示转写结果，confidence表示置信度，取值范围0-100，
		 * <p> 2、 如果需要和上传的原词表进行比对，请使用semanteme中key为contact的字段。
		 * <p> 3、 定制化业务的搜索结果存放在semanteme中，由具体的业务类型进行约定。
		 */
		@Override
		public void onResults(ArrayList<RecognizerResult> results,boolean isLast) {
			String text = "";
			for(int i = 0; i < results.size(); i++)	{
				RecognizerResult result = results.get(i);
				text += result.text;
			}
			resultText += text;
			cloudCallback.recognitionCallback(MSG_CODE.MSG_CLOUD_RESULT, resultText);
			
			if(D){
				Log.d(TAG, "Cloud onResults time:"+System.currentTimeMillis());
			}
//			Log.d(TAG, "CloudResult:"+ resultText);
		}

		/**
		 * 识别结束回调
		 * 本次识别过程结束，已经断开和服务端之间的通信。
		 * 
		 * <p>error 请求成功返回null，否则返回错误对象，继承自Exception
		 */
		@Override
		public void onEnd(SpeechError error) {
			if(D){
				Log.w(TAG, "Cloud onEnd time:"+System.currentTimeMillis());
			}
			
			if(error != null) {	
				
				Log.w(TAG, "on End error:"+ error.getErrorCode()+","+error.getErrorDesc());
				
				cloudCallback.recognitionCallback(MSG_CODE.MSG_CLOUD_ERROR, error.getErrorCode()+"_"+error.getErrorDesc());
			} else {
				Log.w(TAG, "recognize over,onEnd result:"+resultText);
				cloudCallback.recognitionCallback(MSG_CODE.MSG_CLOUD_END, resultText);
			}
			isCloudRecording = false;
		}

		/**
		 * 录音启动回调,录音正常启动后回调此函数。
		 */
		@Override
		public void onBeginOfSpeech() {
			if(D){
				Log.d(TAG, "record begin:"+System.currentTimeMillis());
			}
			isCloudRecording = true;
			cloudCallback.recognitionCallback(MSG_CODE.MSG_CLOUD_START, resultText);
		}

		/**
		 * 录音数据回调
		 * 开发者可以使用volume值更新录音状态
		 * 
		 * <p>buff 录音数据
		 * <p>volume 录音音量值，范围0-10
		 */
		@Override
		public void onBufferReceived(byte[] buffer, int volume) {
			if(D){
				Log.d(TAG, "volume value:"+volume);
			}
			cloudCallback.recognitionCallback(MSG_CODE.MSG_CLOUD_RECORDING, resultText);
		}

		/**
		 * 录音自动停止回调
		 * 
		 * <p>SpeechRecognizer内集成了端点检测功能，当用户一定时间内不说话，默认为用户已
		 *	经不需要再录入语音，会自动调用此回调函数，并停止当前录音。(默认值为2s，可设置)
		 */
		@Override
		public void onEndOfSpeech() {
			if(D){
				Log.d(TAG, "record stoped endOfSpeech:"+System.currentTimeMillis());
			}
//			isCloudRecording = false;
			cloudCallback.recognitionCallback(MSG_CODE.MSG_CLOUD_STOP, resultText);
		}

		@Override
		public void onCancel() {
			if(D){
				Log.d(TAG, "recognizer canceled by user."+System.currentTimeMillis());
			}
			cloudCallback.recognitionCallback(MSG_CODE.MSG_CLOUD_CANCEL, resultText);
			isCloudRecording = false;
			
		}
	};
	
	/**
	 * 获取云识别开启的次数
	 * @return
	 */
	public static int getCloudRecoCount() {
		return cloudRecoCount;
	}
	
}
