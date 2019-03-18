package com.yanxi.animation.domain;

public class Employee {
	private int id;
	private String name;// 姓名
	private String gendar;// 性别
	private int age;// 年龄
	private int roleId;// 岗位信息
	private int deptId;// 部门信息
	private String degree;// 学历
	private String profission;//专业
	private String graduationSchool;// 毕业院校
	private String workDate;// 入职日期
	private String personId;// 身份证号
	private String familyAddress;// 家庭住址
	private String nowAddress;// 现住址
	private String telephone;// 电话
	private boolean isWork;// 在职情况

	public Employee() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getWorkDate() {
		return workDate;
	}

	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getProfission() {
		return profission;
	}

	public void setProfission(String profission) {
		this.profission = profission;
	}

	public String getFamilyAddress() {
		return familyAddress;
	}

	public void setFamilyAddress(String familyAddress) {
		this.familyAddress = familyAddress;
	}

	public String getNowAddress() {
		return nowAddress;
	}

	public void setNowAddress(String nowAddress) {
		this.nowAddress = nowAddress;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public boolean isWork() {
		return isWork;
	}

	public void setWork(boolean isWork) {
		this.isWork = isWork;
	}

	public String getGendar() {
		return gendar;
	}

	public void setGendar(String gendar) {
		this.gendar = gendar;
	}

	

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getGraduationSchool() {
		return graduationSchool;
	}

	public void setGraduationSchool(String graduationSchool) {
		this.graduationSchool = graduationSchool;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", gendar=" + gendar + ", age=" + age + ", roleId=" + roleId
				+ ", deptId=" + deptId + ", degree=" + degree + ", profission=" + profission + ", graduationSchool="
				+ graduationSchool + ", workDate=" + workDate + ", personId=" + personId + ", familyAddress="
				+ familyAddress + ", nowAddress=" + nowAddress + ", telephone=" + telephone + ", isWork=" + isWork
				+ "]";
	}

}