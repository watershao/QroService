package com.ritech.qrobot;

public interface MotorMoveCallback {

	/**
	 * 电机运动后的反应
	 * 电机动作的编号
	 * @param resultCode
	 * @param featureCode
	 */
	public void moveFinish(int resultCode, int featureCode);
}
