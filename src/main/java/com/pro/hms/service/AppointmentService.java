package com.pro.hms.service;



import java.sql.Date;
import java.time.LocalTime;
import java.util.List;

import com.pro.hms.entity.Appointment;

public interface AppointmentService {
	Appointment saveAppointment(Appointment appointment);
	Appointment getAppointmentById(Long id);
	Appointment updateAppointment(Appointment appointment);
	void deleteAppointmentById(Long id);
	
	List<Appointment> getAppointmentByDate(Date date);
	List<Appointment> getByDoctorIdAndDateAndStatus(Long doctorId, Date date,String status);
	List<Appointment> getByDoctorIdAndStatus(Long doctorId,String status);
	List<Appointment> getByPatientIdAndDate(Long patientId, Date date);
	List<Appointment> getByPatientIdAndStatus(Long PatientId,String status);
	List<LocalTime> getTimeByDate(Date date);
	  List<Appointment> getByFloorAndStatus(Integer floor,String status);
}
