package com.ritech.qrobot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.util.Log;
/**
 * 驱动方法
 * @author v_watershao
 *
 */
public class QrobotJni {

	public static final String tag = "QrobotJni:";
	
	private static TchkeyCallback callback;
	
	private static volatile boolean isInit = false;

	public static void setTchkeyCallback(TchkeyCallback cb) {
		callback = cb;
	}

	private static MotorMoveCallback motorCallback;
	
	public static void setMotorMoveCallback(MotorMoveCallback mmc){
		motorCallback = mmc;
	}
	
	/**
	 * 驱动是否初始化完成
	 * @return
	 */
	public static boolean isInit(){
		return isInit;
	}
	
	/**
	 * 初始化驱动
	 */
	public int init(){
		Log.d(tag, "init All Drivers......");
		if (isInit) {
			return 0;
		}
		
		int flag = qrobotInitSystem();
		if (flag == 0) {
			isInit = true;
			Log.d(tag, "init success!");
		} else {
			isInit = false;
			Log.d(tag, "init failed!");
		}
		
		return flag;
	}
	
	/**
	 * 设置心形灯
	 * @param led 颜色设置，0-red;1-greeen;2-blue
	 * @param level 亮度设置，level:0~15级。0-熄灭；15-最亮
	 */
	public static synchronized void setHeartLed(int led,int level){
//		Log.w(tag, "HeartLed:led="+led+",level="+level);
		QrobotJni.qrobotHeartLedCtrl((byte)led, (byte)level);
	}

	/**
	 * 眼睛屏显示
	 * @param eye 0-两眼;1-左眼；2-右眼;
	 * @param data 图片的字节流
	 */
	public void setLcdDisplayBmp(int eye, byte[] data) {
//		Log.d(tag, "setLcdDisplayBmp:eye="+eye+",data="+data.length);	
		qrobotLcdDisPlayBmp((byte)eye, data);
		data = null;
	}
	
    /**
     * 设置眼睛显示图片
     * @param eye 0-两眼;1-左眼；2-右眼;
     * @param eyePath 图片路径
     */
	public void setEye(int eye,String eyePath){
//    	Log.d(tag, "setEye:eye=" + eye + ";eyePath="+eyePath);
		int	size = 0;

		File file = new File(eyePath);
		if(!file.exists()){
			return;
		}
		
		size = (int)file.length();

		byte[] b = new byte[size];
		
		InputStream is;
		try {
			is = new FileInputStream(file);
			is.read(b);
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setLcdDisplayBmp(eye,b);
	}
	
    /**
     * 设置眼睛显示图片
     * @param eye 0-两眼;1-左眼；2-右眼;
     * @param data 图片资源的二进制流
     */
    public void setEyeResource(int eye, byte[] data){
//    	Log.w(tag, "setEyeResource:eye=" + eye + ";data size="+data.length);
    	setLcdDisplayBmp(eye, data);
    }
	/**
	 * 眼睛屏显示
	 * @param eye 0-两眼;1-左眼；2-右眼;
	 * @param data 数据缓冲区
	 * @param offset 该数据在图片中的偏移位置no护士
	 * @param len 数据长度(最大值：4096)
	 * @param fst_frame 	指示是否为该幅图片的第一段数据    0：不是    1：是
	 */
	public static synchronized void setLcdDisplay(int eye,byte[] data,int offset,int len,int fst_frame){
//		Log.d(tag, "LcdDisplay:eye="+eye+",offset="+offset+",len="+len+",fst_frame="+fst_frame);
		QrobotJni.qrobotLcdDisplay((byte)eye, data, (short)offset, (short)len, (byte)fst_frame);
	}

    /**
     * 设置眼睛显示图片
     * @param eye 0-两眼;1-左眼；2-右眼;
     * @param eyePath 图片路径
     */
    public static synchronized void setEye1(int eye,String eyePath) {
//    	Log.d(tag, "setEye:eye=" + eye + ";eyePath="+eyePath);
		int	size = 0;
		int	seg_size = 4096;
		byte flag=0;
		int	len;
		int offset;

		File file = new File(eyePath);
		size = (int)file.length();

		byte[] b = new byte[size];
		
		InputStream is;
		try {
			is = new FileInputStream(file);
//			is = imageRotate(eyePath);
			is.read(b);
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		offset = 0;
		while (offset < size) {
			if ((offset + seg_size) >= size){
				len = size - offset;
			} else {
				len = seg_size;
			}
			if (len > 0) {
				if (offset == 0){
					flag = 1;
				} else {
					flag = 0;
				}
//				Log.d(tag, ">>>>>>EyeLcdDisplay");
//				qrobotLcdDisplay((byte)eye, b, (short) offset, (short) len, flag);
				QrobotJni.setLcdDisplay((byte)eye, b, (short) offset, (short) len, flag);
				offset += len;
			} else {
				break;
			}
		}

	}
	
    /**
     * 设置眼睛显示图片
     * @param eye 0-两眼;1-左眼；2-右眼;
     * @param data 图片资源的二进制流
     */
    public static synchronized void setEyeResource1(int eye, byte[] data){
//    	Log.d(tag, "setEyeResource:eye=" + eye + ";data size="+data.length);
		int	size = 0;
		int	seg_size = 4096;
		byte flag=0;
		int	len;
		int offset;
		
		size = data.length;

		offset = 0;
		while (offset < size) {
			if ((offset + seg_size) >= size){
				len = size - offset;
			} else {
				len = seg_size;
			}
			if (len > 0) {
				if (offset == 0){
					flag = 1;
				} else {
					flag = 0;
				}
//				qrobotLcdDisplay((byte)eye, data, (short) offset, (short) len, flag);
				QrobotJni.setLcdDisplay((byte)eye, data, (short) offset, (short) len, flag);
				offset += len;
			} else {
				break;
			}
		}

    }
    
	/**
	 * 电机控制
	 * 
	 * @param motor 电机部位，0-头上下，1-头左右，2-左翅膀，3-右翅膀
	 * @param mode 运动方式，0- 保持当前状态，1- 停止运动， 2-运动(用于头上下)，正转（用于翅膀），
	 *  3- 反转（只适用于翅膀，即motor=2 or 3）， 4- 回原点（只适用于翅膀，即motor=2 or 3）
	 * @param speed 速度，0~15：0为默认的运动速度，1~15速度按照线性方式增加
	 * @param pos_time 目标点，对于头部，指的是目标位置，范围：0~255
   	 *						 对于翅膀，则代表时间，以20ms为基本单位
	 * @param trap_flag 电机到达指定位置是否返回信息，0-不通知上位机，1-通知上位机
	 */
    public static synchronized void setMotor(int motor,int mode,int speed,int pos_time,int trap_flag){
//    	Log.d(tag, "Motor:motor="+motor+",mode="+mode+",speed="+speed+",pos_time="+pos_time+",trap_flag"+trap_flag);
    	QrobotJni.qrobotMotorCtrl((byte)motor, (byte)mode, (byte)speed, (short)pos_time, (byte)trap_flag);
    }
    
    /**
     * 头部运动设置
     * @param motor 电机部位，0-头上下，1-头左右
     * @param mode 运动方式，0- 保持当前状态，1- 停止运动， 2-运动(用于头上下)
     * @param speed  速度，0~15：0为默认的运动速度，1~15速度按照线性方式增加
     * @param position 目标点，对于头部，指的是目标位置，范围：0~255
     * @param trap_flag  电机到达指定位置是否返回信息，0-不通知上位机，1-通知上位机
     */
    public static synchronized void setHeadMotor(int motor,int mode,int speed,int position,int trap_flag){
//    	Log.d(tag, "HeadMotor:motor="+motor+",mode="+mode+",speed="+speed+",position="+position+",trap_flag="+trap_flag);
    	QrobotJni.qrobotMotorCtrl((byte)motor, (byte)mode, (byte)speed, (short)position, (byte)trap_flag);
    }
    

	/**
	 * 翅膀电机控制
	 * 
	 * @param motor 电机部位，2-左翅膀，3-右翅膀
	 * @param mode 运动方式，0- 保持当前状态，1- 停止运动， 2-正转（用于翅膀），
	 *  3- 反转（只适用于翅膀，即motor=2 or 3）， 4- 回原点（只适用于翅膀，即motor=2 or 3）
	 * @param speed 速度，0~15：0为默认的运动速度，1~15速度按照线性方式增加
	 * @param time 目标点， 对于翅膀，则代表时间，以20ms为基本单位
	 * @param trap_flag 电机到达指定位置是否返回信息，0-不通知上位机，1-通知上位机
	 */
    public static synchronized void setSwingMotor(int motor,int mode,int speed,int time,int trap_flag){
//    	Log.d(tag, "SwingMotor:motor="+motor+",mode="+mode+",speed="+speed+",time="+time+",trap_flag"+trap_flag);
    	QrobotJni.qrobotMotorCtrl((byte)motor, (byte)mode, (byte)speed, (short)time, (byte)trap_flag);
    }
    
	/**
	 * 电机部位查询
	 * @param motor 电机部位  0-头上下  1-头左右
	 * @return 	>0 - 成功   ，-1- 失败
	 */
	public static synchronized int getMotorPos(int motor){
		Log.d(tag, "MotorQuery:motor="+motor);
		return QrobotJni.qrobotGetMotorPos((byte)motor);
	}
	
	/**
	 * 销毁驱动
	 */
	public static synchronized void destoryDriver() {
		Log.d(tag, "qrobotJni destroy");
		qrobotDestory();
	}
	
	/**
	 * 触摸回调
	 * 
	 * @param act
	 * 1代表头顶触摸识别，2代表头顶触摸释放
	 * 4代表嘴巴触摸识别，8代表嘴巴触摸释放
	 * 16代表头长按
	 */
	public void tchkeyCallback(byte act) {
		Log.d(tag, "TchkeyCallback:" + act);
		String content = "";
		if (act == 1) {
			content = "头顶触摸开始";
			Log.w(tag, "#" + act + content);
		} else if (act == 2) {
			content = "头顶触摸结束";
			Log.w(tag, "#" + act + content);
		} else if (act == 4) {
			content = "嘴巴触摸开始";
			Log.w(tag, "#" + act + content);
		} else if (act == 8) {
			content = "嘴巴触摸结束";
			Log.w(tag, "#" + act + content);
		} else if (act == 16) {
			content = "头长按";
			Log.w(tag, "#" + act + content);
		}else {
			Log.w(tag, "#" + act + "没有摸我！！");
		}

		callback.touchFinish((int)act,0);
	}

	/**
	 * 电机运动反馈
	 * @param motor  电机部位，0-头上下，1-头左右，2-左翅膀，3-右翅膀
	 * @param pos 	当前电机位置(0表示翅膀回到原点)
	 * @param warn_info 	大于0时表示电机运动相关警告
	 *     					bit0=1头上下无法找到指定位置；
	 * 					    bit1=1头上下行程太小
	 *					    bit2=1头左右无法找到指定位置；
	 *						bit3=1头左右行程太小
	 *						bit4=1左手无法找到指定位置（原点）；
	 *						bit5=1左手行程太小（保留）
	 *						bit6=1右手无法找到指定位置（原点）；
	 *						bit7=1右手行程太小（保留）
	 */
	public void motorMoveCallback(byte motor,short pos,short warn_info)
	{
		
//		Log.d(tag, "motorMoveCallback:##motor:"+motor+"pos:"+pos+"warn_info:"+warn_info);

	}
	
	static {
		System.loadLibrary("QrobotJni");
	}
	
	/*
	 * native method
	 */
	/**
	 * 初始化所有驱动，触摸，心形灯，眼睛屏，电机
	 * @return 0，代表成功，-1代表失败
	 */
	public native int qrobotInitSystem();
	
	/**
	 * 心形灯控制
	 * @param led 颜色设置，0-red;1-greeen;2-blue
	 * @param level 亮度设置，level:0~15级。0-熄灭；15-最亮
	 */
	public native static void qrobotHeartLedCtrl(byte led,byte level);
	
	/**
	 * 电机控制
	 * 
	 * @param motor 电机部位，0-头上下，1-头左右，2-左翅膀，3-右翅膀
	 * @param mode 运动方式，0- 保持当前状态，1- 停止运动， 2-运动(用于头上下)，正转（用于翅膀），
	 *  3- 反转（只适用于翅膀，即motor=2 or 3）， 4- 回原点（只适用于翅膀，即motor=2 or 3）
	 * @param speed 速度，0~15：0为默认的运动速度，1~15速度按照线性方式增加
	 * @param pos_time 目标点，对于头部，指的是目标位置，范围：0~255
   	 *						 对于翅膀，则代表时间，以20ms为基本单位
	 * @param trap_flag 电机到达指定位置是否返回信息，0-不通知上位机，1-通知上位机
	 */
	public native static void qrobotMotorCtrl(byte motor,byte mode,byte speed,short pos_time,byte trap_flag);
	
	/**
	 * 眼睛屏显示
	 * @param eye 0-两眼;1-左眼；2-右眼;
	 * @param data 数据缓冲区
	 * @param offset 该数据在图片中的偏移位置
	 * @param len 数据长度(最大值：4096)
	 * @param fst_frame 	指示是否为该幅图片的第一段数据    0：不是    1：是
	 */
	public native static void qrobotLcdDisplay(byte eye,byte[] data,short offset,short len,byte fst_frame);
	
	/**
	 * 眼睛屏显示
	 * @param eye 0-两眼;1-左眼；2-右眼;
	 * @param data 图片的字节流
	 */
	public native void qrobotLcdDisPlayBmp(byte eye,byte[] data);
	
	/**
	 * 电机部位查询
	 * @param motor 电机部位  0-头上下  1-头左右
	 * @return 	>0 - 成功   ，-1- 失败
	 */
	public native static int qrobotGetMotorPos(byte motor);
	
	/**
	 * 销毁驱动
	 */
	public native static void qrobotDestory();
	
}