package com.pro.hms.service;

import java.sql.Date;
import java.util.List;

import com.pro.hms.entity.Test;

public interface TestService {
	List<Test> getAllTests();
	Test saveTest(Test test);
	Test getTestById(Long id);
	void deleteTestById(Long id);
	List<Test> getByDepartmentAndStatus(String department,String status);
	List<Test> getByDoctorName(String doctorName);
	List<Test> getByStatus(String status);
	List<Test> getByTestDate(Date date);
	List<Test> getByDoctorNameAndStatus(String doctorName,String status);
	List<Test> getByDoctorNameAndTestDate(String doctorName,Date date);
	List<Test> getByTestDateAndStatus(Date date,String status);
	List<Test> getByTestDateAndDoctorNameAndStatus(Date date,String doctorName,String status);
}
