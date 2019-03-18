package com.yanxi.animation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yanxi.animation.domain.Project;

public interface ProjectMapper {

	List<Project> findAll();

	void save(Project t);

	List<Project> findByEmployId(String employeeId);

	List<Project> findByProjectName(String projectName);

	void update(Project project);

	void deleteById(int id);

	void deleteByEmployeeId(String employeeId);

	Project findByAnimationName(@Param("animationName") String animationName, @Param("projectName") String projectName,
			@Param("employeeId") String employeeId);

}
