package com.sunvision.pngtojpeg.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageConvertUtils {
	/**
	 * 转换图片格式
	 * @param input：需要转换的图片
	 * @param output：转换成功后的图片
	 * @param imageFormatName：需要转成的格式，jpg、png、gif、bmp
	 */
	public static void convertImage(File  input,File output,String imageFormatName){
		try {
			BufferedImage bufi=ImageIO.read(input);
			ImageIO.write(bufi, imageFormatName, output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
}
