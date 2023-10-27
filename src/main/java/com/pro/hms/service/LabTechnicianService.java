package com.pro.hms.service;

import java.util.List;

import com.pro.hms.entity.LabTechnician;

public interface LabTechnicianService {
	
	 	List<LabTechnician> getAllLabTechnicians();
		List<LabTechnician> getAllLabTechniciansByNameOrEmailOrDepartment(String key);
		LabTechnician saveLabTechnician(LabTechnician labTechnician);
		LabTechnician getLabTechnicianById(Long id);
		LabTechnician getLabTechnicianByEmail(String email);
		LabTechnician updateLabTechnician(LabTechnician labTechnician);
		void deleteLabTechnicianById(Long id);
		List<LabTechnician> getTop4LabTechniciansWithMostExperience();
}
