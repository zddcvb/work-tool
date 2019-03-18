package com.yanxi.filemove.utils;

import java.io.File;

public class FileMoveUtils {

	public FileMoveUtils() {
	}

	public void createNewFile(String path) {
		File targetFile = new File(path);
		File[] listFiles = targetFile.listFiles();
		if (listFiles.length > 0) {
			for (File file : listFiles) {
				String filepath = file.getAbsolutePath();
				if (isContainDirectory(file)) {
					createNewFile(filepath);
				} else {
					System.out.println(filepath);
					File swfFile = new File(filepath + "\\swf");
					File imageFile = new File(filepath + "\\图片");
					swfFile.mkdir();
					imageFile.mkdir();
					System.out.println(swfFile.getAbsolutePath());
					System.out.println("success");
				}
			}
		}
	}

	public void moveFile(String path) {
		File targetFile = new File(path);
		File[] listFiles = targetFile.listFiles();
		if (listFiles.length > 0) {
			for (File file : listFiles) {
				String filepath = file.getAbsolutePath();
				if (isContainDirectory(file)) {
					moveFile(filepath);
				}else{
					File swFile=new File(file.getParentFile()+"\\", "swf");
					//File swFile=new File(file.getParentFile()+"\\", "mp4");
					//File swFile=new File(file.getParentFile()+"\\", "mp3");
					//File swFile=new File(file.getParentFile()+"\\", "图片");
					boolean flag = file.renameTo(swFile);
					System.out.println(flag);
					File mFile=new File(filepath);
					mFile.mkdir();
					File imageFile=new File(filepath+"\\","图片");
					imageFile.mkdir();
					swFile.renameTo(new File(filepath+"\\","swf"));
					//swFile.renameTo(new File(filepath+"\\","mp4"));
					//swFile.renameTo(new File(filepath+"\\","mp3"));
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
		System.out.println(file.getAbsolutePath());
		if (file.isFile()) {
			return false;
		}
		File[] listFiles = file.listFiles();
		if (listFiles.length > 0) {
			for (File checkFile : listFiles) {
				if (checkFile.isDirectory()) {
					return true;
				}
			}
		}
		return false;
	}
	public void renameFile(String path){
		File targetFile=new File(path);
		System.out.println(path);
		File[] listFiles = targetFile.listFiles();
		if (listFiles.length>0) {
			for (File file : listFiles) {
				String filepath = file.getAbsolutePath();
				if (file.isDirectory()) {
					if (file.getAbsolutePath().endsWith("mp4")) {
						File newFile=new File(filepath.replace("mp4", "swf"));
						file.renameTo(newFile);
						filepath=newFile.getAbsolutePath();
					}
					renameFile(filepath);
				}
			}
		}
		
	}
}
