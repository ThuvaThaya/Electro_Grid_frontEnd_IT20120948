package com.main.user.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	private int userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String phoneNo;
	private String nic;
	private int branchId;
	
	public User() {
	}
	
	public User(int userId, String firstName, String lastName, String email, String password, String phoneNo,
			String nic, int branchId) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.phoneNo = phoneNo;
		this.nic = nic;
		this.branchId = branchId;
	}


	public User(String firstName, String lastName, String email, String password, String phoneNo,
			String nic, int branchId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.phoneNo = phoneNo;
		this.nic = nic;
		this.branchId = branchId;
	}

	public User(String firstName) {
		this.firstName = firstName;
	}
	

	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public int getBranchId() {
		return branchId;
	}


	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}


	public String getPhoneNo() {
		return phoneNo;
	}


	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}


	public String getNic() {
		return nic;
	}


	public void setNic(String nic) {
		this.nic = nic;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "User{" +
			"userId=" + userId +
			", firstName='" + firstName + '\'' +
			", lastName='" + lastName + '\'' +
			", email='" + email + '\'' +
			", password='" + password + '\'' +
			", phoneNo='" + phoneNo + '\'' +
			'}';
	}
}

