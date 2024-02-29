package com.example.demo;

public class Plan {
	private String planId;
	private String planTitle;
	private String planMemo;
	private String planStart;
	private String planEnd;
	private String creator;
	
	public Plan(String planId, String planTitle, String planMemo, 
			String planStart, String planEnd, String creator) {
		
		this.planId = planId;
		this.planTitle = planTitle;
		this.planMemo = planMemo;
		this.planStart = planStart;
		this.planEnd = planEnd;
		this.creator = creator;
		
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getPlanTitle() {
		return planTitle;
	}

	public void setPlanTitle(String planTitle) {
		this.planTitle = planTitle;
	}

	public String getPlanMemo() {
		return planMemo;
	}

	public void setPlanMemo(String planMemo) {
		this.planMemo = planMemo;
	}

	public String getPlanStart() {
		return planStart;
	}

	public void setPlanStart(String planStart) {
		this.planStart = planStart;
	}

	public String getPlanEnd() {
		return planEnd;
	}

	public void setPlanEnd(String planEnd) {
		this.planEnd = planEnd;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
}
