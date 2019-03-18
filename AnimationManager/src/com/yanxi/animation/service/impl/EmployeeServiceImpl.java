package com.yanxi.animation.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yanxi.animation.base.BaseServiceImpl;
import com.yanxi.animation.domain.Employee;
import com.yanxi.animation.mapper.EmployeeMapper;
import com.yanxi.animation.service.EmployeeService;

/**
 * 员工业务层接口实现类
 * 
 * @author 邹丹丹
 *
 */
@Service("employeeService")
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements EmployeeService {
	@Autowired
	private EmployeeMapper employeeMapper;
	private static Logger logger = Logger.getLogger(EmployeeServiceImpl.class);

	/**
	 * 通过员工姓名查找员工信息
	 */
	@Override
	public Employee findByName(String nameInfo) {
		return employeeMapper.findByName(nameInfo);
	}

	/**
	 * 保存数据
	 */
	@Override
	public void save(Employee t) {
		Employee employee = findByName(t.getName());
		if (employee == null) {
			employeeMapper.save(t);
		} else {
			logger.info("导入数据重复，不在导入！！！");
		}

	}
	/**
	 * 通过员工id数组查找符合条件的数据
	 */
	public List<Employee> findByIds(int[] ids) {
		return employeeMapper.findByIds(ids);
	}
	/**
	 * 通过员工id查找员工信息
	 */
	@Override
	public Employee findById(int id) {
		return employeeMapper.findById(id);
	}
}
