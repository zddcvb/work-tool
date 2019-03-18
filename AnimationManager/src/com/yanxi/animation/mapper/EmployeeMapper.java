package com.yanxi.animation.mapper;

import java.util.List;

import com.yanxi.animation.domain.Employee;

public interface EmployeeMapper {

	Employee findByName(String name);

	Employee findByDeptId(int deptId);

	List<Employee> findByIds(int[] ids);

	void save(Employee t);

	List<Employee> findAll();

	Employee findById(int id);

	void update(Employee employee);

	void delete(int id);

}
