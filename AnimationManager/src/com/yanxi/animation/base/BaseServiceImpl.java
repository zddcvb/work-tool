package com.yanxi.animation.base;

import java.util.List;
/**
 * 服务实现层基类
 * @author 邹丹丹
 *
 * @param <T>
 */
public class BaseServiceImpl<T> implements BaseService<T> {

	@Override
	public List<T> findAll() {
		return null;
	}

	@Override
	public void save(T t) {

	}

	@Override
	public List<T> findByIds(int[] ids) {
		return null;
	}

	@Override
	public T findById(int id) {
		return null;
	}

	@Override
	public void update(T t) {

	}

	@Override
	public void deleteById(T t) {

	}

	@Override
	public void deleteAll() {

	}

	@Override
	public T findByName(String name) {
		return null;
	}

}
