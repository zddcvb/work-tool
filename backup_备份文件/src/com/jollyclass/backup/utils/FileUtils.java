package com.jollyclass.backup.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件工具类：格式化文件大小、获得文件创建时间、最后修改时间、格式化时间
 * 
 * @author 邹丹丹
 * 
 *
 */
public class FileUtils {
	public static long length;
	public static final String DIR_FLAG = "DIR";
	public static final String FILE_FLAG = "FILE";
	public static final String FILE_NO_SUFFIX = "FILE_NO_SUFFIX";
	private static List<String> dirList = new ArrayList<>();
	private static int index = 0;

	/**
	 * 格化式文件大小
	 * 
	 * @param size
	 *            文件大小，long类型
	 * @return 字符串类型的文件容量
	 */
	public static String getDataSize(long size) {
		DecimalFormat formater = new DecimalFormat("####.00");
		long kb = 1024;
		long mb = 1024 * 1024;
		long gb = 1024 * 1024 * 1024;
		long tb = (Long.valueOf(1024)) * (Long.valueOf(1024)) * (Long.valueOf(1024)) * (Long.valueOf(1024));
		if (size < kb) {
			return size + "bytes";
		} else if (size < mb) {
			float kbsize = size / 1024f;
			return formater.format(kbsize) + "KB";
		} else if (size < gb) {
			float mbsize = size / 1024f / 1024f;
			return formater.format(mbsize) + "MB";
		} else if (size < tb) {
			float gbsize = size / 1024f / 1024f / 1024f;
			System.out.println(formater.format(gbsize) + "GB");
			return formater.format(gbsize) + "GB";
		} else {
			return "size: error";
		}
	}

	/**
	 * 读取 文件夹、带后缀的文件，不带后缀的文件的创建时间
	 * 
	 * @param file
	 *            具体的文件
	 * @return 具体的时间
	 */
	public static String getCreateTime(File file) {
		String strTime = null;
		String endStr = "";
		String flag = "";
		String path = file.getAbsolutePath();
		if (file.exists() && file.isFile()) {
			// System.out.println(path);
			if (file.getName().contains(".")) {
				// System.out.println(file.getName());
				endStr = path.substring(path.lastIndexOf("."));
				flag = FILE_FLAG;
			} else {
				// System.out.println(file.getName());
				flag = FILE_NO_SUFFIX;
				endStr = "";
			}
		} else {
			flag = DIR_FLAG;
		}
		// System.out.println(endStr);
		try {
			Process p = Runtime.getRuntime().exec("cmd /C dir " + path + "/tc /C");
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null) {
				boolean fileFlag = line.endsWith(endStr) && flag.equals(FILE_FLAG);
				boolean fileNoSuffixFlag = line.endsWith(file.getName()) && flag.equals(FILE_NO_SUFFIX);
				boolean dirFlag = line.contains("DIR") && flag.equals(DIR_FLAG);
				if (fileFlag || fileNoSuffixFlag || dirFlag) {
					strTime = line.substring(0, 17);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// System.out.println("创建时间 " + strTime);
		// 输出：创建时间 2009-08-17 10:21

		return strTime == null ? "null" : strTime.replace("/", "-").replace("  ", " ");
	}

	/**
	 * 获得文件的最后修改时间
	 * 
	 * @param file
	 *            读取的文件
	 * @return 字符串类型的时间
	 */
	public static String getLastModifyTime(File file) {
		if (!file.exists()) {
			return null;
		}
		long lastModified = file.lastModified();
		return formatTime(lastModified);
	}

	/**
	 * 格式化时间
	 * 
	 * @param time
	 *            需要格式化的时间
	 * @return 格式化后的时间
	 */
	public static String formatTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(time);
	}

	/**
	 * 判断一个文件夹是否包含文件夹
	 * 
	 * @param file
	 * @return 布尔值 false表示不包含文件夹，反之包含
	 */
	public static boolean isContainDirectory(File file) {
		File[] listFiles = file.listFiles();
		for (File checkFile : listFiles) {
			if (checkFile.isDirectory()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取文件夹下文件的容量以及当前文件夹的总容量
	 * 
	 * @param file
	 *            获取的文件夹
	 * @param flag
	 *            是否显示文件夹下的内容
	 * @return list集合
	 */
	public static List<String> getDirData(File file, boolean flag) {
		File[] listFiles = file.listFiles();
		index++;
		if (listFiles.length == 0) {
			String path = file.getAbsolutePath() + "\\0KB" + "\\" + getCreateTime(file) + "\\"
					+ getLastModifyTime(file);
			dirList.add(path);
		} else {
			if (isContainDirectory(file) && index == 1) {
				long size = getDirectorySize(file);
				String root = file.getAbsolutePath() + "\\" + getDataSize(size) + "\\" + getCreateTime(file) + "\\"
						+ getLastModifyTime(file);
				dirList.add(root);
			}
			for (File file2 : listFiles) {
				if (file2.isFile()) {
					if (flag) {
						String filepath = file2.getAbsolutePath() + "\\" + getDataSize(file2.length()) + "\\"
								+ getCreateTime(file2) + "\\" + getLastModifyTime(file2);
						dirList.add(filepath);
					}
				} else {
					getDirData(file2, flag);
					long size = getDirectorySize(file2);
					// System.out.println(file2.getName() + "：" +
					// getDataSize(size));
					String dirpath = file2.getAbsolutePath() + "\\" + getDataSize(size) + "\\" + getCreateTime(file2)
							+ "\\" + getLastModifyTime(file2);
					dirList.add(dirpath);
				}
			}
		}
		return dirList;
	}

	/**
	 * 执行获取文件信息操作：需清楚index和dirList的内容
	 * 
	 * @param file
	 *            文件夹
	 * @param flag
	 *            是否显示文件夹内容
	 * @return list集合
	 */
	public static List<String> getFileInfo(File file, boolean flag) {
		List<String> dirData = getDirData(file, flag);
		index = 0;
		dirList = new ArrayList<>();
		return dirData;
	}

	/**
	 * 获得文件夹容量
	 * 
	 * @param file
	 *            需要获取的文件夹
	 * @return long类型
	 */
	public static long getDirectorySize(File file) {
		long len = 0;
		for (File mFile : file.listFiles()) {
			if (mFile.isFile()) {
				len += mFile.length();
			} else {
				len += getDirectorySize(mFile);
			}
		}
		return len;
	}
}
