package com.qrobot.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ImageTools {

	private static final int leInt(byte[] array) {
		int a = array[0] & 0xff;
		int b = array[1] & 0xff;
		int c = array[2] & 0xff;
		int d = array[3] & 0xff;
		return a | (b << 8) | (c << 16) | (d << 24);
	}

	/**
	 * 读取rgb565的BMP文件
	 * @param path 文件路径
	 * @return
	 */
	public static byte[] readBmpRgb565(String path) {

		// Log.d("ImageTools", ">>>>readRgb565 Path: " + path);
		// Log.d("ImageTools", " Enter readRgb565");
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			DataInputStream is = new DataInputStream(fis);
			byte[] array1 = new byte[14];
			byte[] array2 = new byte[4];

			/* bitmap-file header. 14 bytes */
			is.read(array1);

			/* bitmap-information header. 40 bytes */

			is.read(array2);
			is.read(array2);
			int width = leInt(array2);
			is.read(array2);
			int height = leInt(array2);
			is.read(array1);
			is.read(array1);
			int count = width * height;

			byte[] array = new byte[count * 2];
			byte[] buffer = new byte[3];
			int offset = 0;
			if (array == null || buffer == null) {
				return null;
			}

			for (int i = 0; i < count; i++) {
				is.read(buffer);
				int blue = buffer[0] & 0xF8; // big 5 bits
				int green = buffer[1] & 0xFC; // big 6 bits
				int red = buffer[2] & 0xF8; // big 5 bits
				array[offset++] = (byte) (red | green >> 5); // RRRRRGGG,G
																// little 3 bits
				array[offset++] = (byte) ((green & 0x1c) << 3 | blue >> 3); // GGGBBBBB,G
																			// big
																			// 3
																			// bits
			}
			// is.close();
			// fis.close();
			return array;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static byte[] RGB888ToRGB565(byte[] rgb888) {
		// System.out.println("*****"+rgb888.length);
		int length = rgb888.length;
		byte[] array = new byte[length * 2];
		int offset = 0;
		int out_buff;
		for (int i = 0; i < length; i++) {
			out_buff = ((rgb888[i] & 0xf800) << 3) + ((rgb888[i] & 0x7e0) << 2)
					+ ((rgb888[i] & 0x1f) >> 3);

			array[offset++] = (byte) out_buff;
			array[offset++] = (byte) (out_buff >> 8);
		}
		// System.out.println("*****"+array.length);
		return array;
	}

}
