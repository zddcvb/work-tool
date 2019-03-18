package com.jollyclass.json.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;

import com.jollyclass.json.domain.ClassDetail;
import com.jollyclass.json.domain.LessonDetails;
import com.jollyclass.json.domain.Menus;

/**
 * 读取素材文件夹，将素材文件的各个数据保存到对对应的文件中。
 * 
 * @author zddcvb
 *
 */
public class JsonFileUtils {

	/**
	 * 课程名称
	 */
	private String lessson_name;
	/**
	 * 保存mp4、swf等格式的文件的数组
	 */
	private List<String> pngs = new ArrayList<String>();
	/**
	 * 背景图
	 */
	private String backgroud_pic = ChacterUtils.BACKGROUND_NAME;
	/**
	 * 动画名称
	 */
	private String titile_animate = "";
	/**
	 * 保存图片的排序、x、y坐标，格式:排序_名称_x_y_data
	 */
	private List<String> datas = new ArrayList<String>();
	public static String mHandleMsg = "";
	public int index = 0;
	private String title;
	private String path = "";

	public JsonFileUtils() {

	}

	/**
	 * 读取文件夹，获取lesson_name/titile_animate/backgroud_pic/pngs/datas
	 */
	public String readFile(String filePath) {
		File jsonFile = new File(filePath);
		if (!jsonFile.exists()) {
			mHandleMsg = jsonFile + "不存在";
			return mHandleMsg;
		}
		// 获取lesson_name
		String filename = jsonFile.getName();
		lessson_name = filename;
		title = lessson_name + ChacterUtils.TITLE_NAME_PREFIX;
		File[] listFiles = jsonFile.listFiles();
		if (listFiles.length < 1) {
			titile_animate = "";
		} else {
			for (File file : listFiles) {
				path = filePath;
				String name = file.getName();
				if (file.isDirectory()) {
					path = file.getAbsolutePath();
					readFile(path);
				} else {
					collectData(name);
				}
			}
			if (mHandleMsg != "") {
				return mHandleMsg;
			}
			writeData();
		}

		return mHandleMsg;

	}
	/**
	 * 获取文件夹中的swf文件和data文件
	 * @param name
	 */
	private void collectData(String name) {
		if (name.contains(lessson_name) && !name.contains(ChacterUtils.TITLE_CONTAINS)
				&& !name.contains(ChacterUtils.PNG_PREFIX) && !name.contains(ChacterUtils.TXT_PREFIX)
				&& !name.contains(ChacterUtils.FLA_PREFIX) && !name.contains(ChacterUtils.DATA_PREFIX)) {
			pngs.add(name);
		} else if (name.contains(ChacterUtils.DATA_PREFIX)) {
			datas.add(name);
		} else if (name.contains(ChacterUtils.TITLE_NAME_PREFIX)) {
			titile_animate = title;
		}
	}
	/**
	 * 写入txt文件
	 */
	private void writeData() {
		if (path == "") {
			return;
		}
		ClassDetail classDetail = createDomain(path);
		System.out.println("错误信息：" + mHandleMsg);
		if (mHandleMsg != "") {
			clearCache();
			return;
		}
		String jsonData = GsonUtils2.objectToJson(classDetail);
		System.out.println(jsonData);
		String savePath = path + "\\" + path.substring(path.lastIndexOf("\\") + 1) + ".txt";
		System.out.println("保存的路径：" + savePath);
		FileUtils.writeData(savePath, jsonData);
		clearCache();
	}
	/**
	 * 清理swf和data集合的数据
	 */
	private void clearCache() {
		pngs.clear();
		datas.clear();
		path = "";
		titile_animate="";
	}

	/**
	 * 获取ClassDetail信息
	 * @param jsonPath 需要生成json的文件夹
	 * @return classDetail对象
	 */
	public ClassDetail createDomain(String jsonPath) {
		System.out.println(pngs);
		if (pngs.size() > 0) {
			ClassDetail classDetail = new ClassDetail();
			classDetail.setLesson_name(lessson_name);
			LessonDetails details = new LessonDetails();
			details.setBackgroud_pic(backgroud_pic);
			details.setTitile_animate(titile_animate);
			List<Menus> menus = new ArrayList<Menus>();
			for (String string : pngs) {
				Menus menu = new Menus();
				String menu_title = string.substring(string.lastIndexOf("_") + 1, string.lastIndexOf("."));
				String attribute = string.substring(string.lastIndexOf(".") + 1);
				String icon_selected = lessson_name + ChacterUtils.SPERATOR + menu_title + ChacterUtils.SELECTED_PREFIX;
				String icon_unselected = lessson_name + ChacterUtils.SPERATOR + menu_title
						+ ChacterUtils.UNSELECTED_PREFIX;
				System.out.println(icon_selected);
				File selectedFile = new File(jsonPath + ChacterUtils.PATH_SPERATOR + icon_selected);
				File unSelectedFile = new File(jsonPath + ChacterUtils.PATH_SPERATOR + icon_unselected);
				if (!selectedFile.exists()) {
					mHandleMsg = selectedFile.getName() + ChacterUtils.ERROR_MSG;
					return null;
				}
				if (!unSelectedFile.exists()) {
					mHandleMsg = unSelectedFile.getName() + ChacterUtils.ERROR_MSG;
					return null;
				}
				int[] arrs = getPngWH(jsonPath + ChacterUtils.PATH_SPERATOR + icon_selected);
				String[] titles = checked(jsonPath.substring(jsonPath.lastIndexOf("\\") + 1), menu_title);
				if (titles == null) {
					return null;
				}
				menu.setIndex(Integer.parseInt(titles[0]));
				menu.setPointX(Double.parseDouble(titles[2]));
				menu.setPointY(Double.parseDouble(titles[3]));
				menu.setIconWidth(arrs[0]);
				menu.setIconHeight(arrs[1]);
				menu.setIcon_selected(icon_selected);
				menu.setIcon_unselected(icon_unselected);
				menu.setAttribute(attribute);
				menu.setMenu_title(menu_title);
				menu.setVideo_file(string);
				menus.add(menu);
			}

			Collections.sort(menus, new Comparator<Menus>() {

				@Override
				public int compare(Menus o1, Menus o2) {
					int menu1_index = o1.getIndex();
					int menu2_index = o2.getIndex();
					int index = menu1_index - menu2_index;
					return index;
				}

			});
			details.setMenus(menus);
			classDetail.setLessonDetails(details);
			return classDetail;
		}
		return null;
	}

	/**
	 * 获取图片的宽高
	 * 
	 * @param pngPath
	 *            图片的路径
	 * @return 宽高的数组
	 */
	private int[] getPngWH(String pngPath) {
		int[] arrs = new int[2];
		File file = new File(pngPath);
		System.out.println(file.getAbsolutePath());
		try {
			BufferedImage bufi = ImageIO.read(file);
			int height = bufi.getHeight();
			int width = bufi.getWidth();
			arrs[0] = width;
			arrs[1] = height;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arrs;
	}

	/**
	 * 根据字符，判断是否在datas中存在，若存在返回字符串拆分的数组。
	 * @param fileName 当前的文件名称
	 * @param title 标题名称，需要判断的字符串
	 *            
	 * @return 字符串数组
	 */
	public String[] checked(String fileName, String title) {
		/*if (datas.size()!=pngs.size()) {
			mHandleMsg = fileName + ChacterUtils.DATA_NUMS_ERROR;
			return null;
		}*/
		for (String string : datas) {
			if (string.contains(title)) {
				String[] arrs = string.split(ChacterUtils.SPERATOR);
				if (arrs.length != 5) {
					mHandleMsg = fileName + ChacterUtils.DATA_ERROR + string;
					return null;
				}
				index = 0;
				return arrs;
			} else {
				index = 1;
			}
		}
		if (index == 1) {
			mHandleMsg = fileName + ChacterUtils.DATA_MSG + title;
		}
		return null;
	}
}
