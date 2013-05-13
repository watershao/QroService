package com.qrobot.sensor;

import android.util.Log;

public class QrobotSensor {
	
	private static final String TAG = "QrobotSensor:";

	private static int[] RTC_KEY = new int[3];
	
	private static int rtcType = 0;
	
	private final static int RTC_DVD = 2;
	private final static int RTC_TV = 1;
	/**
	 * 语音ID地址
	 */
	private final static byte[] ADDR_VOICEID = {0, 0};
	
	/**
	 * 机器人ID地址
	 */
	private final static byte[] ADDR_QROBOTID = {0, 18};

	/**
	 * 验证码前18位
	 */
	private final static byte[] ADDR_CODEPRE18 = {0, 36};
	
	/**
	 * 验证码后18位
	 */
	private final static byte[] ADDR_CODESUR18 = {0, 54};
	
    private static byte[] ID_CODE = {0x00,0x01,0x02,0x03,0x04,0x05,0x06,0x07,0x08,0x09,0x0a,0x0b,0x0c,0x0d,0x0e,0x0f,0x10,0x11};
    private static byte[] ADDR = {0x00,0x00};
	
	public void irCtrl(int key, int s){
		
		switch (s) {
		case 1:
			RTC_KEY[0] = 692;
			RTC_KEY[1] = RemoteCtrlCodeValue.RC_ADDRCODE_HUAWEI; 
			break;
		case 2: 
			RTC_KEY[0] = 346;
			RTC_KEY[1] = RemoteCtrlCodeValue.DVD_LG_ADDRCODE; 
			break;
		default:
			break;
		}
		RTC_KEY[2] = key;
		qrobotIrCtrl(RTC_KEY);
	}
	/**
	 * 设置遥控器遥控对象的类型
	 * @param typeCode 1,tv;2,dvd
	 */
	public void setRTCType(int typeCode){
		rtcType = typeCode;
	}
	
	/**
	 * 初始化模块,有温度，红外，加密等模块
	 * @return  0 - 成功, -1 - 失败
	 */
	public int initSensorSystem(){
		int ret = qrobotSensorInitSystem();
		Log.d(TAG, "initSensorSystem ret = "+ret);
		return ret;
	}
	
	/**
	 * 控制红外遥控输出
	 * @param key
	 */
	public void infraredCtrl(int key){
//		Log.d(TAG, "infrared ctrl key = " + Integer.toHexString(key[0])+"*"+Integer.toHexString(key[1]));
		if (rtcType == RTC_TV) {
			RTC_KEY[0] = 692;
			RTC_KEY[1] = RemoteCtrlCodeValue.RC_ADDRCODE_HUAWEI;
		}else if (rtcType == RTC_DVD) {
			RTC_KEY[0] = 346;
			RTC_KEY[1] = RemoteCtrlCodeValue.DVD_LG_ADDRCODE;	
		}
		RTC_KEY[2] = key;
		qrobotIrCtrl(RTC_KEY);
		Log.d(TAG, "infraredCtrl key=" + key);
//		qrobotIrCtrl(key);
	}
	
	/**
	 * 红外控制音量调大
	 */
	public void irVolumeUp(){
		if (rtcType == RTC_TV) {
			infraredCtrl(RemoteCtrlCodeValue.RC_HUAWEI_VOLUMEUP);
		}else if (rtcType == RTC_DVD) {
//			infraredCtrl(RemoteCtrlCodeValue.DVD_LG_UP);
			infraredCtrl(RemoteCtrlCodeValue.DVD_LG_LEFT);
		}
	}
	
	/**
	 * 红外控制音量调小
	 */
	public void irVolumeDown(){
		if (rtcType == RTC_TV) {
			infraredCtrl(RemoteCtrlCodeValue.RC_HUAWEI_VOLUMEDOWN);
		}else if (rtcType == RTC_DVD) {
//			infraredCtrl(RemoteCtrlCodeValue.DVD_LG_DOWN);
			infraredCtrl(RemoteCtrlCodeValue.DVD_LG_RIGHT);
		}
	}
	
	/**
	 * 红外控制频道调大
	 */
	public void irChanelUp(){
		if (rtcType == RTC_TV) {
			infraredCtrl(RemoteCtrlCodeValue.RC_HUAWEI_CHANELUP);
		}else if (rtcType == RTC_DVD) {
//			infraredCtrl(RemoteCtrlCodeValue.DVD_LG_LEFT);
			infraredCtrl(RemoteCtrlCodeValue.DVD_LG_UP);
		}
	}
	
	/**
	 * 红外控制频道调小
	 */
	public void irChanelDown(){
		if (rtcType == RTC_TV) {
			infraredCtrl(RemoteCtrlCodeValue.RC_HUAWEI_CHANELDOWN);
		}else if (rtcType == RTC_DVD) {
//			infraredCtrl(RemoteCtrlCodeValue.DVD_LG_RIGHT);
			infraredCtrl(RemoteCtrlCodeValue.DVD_LG_DOWN);
		}
	}
	
	/**
	 * 红外控制关机
	 */
	public void irShutdown(){
		if (rtcType == RTC_TV) {
			infraredCtrl(RemoteCtrlCodeValue.RC_HUAWEI_SHUTDOWN);
		}else if (rtcType == RTC_DVD) {
			infraredCtrl(RemoteCtrlCodeValue.DVD_LG_POWER);
		}
	}
	
	/**
	 * 红外控制设置
	 */
	public void irSetup(){
		infraredCtrl(RemoteCtrlCodeValue.RC_HUAWEI_SETUP);
	}
	
	/**
	 * 红外控制确认
	 */
	public void irOK(){
		if (rtcType == RTC_TV) {
			infraredCtrl(RemoteCtrlCodeValue.RC_HUAWEI_OK);
		}else if (rtcType == RTC_DVD) {
			infraredCtrl(RemoteCtrlCodeValue.DVD_LG_OK);
		}
	}
	
	/**
	 * 红外控制数字按键
	 * @param num 0-9
	 */
	public void irNumKey(int num){
		switch (num) {
		case 0:
			if (rtcType == RTC_TV) {
				infraredCtrl(RemoteCtrlCodeValue.RC_HUAWEI_NO0);
			}else if (rtcType == RTC_DVD) {
				infraredCtrl(RemoteCtrlCodeValue.DVD_LG_PLAY);
			}
			break;
		case 1:
			if (rtcType == RTC_TV) {
				infraredCtrl(RemoteCtrlCodeValue.RC_HUAWEI_NO1);
			}else if (rtcType == RTC_DVD) {
				infraredCtrl(RemoteCtrlCodeValue.DVD_LG_PLAY);
			}
			break;
		case 2:
			if (rtcType == RTC_TV) {
				infraredCtrl(RemoteCtrlCodeValue.RC_HUAWEI_NO2);
			}else if (rtcType == RTC_DVD) {
				infraredCtrl(RemoteCtrlCodeValue.DVD_LG_PAUSE);
			}
			break;
		case 3:
			if (rtcType == RTC_TV) {
				infraredCtrl(RemoteCtrlCodeValue.RC_HUAWEI_NO3);
			}else if (rtcType == RTC_DVD) {
				infraredCtrl(RemoteCtrlCodeValue.DVD_LG_MENU);
			}
			break;
		case 4:
			if (rtcType == RTC_TV) {
				infraredCtrl(RemoteCtrlCodeValue.RC_HUAWEI_NO4);
			}else if (rtcType == RTC_DVD) {
				infraredCtrl(RemoteCtrlCodeValue.DVD_LG_SPEEDUP);
			}
			break;
		case 5:
			if (rtcType == RTC_TV) {
				infraredCtrl(RemoteCtrlCodeValue.RC_HUAWEI_NO5);
			}else if (rtcType == RTC_DVD) {
				infraredCtrl(RemoteCtrlCodeValue.DVD_LG_SPEEDDOWN);
			}
			break;
		case 6:
			if (rtcType == RTC_TV) {
				infraredCtrl(RemoteCtrlCodeValue.RC_HUAWEI_NO6);
			}else if (rtcType == RTC_DVD) {
				infraredCtrl(RemoteCtrlCodeValue.DVD_LG_OPENCLOSE);
			}
			break;
		case 7:
			if (rtcType == RTC_TV) {
				infraredCtrl(RemoteCtrlCodeValue.RC_HUAWEI_NO7);
			}else if (rtcType == RTC_DVD) {
				infraredCtrl(RemoteCtrlCodeValue.DVD_LG_OPENCLOSE);
			}
			break;
		case 8:
			infraredCtrl(RemoteCtrlCodeValue.RC_HUAWEI_NO8);
			break;
		case 9:
			infraredCtrl(RemoteCtrlCodeValue.RC_HUAWEI_NO9);
			break;
		default:
			Log.d(TAG, "invalid Number.");
			break;
		}
	}
	
	/**
	 * 获取温度
	 * @return 温度
	 **/
	public float tempRead(){
		float temp = qrobotTempRead();
		Log.d(TAG, "tempRead,temp = " + temp);
		return temp;
	}
	
	/**
	 * 往加密芯片指定地址写入注册码 
	 * @param id_code 18个字节的注册码
	 * @param address 语音ID 1;
				<p> 机器人ID 2;
				<p> 验证码前18位：3;
				<p> 验证码后18为：4;
				默认为机器人id
	 */
	public void lktRegistration(byte[] id_code, int address){
		Log.d(TAG, "lktRegistration..."+address);
		byte[] addr = null;
		switch (address) {
		case 1:
			addr = ADDR_VOICEID;
			break;
		case 2:
			addr = ADDR_QROBOTID;
			break;
		case 3:
			addr = ADDR_CODEPRE18;
			break;
		case 4:
			addr = ADDR_CODESUR18;
			break;
			
		}
		if (addr == null) {
			addr = ADDR_QROBOTID;
		}
		qrobotLktRegistration(id_code, addr);
	}
	
	/**
	 * 从加密芯片指定地址读出注册码 
	 * @param address 语音ID 1;
				<p> 机器人ID 2;
				<p> 验证码前18位：3;
				<p> 验证码后18为：4;
				默认为机器人id
	 * @return 注册码
	 */
	public byte[] lktCertification(int address){
		Log.d(TAG, "lktCertification..."+address);
		byte[] addr = null;
		switch (address) {
		case 1:
			addr = ADDR_VOICEID;
			break;
		case 2:
			addr = ADDR_QROBOTID;
			break;
		case 3:
			addr = ADDR_CODEPRE18;
			break;
		case 4:
			addr = ADDR_CODESUR18;
			break;
			
		}
		if (addr == null) {
			addr = ADDR_QROBOTID;
		}
		return qrobotLktCertification(addr);
	}
	
	
	/**
	 * 获取加密芯片ID号
	 * @return ID号
	 */
	public byte[] lktID(){
		byte[] lktId = qrobotLktID();
		
		Log.w(TAG, "lktId..."+lktId);
		return lktId;
	}
	
	/**
	 * 加密认证：将随机码加密后和低10位ID一同返回 
	 * @param  随机码random[], 加密方式algorithm_type
	 * @return 注册码
	 */
	public byte[] lktEncryption(byte[] random, byte algorithm_type){
		byte[] activeCode = qrobotLktEncryption(random, algorithm_type);
		Log.w(TAG, "lktEncryption..."+random);
		return activeCode;
	}
	
	
	
	static {
		System.loadLibrary("QrobotSensor");
	}
	
	
	/**
	 * 控制红外遥控输出
	 * @param key
	 */
//	public native void qrobotIrCtrl(int key);
	public native void qrobotIrCtrl(int[] key);
	
	
	/**
	 * 获取温度
	 * @return 温度
	 */
	public native float qrobotTempRead();
	
	
	/**
	 * 往加密芯片指定地址写入注册码 
	 * @param id_code 18个字节的注册码
	 * @param addr[] 语音ID地址：addr[0]=0; addr[1]=0;
				<p> 机器人ID地址：addr[0]=0; addr[1]=18;
				<p> 验证码前18位：addr[0]=0; addr[1]=36;
				<p> 验证码后18为：addr[0]=0; addr[1]=54;
	 */
	public native void qrobotLktRegistration(byte[] id_code, byte[] addr);
	
	/**
	 * 从加密芯片指定地址读出注册码 
	 * @param addr[] 语音ID地址：addr[0]=0; addr[1]=0;
				<p> 机器人ID地址：addr[0]=0; addr[1]=18;
				<p> 验证码前18位：addr[0]=0; addr[1]=36;
				<p> 验证码后18为：addr[0]=0; addr[1]=54;
	 * @return 注册码
	 */
	public native byte[] qrobotLktCertification(byte[] addr);
	
	/**
	 * 初始化模块,有温度，红外，加密等模块
	 * @return  0 - 成功, -1 - 失败
	 */
	public native int qrobotSensorInitSystem();
	
	
	/**
	 * 获取加密芯片ID号
	 * @return ID号
	 */
	public native byte[] qrobotLktID();
	
	/**
	 * 加密认证：将随机码加密后和低10位ID一同返回 
	 * @param  随机码random[], 加密方式algorithm_type
	 * @return 注册码
	 */
	public native byte[] qrobotLktEncryption(byte[] random, byte algorithm_type);
	
}