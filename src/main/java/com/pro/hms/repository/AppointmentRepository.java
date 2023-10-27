package com.pro.hms.repository;



import java.sql.Date;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.pro.hms.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
	List<Appointment> findByDoctorIdAndDateAndStatus(Long doctorId, Date date,Boolean status);
	List<Appointment> findByDoctorIdAndStatus(Long doctorId,Boolean status);
	List<Appointment> findByPatientIdAndDate(Long patientId, Date date);
	List<Appointment> findByPatientIdAndStatus(Long PatientId,Boolean status);
	List<Appointment> findByDate(Date date);
    List<LocalTime> findTimeByDate(@Param("targetDate") Date targetDate);
    List<Appointment> findByFloorAndStatus(Integer floor,Boolean status);
}
