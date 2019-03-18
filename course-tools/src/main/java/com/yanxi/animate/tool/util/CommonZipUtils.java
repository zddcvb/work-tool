package com.yanxi.animate.tool.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.Test;
/**
 * 文件通用压缩工具
 * @author Administrator
 *
 */
public class CommonZipUtils {
	/**
	 * 压缩文件，zipFilePath最终文件保存的路径
	 * @param file
	 * @return
	 */
	public String zip(File file) {
		ZipOutputStream zout = null;
		boolean flag = FileUtils.checkDirectoryIsExits(file);
		if (!flag) {
			// 若当前文件夹下没有文件夹了
			String zipFilePath = file.getAbsolutePath() + "/" + file.getName() + ".zip";
			try {
				zout = new ZipOutputStream(new FileOutputStream(new File(zipFilePath)), Charset.forName("GBK"));
				compressFile(zout, file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (zout != null) {
					try {
						zout.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			// 若当前文件夹下有文件夹了
			File[] listFiles = file.listFiles();
			for (File listFile : listFiles) {
				zip(listFile);
			}
		}

		return "压缩成功";
	}
	/**
	 * 压缩文件的方法
	 * 1、如果是文件，则执行单个文件的压缩
	 * 2、如果是文件夹，则递归调用compressFile方法，知道文件夹下每个文件都执行zipSingleFile方法
	 * @param zout
	 * @param file
	 */
	private void compressFile(ZipOutputStream zout, File file) {
		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			if (listFiles.length < 1) {
				// 如果文件夹下没有文件
				return;
			} else {
				for (File listFile : listFiles) {
					if (listFile.getName().endsWith(".zip")) {
						continue;
					}
					compressFile(zout, listFile);
				}
			}
		} else {
			zipSingleFile(zout, file);
		}
	}
	/**
	 * 压缩单一一个文件
	 * @param zout
	 * @param file 需要压缩的文件，其中file.getName直接关系着单个文件压缩后保存的位置和路径，若想将文件压缩到一个文件夹下，则修改此处zipEntry的路径即可
	 */
	private void zipSingleFile(ZipOutputStream zout, File file) {
		InputStream is = null;
		try {
			ZipEntry zipEntry = new ZipEntry(file.getName());
			zout.putNextEntry(zipEntry);
			is = new FileInputStream(file);
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = is.read(buffer)) != -1) {
				zout.write(buffer, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@Test
	public void test() throws IOException{
		/*ZipOutputStream zout=new ZipOutputStream(new FileOutputStream(new File("d:/1.zip")), Charset.forName("GBK"));
		compressFile(zout,new File("d:/新建文件夹/夏天在哪里？"));
		zout.close();*/
		zip(new File("d:/新建文件夹"));
		//zipSingleFile(zout, new File("d:/1"));
	}
}
