package com.pro.hms.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pro.hms.entity.Department;
import com.pro.hms.repository.DepartmentRepository;
import com.pro.hms.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService{
	
	DepartmentRepository departmentRepository;
	
	public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
		super();
		this.departmentRepository = departmentRepository;
	}

	@Override
	public List<Department> getAllDepartment() {
		return departmentRepository.findAll();
	}

	@Override
	public Department getDepartmentByName(String name) {
		return departmentRepository.findByName(name);
	}

	@Override
	public Department getDepartmentById(Long Id) {
		return departmentRepository.findById(Id).get();
	}

	@Override
	public void updateDepartment(Department department) {
		 departmentRepository.save(department);
	}

}
