package com.pro.hms.service.impl;

import org.springframework.data.domain.PageRequest;

import java.util.List;


import org.springframework.stereotype.Service;

import com.pro.hms.entity.Doctor;
import com.pro.hms.repository.DoctorRepository;
import com.pro.hms.service.DoctorService;

@Service
public class DoctorServiceImpl implements DoctorService{

	private DoctorRepository doctorRepository;
	
	public DoctorServiceImpl(DoctorRepository doctorRepository) {
		super();
		this.doctorRepository = doctorRepository;
	}

	@Override
	public List<Doctor> getAllDoctors() {
		return doctorRepository.findAll();
	}

	@Override
	public Doctor saveDoctor(Doctor doctor) {
		return doctorRepository.save(doctor);
	}

	@Override
	public Doctor getDoctorById(Long id) {
		return doctorRepository.findById(id).get();
	}

	@Override
	public Doctor updateDoctor(Doctor doctor) {
		return doctorRepository.save(doctor);
	}

	@Override
	public void deleteDoctorById(Long id) {
		doctorRepository.deleteById(id);
		
	}

	@Override
	public Doctor getDoctorByEmail(String email) {
		return doctorRepository.findByEmail(email);
	}

	@Override
	public List<Doctor> getAllDoctorsByNameOrEmail(String key) {
		return doctorRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(key, key);
	}

	@Override
	public List<Doctor> getTop4DoctorsWithMostExperience() {
		return doctorRepository.findTop4ByOrderByDateAsc(PageRequest.of(0, 4));
	}

	@Override
	public List<Doctor> getDoctorsByDepartmentNotInICUAndEmergency() {
		return doctorRepository.findDoctorsByDepartmentNotInICUAndEmergency();
	}
}
