package com.pro.hms.service.impl;



import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pro.hms.entity.Appointment;
import com.pro.hms.repository.AppointmentRepository;
import com.pro.hms.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService{
	private AppointmentRepository appointmentRepository;
	

	public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
		super();
		this.appointmentRepository = appointmentRepository;
	}

	@Override
	public Appointment saveAppointment(Appointment appointment) {
		
		return appointmentRepository.save(appointment);
	}

	@Override
	public Appointment getAppointmentById(Long id) {
		
		return appointmentRepository.findById(id).get();
	}

	@Override
	public Appointment updateAppointment(Appointment appointment) {
		return appointmentRepository.save(appointment);
	}

	@Override
	public void deleteAppointmentById(Long id) {
		appointmentRepository.deleteById(id);		
	}

	@Override
	public List<Appointment> getByDoctorIdAndDateAndStatus(Long doctorId, Date date,String status) {
		return appointmentRepository.findByDoctorIdAndDateAndStatus(doctorId, date, status);
	}

	@Override
	public List<Appointment> getByDoctorIdAndStatus(Long doctorId, String status) {
		return appointmentRepository.findByDoctorIdAndStatus(doctorId, status);
	}

	@Override
	public List<Appointment> getAppointmentByDate(Date date) {
		return appointmentRepository.findByDate(date);
	}

	@Override
	public List<LocalTime> getTimeByDate(Date date) {
		return appointmentRepository.findTimeByDate(date);
	}

	@Override
	public List<Appointment> getByPatientIdAndDate(Long patientId, Date date) {
		return appointmentRepository.findByPatientIdAndDate(patientId, date);
	}

	@Override
	public List<Appointment> getByPatientIdAndStatus(Long PatientId, String status) {
		return appointmentRepository.findByPatientIdAndStatus(PatientId, status);
	}

	@Override
	public List<Appointment> getByFloorAndStatus(Integer floor, String status) {
		return appointmentRepository.findByFloorAndStatus(floor, status);
	}

	
	
}
