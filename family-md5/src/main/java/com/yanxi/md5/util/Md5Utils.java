package com.yanxi.md5.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class Md5Utils {
	public static List<FileMd5> fileMd5s = new ArrayList<FileMd5>();

	@SuppressWarnings("resource")
	public String createFile(File inputFile, File outputFile) {
		StringBuilder builder = new StringBuilder();
		try {
			md5(inputFile);
			for (FileMd5 fileMd5 : fileMd5s) {
				String string = fileMd5.getFileName() + "\n" + fileMd5.getMd5();
				builder.append(string + "\n");
			}
			if (!outputFile.exists()) {
				outputFile=new File("d:/output.txt");
			}
			Writer fw = new FileWriter(outputFile);
			fw.write(builder.toString());
			fw.flush();
			return "success";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "fail";
	}

	public void md5(File file) {
		if (file.exists()) {
			File[] listFiles = file.listFiles();
			if (listFiles != null) {
				for (File listFile : listFiles) {
					if (listFile.isFile()) {
						String path = listFile.getAbsolutePath();
						if (path.endsWith(".mp4") || path.endsWith(".swf")||path.endsWith(".mp3")) {
							FileMd5 fileMd5 = new FileMd5();
							String md5 = createMd5(listFile);
							fileMd5.setFileName(listFile.getName());
							fileMd5.setMd5(md5);
							fileMd5s.add(fileMd5);
						}
					} else {
						md5(listFile);
					}
				}
			}
		}
	}

	private String createMd5(File listFile) {
		String md5Hex = null;
		InputStream is = null;
		try {
			is = new FileInputStream(listFile);
			md5Hex=DigestUtils.md5Hex(is);
			System.out.println(md5Hex);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return md5Hex;
	}

	public static void clearCache(){
		if (fileMd5s!=null&&fileMd5s.size()>0) {
			fileMd5s.clear();
			fileMd5s=new ArrayList<>();
		}
	}
	@Test
	public void test_2() {
		createFile(new File("d:/mp4"), new File("d:/output.txt"));
	}
}
