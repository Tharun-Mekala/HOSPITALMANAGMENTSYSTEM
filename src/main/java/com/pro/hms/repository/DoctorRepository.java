package com.pro.hms.repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pro.hms.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor,Long>{
	public Doctor findByEmail(String email);
	public List<Doctor> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);
	List<Doctor> findTop4ByOrderByDateAsc(Pageable pageable);
	@Query("SELECT d FROM Doctor d WHERE d.department NOT IN ('ICU', 'EMERGENCY')")
	List<Doctor> findDoctorsByDepartmentNotInICUAndEmergency();

}
