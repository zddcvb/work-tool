package com.yanxi.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class SortUpFIle {
	private static File[] listFiles;
	public static void main(String[] args) throws IOException {
		String path="H:\\动画部项目\\阳光视界项目\\小水滴课堂V4.0\\创课文件\\制作文件\\新版播放器\\教学资源\\创课-唐诗";
		sortUpFIle(path);
	}

	public static void sortUpFIle(String path) throws IOException {
		File file=new File(path);
		listFiles = file.listFiles();
		for (File listFile : listFiles) {
			if(!listFile.exists()){
				continue;
			}
			if (listFile.isFile()) {
				System.out.println(listFile.isFile());
				if (listFile.getAbsolutePath().endsWith(".fla")) {
					//System.out.println(listFile.getName());
					String prefix = FilenameUtils.getBaseName(listFile.getName());
					File newFile=new File(listFile.getParentFile(),"/"+prefix);
					newFile.mkdir();
					File small_png=new File(listFile.getParent(),"/"+prefix+".swf");
					//File large_png=new File(listFile.getParent(),"/"+prefix+"_large.jpg");
					FileUtils.copyFileToDirectory(small_png, newFile);
					//FileUtils.copyFileToDirectory(large_png, newFile);
					FileUtils.copyFileToDirectory(listFile, newFile);
					listFile.delete();
					//large_png.delete();
					small_png.delete();					
				}
			}else{
				sortUpFIle(listFile.getAbsolutePath());
			}
		}
	}
}
