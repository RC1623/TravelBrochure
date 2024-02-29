package com.example.demo;

public class User {
	private String userId;
	private String firstName;
	private String lastName;
	private String accountName;
	private String email;
	private String password;
	private String admissionDate;
	
	public User(String userId, String firstName, String lastName, 
			String accountName, String email, String password, String admissionDate){
		
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.accountName = accountName;
		this.email = email;
		this.password = password;
		this.admissionDate = admissionDate;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}
}
