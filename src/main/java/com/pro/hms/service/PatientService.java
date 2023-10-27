package com.pro.hms.service;

import java.util.List;

import com.pro.hms.entity.Patient;

public interface PatientService {
	List<Patient> getAllPatients();
	Patient savePatient(Patient patient);
	Patient getPatientById(Long id);
	Patient updatePatient(Patient patient);
	Patient getPatientByEmail(String email);
	Patient getPatientByAadhar(String aadhar);
	void deletePatientById(Long id);
}
