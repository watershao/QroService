package com.qrobot.motion;

import com.ritech.qrobot.QrobotJni;

public class BaseMotion {

	protected static QrobotJni qrobotJni = null;
	
	public static void setQrobotJni(QrobotJni qj){
		qrobotJni = qj;
		if(qrobotJni == null) return;
		if (!isInit()) {
			if(qrobotJni != null)
				qrobotJni.init();
		}
	}
	
	public BaseMotion () {
		
	}

	
	public static QrobotJni getQrobotJni(){
		return qrobotJni;
	}
	
	/**
	 * 驱动服务是否初始化
	 * @return
	 */
	public static boolean isInit(){
		boolean bInit = false;
		if(qrobotJni != null)
			bInit = qrobotJni.isInit();
		return bInit;
	}
	

	public static final int EYE_ALL = 0;
	public static final int EYE_LEFT = 1;
	public static final int EYE_RIGHT = 2;

	public static final int EYE_WIDTH = 96;
	public static final int EYE_HEIGHT = 132;
	
    /**
     * 设置眼睛显示图片
     * @param eye 0-两眼;1-左眼；2-右眼;
     * @param eyePath 图片路径
     */
	protected static void setEye(int eye,String eyePath) {
		if(qrobotJni != null)
			qrobotJni.setEye(eye, eyePath);
	}

    /**
     * 设置眼睛显示图片
     * @param eye 0-两眼;1-左眼；2-右眼;
     * @param data 图片资源的二进制流
     */
	protected static void setEyeResource(int eye,byte[] data) {
		if(qrobotJni != null)
			qrobotJni.setEyeResource(eye, data);
	}
	
	
	/**
	 * 设置心形灯
	 * @param led 颜色设置，0-red;1-greeen;2-blue
	 * @param level 亮度设置，level:0~15级。0-熄灭；15-最亮
	 */
	protected static void setHeartLed(int led,int level){
		if(qrobotJni != null)
			qrobotJni.setHeartLed(led, level);
	}

	
	public static final String TAG = "BaseMotion:";
	public static final int HORIZONTAL_ORIGIN = 50;// 水平原点
	public static final int VERTICAL_ORIGIN = 0;// 竖直原点

	public static final int SPEED_SLOW = 1;
	public static final int SPEED_MEDIUM = 2;
	public static final int SPEED_FAST = 3;

	public static final int ORIENTATION_VERTICAL = 0;
	public static final int ORIENTATION_HORIZONTAL = 1;

    /**
     * 头部运动设置
     * @param motor 电机部位，0-头上下，1-头左右
     * @param mode 运动方式，0- 保持当前状态，1- 停止运动， 2-运动(用于头上下)
     * @param speed  速度，0~15：0为默认的运动速度，1~3速度按照线性方式增加
     * @param position 目标点，对于头部，指的是目标位置，范围：0~100
     */
	protected static void setHeadMotor(int motor,int mode,int speed,int position){
		//Log.d(TAG, "HeadMotor:motor="+motor+",mode="+mode+",speed="+speed+",position="+position);
		if(qrobotJni != null)
			qrobotJni.setHeadMotor(motor, mode, 5*speed, Math.round(position*255/100), 0);
	}
	
	public static final int WING_UP = 2;
	public static final int WING_DOWN = 3;
	public static final int WING_ORIGIN = 4;
	public static final int WING_TIME_UNIT = 200; //翅膀运动时间的基本单位20ms

	public static final int WING_LEFT = 2;
	public static final int WING_RIGHT = 3;
	
	/**
	 * 翅膀电机控制
	 * 
	 * @param motor 电机部位，2-左翅膀，3-右翅膀
	 * @param mode 运动方式，0- 保持当前状态，1- 停止运动， 2-正转（用于翅膀），
	 *  3- 反转（只适用于翅膀，即motor=2 or 3）， 4- 回原点（只适用于翅膀，即motor=2 or 3）
	 * @param speed 速度，0~3：0为默认的运动速度，1~3速度按照线性方式增加
	 * @param time 代表时间，以20ms为基本单位
	 */
	protected static void setWingMotor(int motor,int mode,int speed,int time){
		//Log.d(TAG, "SwingMotor:motor="+motor+",mode="+mode+",speed="+speed+",time="+time);
		if(qrobotJni != null)
			qrobotJni.setSwingMotor(motor, mode, 5*speed, WING_TIME_UNIT*time, 0);
	}
}
