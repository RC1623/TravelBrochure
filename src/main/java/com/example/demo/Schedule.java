package com.example.demo;

public class Schedule {
	
	private String scheduleId;
	private String scheduleTitle;
	private String scheduleStart;
	private String scheduleEnd;
	private String scheduleMemo;
	private int cost;
	private String planId;
	
	public Schedule(String scheduleId, String scheduleTitle, 
			String scheduleStart, String scheduleEnd, String scheduleMemo, 
			int cost, String planId) {
		
		this.scheduleId = scheduleId;
		this.scheduleTitle = scheduleTitle;
		this.scheduleStart = scheduleStart;
		this.scheduleEnd = scheduleEnd;
		this.scheduleMemo = scheduleMemo;
		this.cost = cost;
		this.planId = planId;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getScheduleTitle() {
		return scheduleTitle;
	}

	public void setScheduleTitle(String scheduleTitle) {
		this.scheduleTitle = scheduleTitle;
	}

	public String getScheduleStart() {
		return scheduleStart;
	}

	public void setScheduleStart(String scheduleStart) {
		this.scheduleStart = scheduleStart;
	}

	public String getScheduleEnd() {
		return scheduleEnd;
	}

	public void setScheduleEnd(String scheduleEnd) {
		this.scheduleEnd = scheduleEnd;
	}

	public String getScheduleMemo() {
		return scheduleMemo;
	}

	public void setScheduleMemo(String scheduleMemo) {
		this.scheduleMemo = scheduleMemo;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}
}
