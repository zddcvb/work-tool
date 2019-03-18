package com.yanxi.animate.tool.domain;
/**
 * 整个课件属性
 * @author Administrator
 *
 */
public class ClassDetail {
	/**
	 * 课件名称
	 */
	private String lesson_name;
	/**
	 * 课件详情对象
	 */
	private LessonDetails lesson_detail;

	public String getLesson_name() {
		return lesson_name;
	}

	public void setLesson_name(String lesson_name) {
		this.lesson_name = lesson_name;
	}

	public LessonDetails getLessonDetails() {
		return lesson_detail;
	}

	public void setLessonDetails(LessonDetails lesson_detail) {
		this.lesson_detail = lesson_detail;
	}

	@Override
	public String toString() {
		return "ClassDetail [lesson_name=" + lesson_name + ", lesson_detail=" + lesson_detail + "]";
	}

}
