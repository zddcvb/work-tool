package com.yanxi.animate.tool.service.impl;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.yanxi.animate.tool.service.FileCheckService;

/**
 * 校验文件，主要以下几个方面： 
 * 1、是否存在data文件，并辨别其格式是否正确。 
 * 2、是否存在_标题.mp4、bg.png文件
 * 3、查询是否有多余的文件：wav、jpg、avi、txt、fla 
 * 4、查询是否存在swf和mp4文件共存的问题。
 * 5、检查swf、mp4、png和data文件命名是否一致。
 * @author 邹丹丹
 *
 */
public class SysFourFileCheckImpl implements FileCheckService {
	private static Logger logger = Logger.getLogger(SysFourFileCheckImpl.class);
	private String msg = "";

	@Override
	public String check(File file) {
		if (file.exists()) {
			File[] listFiles = file.listFiles();
			if (listFiles.length > 1) {
				for (File listFile : listFiles) {
					String filepath = listFile.getAbsolutePath();
					if (listFile.isFile()) {
						if (filepath.endsWith("_data")) {
							if (!checkDataFile(filepath)) {
								return msg;
							}
						} else if (filepath.endsWith(".swf") || filepath.endsWith(".mp4")) {
							if (!checkSwfAndMp4File(filepath)) {
								return msg;
							}
						} else if (filepath.endsWith(".png")) {
							if (!checkPngFileOrOther(filepath)) {
								return msg;
							}
						} else {
							if (filepath.contains(listFile.getParentFile().getName() + ".txt")) {
								continue;
							} else {
								msg = filepath + "是一个多余的文件，请删除！";
								logger.info(msg);
								return msg;
							}
						}
					} else {
						if (StringUtils.isEmpty(msg) || "校验成功，文件无误".equals(msg)) {
							check(listFile);
						}
					}
				}
			}
		}
		if (StringUtils.isEmpty(msg)) {
			msg = "校验成功，文件无误";
		}
		return msg;
	}

	/**
	 * 校验data文件是否存在，并是否符合规则
	 * 
	 * @param filepath
	 * @return
	 */
	private boolean checkDataFile(String filepath) {
		File file = new File(filepath);
		String dataName = file.getName();
		String[] dataArrs = dataName.split("_");
		if (dataArrs.length != 6) {
			msg = dataName + "格式错误";
			logger.info(msg);
			return false;
		} else if (dataArrs.length == 6) {
			if ("0".equals(dataArrs[4]) || "1".equals(dataArrs[4])) {
				String module = dataArrs[1];
				File parentFile = file.getParentFile();
				File swfFile = new File(parentFile.getAbsolutePath(),
						"/" + parentFile.getName() + "_" + module + ".swf");
				File mp4File = new File(parentFile.getAbsolutePath(),
						"/" + parentFile.getName() + "_" + module + ".mp4");
				if (!mp4File.exists()) {
					if (!swfFile.exists()) {
						msg = swfFile.getName() + "不存在或者命名错误！！！";
						return false;
					}
				} else {
					if (swfFile.exists()) {
						msg = module + "的mp4文件和swf文件同时存在，请删除一个！！！";
						return false;
					}
				}
				File selectPngFile = new File(parentFile.getAbsolutePath(),
						"/" + parentFile.getName() + "_" + module + "_选中.png");
				if (!selectPngFile.exists()) {
					msg = selectPngFile.getName() + "不存在或者命名错误！！！";
					return false;
				}
				File unselectPngFile = new File(parentFile.getAbsolutePath(),
						"/" + parentFile.getName() + "_" + module + "_未选中.png");
				if (!unselectPngFile.exists()) {
					msg = unselectPngFile.getName() + "不存在或者命名错误！！！";
					return false;
				}
				return true;
			} else {
				msg = dataName + "格式错误，丢失guide动画的标识：0或1";
				logger.info(msg);
				return false;
			}

		}

		return true;
	}

	/**
	 * 查询是否存在swf和mp4文件，包含标题文件，同时辨别是否存在swf和mp4同时存在的问题，如果同时存在，打印信息 1、
	 * 
	 * @param filepath
	 * @return
	 */
	private boolean checkSwfAndMp4File(String filepath) {
		File file = new File(filepath);
		if (filepath.contains("_标题.mp4")) {
			return true;
		}
		if (filepath.endsWith(".mp4")) {
			return true;
		}
		if (filepath.endsWith("_guide.swf")) {
			return true;
		}
		if (filepath.endsWith(".swf")) {
			String mp4path = filepath.replace(".swf", ".mp4");
			File mp4File = new File(mp4path);
			if (mp4File.exists()) {
				// mp4和swf同时存在，则返回false，打印swf文件多余
				msg = file.getName() + "多余，已存在相同命名的mp4文件";
				logger.info(msg);
				return false;
			} else {
				return true;
			}
		}
		return true;
	}

	/**
	 * 查询是否存在png图，同时查询是否存在选中和未选中的png图。
	 * 
	 * @param filepath
	 * @return
	 */
	private boolean checkPngFileOrOther(String filepath) {
		if (filepath.endsWith("_选中.png")) {
			String unselect_png = filepath.replace("_选中", "_未选中");
			File unselectFile = new File(unselect_png);
			if (!unselectFile.exists()) {
				msg = unselectFile.getName() + "图片不存在";
				logger.info(msg);
				return false;
			}
		}
		if (filepath.endsWith("_未选中.png")) {
			String select_png = filepath.replace("_未选中", "_选中");
			File selectFile = new File(select_png);
			if (!selectFile.exists()) {
				msg = selectFile.getName() + "图片不存在";
				logger.info(msg);
				return false;
			}
		}
		return true;
	}

	@Override
	public void clearCache() {
		logger.info(msg);
		if (!StringUtils.isEmpty(msg)) {
			msg = "";
		}
	}

	@Test
	public void test() {
		/*
		 * boolean checkDataFile =
		 * checkDataFile("D:\\比较粗细\\1_导入：金箍棒变变变_108.9_192.9_0_2data");
		 * logger.info(checkDataFile);
		 */
		String check = check(new File("D:\\新建文件夹"));
		logger.info(check);
	}

}
