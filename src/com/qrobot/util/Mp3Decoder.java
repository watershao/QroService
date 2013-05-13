package com.qrobot.util;

public class Mp3Decoder {

	public Mp3Decoder() {

	}

	/**
	 * 初始化音频解码器
	 * @param file mp3文件路径
	 * @param StartAddr 文件起始地址
	 * @return
	 */
	public native int initAudioDecoder(String file,int startAddr);

	/**
	 * 获取音频buffer
	 * @param audioBuffer 存储音频流的buffer
	 * @param numSamples （采样率）声音文件一秒钟buffer的大小 
	 * @return
	 */
	public native int getAudioBuffer(short[] audioBuffer, int numSamples);

	/**
	 * 关闭音频文件和解码器
	 */
	public native void closeAudioFile();

	/**
	 * 获取声音采样率
	 * @return
	 */
	public native int getAudioSampleRate();

    static {  
        System.loadLibrary("mad");  
    } 
}
