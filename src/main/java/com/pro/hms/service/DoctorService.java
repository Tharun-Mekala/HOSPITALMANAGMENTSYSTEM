package com.pro.hms.service;

import java.util.List;
import com.pro.hms.entity.Doctor;

public interface DoctorService {
	List<Doctor> getAllDoctors();
	List<Doctor> getAllDoctorsByNameOrEmail(String key);
	Doctor saveDoctor(Doctor doctor);
	Doctor getDoctorById(Long id);
	Doctor getDoctorByEmail(String email);
	Doctor updateDoctor(Doctor doctor);
	void deleteDoctorById(Long id);
	List<Doctor> getTop4DoctorsWithMostExperience();
	List<Doctor> getDoctorsByDepartmentNotInICUAndEmergency();
}
