package com.pro.hms.entity;

import java.sql.Date;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Appointment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	private Date date;
	private Long doctorId;
	private String doctorName;
	private Long patientId;
	private String patientName;
	private Boolean status;
	private StringBuffer disease;
	private StringBuffer prescription;
	private String testsList;
	private LocalTime Time;
	private Long depId;
	private Integer bedNo;
	private Integer floor;
	private List<String> reports;
	private List<Long> tests;

	public Appointment() {
		super();
	}
	

	public Appointment(Long id, Date date, Long doctorId, String doctorName, Long patientId, String patientName,
			Boolean status, StringBuffer disease, StringBuffer prescription, LocalTime time, Long depId, Integer bedNo,
			Integer floor, List<String> reports) {
		super();
		this.id = id;
		this.date = date;
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.patientId = patientId;
		this.patientName = patientName;
		this.status = status;
		this.disease = disease;
		this.prescription = prescription;
		Time = time;
		this.depId = depId;
		this.bedNo = bedNo;
		this.floor = floor;
		this.reports = reports;
	}



	public Long getDepId() {
		return depId;
	}

	public void setDepId(Long depId) {
		this.depId = depId;
	}

	public Integer getBedNo() {
		return bedNo;
	}

	public void setBedNo(Integer bedNo) {
		this.bedNo = bedNo;
	}

	public LocalTime getTime() {
		return Time;
	}

	public void setTime(LocalTime time) {
		Time = time;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public StringBuffer getDisease() {
		return disease;
	}

	public void setDisease(StringBuffer disease) {
		this.disease = disease;
	}

	public StringBuffer getPrescription() {
		return prescription;
	}

	public void setPrescription(StringBuffer prescription) {
		this.prescription = prescription;
	}

	public Integer getFloor() {
		return floor;
	}

	public void setFloor(Integer floor) {
		this.floor = floor;
	}

	public List<String> getReports() {
		return reports;
	}

	public void setReports(List<String> reports) {
		this.reports = reports;
	}


	public List<Long> getTests() {
		return tests;
	}
	public void setTests(List<Long> tests) {
		this.tests = tests;
	}
	public String getTestsList() {
		return testsList;
	}
	public void setTestsList(String testsList) {
		this.testsList = testsList;
	}
	
			
}
