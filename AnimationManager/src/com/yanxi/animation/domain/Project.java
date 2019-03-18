package com.yanxi.animation.domain;

public class Project {
	private int id;
	private String projectName;//项目名称
	private String hardLevel;//难易级别
	private String animationName;//动画名称
	private int completedNum;//完成数量
	private String description;//动画描述
	private String employeeId;//负责人id
	private String joinId;//参与人员id
	private String year;//评估时间
	public Project() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getHardLevel() {
		return hardLevel;
	}

	public void setHardLevel(String hardLevel) {
		this.hardLevel = hardLevel;
	}

	public String getAnimationName() {
		return animationName;
	}

	public void setAnimationName(String animationName) {
		this.animationName = animationName;
	}

	public int getCompletedNum() {
		return completedNum;
	}

	public void setCompletedNum(int completedNum) {
		this.completedNum = completedNum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getJoinId() {
		return joinId;
	}

	public void setJoinId(String joinId) {
		this.joinId = joinId;
	}


	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}


	@Override
	public String toString() {
		return "Project [id=" + id + ", projectName=" + projectName + ", hardLevel=" + hardLevel + ", animationName="
				+ animationName + ", completedNum=" + completedNum + ", description=" + description + ", employeeId="
				+ employeeId + ", joinId=" + joinId + ", year=" + year + "]";
	}
	

}