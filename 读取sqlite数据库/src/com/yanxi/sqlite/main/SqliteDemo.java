package com.yanxi.sqlite.main;

import java.util.List;

public class SqliteDemo {

	public static void main(String[] args) {
		String dbpath = "D:/developTools/sqlite/user.db";
		SqliteUtils utils = new SqliteUtils(dbpath);
		//List<User> selectAll = utils.selectAll();
		//System.out.println(selectAll);
		User user=new User(5,"王五",50);
		utils.insert(user);
	}
	

}
