package com.yanxi.animation.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yanxi.animation.domain.Department;
import com.yanxi.animation.domain.Employee;
import com.yanxi.animation.domain.Project;
import com.yanxi.animation.domain.Role;
import com.yanxi.animation.util.JDBCUtil;
import com.yanxi.animation.util.PoiUtil;

public class ManagerTest {
	private JDBCUtil jdbcUtil;
	private PoiUtil poiUtil;

	@SuppressWarnings("resource")
	public void tearup() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		jdbcUtil = (JDBCUtil) context.getBean("jdbcUtil");
		poiUtil = (PoiUtil) context.getBean("poiUtil");
	}

	@Test
	public void test_dept() {
		tearup();
		PoiUtil util = new PoiUtil();
		List<Department> departments = util.readDeptData("d:/dept.xls");
		String message = jdbcUtil.importDeptData(departments);
		System.out.println(message);
	}

	@Test
	public void test_role() {
		tearup();
		List<Role> roles = poiUtil.readRoleData("d:/阳光视界岗位信息表.xls");
		String message = jdbcUtil.importRoleData(roles);
		System.out.println(message);
	}

	@Test
	public void test_emloyee() {
		tearup();
		List<Employee> employees = poiUtil.readEmployeeData("d:/动画部人员信息表.xls");
		String message = jdbcUtil.importEmployeeData(employees);
		System.out.println(message);
	}

	@Test
	public void test_project() {
		tearup();
		List<Project> projects = poiUtil.readProjectData("d:/动画部2017年工作总表.xls");
		String message = jdbcUtil.importProjectData(projects);
		System.out.println(message);
	}
}
