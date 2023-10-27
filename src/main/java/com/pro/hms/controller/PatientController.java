package com.pro.hms.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.pro.hms.entity.Patient;
import com.pro.hms.service.AppointmentService;
import com.pro.hms.service.EmailService;
import com.pro.hms.service.PatientService;

import jakarta.servlet.http.HttpSession;


@Controller
public class PatientController {
	private PatientService patientService;
	private AppointmentService appointmentService;
	@Autowired
	private EmailService emailService;
	private String email="";
	public PatientController(PatientService patientService,AppointmentService appointmentService,EmailService emailService) {
		super();
		this.patientService = patientService;
		this.appointmentService=appointmentService;
		this.emailService=emailService;
	}
	@GetMapping("/loginPatient")
	public String loginPatient(Model model)
	{
		Patient patient= new Patient();
		model.addAttribute("patient",patient);
		return "Patient-Login";
	}
	@PostMapping("/patientDashBoard")
	public String verifyPatient(Model model,@RequestParam String email,@RequestParam String password)
	{
		Patient patient= patientService.getPatientByEmail(email);
		if(patient!=null&&patient.getPassword().equals(password))
		{
			this.email=email;
			return "redirect:/patientDashBoard";
		}
		else
			return "redirect:/loginPatient?error";
	}
	
	@GetMapping("/logoutPatient")
	public String logoutpatient()
	{
		
		this.email="";
		return "redirect:/loginPatient";
	}
	
	
	@GetMapping("/patientDashBoard")
	public String patientDashBoard(Model model)
	{
		LocalDate currentDate = LocalDate.now();
		Date date=Date.valueOf(currentDate);
		Patient patient=patientService.getPatientByEmail(this.email);
		model.addAttribute("patient", patient);
		model.addAttribute("todayAppointments",appointmentService.getByPatientIdAndDate(patient.getId(), date));
		model.addAttribute("ongoingAppointments",appointmentService.getByPatientIdAndStatus(patient.getId(), true));
		model.addAttribute("reports",patient.getReports());
		model.addAttribute("treatedAppointments",appointmentService.getByPatientIdAndStatus(patient.getId(), false));
		return "Patient-DashBoard";
	}
	@PostMapping("/addPatient")
	public String saveStudent(@ModelAttribute Patient patient) {
		patientService.savePatient(patient);
		return "Patient-Login";
	}
	@GetMapping("/patients/edit/{id}")
	public String editPatient(@PathVariable Long id, Model model)
	{
		model.addAttribute("patient",patientService.getPatientById(id));
		return "Patient-Edit";
	}
	//update function
	@PostMapping("/patients/{id}")
	public String updatePatient(@PathVariable Long id,
			@ModelAttribute("patient") Patient patient,
			Model model) {
		Patient ep=patientService.getPatientById(id);
		ep.setId(id);
		ep.setName(patient.getName());
		ep.setAge(patient.getAge());
		ep.setEmail(patient.getEmail());
		ep.setPhone(patient.getPhone());
		patientService.updatePatient(ep);
		return "redirect:/patients";	
	}
	//delete function
	@GetMapping("/patients/{id}")
	public String deletePatient(@PathVariable Long id) {
		patientService.deletePatientById(id);
		return "redirect:/patients";
	}
	
	@GetMapping("/deleteAppointment/{id}")
	public String deleteAppointment(@PathVariable Long id,Model model)
	{
		appointmentService.deleteAppointmentById(id);
		return "redirect:/patientDashBoard";
	}
	
	
	@PostMapping("/addReports/{id}")
	public String addReports(@PathVariable Long id,@RequestParam MultipartFile[] report,Model model)
	{
		Patient p=patientService.getPatientById(id);
		for(MultipartFile file:report)
		{
			p.addReport(file.getOriginalFilename());
			try {
        		File filePath=new ClassPathResource("static/Photos").getFile();
        		Path path=Paths.get(filePath.getAbsolutePath()+File.separator+file.getOriginalFilename());
        		Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
		}
		patientService.updatePatient(p);
		return "redirect:/patientDashBoard";
	}
	@PostMapping("/deleteReport/{id}/{report}")
	public String deleteReport(@PathVariable Long id,@PathVariable String report,Model model)
	{
		Patient p=patientService.getPatientById(id);
		p.getReports().remove(report);
		patientService.updatePatient(p);
		return "redirect:/patientDashBoard";
	}
	

}
