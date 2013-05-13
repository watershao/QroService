package com.ritech.qrobot;

public interface TchkeyCallback {

	/**
	 * 触摸后的反应
	 * 触摸动作的编号
	 * @param resultCode
	 * @param featureCode
	 */
	public void touchFinish(int resultCode, int featureCode);
}
