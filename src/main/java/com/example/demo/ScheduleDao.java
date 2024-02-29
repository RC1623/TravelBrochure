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
public class ScheduleDao {
	String url = "jdbc:mysql://localhost/sampledb?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
	String user = "root";
	String pass = "********";
	Connection con;
	Statement st;
	PreparedStatement ps;
	ResultSet rs;

	public List<Schedule> findByPlanId(String planId) {

		List<Schedule> scheduleList = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT * FROM sampledb.schedule_list WHERE plan_id = ? ORDER BY schedule_start;");
			ps.setString(1, planId);
			rs = ps.executeQuery();

			while (rs.next()) {
				String scheduleId = rs.getString("schedule_id");
				String scheduleTitle = rs.getString("schedule_title");
				String scheduleStart = rs.getString("schedule_start");
				String scheduleEnd = rs.getString("schedule_end");
				String scheduleMemo = rs.getString("schedule_memo");
				int cost = rs.getInt("cost");

				Schedule schedule = new Schedule(scheduleId, scheduleTitle,
						scheduleStart, scheduleEnd, scheduleMemo, cost, planId);

				scheduleList.add(schedule);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return scheduleList;
	}
	
	public List<Schedule> findByScheduleId(String scheduleId) {

		List<Schedule> scheduleList = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("SELECT * FROM sampledb.schedule_list WHERE schedule_id = ?;");
			ps.setString(1, scheduleId);
			rs = ps.executeQuery();

			while (rs.next()) {
				String planId = rs.getString("plan_id");
				String scheduleTitle = rs.getString("schedule_title");
				String scheduleStart = rs.getString("schedule_start");
				String scheduleEnd = rs.getString("schedule_end");
				String scheduleMemo = rs.getString("schedule_memo");
				int cost = rs.getInt("cost");

				Schedule schedule = new Schedule(scheduleId, scheduleTitle,
						scheduleStart, scheduleEnd, scheduleMemo, cost, planId);

				scheduleList.add(schedule);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return scheduleList;
	}

	public void insert(String scheduleId, String scheduleTitle, String scheduleStart,
			String scheduleEnd, String scheduleMemo, String cost, String planId) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("INSERT INTO sampledb.schedule_list"
					+ "(schedule_id, schedule_title, schedule_start, "
					+ "schedule_end, schedule_memo, cost, plan_id) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?) ;");

			ps.setString(1, scheduleId);
			ps.setString(2, scheduleTitle);
			ps.setString(3, scheduleStart);
			ps.setString(4, scheduleEnd);
			if(scheduleMemo == "" || scheduleMemo == null) {
				ps.setString(5, null);
			}else {
				ps.setString(5, scheduleMemo);
			}
			if(cost == "" || cost == null) {
				ps.setString(6, null);
			}else {
				ps.setString(6, cost);
			}
			ps.setString(7, planId);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void update(String scheduleId, String scheduleTitle, String scheduleStart,
			String scheduleEnd, String scheduleMemo, String cost) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("UPDATE sampledb.schedule_list SET "
					+ "schedule_title = ?, schedule_start = ?, schedule_end = ?, schedule_memo = ?, cost = ? "
					+ "WHERE schedule_id = ? ;");

			ps.setString(1, scheduleTitle);
			ps.setString(2, scheduleStart);
			ps.setString(3, scheduleEnd);
			ps.setString(4, scheduleMemo);
			ps.setString(5, cost);
			ps.setString(6, scheduleId);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delete(String scheduleId) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("DELETE FROM sampledb.schedule_list WHERE schedule_id = ? ;");
			ps.setString(1, scheduleId);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteByPlanId(String planId) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			ps = con.prepareStatement("DELETE FROM sampledb.schedule_list WHERE plan_id = ? ;");
			ps.setString(1, planId);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
