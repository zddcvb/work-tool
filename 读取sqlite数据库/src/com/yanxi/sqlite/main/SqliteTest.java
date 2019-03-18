package com.yanxi.sqlite.main;

import java.util.List;

import org.junit.Test;
import org.junit.validator.PublicClassValidator;

public class SqliteTest {

	public SqliteUtils getSqliteUtils() {
		String dbpath = "D:/developTools/sqlite/user.db";
		SqliteUtils utils = new SqliteUtils(dbpath);
		return utils;
	}

	@Test
	public void test_selectAll() {
		SqliteUtils sqliteUtils = getSqliteUtils();
		List<User> selectAll = sqliteUtils.selectAll();
		System.out.println(selectAll);
	}

	@Test
	public void test_insert() {
		SqliteUtils sqliteUtils = getSqliteUtils();
		for (int i = 5; i < 20; i++) {
			User user = new User(i + 1, "王五_" + (i + 1), 50 + i);
			sqliteUtils.insert(user);
		}
	}

	@Test
	public void test_udpate(){
		User user = new User(6, "王二" , 12);
		SqliteUtils sqliteUtils = getSqliteUtils();
		sqliteUtils.update(user);
	}
	
	@Test
	public void test_selectById(){
		SqliteUtils sqliteUtils = getSqliteUtils();
		User user = sqliteUtils.selectById(7);
		System.out.println(user);
	}
	@Test
	public void test_delete(){
		SqliteUtils sqliteUtils = getSqliteUtils();
		sqliteUtils.delete(8);
	}
}
