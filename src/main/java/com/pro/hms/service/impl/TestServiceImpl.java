package com.pro.hms.service.impl;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pro.hms.entity.Test;
import com.pro.hms.repository.TestRepository;
import com.pro.hms.service.TestService;

@Service
public class TestServiceImpl implements TestService{

	private TestRepository testRepository;
	
	public TestServiceImpl(TestRepository testRepository) {
		super();
		this.testRepository = testRepository;
	}

	@Override
	public List<Test> getAllTests() {
		return testRepository.findAll();
	}

	@Override
	public Test saveTest(Test test) {
		return testRepository.save(test);
	}

	@Override
	public Test getTestById(Long id) {
		return testRepository.findById(id).get();
	}

	@Override
	public void deleteTestById(Long id) {
		testRepository.deleteById(id);
		
	}
	
	@Override
	public List<Test> getByDepartmentAndStatus(String department, String status) {
		return testRepository.findByTestingDepartmentAndStatus(department, status);
	}

	@Override
	public List<Test> getByDoctorName(String doctorName) {
		// TODO Auto-generated method stub
		return testRepository.findByDoctorName(doctorName);
	}

	@Override
	public List<Test> getByStatus(String status) {
		// TODO Auto-generated method stub
		return testRepository.findByStatus(status);
	}

	@Override
	public List<Test> getByTestDate(Date date) {
		// TODO Auto-generated method stub
		return testRepository.findByTestDate(date);
	}

	@Override
	public List<Test> getByDoctorNameAndStatus(String doctorName, String status) {
		return null;
	}

	@Override
	public List<Test> getByDoctorNameAndTestDate(String doctorName, Date date) {
		return null;
	}

	@Override
	public List<Test> getByTestDateAndStatus(Date date, String status) {
		return testRepository.findByTestDateAndStatus(date, status);
	}

	@Override
	public List<Test> getByTestDateAndDoctorNameAndStatus(Date date, String doctorName, String status) {
		return testRepository.findByTestDateAndDoctorNameAndStatus(date, doctorName, status);
	}

}
