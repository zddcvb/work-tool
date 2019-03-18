package com.jollyclass.json.domain;
/**
 * 选择界面的具体属性
 * @author Administrator
 *
 */
public class Menus1 {
	/**
	 * 菜单的x坐标
	 */
	private double pointX;
	/**
	 * 菜单的y坐标
	 */
	private double pointY;
	/**
	 * 菜单的宽
	 */
	private int iconWidth;
	/**
	 * 菜单的高
	 */
	private int iconHeight;
	/**
	 * 菜单的选中的名称
	 */
	private String icon_selected;
	/**
	 * 菜单的未选中的名称
	 */
	private String icon_unselected;
	/**
	 * 菜单的标题
	 */
	private String menu_title;
	/**
	 * 菜单的格式
	 */
	private String attribute;
	/**
	 * 菜单的名称
	 */
	private String video_file;

	public double getPointX() {
		return pointX;
	}

	public void setPointX(double pointX) {
		this.pointX = pointX;
	}

	public double getPointY() {
		return pointY;
	}

	public void setPointY(double pointY) {
		this.pointY = pointY;
	}

	public int getIconWidth() {
		return iconWidth;
	}

	public void setIconWidth(int iconWidth) {
		this.iconWidth = iconWidth;
	}

	public int getIconHeight() {
		return iconHeight;
	}

	public void setIconHeight(int iconHeight) {
		this.iconHeight = iconHeight;
	}

	public String getIcon_selected() {
		return icon_selected;
	}

	public void setIcon_selected(String icon_selected) {
		this.icon_selected = icon_selected;
	}

	public String getIcon_unselected() {
		return icon_unselected;
	}

	public void setIcon_unselected(String icon_unselected) {
		this.icon_unselected = icon_unselected;
	}

	public String getMenu_title() {
		return menu_title;
	}

	public void setMenu_title(String menu_title) {
		this.menu_title = menu_title;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getVideo_file() {
		return video_file;
	}

	public void setVideo_file(String video_file) {
		this.video_file = video_file;
	}

	@Override
	public String toString() {
		return "LessonDetail [pointX=" + pointX + ", pointY=" + pointY + ", iconWidth=" + iconWidth + ", iconHeight="
				+ iconHeight + ", icon_selected=" + icon_selected + ", icon_unselected=" + icon_unselected
				+ ", menu_title=" + menu_title + ", attribute=" + attribute + ", video_file=" + video_file + "]";
	}

}
