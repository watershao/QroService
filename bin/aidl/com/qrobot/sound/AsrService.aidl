package com.qrobot.sound;
import com.qrobot.sound.TtsData;

interface AsrService{
	/**
	 * 用户反应超时参数设置，预置为3000 毫秒
	 * @param milTimes 取值为 1000～5000，0为关闭超时单位:毫秒
	 */	
	void setResponseTimeout(in int timeout);
	
	/**
	 * 语音超时参数设置，预置为4000 毫秒
	 * @param milTimes 取值为 1000～20000，0为关闭超时，单位:毫秒
	 */
	void setSpeechTimeout(in int milTimes);

	/**
	 * 设置敏感度
	 * @param mode 0-5 值越大敏感度越高
	 */
	void setSensitivity(in int mode);

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
	int changScenceFileString(in String s);
	
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
	int changScenceFileByteArray(in byte[] buffer);


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
	int changScenceFilePath(String path);
	
	
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
	int beginLexicon(String lexiconName, boolean isPersonName);
	
	/**
	 * 添加词条,如果多次加入相同 ID 和相同词条文本，则在建词典文法时只会保留一条。
	 * @param word 词条文本，UTF-16 字符串，大小端必须与应用平台相同。词条文本长度不应超过40 字符，若超过将进行截断，
	 * 					只处理前40 字符。
	 * @param id 本命令词用户指定 ID。ID 必须在0～2的32次方（不包括2的32次方）的范围内。
	 * @return 0，词条添加成功；3，参数错误；
	 * <p>7,WorkSpcae 内存不足，无法再进行添加（最后一条没有添加）
	 * <p>11,错误的调用，状态非法
	 */
	
	int addLexiconItem(String word, int id);	
	
	/**
	 * 完成分步模式创建词典单元。
	 * @return 0,创建成功并存储;3,参数错误；
	 * <p>7,内存不足；
	 * <p>8,资源无效，如资源采样率不对、与其他资源文件不匹配或资源文件已经被破坏等
	 * <p>9,打开文件失败。通过读文件回调读取资源文件、词典或场景等文件失败
	 * <p>11,错误的调用，状态非法
	 * <p>16,数据存储失败，可能是系统提供的可写持久存储介质满
	 */
	int endLexicon();	
	
	/**
	 * 停止识别
	 */	
	void stopRecognize();

	/**
	 * 开始识别
	 */	
	boolean startRecognize(in String key, in String sceneName);

	/**
	 * 判断识别模块是否空闲
	 */	
	boolean isAsrFree();
	
	
	/**
	 * 是否保存录音
	 */
	boolean saveRecord(in boolean isSave);
	
	/**
	 * 开启云识别
	 */
	boolean startCloudAsr();
	
	/**
	 * 开始录音，并保存
	 * @param audioFileName 保存的录音文件名称,默认为/mnt/sdcard/qrobot/record/voice.pcm
	 * @return
	 */
	int startFileRecord(in String audioFileName);
	
	/**
	 * 停止录音
	 */
	void stopRecord();
	
	/**
	 * 开始录音文件识别，目前仅能使用pcm文件
	 * @param sceneName 场景文件名称
	 * @param audioFileName 录音文件名称
	 * @param openCloudAsr 是否开启云识别
	 * @return
	 */
	int startFileAsr(in String sceneName,in String audioFileName,in boolean openCloudAsr);
	
}