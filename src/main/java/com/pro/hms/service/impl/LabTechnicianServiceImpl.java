package com.pro.hms.service.impl;

import java.util.List;


import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.pro.hms.entity.LabTechnician;
import com.pro.hms.repository.LabTechnicianRepository;
import com.pro.hms.service.LabTechnicianService;

@Service
public class LabTechnicianServiceImpl  implements LabTechnicianService{
	
	private LabTechnicianRepository labTechnicianRepository;
	
	public LabTechnicianServiceImpl(LabTechnicianRepository labTechnicianRepository) {
		super();
		this.labTechnicianRepository = labTechnicianRepository;
	}	

	@Override
	public List<LabTechnician> getAllLabTechnicians() {
		return labTechnicianRepository.findAll();
	}

	@Override
	public List<LabTechnician> getAllLabTechniciansByNameOrEmailOrDepartment(String key) {
		return labTechnicianRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrTestingDepartmentContainingIgnoreCase(key, key, key);
	}

	@Override
	public LabTechnician saveLabTechnician(LabTechnician labTechnician) {
		return labTechnicianRepository.save(labTechnician);
	}

	@Override
	public LabTechnician getLabTechnicianById(Long id) {
		return labTechnicianRepository.findById(id).get();
	}

	@Override
	public LabTechnician getLabTechnicianByEmail(String email) {
		return labTechnicianRepository.findByEmail(email);
	}

	@Override
	public LabTechnician updateLabTechnician(LabTechnician labTechnician) {
		return labTechnicianRepository.save(labTechnician);
	}

	@Override
	public void deleteLabTechnicianById(Long id) {
		labTechnicianRepository.deleteById(id);
	}

	@Override
	public List<LabTechnician> getTop4LabTechniciansWithMostExperience() {
		return labTechnicianRepository.findTop4ByOrderByDateAsc(PageRequest.of(0, 4));
	}

}
