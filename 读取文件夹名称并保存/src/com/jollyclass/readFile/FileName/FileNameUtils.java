package com.jollyclass.readFile.FileName;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.omg.CORBA.Environment;

public class FileNameUtils {
	private StringBuilder builder=new StringBuilder();
	private String basePath="C:\\Users\\Administrator\\Desktop\\";
	public void create(String path){
		//读取文件，保存文件名称
		StringBuilder nameBuilder=readFile(path);
		//写入txt中
		String out=basePath+path.substring(path.lastIndexOf("\\"))+".txt";
		writeTxt(nameBuilder.toString(),out);
	}
	private StringBuilder readFile(String filePath) {
		File file=new File(filePath);
		File[] listFiles = file.listFiles();
		for (File mFile : listFiles) {
			if (mFile.isDirectory()) {
				if (!isContainDirectory(mFile)) {
					builder.append(mFile.getName()+"\r\n");
				}				
				readFile(mFile.getAbsolutePath());
			}
		}
		return builder;
	}

	private void writeTxt(String str,String path) {
		FileWriter writer=null;
		try {
			writer = new FileWriter(path);
			writer.write(str);
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally {
			if (writer!=null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
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
	public  boolean isContainDirectory(File file) {
		File[] listFiles = file.listFiles();
		for (File checkFile : listFiles) {
			if (checkFile.isDirectory()) {
				return true;
			}
		}
		return false;
	}
}
