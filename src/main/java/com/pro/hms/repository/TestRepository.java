package com.pro.hms.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.hms.entity.Test;

public interface TestRepository extends JpaRepository<Test, Long>{
	
	List<Test> findByTestingDepartmentAndStatus(String department, String status);
	List<Test> findByDoctorName(String doctorName);
	List<Test> findByStatus(String status);
	List<Test> findByTestDate(Date date);
	List<Test> findByDoctorNameAndStatus(String doctorName,String status);
	List<Test> findByDoctorNameAndTestDate(String doctorName,Date date);
	List<Test> findByTestDateAndStatus(Date date,String status);
	List<Test> findByTestDateAndDoctorNameAndStatus(Date date,String doctorName,String status);
}
