package com.pro.hms.entity;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
public class LabTechnician {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String name;
	private Date date;
	private Integer experience;
	private String testingDepartment;
	private String gender;
	private String profile_photo;
	private String email;
	private String password;
	private String phone;
	
	public LabTechnician() {
		super();
	}

	public LabTechnician(Long id, String name, Date date, Integer experience, String testingDepartment, String gender,
			String profile_photo, String email, String password, String phone) {
		super();
		this.id = id;
		this.name = name;
		this.date = date;
		this.experience = experience;
		this.testingDepartment = testingDepartment;
		this.gender = gender;
		this.profile_photo = profile_photo;
		this.email = email;
		this.password = password;
		this.phone = phone;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getExperience() {
		return experience;
	}

	public void setExperience(Integer experience) {
		this.experience = experience;
	}

	public String getTestingDepartment() {
		return testingDepartment;
	}

	public void setTestingDepartment(String testingDepartment) {
		this.testingDepartment = testingDepartment;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getProfile_photo() {
		return profile_photo;
	}

	public void setProfile_photo(String profile_photo) {
		this.profile_photo = profile_photo;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
