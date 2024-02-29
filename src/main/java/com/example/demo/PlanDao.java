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
public class PlanDao {

	String url = "jdbc:mysql://localhost/sampledb?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
	String user = "root";
	String pass = "********";
	Connection con;
	Statement st;
	PreparedStatement ps;
	ResultSet rs;

	public List<Plan> findByPlanId(String planId) {

		List<Plan> planList = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			// ここのクエリ後で修正する
			ps = con.prepareStatement("SELECT * FROM sampledb.plan_list WHERE plan_id = ? ;");
			ps.setString(1, planId);
			rs = ps.executeQuery();

			while (rs.next()) {
				String planTitle = rs.getString("plan_title");
				String planMemo = rs.getString("plan_memo");
				String planStart = rs.getString("plan_start");
				String planEnd = rs.getString("plan_end");
				String creator = rs.getString("creator");

				Plan plan = new Plan(planId, planTitle,
						planMemo, planStart, planEnd, creator);

				planList.add(plan);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return planList;
	}
	
	public List<Plan> findByCreator(String creator) {

		List<Plan> planList = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT * FROM sampledb.plan_list WHERE creator = ? ;");
			ps.setString(1, creator);
			rs = ps.executeQuery();

			while (rs.next()) {
				String planTitle = rs.getString("plan_title");
				String planMemo = rs.getString("plan_memo");
				String planStart = rs.getString("plan_start");
				String planEnd = rs.getString("plan_end");
				String planId = rs.getString("plan_id");

				Plan plan = new Plan(planId, planTitle,
						planMemo, planStart, planEnd, creator);

				planList.add(plan);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return planList;
	}
	
	public List<String> findPlanIdByCreator(String creator) {

		List<String> planIdList = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT plan_id FROM sampledb.plan_list WHERE creator = ? ;");
			ps.setString(1, creator);
			rs = ps.executeQuery();

			while (rs.next()) {
				planIdList.add(rs.getString("plan_id"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return planIdList;
	}

	public void insert(String planId, String planTitle, String planMemo,
			String planStart, String planEnd, String creator) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("INSERT INTO sampledb.plan_list"
					+ "(plan_id, plan_title, plan_memo, plan_start, plan_end, creator) "
					+ "VALUES(?, ?, ?, ?, ?, ?) ;");

			ps.setString(1, planId);
			ps.setString(2, planTitle);
			ps.setString(3, planMemo);
			ps.setString(4, planStart);
			ps.setString(5, planEnd);
			ps.setString(6, creator);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(String planId, String planTitle, String planMemo,
			String planStart, String planEnd, String creator) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("UPDATE sampledb.plan_list SET "
					+ "plan_title = ?, plan_memo = ?, plan_start = ?, plan_end = ?, creator = ? "
					+ "WHERE plan_id = ? ;");

			ps.setString(1, planTitle);
			ps.setString(2, planMemo);
			ps.setString(3, planStart);
			ps.setString(4, planEnd);
			ps.setString(5, creator);
			ps.setString(6, planId);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(String planId) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("DELETE FROM sampledb.plan_list WHERE plan_id = ? ;");

			ps.setString(1, planId);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteByCreator(String creator) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("DELETE FROM sampledb.plan_list WHERE creator = ? ;");

			ps.setString(1, creator);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
