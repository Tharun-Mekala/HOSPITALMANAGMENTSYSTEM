package com.pro.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.hms.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

	Department findByName(String name);
}
