package com.pro.hms.service;

import java.util.List;

import com.pro.hms.entity.Department;

public interface DepartmentService {
	List<Department> getAllDepartment();
	Department getDepartmentByName(String name);
	Department getDepartmentById(Long Id);
	void updateDepartment(Department department);
}
