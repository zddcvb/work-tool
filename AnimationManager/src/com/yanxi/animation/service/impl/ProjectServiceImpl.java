package com.yanxi.animation.service.impl;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yanxi.animation.base.BaseServiceImpl;
import com.yanxi.animation.domain.Employee;
import com.yanxi.animation.domain.Project;
import com.yanxi.animation.mapper.EmployeeMapper;
import com.yanxi.animation.mapper.ProjectMapper;
import com.yanxi.animation.service.ProjectService;
/**
 * 项目业务层接口实现类
 * @author 邹丹丹
 *
 */
@Service("projectService")
public class ProjectServiceImpl extends BaseServiceImpl<Project> implements ProjectService {
	@Autowired
	private ProjectMapper projectMapper;
	@Autowired
	private EmployeeMapper employeeMapper;
	private static Logger logger = Logger.getLogger(ProjectServiceImpl.class);
	/**
	 * 根据负责员工id查找所有负责的项目
	 */
	@Override
	public List<Project> findAllByEmployeeId(String employeeId) {
		List<Project> projects = projectMapper.findByEmployId(employeeId);
		return projects;
	}
	/**
	 * 查找所有数据
	 */
	@Override
	public List<Project> findAll() {
		return projectMapper.findAll();
	}
	/**
	 * 保存数据
	 */
	@Override
	public void save(Project t) {
		Project project = findByAnimationName(t.getAnimationName(), t.getProjectName(), t.getEmployeeId());
		if (project == null) {
			projectMapper.save(t);
		} else {
			logger.info("导入数据重复，不在导入！！！");
		}

	}
	/**
	 * 根据负责员工的姓名查找所负责的项目
	 */
	@Override
	public List<Project> findAllByEmployeeName(String employeeName) {
		Employee employee = employeeMapper.findByName(employeeName);
		if (employee == null) {
			return null;
		}
		logger.info(employee.getId());
		List<Project> projects = projectMapper.findByEmployId(String.valueOf(employee.getId()));
		return projects;
	}
	/**
	 * 根据动画名称查找项目
	 */
	@Override
	public Project findByAnimationName(String animationName, String projectName, String employeeId) {
		return projectMapper.findByAnimationName(animationName, projectName, employeeId);
	}
	/**
	 * 根据项目名称查询数据
	 */
	@Override
	public List<Project> findAllByName(String projectName) {
		return projectMapper.findByProjectName(projectName);
	}

}
