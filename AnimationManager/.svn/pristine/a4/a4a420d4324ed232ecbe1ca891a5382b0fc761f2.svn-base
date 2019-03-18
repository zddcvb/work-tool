package com.yanxi.animation.util;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import com.yanxi.animation.domain.Department;
import com.yanxi.animation.domain.Employee;
import com.yanxi.animation.domain.Project;
import com.yanxi.animation.domain.Role;
import com.yanxi.animation.service.DepartmentService;
import com.yanxi.animation.service.EmployeeService;
import com.yanxi.animation.service.ProjectService;
import com.yanxi.animation.service.RoleService;

/**
 * 处理导入和查询数据库操作。
 * 
 * @author Administrator
 *
 */
public class JDBCUtil {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private EmployeeService employeeService;
	private static Logger log = Logger.getLogger(JDBCUtil.class);

	/**
	 * 将项目数据导入数据库中
	 * 
	 * @param projects
	 *            需要导入的项目数据
	 * @return 返回一个消息字符串
	 */
	public String importProjectData(List<Project> projects) {
		log.info("importProjectData");
		String message = "";
		if (projects.size() > 0) {
			for (Project project : projects) {
				projectService.save(project);
			}
			message = MessageField.IMPORT_DATABASE_SUCCESS;
		} else {
			message = MessageField.IMPORT_DATABASE_FAIL;
		}
		return message;
	}

	/**
	 * 导入部门信息数据到数据库中
	 * 
	 * @param departments
	 *            需要导入的部门数据
	 * @return 返回一个消息的字符串
	 */
	public String importDeptData(List<Department> departments) {
		log.info("importDeptData");
		String message = "";
		if (departments.size() > 0) {
			log.info("import start");
			for (Department dept : departments) {
				log.info(departmentService);
				departmentService.save(dept);
			}
			message = MessageField.IMPORT_DATABASE_SUCCESS;
		} else {
			message = MessageField.IMPORT_DATABASE_FAIL;
		}
		return message;
	}

	/**
	 * 导入岗位信息
	 * 
	 * @param roles
	 *            需要导入的岗位数据
	 * @return 返回一个消息字符串
	 */
	public String importRoleData(List<Role> roles) {
		log.info("importRoleData");
		String message = "";
		if (roles.size() > 0) {
			System.out.println(roleService);
			for (Role role : roles) {
				roleService.save(role);
			}
			message = MessageField.IMPORT_DATABASE_SUCCESS;
		} else {
			message = MessageField.IMPORT_DATABASE_FAIL;
		}
		return message;
	}

	/**
	 * 导入员工信息到数据库中
	 * 
	 * @param employees
	 *            需要导入的数据
	 * @return 返回消息字符串
	 */
	public String importEmployeeData(List<Employee> employees) {
		log.info("importEmployeeData");
		String message = "";
		if (employees.size() > 0) {
			for (Employee employee : employees) {
				employeeService.save(employee);
			}
			message = MessageField.IMPORT_DATABASE_SUCCESS;
		} else {
			message = MessageField.IMPORT_DATABASE_FAIL;
		}
		return message;
	}

	public List<Project> findAll() {
		log.info("findAll");
		return projectService.findAll();
	}

	public List<Project> findAllByName(String nameInfo) {
		log.info("findAllByName");
		return projectService.findAllByName(nameInfo);
	}
}
