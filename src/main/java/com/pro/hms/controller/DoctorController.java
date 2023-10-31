package com.pro.hms.controller;


import java.io.File;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pro.hms.entity.Appointment;
import com.pro.hms.entity.Department;
import com.pro.hms.entity.Doctor;
import com.pro.hms.entity.Test;
import com.pro.hms.resources.Password;
import com.pro.hms.service.AppointmentService;
import com.pro.hms.service.DepartmentService;
import com.pro.hms.service.DoctorService;
import com.pro.hms.service.EmailService;
import com.pro.hms.service.TestService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class DoctorController {

	private DoctorService doctorService;
	private AppointmentService appointmentService;
	private DepartmentService departmentService;
	private TestService testService;
	private Doctor doctor;
	@Autowired
	private EmailService emailService;

	public DoctorController(DoctorService doctorService,AppointmentService appointmentService,DepartmentService departmentService,TestService testService) {
		super();
		this.doctorService = doctorService;
		this.appointmentService= appointmentService;
		this.departmentService=departmentService;
		this.testService=testService;
	}
	
	@GetMapping("/loginDoctor")
	public String loginDoctor()
	{
		return "Doctor-Login";
	}
	@PostMapping("/loginDoctor")
	public String verifyDoctor(@RequestParam String email,@RequestParam String password,Model model)
	{
		Doctor doc= doctorService.getDoctorByEmail(email);
		if(doc==null)
		{
			model.addAttribute("error", "Invalid Email/Password.");
			return "Doctor-Login";
		}
		else
		{
			if(doc.getPassword().equals(password))
			{
				this.doctor=doc;
				return "redirect:/doctorDashBoard";
			}
			else
				model.addAttribute("error", "Invalid Email/Password.");
				return "Doctor-Login";
		}
	}
	@GetMapping("/logoutDoctor")
	public String logoutDoctor() 
	{
		this.doctor=null;
       return "redirect:/loginDoctor";
	}
	
	
	@PostMapping("/Doctor-SendPasswordforFP")
	public String doctorSendOTPforFP(@RequestParam String email,Model model)
	{
		Doctor doc=doctorService.getDoctorByEmail(email);
		doc.setPassword(Password.generatePassword());
		emailService.sendEmail(doc.getEmail(),"Your Temporary Password.","\nYour Temporary password : "+doc.getPassword()+"\n Please Change Your Password after Login for Security reasons");
		doctorService.updateDoctor(doc);
		return "redirect:/loginDoctor";
		
	}
	
	@GetMapping("/doctorChangePassword")
	public String doctorChangePassword(Model model)
	{
		return "Doctor-ChangePassword";
	}
	
	@PostMapping("/doctorChangePassword")
	public String doctorUpdatePassword(@RequestParam String password,Model model)
	{
		doctor.setPassword(password);
		doctorService.updateDoctor(doctor);
		return "redirect:/doctorDashBoard";
	}
	
	@GetMapping("/doctorDashBoard")
	public String doctorDashBoard(Model model)
	{
		if(doctor==null)
			return "redirect:/loginDoctor";
		LocalDate currentDate = LocalDate.now();
		Date date=Date.valueOf(currentDate);
		model.addAttribute("doctor", this.doctor);
		model.addAttribute("departments",departmentService.getAllDepartment());
		model.addAttribute("todayAppointments",appointmentService.getAppointmentByDate(date));
		model.addAttribute("currentAppointments",appointmentService.getByDoctorIdAndStatus(this.doctor.getId(), "admited"));
		model.addAttribute("treatedAppointments",appointmentService.getByDoctorIdAndStatus(this.doctor.getId(), "discharged"));
		return "Doctor-DashBoard";
	}
	
	
	
	@PostMapping("/treatPatient/{appointmentId}")
	public String treatPatient(@PathVariable Long appointmentId,@RequestParam String disease,@RequestParam String prescription,@RequestParam String noteToNurse,@RequestParam String tests,@RequestParam String department,Model model)
	{
		String error=null;
		Appointment ea=appointmentService.getAppointmentById(appointmentId);
		ea.setDisease(new StringBuffer(disease));
		ea.setPrescription(new StringBuffer(prescription));
		ea.setNoteToNurse(noteToNurse);
		List<Long> listofTests = new ArrayList<>();
		LocalDate currentDate = LocalDate.now();
		Date date=Date.valueOf(currentDate);
		tests=tests.toUpperCase();
		if(ea.getTestsList()==null)
			ea.setTestsList(tests);
		else
			ea.setTestsList(ea.getTestsList()+tests);
		String td="";
		for(String s:tests.split("\n"))
		{
			if(!s.isEmpty())
			{
				if(s.contains("CBP")||s.contains("BLOOD")||s.contains("HIV")||s.contains("HBA1C")||s.contains("SERUM CREATININE"))
					td="PHLEBOTOMY";
				else if(s.contains("X-RAY")||s.contains("X RAY"))
					td="X-RAY";
				else if(s.contains("MRI"))
					td="MRI";
				else if(s.contains("CT-SCAN"))
					td="CT-SCAN";
				else if(s.contains("2D-ECHO"))
					td="2D-ECHO";
				else if(s.contains("ECG"))
					td="ECG";
				else if(s.contains("ENDOSCOPY"))
					td="ENDOSCOPY";
				Test test =new Test(s,ea.getId(),ea.getPatientId(),ea.getDoctorId(),date, "InCompleted",td,ea.getPatientName(),ea.getDoctorName());
				testService.saveTest(test);
				listofTests.add(test.getId());
			}
		}
		ea.setTests(listofTests);
		Department dep=departmentService.getDepartmentByName(department);
		if(dep!=null)
		{
			int OB=dep.getOccpuiedBeds();
			if(OB<=30)
			{
				OB++;
				dep.setOccpuiedBeds(OB);
				ea.setDepId(dep.getId());
				ea.setStatus("admited");
				ea.setBedNo(dep.setBed(ea.getId()));
				ea.setFloor(dep.getFloor());
				departmentService.updateDepartment(dep);
			}
			else
			{
				error="Beds are Full!!!";
			}
		}
		else
			ea.setStatus("discharged");
		appointmentService.updateAppointment(ea);
		return "redirect:/doctorDashBoard";
	}
	@PostMapping("/onTreatingPatient/{doctorId}/{appointmentId}")
	public String OnTreating(@PathVariable Long doctorId,@PathVariable Long appointmentId,@RequestParam String disease,@RequestParam String prescription,Model model)
	{
		String error=null;
		Doctor doctor=doctorService.getDoctorById(doctorId);
		Appointment ea=appointmentService.getAppointmentById(appointmentId);
		ea.setDisease(new StringBuffer(disease));
		ea.setPrescription(new StringBuffer(prescription.trim()));
		appointmentService.updateAppointment(ea);
		return "redirect:/doctorDashBoard";
	}
	@PostMapping("/dischargePatient/{doctorId}/{appointmentId}")
	public String dischargePatient(@PathVariable Long doctorId,@PathVariable Long appointmentId,Model model)
	{
		Doctor doctor=doctorService.getDoctorById(doctorId);
		Appointment ea=appointmentService.getAppointmentById(appointmentId);
		ea.setStatus("discharged");
		Department dep=departmentService.getDepartmentById(ea.getDepId());
		dep.removeBed(ea.getBedNo(), appointmentId);
		int ob=dep.getOccpuiedBeds();
		dep.setOccpuiedBeds(--ob);
		departmentService.updateDepartment(dep);
		appointmentService.updateAppointment(ea);
		return "redirect:/doctorDashBoard";
	}
}