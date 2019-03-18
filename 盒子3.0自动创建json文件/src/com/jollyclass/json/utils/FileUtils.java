package com.jollyclass.json.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
/**
 * 读写文件
 * @author Administrator
 *
 */
public class FileUtils {
	/**
	 * 向文件中写入数据，已utf-8形式
	 * @param savePath 需要保存的文件
	 * @param data 写入的数据
	 */
	public static void writeData(String savePath,String data){
		OutputStream out=null;
		OutputStreamWriter osw=null;
		try {
			out = new FileOutputStream(savePath);
			 osw=new OutputStreamWriter(out, "utf-8");
			 osw.write(data);
			 osw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (osw!=null) {
				try {
					osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
}
