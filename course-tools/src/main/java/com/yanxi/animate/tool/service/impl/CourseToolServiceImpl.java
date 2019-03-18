package com.yanxi.animate.tool.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.yanxi.animate.tool.domain.ClassDetail;
import com.yanxi.animate.tool.domain.LessonDetails;
import com.yanxi.animate.tool.domain.Menus;
import com.yanxi.animate.tool.service.CourseToolService;
import com.yanxi.animate.tool.util.CommonZipUtils;
import com.yanxi.animate.tool.util.FileUtils;
import com.yanxi.animate.tool.util.GsonUtils;
import com.yanxi.animate.tool.util.ImageUtil;

/**
 * 课件工具实现类
 * 
 * @author 邹丹丹
 *
 */
public class CourseToolServiceImpl implements CourseToolService {
	private static Logger logger = Logger.getLogger(CourseToolServiceImpl.class);
	private List<String> datas = new ArrayList<>();
	private String lessons_title_name = null;
	private String bg_name = "bg.png";
	private String json_path = "";
	public String msg = "";

	@Override
	public void zipFile(File file) {
		CommonZipUtils commonZipUtils = new CommonZipUtils();
		commonZipUtils.zip(file);
	}

	public void moveFile(File file) {

	}

	/**
	 * 创建json文件
	 */
	@Override
	public String createLessons(File file) {
		if (file.exists()) {
			File[] listFiles = file.listFiles();
			if (listFiles.length > 0) {
				// 获取文件夹中的data数据
				for (File listFile : listFiles) {
					if (listFile.isFile()) {
						collectDataFileInfo(listFile);
					} else {
						createLessons(listFile);
					}
				}
				logger.info(datas);
				if (datas.size() > 0) {
					writeJsonFile();
				}
			}
		}
		msg = "json文本创建成功！";
		return msg;
	}

	/**
	 * 获取data文件，并获取标题名
	 * 
	 * @param listFile
	 */
	private void collectDataFileInfo(File listFile) {
		String name = listFile.getName();
		String parentName = listFile.getParentFile().getName();
		if (name.contains("_data")) {
			json_path = listFile.getParentFile().getAbsolutePath();
			logger.info("json_path:" + json_path);
			lessons_title_name = parentName;
			logger.info("lessons_title_name:" + lessons_title_name);
			datas.add(name);
		}
	}

	/**
	 * 写入json文件
	 */
	private void writeJsonFile() {
		// 生成domain数据
		ClassDetail classDetail = createClassDetail();
		if (classDetail == null) {
			return;
		}
		// 将domain数据转成json文本
		String json = GsonUtils.objectToJson(classDetail);
		// 将json写入到本地磁盘
		// if (!StringUtils.isEmpty(lessons_title_name)) {
		String savePath = json_path + "/" + lessons_title_name + ".txt";
		FileUtils.writeData(savePath, json);
		clearCache();
		// }
	}

	/**
	 * 获取课件domain
	 * 
	 * @return
	 */
	private ClassDetail createClassDetail() {

		// 获取菜单集合， 给菜单进行排序,按照index进行排序
		List<Menus> menus = getMenus();
		if (menus == null) {
			logger.info(msg);
			return null;
		}
		menus = sortMenus(menus);
		// 结构datas数据文件，获得lesson课件
		String title_animate = "";
		if (!StringUtils.isEmpty(lessons_title_name)) {
			title_animate = lessons_title_name + "_标题.mp4";
			if (!checkFileIsExist(title_animate)) {
				msg = title_animate + "不存在！";
				logger.info(msg);
				return null;
			}
		}

		if (!checkFileIsExist(bg_name)) {
			msg = bg_name + "不存在";
			logger.info(msg);
			return null;
		}
		LessonDetails lessonDetails = new LessonDetails();
		lessonDetails.setTitile_animate(title_animate);
		lessonDetails.setMenus(menus);
		lessonDetails.setBackgroud_pic(bg_name);
		// 获得classdetails
		ClassDetail classDetail = new ClassDetail();
		classDetail.setLesson_name(lessons_title_name);
		classDetail.setLessonDetails(lessonDetails);
		return classDetail;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param filename
	 * @return
	 */
	private Boolean checkFileIsExist(String filename) {
		logger.info("jsonpath:" + json_path);
		String filepath = json_path + "/" + filename;
		logger.info("filepath:" + filepath);
		File file = new File(filepath);
		return !file.exists() ? false : true;
	}

	/**
	 * 给集合进行排序，根据index进行排序
	 * 
	 * @param menus
	 * @return
	 */
	private List<Menus> sortMenus(List<Menus> menus) {
		Collections.sort(menus, new Comparator<Menus>() {

			@Override
			public int compare(Menus o1, Menus o2) {
				return o1.getIndex() > o2.getIndex() ? 1 : -1;
			}

		});
		return menus;
	}

	/**
	 * 解析datas文件，获取menu菜单内容，并生成集合
	 * 
	 * @return
	 */
	private List<Menus> getMenus() {
		// 解析datas文件
		List<Menus> listMenus = new ArrayList<>();
		if (!datas.isEmpty()) {
			for (final String data : datas) {
				final String[] infos = data.split("_");
				final Menus menus = new Menus();
				menus.setIndex(Integer.parseInt(infos[0]));
				menus.setPointX(Double.parseDouble(infos[2]));
				menus.setPointY(Double.parseDouble(infos[3]));
				final String icon_selected = lessons_title_name + "_" + infos[1] + "_选中.png";
				final String icon_unselected = lessons_title_name + "_" + infos[1] + "_未选中.png";
				// 校审图片是否存在
				String[] selected_pngInfos = checkImgIsExist(json_path + "/" + icon_selected);
				if (selected_pngInfos.length <= 1) {
					msg = selected_pngInfos[0];
					return null;
				}
				final String[] unselected_pngInfos = checkImgIsExist(json_path + "/" + icon_unselected);
				if (unselected_pngInfos.length <= 1) {
					msg = unselected_pngInfos[0];
					return null;
				}
				// 判断swf或者mp4文件是否存在
				String video_file = getLessonType(infos[1]);
				String guide_animate = "";
				String attribute = "";
				if (video_file == null) {
					return null;
				}
				if (video_file.contains("_guide")) {
					guide_animate = video_file.substring(video_file.lastIndexOf("/") + 1, video_file.length());
					video_file = video_file.replace("_guide.swf", ".mp4");
					attribute = "mp4";
				} else {
					attribute = video_file.substring(video_file.lastIndexOf(".") + 1, video_file.length());
				}
				menus.setIconWidth(Integer.parseInt(selected_pngInfos[1]));
				menus.setIconHeight(Integer.parseInt(selected_pngInfos[2]));
				menus.setIcon_selected(icon_selected);
				menus.setIcon_unselected(icon_unselected);
				menus.setMenu_title(infos[1]);
				menus.setAttribute(attribute);
				menus.setVideo_file(video_file);
				menus.setGuide_animate(guide_animate);
				listMenus.add(menus);
			}
		}
		return listMenus;
	}

	/**
	 * 获取课件的类型
	 * 
	 * @param lessonName
	 * @return
	 */
	private String getLessonType(String lessonName) {
		String swfName = json_path + "/" + lessons_title_name + "_" + lessonName + ".swf";
		String mp4Name = json_path + "/" + lessons_title_name + "_" + lessonName + ".mp4";
		String guideName = json_path + "/" + lessons_title_name + "_" + lessonName + "_guide.swf";
		File mp4File = new File(mp4Name);
		if (mp4File.exists()) {
			File guideFile = new File(guideName);
			if (guideFile.exists()) {
				return lessons_title_name + "_" + lessonName + "_guide.swf";
			}
			return lessons_title_name + "_" + lessonName + ".mp4";
		}
		File swfFile = new File(swfName);
		if (swfFile.exists()) {
			return lessons_title_name + "_" + lessonName + ".swf";
		}
		msg = "swf和mp4文件都不存在";
		logger.info(msg);
		return null;
	}

	/**
	 * 获取图片的长宽
	 * 
	 * @param pngPath
	 * @return
	 */
	private String[] checkImgIsExist(String pngPath) {
		return ImageUtil.getImgWH(pngPath);
	}

	/**
	 * 清除缓存
	 */
	public void clearCache() {
		if (!datas.isEmpty()) {
			datas.clear();
		}
		// json_path = "";
		// lessons_title_name = "";
		msg = "";
		// json_path="";
	}

	@Test
	public void test_1() {
		createLessons(new File("d:/比较粗细"));
	}
}
