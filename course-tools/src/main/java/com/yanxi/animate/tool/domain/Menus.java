package com.yanxi.animate.tool.domain;

public class Menus {
	private int index;
	private double pointX;
	private double pointY;
	private int iconWidth;
	private int iconHeight;
	private String icon_selected;
	private String icon_unselected;
	private String menu_title;
	private String attribute;
	private String video_file;
	private String guide_animate;
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

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

	public String getGuide_animate() {
		return guide_animate;
	}

	public void setGuide_animate(String guide_animate) {
		this.guide_animate = guide_animate;
	}

	@Override
	public String toString() {
		return "Menus [index=" + index + ", pointX=" + pointX + ", pointY=" + pointY + ", iconWidth=" + iconWidth
				+ ", iconHeight=" + iconHeight + ", icon_selected=" + icon_selected + ", icon_unselected="
				+ icon_unselected + ", menu_title=" + menu_title + ", attribute=" + attribute + ", video_file="
				+ video_file + ", guide_animate=" + guide_animate + "]";
	}

	

}
