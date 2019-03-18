package com.yanxi.animation.mapper;

import java.util.List;

import com.yanxi.animation.domain.Role;

public interface RoleMapper {

	Role findById(int id);

	List<Role> findByDeptId(int deptId);

	List<Role> findAll();

	void save(Role role);

	void update(Role role);

	void deleteById(int id);

	void deleteByDeptId(int deptId);

	Role findByName(String nameInfo);

	Role findByNameAnDept(Role role);

	List<Role> findAllByName(String roleName);

}
