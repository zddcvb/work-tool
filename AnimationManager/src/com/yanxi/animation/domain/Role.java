package com.yanxi.animation.domain;

public class Role {
	private int id;
	private String roleName;//岗位名称
	private int deptId;//部门id
	public Role() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public int getDeptId() {
		return deptId;
	}


	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}


	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + ", deptId=" + deptId + "]";
	}


}
