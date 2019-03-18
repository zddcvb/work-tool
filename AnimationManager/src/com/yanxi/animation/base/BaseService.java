package com.yanxi.animation.base;

import java.util.List;
/**
 * 基类：实现数据库增删改查业务
 * @author 邹丹丹
 *
 * @param <T>
 */
public interface BaseService<T> {

	List<T> findAll();

	List<T> findByIds(int[] ids);

	T findByName(String name);
	
	void save(T t);

	T findById(int id);

	void update(T t);

	void deleteById(T t);

	void deleteAll();
}
