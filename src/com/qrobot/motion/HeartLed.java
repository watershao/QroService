package com.qrobot.motion;

/**
 * 心形灯
 * @author water
 *
 */
public class HeartLed extends BaseMotion {

	public HeartLed() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static final boolean reset() {
		return setColor(0);
	}

	public static final boolean setColor(int rgb) {
		return false;
	}

	public static final boolean setColor(int red, int green, int blue) {
		int c1 = (red & 0xff) << 16;
		int c2 = (green & 0xff) << 8;
		int c3 = blue & 0xff;
		return setColor(c1 | c2 | c3);
	}
	
	/**
	 * 设置心形灯的显示颜色
	 * @param red 红色值
	 * @param green 绿色值
	 * @param blue 蓝色值
	 * @param time 持续时间
	 */
	public static final void setColors(int red, int green, int blue, int time) {
		int rlevel = Math.round(red/17);
		int glevel = Math.round(green/17);
		int blevel = Math.round(blue/17);
		setHeartLed(0, rlevel);
		setHeartLed(1, glevel);
		setHeartLed(2, blevel);
		if (time != 0) {
			try {
				Thread.sleep(time);
				setHeartLed(0, 0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
