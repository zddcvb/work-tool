package com.yanxi.filemove.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

	private ZipOutputStream out;
	private String zipFileName;

	public ZipUtils() {

	}

	public void zip(String sourceFilepath) throws Exception {
		System.out.println("压缩中...");
		File sourceFile = new File(sourceFilepath);
		if (!checkDirectoryIsExits(sourceFile)) {
			File parenFile = sourceFile.getParentFile();
			zipFileName = parenFile.getParentFile() + "/" + parenFile.getName() + ".zip";
			System.out.println(zipFileName);
			out = new ZipOutputStream(new FileOutputStream(zipFileName), Charset.forName("utf-8"));
			// 调用函数
			System.out.println("name:" + parenFile.getName());
			compress(out, parenFile, parenFile.getName());
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
		if (file.isFile()) {
			return false;
		}
		File[] listFiles = file.listFiles();
		for (File checkFile : listFiles) {
			if (checkFile.isDirectory()) {
				return true;
			}
		}
		return false;
	}

	public void compress(ZipOutputStream out, File sourceFile, String base) throws Exception {
		System.out.println("sourceFile:" + sourceFile.getName());
		// 如果路径为目录（文件夹）
		if (sourceFile.isDirectory()) {
			// 取出文件夹中的文件（或子文件夹）
			File[] flist = sourceFile.listFiles();

			if (flist.length == 0)// 如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
			{
				base = base.substring(base.lastIndexOf("/")+1);
				System.out.println("base:" + base);
				out.putNextEntry(new ZipEntry(base + "/"));
			} else// 如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
			{
				for (int i = 0; i < flist.length; i++) {
					File firstFile = flist[i].getParentFile();
					System.out.println(firstFile.getName() + "/" + flist[i].getName());
					compress(out, flist[i], firstFile.getName() + "/" + flist[i].getName());
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
		// 将源文件写入到zip文件中
		while ((len = fis.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		fis.close();
	}
}
