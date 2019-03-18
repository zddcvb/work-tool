package com.yanxi.animation.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yanxi.animation.base.BaseServiceImpl;
import com.yanxi.animation.domain.Department;
import com.yanxi.animation.mapper.DepartmentMapper;
import com.yanxi.animation.service.DepartmentService;
/**
 * 部门业务层接口实现类
 * @author 邹丹丹
 *
 */
@Service("departmentService")
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements DepartmentService {
	@Autowired
	private DepartmentMapper departmentMapper;
	private static Logger logger=Logger.getLogger(DepartmentServiceImpl.class);
	/**
	 * 保存数据
	 */
	@Override
	public void save(Department t) {
		Department department = departmentMapper.findByName(t.getDeptName());
		if (department==null) {
			departmentMapper.save(t);
		}else{
			logger.info("导入数据重复，不在导入！！！");
		}
	}
	/**
	 * 通过部门名称查找部门信息
	 */
	@Override
	public Department findByName(String nameInfo) {
		return departmentMapper.findByName(nameInfo);
	}
	
	

}
