package com.yanxi.animation.service;

import com.yanxi.animation.base.BaseService;
import com.yanxi.animation.domain.Role;
/**
 * 岗位业务层接口
 * @author 邹丹丹
 *
 */
public interface RoleService extends BaseService<Role> {
	/**
	 * 判断表中是否有重复数据
	 * @param role 需要判断的对象
	 * @return boolean对象
	 */
	boolean checkSameRole(Role role);
}
