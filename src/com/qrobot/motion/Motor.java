package com.qrobot.motion;


public class Motor extends BaseMotion{

	/**
	 * 设置头上下运动，到达指定位置不通知上位机
	 * @param speed 1.2.3
	 * @param position 0-100,0为原点
	 */
	public static void setHeadVerticalPosition(int speed, int position){
		setHeadMotor(ORIENTATION_VERTICAL, 2, speed, position);
			
	}

	/**
	 * 设置头左右运动，到达指定位置不通知上位机
	 * @param speed 1.2.3
	 * @param position 0-100,50为原点
	 */
	public static void setHeadHorizontalPosition(int speed, int position){
		setHeadMotor(ORIENTATION_HORIZONTAL, 2, speed, position);
			
	}
	
	/**
	 * 设置头上下回到原点
	 * @param speed 速度，1,2,3三个级别
	 */
	public static void resetHeadVertical(int speed){
		setHeadVerticalPosition(speed, VERTICAL_ORIGIN);
			
	}
	
	/**
	 * 设置头左右回到原点
	 * @param speed 速度，1,2,3三个级别
	 */
	public static void resetHeadHorizontal(int speed){
		setHeadHorizontalPosition(speed, HORIZONTAL_ORIGIN);
			
	}
	
	/**
	 * 设置头回到初始位置
	 */
	public static void resetHead(){
		setHeadVerticalPosition(SPEED_FAST, VERTICAL_ORIGIN);
		setHeadHorizontalPosition(SPEED_FAST, HORIZONTAL_ORIGIN);
	}



	/**
	 * 左翅膀向上运动
	 * @param speed 运动速度，1-3三级
	 * @param time 运动时间
	 */
	public static void setLeftWingUp(int speed, int time){
		setWingMotor(WING_LEFT, WING_UP, speed, time);
	}

	/**
	 * 左翅膀向下运动
	 * @param speed 运动速度，1-3三级
	 * @param time 运动时间
	 */	
	public static void setLeftWingDown(int speed, int time){
		setWingMotor(WING_LEFT, WING_DOWN, speed, time);	
	}

	/**
	 * 右翅膀向上运动
	 * @param speed 运动速度，1-3三级
	 * @param time 运动时间
	 */	
	public static void setRightWingUp(int speed, int time) {
		setWingMotor(WING_RIGHT, WING_UP, speed, time);	
	}

	/**
	 * 右翅膀向下运动
	 * @param speed 运动速度，1-3三级
	 * @param time 运动时间
	 */
	public static void setRightWingDown(int speed, int time) {
		setWingMotor(WING_RIGHT, WING_DOWN, speed, time);
	}
	
	/**
	 * 左翅膀回原点
	 * @param speed 运动速度，1-3三级
	 */
	public static void resetLeftWing(int speed) {
		setWingMotor(WING_LEFT, WING_ORIGIN, speed, 0);
	}
	
	/**
	 * 右翅膀回原点
	 * @param speed 运动速度，1-3三级
	 */	
	public static void resetRightWing(int speed) {
		setWingMotor(WING_RIGHT, WING_ORIGIN, speed, 0);
	}

	/**
	 * 左右翅膀都回原点
	 */
	public static void resetWing() {
		resetLeftWing(SPEED_FAST);
		resetRightWing(SPEED_FAST);
	}
	
}
