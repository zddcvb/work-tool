package com.yanxi.animation.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yanxi.animation.base.BaseServiceImpl;
import com.yanxi.animation.domain.Role;
import com.yanxi.animation.mapper.RoleMapper;
import com.yanxi.animation.service.RoleService;
/**
 * 岗位服务层接口实现类
 * @author 邹丹丹
 *
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
	@Autowired
	private RoleMapper roleMapper;
	private static Logger logger=Logger.getLogger(RoleServiceImpl.class);
	/**
	 * 保存数据
	 */
	@Override
	public void save(Role t) {
		boolean checkSameRole = checkSameRole(t);
		if (!checkSameRole) {
			roleMapper.save(t);
		}else{
			logger.info("导入数据重复，不在导入！！！");
		}
		
	}
/**
 * 根据岗位名称查找岗位信息
 */
	@Override
	public Role findByName(String nameInfo) {
		return roleMapper.findByName(nameInfo);
	}
	/**
	 * 判定岗位信息是否重复
	 */
	public boolean checkSameRole(Role role){
		List<Role> roles=roleMapper.findAllByName(role.getRoleName());
		for (Role role2 : roles) {
			if (role2.getDeptId()==role.getDeptId()) {
				return true;
			}
		}
		return false;
	}
	
}
