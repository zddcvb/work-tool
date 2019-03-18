package com.jollyclass.json.domain;

import java.util.List;
/**
 * 课件的信息
 * @author Administrator
 *
 */
public class LessonDetails {
	/**
	 * 标题名称
	 */
	private String titile_animate;
	/**
	 * 菜单项
	 */
	private List<Menus> select_menus;
	/**
	 * 背景图
	 */
	private String backgroud_pic;

	public String getTitile_animate() {
		return titile_animate;
	}

	public void setTitile_animate(String titile_animate) {
		this.titile_animate = titile_animate;
	}

	public List<Menus> getMenus() {
		return select_menus;
	}

	public void setMenus(List<Menus> select_menus) {
		this.select_menus = select_menus;
	}

	public String getBackgroud_pic() {
		return backgroud_pic;
	}

	public void setBackgroud_pic(String backgroud_pic) {
		this.backgroud_pic = backgroud_pic;
	}

	@Override
	public String toString() {
		return "LessonDetails [titile_animate=" + titile_animate + ", select_menus=" + select_menus + ", backgroud_pic="
				+ backgroud_pic + "]";
	}

}
