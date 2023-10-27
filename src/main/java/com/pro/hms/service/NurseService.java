package com.pro.hms.service;

import java.util.List;

import com.pro.hms.entity.Nurse;

public interface NurseService {

	List<Nurse> getAllNurses();
	Nurse saveNurse(Nurse nurse);
	Nurse getNurseById(Long id);
	Nurse updateNurse(Nurse nurse);
	void deleteNurseById(Long id);
	List<Nurse> getAllNurseByNameOrEmailOrShift(String key);
	Nurse getNurseByEmail(String email);
	List<Nurse> getTop4NursesWithMostExperience();
}
