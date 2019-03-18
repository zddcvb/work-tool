package com.yanxi.data.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * 读写文件
 * 
 * @author Administrator
 *
 */
public class FileUtils {
	private static Logger logger=Logger.getLogger(FileUtils.class);
	/**
	 * 向文件中写入数据，已utf-8形式
	 * 
	 * @param savePath
	 *            需要保存的文件
	 * @param data
	 *            写入的数据
	 */
	public static void writeData(String savePath, String data) {
		OutputStream out = null;
		OutputStreamWriter osw = null;
		try {
			File file = new File(savePath);
			if (file.exists()) {
				file.delete();
			}
			out = new FileOutputStream(file);
			osw = new OutputStreamWriter(out, "utf-8");
			osw.write(data);
			osw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 删除list集合中相同名字，不同后缀名的数据，如果同时存在swf和mp4格式的文件，则只选择mp4文件
	 * @param sourceList 原始list集合
	 * @return list集合
	 */
	public static List<String> deleteSameNameItemFromList(List<String> sourceList) {
		List<String> fileNames = new ArrayList<>();
		if (sourceList.isEmpty()) {
			return null;
		}
		long startTime = System.currentTimeMillis();
		String compator = "";
		for (int i = 0; i < sourceList.size(); i++) {
			String fileName = sourceList.get(i);
			String subFileName = fileName.substring(0, fileName.lastIndexOf("."));
			if (subFileName.equals(compator)) {
				continue;
			}
			boolean flag = false;
			for (int j = i + 1; j < sourceList.size(); j++) {
				String compartorName = sourceList.get(j);
				String subCompartorName = compartorName.substring(0, compartorName.lastIndexOf("."));
				if (subCompartorName.equals(subFileName)) {
					flag = true;
					if (fileName.endsWith(".mp4")) {
						logger.info(fileName);
						compator = subCompartorName;
						fileNames.add(fileName);
					}
					break;
				} else {
					flag = false;
				}
			}
			if (!flag) {
				fileNames.add(fileName);
			}
		}
		long endTime = System.currentTimeMillis();
		long time = endTime - startTime;
		logger.info(time);
		return fileNames;
	}
	
	
}
