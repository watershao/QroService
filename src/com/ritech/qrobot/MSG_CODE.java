package com.ritech.qrobot;
/** 
 * 类说明：   Q3机器人应用中使用的消息代码ID
 * @author  Wu Langlang 
 * @date    2012-7-16
 * @version 1.0
 */
public class MSG_CODE {
	
	public static final String GRAMMAR_NAME = "chat";
	/*
	 * 语音识别标识
	 */
	public static final String RESULT_CODE = "RESULT_CODE";
	public static final String FEATURE_CODE = "FEATURE_CODE";
	public static final String FLAG_CODE = "FLAG_CODE";
	public static final String RECOGNITION_RESULT = "RECOGNITION_RESULT";
	
	//本地计数和云端计数
	public static final String MSG_ASRCOUNT_LOCAL = "ASR_LOCAL";
	public static final String MSG_ASRCOUNT_CLOUD = "ASR_CLOUD";

	public static final int MSG_START_RECORD = 0x310;
	public static final int MSG_STOP_RECORD = 0x311;
	public static final int MSG_SPEECH_START = 0x401;
	public static final int MSG_SPEECH_END = 0x402;
	public static final int MSG_SPEECH_FLUSH_END = 0x403;
	public static final int MSG_SPEECH_NO_DETECT = 0x40f;
	public static final int MSG_RESPONSE_TIMEOUT = 0x410;
	public static final int MSG_SPEECH_TIMEOUT = 0x411;
	public static final int MSG_END_BY_USER = 0x412;
	
	public static final int MSG_HAVE_RESULT = 0x500;  //改为 MSG_LOCALE_RESULT = 0x500
   
	/*云端识别结果ID*/
	public static final int MSG_CLOUND_RESULT = 0x501;// 消息代码可改
	
	//云识别标识
	public static final int MSG_CLOUD_START = 10000;
	public static final int MSG_CLOUD_RECORDING = 10001;
	public static final int MSG_CLOUD_RESULT = 10002;
	public static final int MSG_CLOUD_STOP = 10003;
	public static final int MSG_CLOUD_END = 10004;
	public static final int MSG_CLOUD_CANCEL = 10005;
	public static final int MSG_CLOUD_ERROR = 10006;
	
	//语音识别类型
	public static final String MSG_ASR_TYPE = "ASR_TYPE";
	public static final int MSG_ASR_LOCAL = 0;
	public static final int MSG_ASR_CLOUD = 1;
	
	/**
	 * 语音合成标识
	 */
	public static final int MSG_PLAY_START = 0;//RESULT_CODE_START = 0; 
	public static final int MSG_PLAY_SPEAKING = 1;//RESULT_CODE_SPEAKING = 1;
	public static final int MSG_PLAY_DONE = 2;//RESULT_CODE_DONE = 2;
	public static final int MSG_PLAY_STOP = 3;//RESULT_CODE_STOP = 3;
	public static final int MSG_PLAY_RECORD=4;
	public static final int MSG_PLAY_FINISH = 5;
	public static final int NO_CALLBACK = 0;
	
	/**
	 * 语音合成角色标识
	 */
	public static final int ROLE_XIAOYAN = 0;
	public static final int ROLE_DONALD_DUCK = 1;
	public static final int ROLE_XU_BABY = 2;
	
	/*
	 * 触摸驱动标识
	 */
	public static final int TOUCH_HEAD_ON = 1;
	public static final int TOUCH_HEAD_OFF = 2;
	public static final int TOUCH_MOUTH_ON = 4;
	public static final int TOUCH_MOUTH_OFF = 8;
	public static final int TOUCH_HEAD_LONG = 16;
	
	/*
	 * Text Command命令标志
	*/
	public static final int MSG_NORMAL_INITMOT = 0;  //初始化完成标志
	public static final int MSG_NORMAL_TEXTCMD = 1;  //
	public static final int MSG_NORMAL_ROBOTID = 2;  //机器人ID
}
