package com.qrobot.motion;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.util.Log;

public class MotionScript {

	private ScriptParse eyeScript = null;
	private ScriptParse actionScript = null;

	public MotionScript (){
	}
	
	/**
	 * 设置脚本解析的文本内容
	 * @param parentPath 脚本源文件的父路径
	 * @param resource 脚本内容
	 * @param type 脚本类型，0为运动脚本类型，1为眼睛显示脚本类型
	 */
	public void setResourceString(String parentPath, String resource, int type) {
		if(type == 0)
			motionScriptParse(parentPath, resource);
		else
			eyeScriptParse(parentPath, resource);
	}
	
	/**
	 * 眼睛显示脚本解析
	 * @param parentPath
	 * @param resource
	 */
	private void eyeScriptParse(String parentPath, String resource){
		if(eyeScript != null && eyeScript.isRun())
			return;
		eyeScript = new ScriptParse(parentPath, resource);
		eyeScript.start();
	}
	
	/**
	 * 运动脚本解析
	 * @param parentPath
	 * @param resource
	 */
	private void motionScriptParse(String parentPath, String resource){
		if(actionScript != null && actionScript.isRun())
			return;
		actionScript = new ScriptParse(parentPath, resource);
		actionScript.start();
	}
	

	/**
	 * 设置脚本源文件的二进制流
	 * @param parentPath 脚本源文件的父路径
	 * @param array 脚本源文件的二进制流
	 * @param type 脚本类型，0为运动脚本类型，1为眼睛显示脚本类型
	 */
	public void setResourceByteArray(String parentPath, byte[] array, int type) {
		setResourceString(parentPath, new String(array), type);
	}

	/**
	 * 设置脚本文件路径
	 * @param path 脚本文件路径
	 * @param type 脚本类型，0为运动脚本类型，1为眼睛显示脚本类型
	 */
	public void setScriptPath(String path, int type) {
		// Sport.this.path = path;
//		path = "\\sdcard\\qrobot\\motion\\statechange\\head_reset.txt";
		Log.w("MotionScript:path:", path);
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		String parentPath = file.getParent();
		Log.d("MotionScript:parentPath:", parentPath);
			FileInputStream fis;
			try {
				fis = new FileInputStream(file);
				int length = (int) file.length();
				byte[] array = new byte[length];
				ByteArrayOutputStream baos = new ByteArrayOutputStream(length);
				int count;
				while ((count = fis.read(array)) != -1) {
					baos.write(array, 0, count);
				}
				setResourceByteArray(parentPath, baos.toByteArray(), type);
				baos.close();
				fis.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	/**
	 * 停止脚本解析
	 * @param type 0,为运动脚本。1，为眼睛脚本。2为所有脚本
	 */
	public void stopScriptParse(int type) {
		switch (type) {
		case 0:
			if(actionScript != null)
				actionScript.stop();
			
			break;

		case 1:
			if(eyeScript != null)
				eyeScript.stop();
			
			break;
		case 2:	
		default:
			if(eyeScript != null)
				eyeScript.stop();
			if(actionScript != null)
				actionScript.stop();
			
			break;
		}
	}

	/**
	 * 判断脚本解析是否正在进行
	 * @return
	 */
	public boolean isScriptRun() {
		if((eyeScript != null && eyeScript.isRun()) || (actionScript != null && actionScript.isRun()))
			return true;
		return false;
	}
	
	/**
	 * 判断眼睛脚本解析是否正在进行
	 * @return
	 */
	public boolean isEyeScriptRun(){
		if(eyeScript != null && eyeScript.isRun())
			return true;
		return false;
	}
	
	/**
	 * 判断运动脚本解析是否正在进行
	 * @return
	 */
	public boolean isMotionScriptRun(){
		if(actionScript != null && actionScript.isRun())
			return true;
		return false;
	}
}
