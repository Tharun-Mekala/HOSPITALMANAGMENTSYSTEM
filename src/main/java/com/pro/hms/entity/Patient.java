package com.pro.hms.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	@Column
	private String aadhar;
	private String name;
	private String email;
	private String phone;
	private String password;
	private String age;
	private List<String> reports;
	public Patient() {
		super();
	}
	
	public Patient(String aadhar,String name, String email, String phone, String password,String age) {
		super();
		this.aadhar=aadhar;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.age=age;
	}
	
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAadhar() {
		return aadhar;
	}

	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}

	public List<String> getReports() {
		return reports;
	}

	public void setReports(List<String> reports) {
		this.reports = reports;
	}
	
	public void addReport(String report)
	{
		if(this.reports==null)
			this.reports=new ArrayList<>();
		this.reports.add(report);
	}
	public void removeReport(String name)
	{
		this.reports.remove(name);
	}
}
