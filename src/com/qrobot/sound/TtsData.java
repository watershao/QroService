package com.qrobot.sound;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class TtsData implements Parcelable {

	public static final int NO_CALLBACK = 0;
	private static final String CLEAR_VALUE = "[d]";
	private static final String[] ROLES = new String[]{"[m3]", "[m54]", "[m55]"};

	public static final int ROLE_XIAOYAN = 0;
	public static final int ROLE_DONALD_DUCK = 1;
	public static final int ROLE_XU_BABY = 2;

	private String broadcastKey;
	private List<TtsNode> list;

	public TtsData(String broadcastKey, boolean isClear) {
		this.broadcastKey = broadcastKey;
		list = new ArrayList<TtsNode>();
		if (isClear) {
			list.add(new Clear());
		}
	}

	public TtsData(Parcel source) {
		readFromParcel(source);
	}

	/**
	 * 清空TtsData数据
	 */
	public void clearData(){
		list.clear();
	}
	
	/**
	 * 添加文本资源
	 * @param text
	 * @return
	 */
	public boolean addText(String text) {
		return list.add(new TtsNode(text));
	}

	/**
	 * 添加文本资源和回调码
	 * @param text
	 * @param callbackCode
	 * @return
	 */
	public boolean addText(String text, int callbackCode) {
		return list.add(new TtsNode(text, callbackCode));
	}

	/**
	 * 添加文本资源，回调码，标识
	 * @param text
	 * @param callbackCode
	 * @param flag
	 * @return
	 */
	public boolean addText(String text, int callbackCode, String flag) {
		return list.add(new TtsNode(text, callbackCode,flag));
	}
	
	/**
	 * 添加本地媒体资源
	 * @param filePath
	 * @param callbackCode
	 * @return
	 */
	public boolean addMedia(String filePath, int callbackCode) {
		return addText(filePath,callbackCode,1);
	}

	/**
	 * 添加本地媒体资源
	 * @param filePath
	 * @param callbackCode
	 * @param flag
	 * @return
	 */
	public boolean addMedia(String filePath, int callbackCode, String flag) {
		return addText(filePath,callbackCode,1,flag);
	}	
	/**
	 * 添加合成资源
	 * resourceId为0，添加文本资源，为1添加本地媒体资源,开启录音资源为2，添加播放记忆点为4
	 * @param text
	 * @param callbackCode
	 * @param resourceId
	 * @return
	 */
	public boolean addText(String text, int callbackCode, int resourceId) {
		return list.add(new TtsNode(text, callbackCode, resourceId));
	}

	/**
	 * 添加合成资源
	 * resourceId为0，添加文本资源，为1添加本地媒体资源,开启录音资源为2，添加播放记忆点为4
	 * @param text
	 * @param callbackCode
	 * @param resourceId
	 * @param flag
	 * @return
	 */
	public boolean addText(String text, int callbackCode, int resourceId, String flag) {
		return list.add(new TtsNode(text, callbackCode, resourceId, flag));
	}	
	/**
	 * 开启录音功能
	 * @param callbackCode
	 * @param resourceId 文本资源为0，本地媒体文件为1,开启录音资源为2，添加播放记忆点为4
	 * @return
	 */
	public boolean addRecord(int callbackCode, int resourceId){
		return addText("", callbackCode, resourceId);
	}

	/**
	 * 添加播放记忆点
	 * @return
	 */
	public boolean addAppend(){
		return addText("", 0, 4);
	}
	
	/**
	 * 设置静音时间
	 * @param value 单位：ms
	 * @return
	 */
	public boolean setWaitTime(int value) {
		if (value > 0) {
			return list.add(new WaitTime(value));
		}
		return false;
	}

	/**
	 * 设置语速
	 * @param value 0~10
	 * @return
	 */
	public boolean setSpeed(int value) {
		if (value >= 0 && value <= 10) {
			return list.add(new Speed(value));
		}
		return false;
	}

	/**
	 * 设置音量
	 * @param value 0-10
	 * @return
	 */
	public boolean setVolume(int value) {
		if (value >= 0 && value <= 10) {
			return list.add(new Volume(value));
		}
		return false;
	}

	/**
	 * 设置播音员 id
	 * @param id
	 * @return
	 */
	public boolean setRole(int id) {
		if (id >= 0 && id < ROLES.length) {
			return list.add(new Role(id));
		}
		return false;
	}

	/**
	 * 设置播音员
	 * @param value
	 * @return
	 */
	public boolean setRole(String value) {
		return list.add(new Role(value));
	}

	public String getBroadcastKey() {
		return broadcastKey;
	}

	public int getListSize() {
		return list.size();
	}

	public String getText(int location) {
		return list.get(location).getText();
	}

	public int getCallbackCode(int location) {
		return list.get(location).getCallbackCode();
	}

	public int getResourceId(int location) {
		return list.get(location).getResourceId();
	}

	public String getFlag(int location) {
		return list.get(location).getFlag();
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(broadcastKey);
		dest.writeInt(list.size());
		for (TtsNode node : list) {
			dest.writeString(node.getText());
			dest.writeInt(node.getCallbackCode());
			dest.writeInt(node.getResourceId());
			dest.writeString(node.getFlag());
		}
	}

	public void readFromParcel(Parcel source) {
		broadcastKey = source.readString();
		int count = source.readInt();
		list = new ArrayList<TtsData.TtsNode>(count);
		for (int i = 0; i < count; i++) {
			String text = source.readString();
			int callbackCode = source.readInt();
			int resourceId = source.readInt();
			String flag = source.readString();
			list.add(new TtsNode(text, callbackCode,resourceId,flag));
		}
	}

	public static final Parcelable.Creator<TtsData> CREATOR = new Parcelable.Creator<TtsData>() {

		@Override
		public TtsData createFromParcel(Parcel source) {
			return new TtsData(source);
		}

		@Override
		public TtsData[] newArray(int size) {
			return new TtsData[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	private class TtsNode {

		String text;
		int callbackCode = NO_CALLBACK;
		/**
		 * 资源id，文本资源为0，本地媒体文件为1,开启录音资源为2，添加播放记忆点为4
		 */
		int resourceId = 0;
		String flag;

		
		TtsNode(String text) {
			this.text = text;
		}

		TtsNode(String text, int callbackCode) {
			this.text =text;
			this.callbackCode = callbackCode;
		}
		
		TtsNode(String text, int callbackCode, int resourceId) {
			this.text = text;
			this.callbackCode = callbackCode;
			this.resourceId = resourceId;
		}
		
		TtsNode(String text, int callbackCode,String flag) {
			this.text =text;
			this.callbackCode = callbackCode;
			this.flag = flag;
		}
		
		TtsNode(String text, int callbackCode, int resourceId, String flag) {
			this.text = text;
			this.callbackCode = callbackCode;
			this.resourceId = resourceId;
			this.flag = flag;
		}
		
		
		int getResourceId() {
			return resourceId;
		}

		String getText() {
			return text;
		}

		int getCallbackCode() {
			return callbackCode;
		}
		
		String getFlag() {
			return flag;
		}
	}

	/**
	 * 语速
	 * 
	 * @author v_swduan
	 * 
	 */
	private class Speed extends TtsNode {

		Speed(int value) {
			super("[s" + value + "]");
		}
	}

	/**
	 * 音量
	 * 
	 * @author v_swduan
	 * 
	 */
	private class Volume extends TtsNode {

		Volume(int value) {
			super("[v" + value + "]");
		}
	}

	/**
	 * 静音时间
	 * 
	 * @author v_swduan
	 * 
	 */
	private class WaitTime extends TtsNode {

		WaitTime(int value) {
			super("[p" + value + "]");
		}
	}

	/**
	 * 
	 * 恢复默认的合成参数，不能恢复语种和发音人
	 * 
	 */
	private class Clear extends TtsNode {

		Clear() {
			super(CLEAR_VALUE);
		}
	}

	/**
	 * 播音员
	 * 
	 * @author v_swduan
	 * 
	 */
	private class Role extends TtsNode {

		Role(int id) {
			super(ROLES[id]);
		}

		Role(String value) {
			super("[m" + value + "]");
		}
	}
}