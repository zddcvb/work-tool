package com.jollyclass.zipDic.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils2 {

	public ZipUtils2() {

	}

	public void zip(String sourceFileName) throws Exception {
		System.out.println("压缩中...");
		File sourceFile = new File(sourceFileName);
		if (!checkDirectoryIsExits(sourceFile)) {
			String zipFileName = sourceFile.getParentFile() + "/" + sourceFile.getName() + ".zip";
			System.out.println(zipFileName);
			// 创建zip输出流
			// ZipOutputStream out = new ZipOutputStream(new
			// FileOutputStream(zipFileName));
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName), Charset.forName("utf-8"));
			// 调用函数
			compress(out, sourceFile, sourceFile.getName());
			out.close();
			System.out.println("压缩完成");
		} else {
			File[] listFiles = sourceFile.listFiles();
			for (File file : listFiles) {
				zip(file.getAbsolutePath());
			}
		}

	}

	private boolean checkDirectoryIsExits(File file) {
		File[] listFiles = file.listFiles();
		for (File checkFile : listFiles) {
			if (checkFile.isDirectory()) {
				return true;
			}
		}
		return false;
	}

	public void compress(ZipOutputStream out, File sourceFile, String base) throws Exception {
		// 如果路径为目录（文件夹）
		if (sourceFile.isDirectory()) {
			// 取出文件夹中的文件（或子文件夹）
			File[] flist = sourceFile.listFiles();

			if (flist.length == 0)// 如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
			{
				System.out.println(base + "/");
				out.putNextEntry(new ZipEntry(base + "/"));
			} else// 如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
			{
				for (int i = 0; i < flist.length; i++) {
					compress(out, flist[i], flist[i].getName());
				}
			}
		} else// 如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
		{
			zipSingalFile(out, sourceFile, base);
		}
	}

	private void zipSingalFile(ZipOutputStream out, File sourceFile, String base)
			throws IOException, FileNotFoundException {
		out.putNextEntry(new ZipEntry(base));
		FileInputStream fis = new FileInputStream(sourceFile);
		int len = 0;
		byte[] buffer = new byte[1024];
		System.out.println(base);
		// 将源文件写入到zip文件中
		while ((len = fis.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		fis.close();
	}
}
