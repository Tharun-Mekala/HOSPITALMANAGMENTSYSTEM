package com.pro.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.hms.entity.Patient;

public interface PatientRepository extends JpaRepository<Patient,Long> {
	public Patient findByEmail(String email);
	public Patient findByAadhar(String aadhar);
}
