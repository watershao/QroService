package com.iflytek.asr.AsrService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.ritech.qrobot.MSG_CODE;

import android.content.Context;
import android.os.Process;
import android.util.Log;

public class Asr {

	private static final String tag = "Asr:";

	private static final int ES_PARAM_SENSITIVITY = 1;
	private static final int ES_PARAM_RESPONSETIMEOUT = 2;
	private static final int ES_PARAM_SPEECHTIMEOUT = 3;
	private static final int ES_PARAM_SPEECHNOTIFY = 4;
	private static final int ES_PARAM_AUDIODISCARD = 5;
	private static final int ES_PARAM_ENHANCEVAD = 6;
	private static final int ES_PARAM_DISABLEVAD = 7;

	private static final int SEN_SENSITIVE = 5;
	private static final int SEN_ACTIVE = 4;
	private static final int SEN_NORMAL = 3;
	private static final int SEN_PRECISE = 2;
	private static final int SEN_STRICT = 1;
	private static final int SEN_DEFAULT = 0;

	private static volatile int localRecoCount = 0;
	
	protected static final boolean D = true;
	/**
	 * 获取本地识别开启次数
	 * @return
	 */
	public static int getLocalRecoCount() {
		return localRecoCount;
	}

	/**
	 * localRecoCount的自增操作
	 * @return
	 */
	private synchronized static int localRecoCountInc(){
		if (localRecoCount > 10) {
			localRecoCount = 0;
		}
		return ++localRecoCount;
	}
	
	/**
	 * 初始化语音识别引擎
	 */
	public static final void init() {
		int code = Asr.JniCreate();
		Log.w(tag, "AsrInit Code="+String.valueOf(code));
	}

	/**
	 * 用户反应超时参数设置，预置为3000 毫秒
	 * @param milTimes 取值为 1000～5000，0为关闭超时单位:毫秒
	 */
	public static final void setResponseTimeout(int milTimes) {
		JniSetParam(ES_PARAM_RESPONSETIMEOUT, milTimes);
	}

	/**
	 * 语音超时参数设置，预置为4000 毫秒
	 * @param milTimes 取值为 1000～20000，0为关闭超时，单位:毫秒
	 */
	public static final void setSpeechTimeout(int milTimes) {
		JniSetParam(ES_PARAM_SPEECHTIMEOUT, milTimes);
		Log.d(tag, "speechtimeout:"+milTimes);
//		setSensitivity(3);
	}
	
	/**
	 * 设置敏感度
	 * @param mode 0-5 值越大敏感度越高
	 */
	public static final void setSensitivity(int mode) {
		JniSetParam(ES_PARAM_SENSITIVITY, mode);
		Log.d(tag, "sensitivity:"+mode);
	}

	/**
	 * 根据字符串创建相应的文法网络
	 * @param s
	 * @return 0,创建文法网络成功并存储;
	 * <p>3,参数错误；
	 * <p>5，创建grammar 失败，如单个文法中所有文法语句的所有文法节点
	 * <p>总体数目不得超过254；或纯英文版不可以使用内置数字串等;
	 * <p>7,内存不足;
	 * <p>8,资源无效，如资源采样率不对、与其他资源文件不匹配或资源文件已经被破坏等;
	 * <p>9,打开文件失败。通过读文件回调读取资源文件、词典或场景等文件失败;
	 * <p>11,错误的调用，状态非法;
	 * <p>12,文法描述文本格式错误
	 * <p>16,数据存储失败，可能是系统提供的可写持久存储介质满
	 */
	public static final int changScenceFile(String s) {
		int ret = 0;
		try {
			ret = changScenceFile(s.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 根据场景路径创建相应的文法网络
	 * @param path 文件路径
	 * @return 0,创建文法网络成功并存储;
	 * <p> -1,文件不存在
	 * <p>3,参数错误；
	 * <p>5，创建grammar 失败，如单个文法中所有文法语句的所有文法节点
	 * <p>总体数目不得超过254；或纯英文版不可以使用内置数字串等;
	 * <p>7,内存不足;
	 * <p>8,资源无效，如资源采样率不对、与其他资源文件不匹配或资源文件已经被破坏等;
	 * <p>9,打开文件失败。通过读文件回调读取资源文件、词典或场景等文件失败;
	 * <p>11,错误的调用，状态非法;
	 * <p>12,文法描述文本格式错误
	 * <p>16,数据存储失败，可能是系统提供的可写持久存储介质满
	 */
	public static final int changScenceFilePath(String path) {
//		path += "";
		Log.w(tag, "scene path:"+path);
		int ret = 0;
		File file = new File(path);
		if (!file.exists()) {
			return -1;
		}
		InputStream is;
		try {
			is = new FileInputStream(file);
			int size = is.available();
//			Log.d(tag, "sceneLength:"+file.length()+"io len:"+size);
			byte[] buffer = new byte[size+2];
			int count = 0;
			while (count < size) {
				count = is.read(buffer, count, size - count);
			}
			
			is.close();
			Log.w(tag, "scene buffer:"+buffer.length);
			ret = changScenceFile(buffer);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 分步调用模式创建文法网络的一个词典单元，开始
	 * <p>提供一种辅助的创建文法单元的接口，用户先调用EsrBeginLexicon，然后逐次调用EsrAddLexiconItem，最后调用EsrEndLexicon完成词典单元创建。
	 *	这套接口仅限于创建词典单元，不能够创建文法网络单元。支持创建空词典，即不调用 EsrAddLexiconItem。
	 * @param lexiconName 词典名称，name 字符串内容长度限制为1 至8 字符，字符串必须由英文字母和数字组成
	 * @param isPersonName 是否作为人名特殊处理
	 * @return  0,成功；3,参数错误;
	 * <p>11,错误的调用，状态非法;
	 * <p>17,词典单元名称非法;
	 */
	public static final int beginLexicon(String lexiconName, boolean isPersonName){
		return JniBeginLexicon(lexiconName, isPersonName);
	}
	
	/**
	 * 添加词条,如果多次加入相同 ID 和相同词条文本，则在建词典文法时只会保留一条。
	 * @param word 词条文本，UTF-16 字符串，大小端必须与应用平台相同。词条文本长度不应超过40 字符，若超过将进行截断，
	 * 					只处理前40 字符。
	 * @param id 本命令词用户指定 ID。ID 必须在0～2的32次方（不包括2的32次方）的范围内。
	 * @return 0，词条添加成功；3，参数错误；
	 * <p>7,WorkSpcae 内存不足，无法再进行添加（最后一条没有添加）
	 * <p>11,错误的调用，状态非法
	 */
	
	public static final int addLexiconItem(String word, int id){
		return JniAddLexiconItem(word, id);
	}
	
	/**
	 * 完成分步模式创建词典单元。
	 * @return 0,创建成功并存储;3,参数错误；
	 * <p>7,内存不足；
	 * <p>8,资源无效，如资源采样率不对、与其他资源文件不匹配或资源文件已经被破坏等
	 * <p>9,打开文件失败。通过读文件回调读取资源文件、词典或场景等文件失败
	 * <p>11,错误的调用，状态非法
	 * <p>16,数据存储失败，可能是系统提供的可写持久存储介质满
	 */
	public static final int endLexicon(){
		return JniEndLexicon();
	}
	
	/**
	 * 根据文件buffer创建相应的文法网络
	 * @param buffer
	 * @return 0,创建文法网络成功并存储;
	 * <p>3,参数错误；
	 * <p>5，创建grammar 失败，如单个文法中所有文法语句的所有文法节点
	 * <p>总体数目不得超过254；或纯英文版不可以使用内置数字串等;
	 * <p>7,内存不足;
	 * <p>8,资源无效，如资源采样率不对、与其他资源文件不匹配或资源文件已经被破坏等;
	 * <p>9,打开文件失败。通过读文件回调读取资源文件、词典或场景等文件失败;
	 * <p>11,错误的调用，状态非法;
	 * <p>12,文法描述文本格式错误
	 * <p>16,数据存储失败，可能是系统提供的可写持久存储介质满
	 */

	public static final int changScenceFile(byte[] buffer) {
		int code  = Asr.JniBuildGrammar(buffer, buffer.length);		
		Log.w("BuildGrammar ret=",String.valueOf(code));
		return code;
	}

	/**
	 * 停止识别
	 */
	public static final void stopRecognize() {
		Log.d(tag, "stopRecognize.");
		Asr.JniStop();
		isRunning = false;
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 销毁识别对象
	 */
	public static final void destroyRecognize() {
		Asr.JniDestroy();
		isRunning = false;
	}

	private static RecognitionCallback callback;

	public static void setRecognitionCallback(RecognitionCallback cb) {
		callback = cb;
	}

	private static volatile boolean isRunning = false;

	/**
	 * 识别是否空闲
	 * @return
	 */
	public static final boolean isFree() {
		return !isRunning;
	}

	public static final boolean startRecognize(String key, String sceneName) {
		Log.d(tag, "startRecognize: key = " + key + ", sceneName = "
				+ sceneName + ", isRunning = " + isRunning);
		
		isFileAsr =false;
		
		if (!isRunning) {
			isRunning = true;
			Asr.JniStart(sceneName);
			Asr.startRecoThread(key);
			return true;
		}
		return false;
	}

	private static String broadcastKey;

	public static String getBroadcastKey() {
		return broadcastKey;
	}

	/**
	 * 开启录音线程
	 */
	public synchronized static void startRecoThread(String key) {
		broadcastKey = key;
		class AsrRunThread implements Runnable {
			@Override
			public void run() {
				//设置录音线程为最高优先级
				Process.setThreadPriority(Process.THREAD_PRIORITY_AUDIO);
				JniRunTask();
			}
		}
		Thread asrRun = new Thread(new AsrRunThread());
		asrRun.setPriority(Thread.MAX_PRIORITY);
		asrRun.start();
	}

	/**
	 * 是否保存录音
	 */
	private static volatile boolean isSaveRecord = false;
	
	/**
	 * 是否保存录音到本地
	 * @param isSave
	 * @return
	 */
	public static boolean saveRecord(boolean isSave){
		isSaveRecord = isSave;
		return isSaveRecord;
	}
	
	/**
	 * 开启云识别
	 * @param context
	 * @return
	 */
	public static boolean startCloudAsr(Context context){
		return AsrRecord.startCloudAsr(context);
	}
	
	static long startTime = 0, curTime = 0;
	
	/**
	 * 开始录音，并保存
	 * @param audioFileName 保存的录音文件名称,默认为/sdcard/qrobot/record/voice.pcm
	 * @return
	 */
	public static int startFileRecord(final String audioFileName){
		int ret = FileAsr.startFileRecord(audioFileName);
		return ret;
	}
	
	/**
	 * 是否是本地录音文件识别
	 */
	private static volatile boolean isFileAsr = false;
	
	/**
	 * 开始录音文件识别，目前仅能使用pcm文件
	 * @param context 云识别用到的上下文
	 * @param sceneName 场景名称
	 * @param audioFileName 录音文件名称
	 * @param openCloudAsr 是否开启云识别
	 * @return
	 */
	public static int startFileAsr(Context context, String sceneName,
									String audioFileName, boolean openCloudAsr){
		isFileAsr = true;
		int ret = FileAsr.startFileAsr(context, sceneName, audioFileName, openCloudAsr);
		return ret;
	}
	
	/**
	 * 设置本地文件识别的状态
	 * @param isFileAsr
	 */
	public static void setFileAsrState(boolean isFileAsr){
		Asr.isFileAsr = isFileAsr;
	}
	
	/**
	 * 停止录音
	 */
	public static void stopRecord(){
		AsrRecord.stopRecord();
	}
	
	/**
	 * 设置asr的运行状态
	 * @param isRunning
	 */
	public static void setAsrRunningState(boolean isRunning) {

		Asr.isRunning = isRunning;
	}
	
	/**
	 * 识别回调函数
	 */
	public static int onCallMessage(int msgType) {

		Log.d(tag, "onCallMessage: " + Integer.toHexString(msgType) + ", " + msgType);

		switch (msgType) {
			case MSG_CODE.MSG_START_RECORD :
				if(D){
					startTime = System.currentTimeMillis();
					curTime = 0;
					Log.w("Qrob_Test", "MSG_START_RECORD:"+curTime+"#"+startTime);
				}
				
				if (isFileAsr) {
					break;
				}
				
				AsrRecord.saveRecord(isSaveRecord);
				localRecoCountInc();
				AsrRecord.startRecord();
				break;
			case MSG_CODE.MSG_STOP_RECORD :
				if(D){
					curTime = System.currentTimeMillis() - startTime;
					Log.w("Qrob_Test", "MSG_STOP_RECORD:"+curTime+"*"+System.currentTimeMillis());
				}
				isRunning = false;
				
				if (isFileAsr) {
					break;
				}
				
				AsrRecord.stopRecord();
				break;
				
			case MSG_CODE.MSG_SPEECH_START:
				if(D){
					curTime = System.currentTimeMillis() - startTime;
					Log.w("Qrob_Test","MSG_SPEECH_START："+curTime+"#"+System.currentTimeMillis());
					
				}
				break;
			case MSG_CODE.MSG_SPEECH_END:
				
				if(D){
					curTime = System.currentTimeMillis() - startTime;
					Log.w("Qrob_Test","MSG_SPEECH_END："+curTime+"#"+System.currentTimeMillis());
				}
//				Log.d(tag,"MSG_SPEECH_END："+curTime+"#"+System.currentTimeMillis());
				break;
			case MSG_CODE.MSG_SPEECH_FLUSH_END:
				
				if(D){
					curTime = System.currentTimeMillis() - startTime;
					Log.d(tag,"MSG_SPEECH_FLUSH_END："+curTime+"#"+System.currentTimeMillis());
				}
				break;				
			case MSG_CODE.MSG_SPEECH_NO_DETECT:
				
				if(D){
					curTime = System.currentTimeMillis() - startTime;
					Log.d(tag,"MSG_SPEECH_NO_DETECT："+curTime);
				}
				break;
			case MSG_CODE.MSG_RESPONSE_TIMEOUT:
				
				if(D){
					curTime = System.currentTimeMillis() - startTime;
					Log.w("Qrob_Test","MSG_RESPONSE_TIMEOUT："+curTime+"#"+System.currentTimeMillis());
				}
				break;
			case MSG_CODE.MSG_SPEECH_TIMEOUT:
				
				if(D){
					curTime = System.currentTimeMillis() - startTime;
					Log.w("Qrob_Test","MSG_SPEECH_TIMEOUT："+curTime+"#"+System.currentTimeMillis());
				}
				break;
			case MSG_CODE.MSG_END_BY_USER:
				
				if(D){
					curTime = System.currentTimeMillis() - startTime;
					Log.d(tag,"MSG_END_BY_USER："+curTime);
				}
				break;	
			case MSG_CODE.MSG_HAVE_RESULT:	
				if(D){
					curTime = System.currentTimeMillis() - startTime;
					Log.d(tag,curTime+"MSG_HAVE_RESULT");
				}
				break;				
		}

		callback.recognitionFinish(msgType, null);
		return 0;
	}

	/**
	 * 识别回调结果
	 * @return
	 */
	public static int onCallResult() {
		Log.d(tag, "onCallResult");

		int iResCount = 0;
		int iSlotCount = 0;
		int iItemCount = 0;
		//1. get result 取总结果数
		iResCount = JniGetResCount();
		Log.w(tag, "onCallResult: " + iResCount);
		for (int iRes = 0; iRes < iResCount; iRes++) {
			//1.1  Get result count
			int sentenceId = JniGetSentenceId(iRes);
			iSlotCount = JniGetSlotNumber(iRes);
			int confidence = JniGetConfidence(iRes);

			RecognitionResult rs = new RecognitionResult(sentenceId,
					confidence, iSlotCount);

			// 1.2 Get slot
			for (int iSlot = 0; iSlot < iSlotCount; iSlot++) {
				iItemCount = JniGetItemNumber(iRes, iSlot);
				if (iItemCount <= 0) {
					continue;
				}
				int itemIds[] = new int[iItemCount];
				String itemTexts[] = new String[iItemCount];

				for (int iItem = 0; iItem < iItemCount; iItem++) {
					itemIds[iItem] = JniGetItemId(iRes, iSlot, iItem);
					itemTexts[iItem] = JniGetItemText(iRes, iSlot, iItem);
					Log.w(tag, "encoding:"+StringEncoding.getEncoding(
							itemTexts[iItem])+">>>>itemId:"+itemIds[iItem]+
							",itemTexts:"+itemTexts[iItem]);
					if (null == itemTexts[iItem]) {
						itemTexts[iItem] = "";
					}
				}
				rs.addSlot(iItemCount, itemIds, itemTexts);
			}
			if(D){
				Log.w("Qrob_Test", System.currentTimeMillis()+".Local result time:"+(System.currentTimeMillis()-startTime));
				Log.d(tag, "result:"+rs.mSlotList.get(0).mItemTexts[0]);
			}
			Log.d(tag, System.currentTimeMillis()+".Local result time:"+(System.currentTimeMillis()-startTime));
			callback.recognitionFinish(MSG_CODE.MSG_HAVE_RESULT, rs);
			return 0;
		}
		callback.recognitionFinish(MSG_CODE.MSG_HAVE_RESULT, null);
		return 0;
	}

	/**
	 * Java native interface code
	 */
	static {
		System.loadLibrary("Aitalk");
	}

	public native static int JniGetVersion();

	public native static int JniCreate();

	public native static int JniDestroy();

	public native static int JniStart(String sceneName);

	public native static int JniStop();

	public native static int JniRunTask();

	/**
	 * 取总结果数
	 * @return
	 */
	public native static int JniGetResCount();

	public native static int JniGetSentenceId(int resIndex);

	public native static int JniGetConfidence(int resIndex);

	public native static int JniGetSlotNumber(int resIndex);

	public native static int JniGetItemNumber(int resIndex, int slotIndex);

	public native static int JniGetItemId(int resIndex, int slotIndex,
			int itemIdex);

	public native static String JniGetItemText(int resIndex, int slotIndex,
			int itemIdex);

	public native static int JniAppendData(byte[] data, int length);

	public native static int JniBuildGrammar(byte[] xmlText, int length);

	public native static int JniAddLexiconItem(String word, int id);

	public native static int JniBeginLexicon(String lexiconName,
			boolean isPersonName);

	public native static int JniEndLexicon();

	public native static int JniMakeVoiceTag(String lexiconName, String item,
			byte[] data, int dataLen);

	public native static int JniSetParam(int paramId, int paramValue);

}
