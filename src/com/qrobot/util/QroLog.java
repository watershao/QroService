package com.qrobot.util;

import android.util.Log;

/**
 * 日志文件控制
 * @author water
 *
 */
public class QroLog {

	public static void LogD(String tag, String msg){
		Log.d(tag, msg);
	}
	
	public static void LogE(String tag, String msg){
		Log.e(tag, msg);
	}
	
	public static void LogW(String tag, String msg){
		Log.w(tag, msg);
	}
	
	public static void LogI(String tag, String msg){
		Log.i(tag, msg);
	}
	
	public static void LogV(String tag, String msg){
		Log.v(tag, msg);
	}
}
