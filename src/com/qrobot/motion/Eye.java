package com.qrobot.motion;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.qrobot.util.ImageTools;
import com.qrobot.util.SysImages;
import com.ritech.qrobot.QrobotJni;

public class Eye extends BaseMotion {

	public static final String SYS_IMAGE_PATH = "/mnt/sdcard/qrobot/motion/sys/";

	private static byte[] resetBuffer = new byte[0x6300];
	private static byte[] tempRgb565 = new byte[0x6300];
	private static int[] tempRgb888 = new int[EYE_WIDTH * EYE_HEIGHT];

//	private static QrobotJni qrobotJni = null;
	// private static List<byte[]> imageList = null;

	private static volatile boolean bStopRunEye = false;

	/**
	 * 初始化眼睛屏幕
	 */
	public static void resetEye() {
		bStopRunEye = true;
		setEyeResource(EYE_ALL, resetBuffer);
	}
	
	/**
	 * 显示系统默认图片
	 * @param count 显示次数
	 * @param num 目前仅限于1-43
	 */
	public static void showSysImage(int count, int num) {
		String[] images = null;
		int tm = 60;
		int cnt = SysImages.IMAGE_LIST.length;
		int itemIndex = 0;
		if (num > 0 && num < (cnt + 1)) {
			itemIndex = num - 1;
		} else {
			Random rnd = new Random();
			itemIndex = rnd.nextInt(cnt);
		}
		images = SysImages.IMAGE_LIST[itemIndex];
		int imgLen = images.length;

		List<byte[]> imageList = new ArrayList<byte[]>();
		imageList.clear();

		bStopRunEye = false;
		for (int len = 0; len < imgLen; len++) {
			String imgPath = SYS_IMAGE_PATH + images[len] + ".bmp";
			byte[] imgBytes = ImageTools.readBmpRgb565(imgPath);
			if (imgBytes != null && imgBytes.length > 0) {
				imageList.add(imgBytes);
			} else {
				return;
			}
		}
		int isize = imageList.size();
		if (imgLen != isize)
			imgLen = isize;

		if (bStopRunEye)
			return;
		
//		if(qrobotJni == null){
//			qrobotJni = new QrobotJni();
//		}
		
		if (imgLen > 0) {
			if (images[0].indexOf("left") == -1) {
				for (int i = 0; i < count; i++) {
					for (int j = 0; j < imgLen; j++) {
						if (bStopRunEye)
							return;
						setEyeResource(0, imageList.get(j));
						try {
							Thread.sleep(tm);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} else {
				for (int i = 0; i < count; i++) {
					boolean bLeft = false;
					boolean bRight = false;
					for (int j = 0; j < imgLen; j++) {
						if (bStopRunEye)
							return;
						if (j % 2 == 0) {
							bLeft = true;
							setEyeResource(2, imageList.get(j));
						} else {
							bRight = true;
							setEyeResource(1, imageList.get(j));
						}

						if (bLeft && bRight) {
							try {
								Thread.sleep(tm);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							bLeft = false;
							bRight = false;
						}
					}
				}
			}
		}
		imageList.clear();
	}

	/**
	 * 设置眼睛显示图片
	 * 
	 * @param eye  0-两眼;1-左眼；2-右眼;
	 * @param data  图片资源的二进制流
	 * @return
	 */
	public static final void setEye(int eye, byte[] data) {
		setEye(eye, data);
	}

	/**
	 * 设置眼睛屏的显示
	 * 
	 * @param eye 0-两眼;1-左眼；2-右眼;
	 * @param count 显示次数
	 * @param eyePath  图片路径(仅用于RGB565的bmp和bin图片文件)
	 */
	public static void showEye(int eye, int count, String eyePath) {
		// setEyeResource(eye, resetBuffer);
		File eyeFile =  new File(eyePath);
		if (!eyeFile.exists()) {
			return;
		}
		
		byte[] imgBytes = null;
		if (eyePath.indexOf(".bin") == -1)
			imgBytes = ImageTools.readBmpRgb565(eyePath);
		if (imgBytes == null)
			return;
		bStopRunEye = false;
		for (int i = 0; i < count; i++) {
			// Log.d(TAG, "****????????>>>>> ShowEye");
			if (bStopRunEye)
				return;
			// setEye(eye, eyePath);
			// setEyeResource(eye, readRGB565(eyePath));
			if (imgBytes == null) {
				setEye(eye, eyePath);
			} else {
				setEyeResource(eye, imgBytes);
			}
		}
		Runtime.getRuntime().gc();
		// System.gc();
	}

}