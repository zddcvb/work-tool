package com.jollyclass.attence.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.jollyclass.attence.constant.StringUtils;

/**
 * 通用工具类 用来获取时间、日期等等。
 * 
 * @author saber
 *
 */
public class CommonUtils {
	/**
	 * 查询当前月的工作天数
	 * 
	 * @param date
	 *            需要查询的日期
	 * @return 工作天数
	 */
	public static int getResultByMonth(String date) {
		int count = 0;
		int month = Integer.parseInt(date.substring(5, 7));
		int year = Integer.parseInt(date.substring(0, 4));
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		while (calendar.get(Calendar.MONDAY) < month) {
			int day = calendar.get(Calendar.DAY_OF_WEEK);
			if (!(day == Calendar.SUNDAY || day == Calendar.SATURDAY)) {
				count++;
			}
			System.out.println(day);
			calendar.add(Calendar.DATE, 1);
		}
		System.out.println("当前总共的工作日有：" + count);
		return count;
	}

	/**
	 * 根据给定的日期，查询一个月当中所有的工作日，并返回list集合
	 * 
	 * @param date
	 *            需要查询的日期，格式为2017-06
	 * @return list集合
	 */
	public static List<String> getResultsByMonth(String date) {
		System.out.println("========getResultsByMonth==============");
		List<String> weekIndexs = new ArrayList<String>();
		int month = Integer.parseInt(date.substring(5, 7));
		int year = Integer.parseInt(date.substring(0, 4));
		System.out.println(month + " " + year);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		int index = calendar.get(Calendar.MONDAY);
		System.out.println("index:" + calendar.get(Calendar.MONDAY));
		while (calendar.get(Calendar.MONDAY) < month) {
			int day = calendar.get(Calendar.DAY_OF_WEEK);
			if (!(day == Calendar.SUNDAY || day == Calendar.SATURDAY)) {
				Date mDate = (Date) calendar.getTime().clone();
				String parse = new SimpleDateFormat("yyyy-MM-dd").format(mDate);
				weekIndexs.add(parse);
			}
			calendar.add(Calendar.DATE, 1);
			int flag= calendar.get(Calendar.MONDAY);
			System.out.println("while :" +  calendar.get(Calendar.MONDAY));
			if (index==11&&flag==0) {
				break;
			}
			
			
		}
		System.out.println("当前总共的工作日有：" + weekIndexs.size());
		return weekIndexs;
	}

	/**
	 * 读写数据保存到磁盘中
	 * 
	 * @param buffer
	 *            将循环得到的数据保存到stringbuffer中
	 * @param path
	 *            保存的地址
	 */
	public static void writeTxt(StringBuffer buffer, String path) {
		// 保存数据
		Writer bufr = null;
		try {
			File file = new File(path);
			bufr = new FileWriter(file);
			bufr.write(buffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (buffer != null) {
				try {
					bufr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 根据打卡时间获得星期
	 * 
	 * @param string
	 *            需要转换的日期和时间
	 * @return 返回星期
	 */
	public static String getweek(String string) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat weekFormat = new SimpleDateFormat("EEEE");
		String week = null;
		try {
			Date date = format.parse(string);
			week = weekFormat.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return week;
	}

	/**
	 * 判断name是否为空，或者为默认显示的值
	 * 
	 * @param str
	 *            控件的内容
	 * @param content
	 *            定义的内容，判断控件的内容是否等于其初始值
	 * @return 布尔值，如果为true，则不为空，如果为false，则为空。
	 */
	public static boolean isEmpty(String str, String content) {
		// System.out.println("=======isEmpty========");
		boolean flag = false;
		if (str == null || str.length() < 1 || str.equals(content)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据考勤记录时间，与标准时间比对，获得迟到早退的时间
	 * 
	 * @param targetTime
	 *            考勤的记录时间，包含了年月日时分秒
	 * @return 数组
	 */
	public static String[] getSeconds(String targetTime) {
		// System.out.println("=====getSeconds======");
		String[] status = new String[3];
		targetTime = targetTime.substring(targetTime.indexOf(":") - 2, targetTime.length());
		// 分割数组
		String[] targets = targetTime.split(":");
		String[] monring = StringUtils.MONRING_TIME.split(":");
		String[] night = StringUtils.NIGHT_TIME.split(":");
		//
		int hours = Integer.parseInt(targets[0]);
		int mins = Integer.parseInt(targets[1]);
		int secodes = Integer.parseInt(targets[2]);
		int hourNum;
		int minsNum;
		int secodesNum;
		if (hours >= 9 && hours < 12) {
			status[0] = StringUtils.WOKING_FIELD;
			if (hours >= 9 | mins >= 0 | secodes > 0) {
				status[1] = StringUtils.LATE_FIELD;
				hourNum = hours - Integer.parseInt(monring[0]);
				minsNum = mins - Integer.parseInt(monring[1]);
				secodesNum = secodes - Integer.parseInt(monring[2]);
				status[2] = hourNum + "时" + minsNum + "分" + secodesNum + "秒";
			} else {
				status[1] = StringUtils.COMMON_FIELD;
				status[2] = StringUtils.COMMON_FIELD;
			}
		} else if (hours < 9) {
			status[0] = StringUtils.WOKING_FIELD;
			status[1] = StringUtils.COMMON_FIELD;
			status[2] = StringUtils.COMMON_FIELD;
		} else {
			status[0] = StringUtils.WOKED_FIELD;
			if (hours < 18) {
				status[1] = StringUtils.LEFT_EARLY;
				if ((hours == 13 && mins <= 30) || hours == 12) {
					hourNum = Integer.parseInt(night[0]) - (hours + 1) - 1;
					minsNum = 30;
					secodesNum = 0;
				} else {
					hourNum = Integer.parseInt(night[0]) - hours - 1;
					minsNum = 60 - mins - 1;
					secodesNum = 60 - secodes;
				}
				status[2] = hourNum + "时" + minsNum + "分" + secodesNum + "秒";
			} else {
				status[1] = StringUtils.COMMON_FIELD;
				status[2] = StringUtils.COMMON_FIELD;
			}
		}
		return status;
	}

}
