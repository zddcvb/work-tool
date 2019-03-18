package com.yanxi.animate.tool.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

public class ImageUtil {
	private static Logger logger = Logger.getLogger("ImageUtil");

	/**
	 * 获取图片的宽高
	 * 
	 * @param pngPath
	 *            图片的路径
	 * @return 宽高的数组
	 */
	public static int[] getPngWH(String pngPath) {
		int[] arrs = new int[2];
		File file = new File(pngPath);
		logger.info(file.getAbsolutePath());
		try {
			BufferedImage bufi = ImageIO.read(file);
			int height = bufi.getHeight();
			int width = bufi.getWidth();
			arrs[0] = width;
			arrs[1] = height;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arrs;
	}

	/**
	 * 获取图片的宽高
	 * 
	 * @param pngPath
	 *            图片的路径
	 * @return 宽高的数组
	 */
	public static String[] getImgWH(String pngPath) {
		String[] arrs = new String[3];
		File file = new File(pngPath);
		logger.info(file.getAbsolutePath());
		if (file.exists()) {
			try {
				BufferedImage bufi = ImageIO.read(file);
				int height = bufi.getHeight();
				int width = bufi.getWidth();
				arrs[0] = "文件存在";
				arrs[1] = width + "";
				arrs[2] = height + "";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			arrs=new String[1];
			arrs[0]=file.getName()+"不存在";
		}
		return arrs;
	}
}
