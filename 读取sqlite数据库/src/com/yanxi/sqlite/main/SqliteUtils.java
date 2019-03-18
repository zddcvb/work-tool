package com.yanxi.sqlite.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * sqlite数据库操作工具类
 * @author 邹丹丹
 *
 */
public class SqliteUtils {
	/**
	 * 数据库类名
	 */
	private String className = "org.sqlite.JDBC";
	/**
	 * sqlite数据库地址
	 * 
	 */
	private String baseUrl = "jdbc:sqlite://";
	/**
	 * db数据库文件路径
	 */
	private String dbpath;
	
	public SqliteUtils(String dbpath) {
		this.dbpath = dbpath;
	}
	/**
	 * 获得connection连接对象
	 * @return  connection
	 */
	private Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(className);
			connection = DriverManager.getConnection(baseUrl + dbpath);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	/**
	 * 选择所有数据
	 * @return list集合
	 */
	public List<User> selectAll() {
		List<User> users = new ArrayList<>();
		Connection connection = getConnection();
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("select * from userinfo");
			ResultSet resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				User user = new User();
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");
				user.setAge(age);
				user.setId(id);
				user.setName(name);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	/**
	 * 根据id选择数据
	 * @param id
	 * @return user对象
	 */
	public User selectById(int id){
		Connection connection = getConnection();
		User user=new User();
		try {
			PreparedStatement pps = connection.prepareStatement("select * from userinfo where id=?");
			pps.setInt(1, id);
			ResultSet resultSet = pps.executeQuery();
			String name = resultSet.getString("name");
			int age = resultSet.getInt("age");
			user.setAge(age);
			user.setName(name);			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	/**
	 * 插入数据
	 * @param user
	 * @return boolean
	 */
	public boolean insert(User user) {
		// User user=new User(5,"王五",50);
		Connection connection = getConnection();
		boolean flag = true;
		try {
			PreparedStatement prepareStatement = connection.prepareStatement("insert into userinfo values(?,?,?)");
			prepareStatement.setInt(1, user.getId());
			prepareStatement.setString(2, user.getName());
			prepareStatement.setInt(3, user.getAge());
			flag = prepareStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 根据用户更新数据
	 * @param user
	 * @return boolean
	 */
	public boolean update(User user) {
		Connection connection = getConnection();
		boolean flag = true;
		try {
			PreparedStatement prepareStatement = connection
					.prepareStatement("update  userinfo set name=?,age=? where id=?");
			prepareStatement.setInt(3, user.getId());
			prepareStatement.setString(1, user.getName());
			prepareStatement.setInt(2, user.getAge());
			flag = prepareStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 根据id删除
	 * @param id
	 * @return boolean
	 */
	public boolean delete(int id){
		Connection connection = getConnection();
		boolean flag = true;
		try {
			PreparedStatement prepareStatement = connection
					.prepareStatement("delete  from userinfo where id=?");
			prepareStatement.setInt(1, id);
			flag = prepareStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
