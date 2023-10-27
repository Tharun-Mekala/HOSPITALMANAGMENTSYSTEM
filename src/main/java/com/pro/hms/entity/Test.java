package com.pro.hms.entity;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
public class Test {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String name;
	private Long appId;
	private Long patId;
	private Long docId;
	private Date testDate;
	private String status;
	private String testingDepartment;
	private String patientName;
	private String doctorName;

	public Test() {
		super();
	}
	public Test(String name, Long appId, Long patId, Long docId, Date testDate, String status, String testingDepartment,
			String patientName, String doctorName) {
		super();
		this.name = name;
		this.appId = appId;
		this.patId = patId;
		this.docId = docId;
		this.testDate = testDate;
		this.status = status;
		this.testingDepartment = testingDepartment;
		this.patientName = patientName;
		this.doctorName = doctorName;
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
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public Long getDocId() {
		return docId;
	}
	public void setDocId(Long docId) {
		this.docId = docId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getTestDate() {
		return testDate;
	}
	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}
	
	public String getTestingDepartment() {
		return testingDepartment;
	}

	public void setTestingDepartment(String testingDepartment) {
		this.testingDepartment = testingDepartment;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public Long getPatId() {
		return patId;
	}

	public void setPatId(Long patId) {
		this.patId = patId;
	}
}
