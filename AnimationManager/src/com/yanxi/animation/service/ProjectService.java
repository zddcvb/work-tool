package com.yanxi.animation.service;

import java.util.List;

import com.yanxi.animation.base.BaseService;
import com.yanxi.animation.domain.Project;
/**
 * 项目业务层接口
 * @author 邹丹丹
 *
 */
public interface ProjectService extends BaseService<Project> {
	/**
	 * 根据负责员工id查找所有负责的项目
	 * 
	 * @param employeeId
	 *            负责员工id
	 * @return list集合
	 */
	List<Project> findAllByEmployeeId(String employeeId);

	/**
	 * 根据项目名称查找所有的项目情况
	 * 
	 * @param nameInfo
	 *            项目名称
	 * @return list集合
	 */
	public List<Project> findAllByName(String projectName);

	/**
	 * 根据负责员工的姓名查找所负责的项目
	 * 
	 * @param employeeName
	 *            负责人姓名
	 * @return list集合
	 */
	public List<Project> findAllByEmployeeName(String employeeName);

	/**
	 * 根据动画名称查找项目
	 * 
	 * @param animationName
	 *            动画名称
	 * @return project对象
	 */
	public Project findByAnimationName(String animationName, String projectName, String employeeId);
}
