package com.yanxi.animation.mapper;

import java.util.List;

import com.yanxi.animation.domain.Department;

/**
 * 部门mapper
 * 
 * @author 邹丹丹
 *
 */
public interface DepartmentMapper {
	/**
	 * 保存数据
	 * 
	 * @param t
	 *            需要保存的对象
	 */
	void save(Department t);

	/**
	 * 查找所有
	 * 
	 * @return list集合
	 */
	List<Department> findAll();

	/**
	 * 根据id查找数据
	 * 
	 * @param id
	 * @return
	 */
	Department findById(int id);

	/**
	 * 通过姓名查找部门
	 * 
	 * @param name
	 *            需要查找的姓名
	 * @return 部门对象
	 */

	Department findByName(String name);

	void update(Department dept);

	void deleteById(int id);

	void deleteByName(String deptName);
}
