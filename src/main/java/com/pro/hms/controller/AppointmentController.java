package com.pro.hms.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pro.hms.entity.Appointment;
import com.pro.hms.entity.Doctor;
import com.pro.hms.entity.Patient;
import com.pro.hms.resources.TokenTime;
import com.pro.hms.service.AppointmentService;
import com.pro.hms.service.DoctorService;
import com.pro.hms.service.PatientService;

import java.util.*;

@Controller
public class AppointmentController {
	@Autowired
	private DoctorService doctorService;
	@Autowired
	private PatientService patientService;
	private AppointmentService appointmentService;
	private List<LocalTime> allSlots=TokenTime.generateTimeIntervals();
	
	public AppointmentController(DoctorService doctorService, PatientService patientService,
			AppointmentService appointmentService) {
		super();
		this.doctorService = doctorService;
		this.patientService = patientService;
		this.appointmentService = appointmentService;
	}


	@GetMapping("/bookAppointment")
	public String bookAppointment(Model model)
	{
		Appointment appointment= new Appointment();
		List<Doctor> doctors=doctorService.getDoctorsByDepartmentNotInICUAndEmergency();
		Collections.sort(doctors,new Comparator<Doctor>() {
            @Override
            public int compare(Doctor d1, Doctor d2) {
                return d1.getDepartment().compareTo(d2.getDepartment());
            }
        });
		model.addAttribute("appointment",appointment);
		model.addAttribute("doctors",doctors);
		model.addAttribute("appointmentConfirmed",false);
		LocalDate minDate = LocalDate.now();
        LocalDate maxDate = minDate.plusWeeks(1);
		model.addAttribute("minDate",minDate);
		model.addAttribute("maxDate",maxDate);
		model.addAttribute("timeSlots",allSlots);
		return "BookAppointment";
	}
	@PostMapping("/bookAppointment")
	public String conformAppiontment(Model model,@ModelAttribute Appointment appointment,@RequestParam String aadhar)
	{
		Patient p=patientService.getPatientByAadhar(aadhar);
		model.addAttribute("doctors",doctorService.getAllDoctors());
		LocalDate minDate = LocalDate.now();
        LocalDate maxDate = minDate.plusWeeks(1);
		model.addAttribute("minDate",minDate);
		model.addAttribute("maxDate",maxDate);
		model.addAttribute("timeSlots",allSlots);
		if(p==null)
		{
			 model.addAttribute("error", "Aadhar number does not exist. Please create a patient account.");
			 return "Bookappointment";
		}
		List<Appointment> ap=appointmentService.getAppointmentByDate(appointment.getDate());
		for(Appointment a:ap)
		{
			System.out.println(a.getTime()+" "+appointment.getTime());
			if(a.getTime().compareTo(appointment.getTime())==0)
			{
				System.out.println("error rasied");
				model.addAttribute("error", "Appointments already choosen on the selected date & time. Please choose another time/Date.");
				 return "Bookappointment";
			}
		}
		if(ap.size()>30)
		{
			 model.addAttribute("error", "Appointments are full on the selected date. Please choose another date.");
			 return "bookappointment";
		}
		appointment.setDoctorName(doctorService.getDoctorById(appointment.getDoctorId()).getName());
		appointment.setPatientId(p.getId());
		appointment.setPatientName(p.getName());
		appointment.setReports(p.getReports());
		appointment.setStatus("booked");
		appointmentService.saveAppointment(appointment);
		model.addAttribute("success", "Appointments Sucessfully booked.");
		return "BookAppointment";
	}
	
}
