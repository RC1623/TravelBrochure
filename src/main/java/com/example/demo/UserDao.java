package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserDao {

	String url = "jdbc:mysql://localhost/sampledb?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
	String user = "root";
	String pass = "********";
	Connection con;
	Statement st;
	PreparedStatement ps;
	ResultSet rs;

	public List<User> findByUserId(String userId) {

		List<User> userList = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT * FROM sampledb.user_list WHERE user_id = ? ;");
			ps.setString(1, userId);
			rs = ps.executeQuery();

			while (rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String accountName = rs.getString("account_name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String admissionDate = rs.getString("admission_date");

				User user = new User(userId, firstName, lastName, accountName, email, password, admissionDate);

				userList.add(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userList;
	}

	public List<User> findByEmail(String email) {

		List<User> userList = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT * FROM sampledb.user_list WHERE email = ? ;");
			ps.setString(1, email);
			rs = ps.executeQuery();

			while (rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String accountName = rs.getString("account_name");
				String userId = rs.getString("user_id");
				String password = rs.getString("password");
				String admissionDate = rs.getString("admission_date");

				User user = new User(userId, firstName, lastName, accountName, email, password, admissionDate);

				userList.add(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userList;
	}

	public String findUserIdByLoginInfo(String email, String password) {
		
		String userId = "";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT user_id FROM sampledb.user_list WHERE email = ? AND password = ? ;");
			ps.setString(1, email);
			ps.setString(2, password);
			rs = ps.executeQuery();

			while (rs.next()) {
				userId = rs.getString("user_id");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userId;
	}

	public List<User> findByLogin(String email, String password) {

		List<User> userList = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT * FROM sampledb.user_list WHERE email = ? AND password = ? ;");
			ps.setString(1, email);
			ps.setString(2, password);
			rs = ps.executeQuery();

			while (rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String accountName = rs.getString("account_name");
				String userId = rs.getString("user_id");
				String admissionDate = rs.getString("admission_date");

				User user = new User(userId, firstName, lastName, accountName, email, password, admissionDate);

				userList.add(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userList;
	}
	
	public List<User> findByAccountName(String accountName) {

		List<User> userList = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT * FROM sampledb.user_list WHERE account_name = ? ;");
			ps.setString(1, accountName);
			rs = ps.executeQuery();

			while (rs.next()) {
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String userId = rs.getString("user_id");
				String admissionDate = rs.getString("admission_date");

				User user = new User(userId, firstName, lastName, accountName, email, password, admissionDate);

				userList.add(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userList;
	}
	
	public void insert(String userId, String firstName, String lastName, String accountName,
			String email, String password, String admissionDate) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("INSERT INTO sampledb.user_list"
					+ "(user_id, first_name, last_name, account_name, email, password, admission_date) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?) ;");

			ps.setString(1, userId);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.setString(4, accountName);
			ps.setString(5, email);
			ps.setString(6, password);
			ps.setString(7, admissionDate);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(String userId, String firstName, String lastName, String accountName,
			String email, String password, String admissionDate) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("UPDATE sampledb.user_list SET "
					+ "first_name = ?, last_name = ?, account_name = ?, email = ?, password = ?,  admission_date = ? "
					+ "WHERE user_id = ? ;");

			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, accountName);
			ps.setString(4, email);
			ps.setString(5, password);
			ps.setString(6, admissionDate);
			ps.setString(7, userId);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(String userId) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("DELETE FROM sampledb.user_list WHERE user_id = ? ;");

			ps.setString(1, userId);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
