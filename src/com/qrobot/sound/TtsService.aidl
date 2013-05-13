package com.qrobot.sound;
import com.qrobot.sound.TtsData;

interface TtsService{
	/**
	 * 播放是否空闲
	 */
	boolean isAudioFree();

	/**
	 * 播放合成文本
	 */
	boolean playTts(in TtsData data);

	/**
	 * 停止播放
	 */
	boolean stopAudio();

	/**
	 * 播放本地媒体文件
	 */
	boolean playMedia(in String fileName, in String broadcastKey);

	/**
	 * 按照名字播放私有媒体文件
	 */
	boolean playPraviteMediaForName(in String name, in String broadcastKey); 

	/**
	 * 按照id播放私有媒体文件
	 */	
	boolean playPraviteMediaForId(in int id, in String broadcastKey); 
	
}