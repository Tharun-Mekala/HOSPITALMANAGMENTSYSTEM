package com.pro.hms.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.hms.entity.Doctor;
import com.pro.hms.entity.Nurse;

public interface NurseRepository extends JpaRepository<Nurse,Long>{
	public Nurse findByEmail(String email);
	public List<Nurse> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrShiftContainingIgnoreCase(String name, String email,String shift);
	List<Nurse> findTop4ByOrderByDateAsc(Pageable pageable);
}
