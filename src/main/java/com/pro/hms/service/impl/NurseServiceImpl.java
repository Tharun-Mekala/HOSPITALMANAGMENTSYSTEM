package com.pro.hms.service.impl;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.pro.hms.entity.Nurse;
import com.pro.hms.repository.NurseRepository;
import com.pro.hms.service.NurseService;


@Service
public class NurseServiceImpl implements NurseService{

	private NurseRepository nurseRepository;
	
	public NurseServiceImpl(NurseRepository nurseRepository) {
		super();
		this.nurseRepository = nurseRepository;
	}

	@Override
	public List<Nurse> getAllNurses() {
		return nurseRepository.findAll();
		
	}

	@Override
	public Nurse saveNurse(Nurse nurse) {
		return nurseRepository.save(nurse);
		
	}

	@Override
	public Nurse getNurseById(Long id) {
		return nurseRepository.findById(id).get();
		
	}

	@Override
	public Nurse updateNurse(Nurse nurse) {
		return nurseRepository.save(nurse);
		
	}

	@Override
	public void deleteNurseById(Long id) {
		nurseRepository.deleteById(id);
		
	}

	@Override
	public List<Nurse> getAllNurseByNameOrEmailOrShift(String key) {
		return nurseRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrShiftContainingIgnoreCase(key, key, key);
	}

	@Override
	public Nurse getNurseByEmail(String email) {
		return nurseRepository.findByEmail(email);
	}

	@Override
	public List<Nurse> getTop4NursesWithMostExperience() {
		// TODO Auto-generated method stub
		return nurseRepository.findTop4ByOrderByDateAsc(PageRequest.of(0, 4));
	}

}
