package com.yanxi.animate.tool.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.yanxi.animate.tool.constant.ChacterUtils;
import com.yanxi.animate.tool.domain.ClassDetail;
import com.yanxi.animate.tool.domain.LessonDetails;
import com.yanxi.animate.tool.domain.Menus;

/**
 * 读取素材文件夹，将素材文件的各个数据保存到对对应的文件中。
 * 
 * @author zddcvb
 *
 */
public class JsonFileUtils {
	private static Logger logger=Logger.getLogger(JsonFileUtils.class);
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
		logger.info("filename:" + filename);
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
	 * 获取文件夹中的swf文件或者mp4文件和data文件
	 * 
	 * @param name
	 *            需要判断的文件名
	 */
	private void collectData(String name) {
		if (name.contains(lessson_name) && !name.contains(ChacterUtils.TITLE_CONTAINS)
				&& (name.endsWith(".swf") || name.endsWith(".mp4"))) {
			pngs.add(name);
		} else if (name.contains(ChacterUtils.DATA_PREFIX)) {
			datas.add(name);
			logger.info("name:" + name);
		} else if (name.contains(ChacterUtils.TITLE_NAME_PREFIX)) {
			titile_animate = title;
		}else if (name.endsWith(".wav")) {
			mHandleMsg="当前文件存在wav文件，请删除！！！";
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
		logger.info("错误信息：" + mHandleMsg);
		if (mHandleMsg != "") {
			clearCache();
			return;
		}
		String jsonData = GsonUtils.objectToJson(classDetail);
		logger.info(jsonData);
		String savePath = path + "\\" + path.substring(path.lastIndexOf("\\") + 1) + ".txt";
		logger.info("保存的路径：" + savePath);
		FileUtils.writeData(savePath, jsonData);
		clearCache();
	}

	/**
	 * 获取ClassDetail信息
	 * 
	 * @param jsonPath
	 *            需要生成json的文件夹
	 * @return classDetail对象
	 */
	public ClassDetail createDomain(String jsonPath) {
		logger.info("pngs:" + pngs);
		List<String> newPngs = FileUtils.deleteSameNameItemFromList(pngs);
		if (newPngs.size() > 0) {
			ClassDetail classDetail = new ClassDetail();
			classDetail.setLesson_name(lessson_name);
			LessonDetails details = new LessonDetails();
			details.setBackgroud_pic(backgroud_pic);
			details.setTitile_animate(titile_animate);
			List<Menus> menus = new ArrayList<Menus>();
			for (String string : newPngs) {
				if (string.contains("_guide.")) {
					continue;
				}
				Menus menu = new Menus();
				String menu_title = string.substring(string.lastIndexOf("_") + 1, string.lastIndexOf("."));
				String attribute = string.substring(string.lastIndexOf(".") + 1);
				String icon_selected = lessson_name + ChacterUtils.SPERATOR + menu_title + ChacterUtils.SELECTED_PREFIX;
				String icon_unselected = lessson_name + ChacterUtils.SPERATOR + menu_title
						+ ChacterUtils.UNSELECTED_PREFIX;
				logger.info(icon_selected);
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
				//获取图片的x、y信息
				int[] arrs = getPngWH(jsonPath + ChacterUtils.PATH_SPERATOR + icon_selected);
				//读取data文件的信息，获取名称、x、y、引导动画、排序等相关信息
				String[] titles = checked(jsonPath.substring(jsonPath.lastIndexOf("\\") + 1), menu_title);
				if (titles == null) {
					return null;
				}
				String index = titles[0];
				String xPoint = titles[2];
				String yPoint = titles[3];
				String guide_flag=titles[4];
				// 判断x、y坐标，如果没有小数点，则添加.0，使其变成带有小数点
				// 将x、y坐标匹配一定的规则，利用正则表达式来匹配(\\d*[.]\\d*)
				Boolean xMatch = matchPoint(xPoint, titles, "的x坐标出现问题！！！");
				if (!xMatch) {
					return null;
				}
				Boolean yMatch = matchPoint(yPoint, titles, "的y坐标出现问题！！！");
				if (!yMatch) {
					return null;
				}
				if ("1".equals(guide_flag)) {
					String guide_animate=lessson_name + ChacterUtils.SPERATOR + menu_title
							+ChacterUtils.SPERATOR+"guide.swf";
					menu.setGuide_animate(guide_animate);
				}else if("0".equals(guide_flag)){
					menu.setGuide_animate("");
				}
				logger.info(xPoint + "::" + yPoint);
				menu.setIndex(Integer.parseInt(index));
				menu.setPointX(Double.parseDouble(xPoint));
				menu.setPointY(Double.parseDouble(yPoint));
				menu.setIconWidth(arrs[0]);
				menu.setIconHeight(arrs[1]);
				menu.setIcon_selected(icon_selected);
				menu.setIcon_unselected(icon_unselected);
				menu.setAttribute(attribute);
				menu.setMenu_title(menu_title);
				menu.setVideo_file(string);
				menus.add(menu);
			}
			// 重新针对菜单集合进行排序
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
			logger.info("classDetail" + classDetail.toString());
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
		logger.info(file.getAbsolutePath());
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
	 * 
	 * @param fileName
	 *            当前的文件名称
	 * @param title
	 *            标题名称，需要判断的字符串
	 * @return 字符串数组
	 */
	private String[] checked(String fileName, String title) {
		for (String string : datas) {
			String[] arrs = string.split(ChacterUtils.SPERATOR);
			if (arrs[1].equals(title)) {
				if (arrs.length != 6) {
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

	/**
	 * 清除静态变量的消息，使其变成空，如果重新执行新的生成操作，必须调用此方法
	 */
	public void clearMsg() {
		mHandleMsg = "";
	}

	/**
	 * 清理swf和data集合的数据
	 */
	private void clearCache() {
		pngs.clear();
		datas.clear();
		path = "";
		titile_animate = "";
	}

	/**
	 * 校验data数据中的x、y坐标，如果是整数，则添加.0字符串，如果为负数，则匹配(-\\d*[.]\\d*)，如果为正数，则匹配(\\d*[.]\
	 * \d*)
	 * 
	 * @param point
	 *            坐标值
	 * @param arrs
	 *            data数组
	 * @param msg
	 *            需要传递的msg
	 * @return boolean，true则通过，false则报错。
	 */
	private Boolean matchPoint(String point, String[] arrs, String msg) {
		String plusRegix = "(-\\d*[.]\\d*)";
		String commonRegix = "(\\d*[.]\\d*)";
		if (!point.contains(".")) {
			point += ".0";
		}
		if (point.startsWith("-")) {
			if (!point.matches(plusRegix)) {
				mHandleMsg = String.join("_", arrs) + msg;
				return false;
			}
		} else {
			if (!point.matches(commonRegix)) {
				mHandleMsg = lessson_name + ":" + String.join("_", arrs) + msg;
				return false;
			}
		}
		return true;
	}
}
