package com.yanxi.data.utils;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * 文件创建工具类
 * 
 * @author 邹丹丹
 *
 */
public class FileMoveUtils {
	private static Logger logger=Logger.getLogger(FileMoveUtils.class);
	public FileMoveUtils() {
	}

	/**
	 * 在一个文件夹下，创建swf和图片文件夹
	 * 
	 * @param path
	 *            文件路径
	 */
	public void createNewFile(String path) {
		File targetFile = new File(path);
		File[] listFiles = targetFile.listFiles();
		if (listFiles.length > 0) {
			for (File file : listFiles) {
				String filepath = file.getAbsolutePath();
				if (isContainDirectory(file)) {
					createNewFile(filepath);
				} else {
					logger.info(filepath);
					File swfFile = new File(filepath + "\\swf");
					File imageFile = new File(filepath + "\\图片");
					swfFile.mkdir();
					imageFile.mkdir();
					logger.info(swfFile.getAbsolutePath());
					logger.info("success");
				}
			}
		}
	}

	/**
	 * 转移数据，将原始的文件移动到swf文件目录下，同时在同级目录创建图片文件夹
	 * 
	 * @param path
	 *            文件路径
	 */
	public void moveFile(String path) {
		File targetFile = new File(path);
		File[] listFiles = targetFile.listFiles();
		if (listFiles.length > 0) {
			for (File file : listFiles) {
				String filepath = file.getAbsolutePath();
				logger.info("filepath:" + filepath);
				if (isContainDirectory(file)) {
					moveFile(filepath);
				} else {
					if (file.isDirectory()) {
						File swFile = new File(file.getParentFile() + "\\", "swf");
						file.renameTo(swFile);
						File mFile = new File(filepath);
						mFile.mkdir();
						File imageFile = new File(filepath + "\\", "图片");
						imageFile.mkdir();
						swFile.renameTo(new File(filepath + "\\", "swf"));
						logger.info(new File(filepath + "\\", "swf"));
					} else {
						File sourceFile=file.getParentFile();
						File swFile = new File(sourceFile.getParentFile() + "\\", "swf");
						file.getParentFile().renameTo(swFile);
						File mFile = new File(sourceFile.getAbsolutePath());
						mFile.mkdir();
						File imageFile = new File(sourceFile.getAbsolutePath() + "\\", "图片");
						imageFile.mkdir();
						swFile.renameTo(new File(sourceFile + "\\", "swf"));
						logger.info("file:"+new File(sourceFile + "\\", "swf"));
					}
				}
			}
		}
	}

	/**
	 * 判断一个文件夹是否包含文件夹
	 * 
	 * @param file
	 * @return 布尔值 false表示不包含文件夹，反之包含
	 */
	public boolean isContainDirectory(File file) {
		logger.info(file.getName()+":"+file.isFile());
		if (!file.exists()||file.isFile()) {
			return false;
		}
		File[] listFiles = file.listFiles();
		if (listFiles==null||listFiles.length > 0) {
			for (File checkFile : listFiles) {
				if (checkFile.isDirectory()) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 重命名文件夹
	 * 
	 * @param path
	 *            文件路径
	 */
	public void renameFile(String path) {
		File targetFile = new File(path);
		logger.info(path);
		File[] listFiles = targetFile.listFiles();
		if (listFiles.length > 0) {
			for (File file : listFiles) {
				String filepath = file.getAbsolutePath();
				if (file.isDirectory()) {
					if (file.getAbsolutePath().endsWith("mp4")) {
						File newFile = new File(filepath.replace("mp4", "swf"));
						file.renameTo(newFile);
						filepath = newFile.getAbsolutePath();
					}
					renameFile(filepath);
				}
			}
		}

	}
}
