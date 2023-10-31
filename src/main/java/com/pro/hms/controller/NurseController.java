package com.pro.hms.controller;

import java.io.File;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

import com.pro.hms.entity.Doctor;
import com.pro.hms.entity.Nurse;
import com.pro.hms.resources.Password;
import com.pro.hms.service.AppointmentService;
import com.pro.hms.service.EmailService;
import com.pro.hms.service.NurseService;

@Controller
public class NurseController {
	private NurseService nurseService;
	private AppointmentService appointmentService;
	@Autowired
	private EmailService emailService;
	private Nurse nurse;
	public NurseController(NurseService nurseService,AppointmentService appointmentService) {
		super();
		this.nurseService = nurseService;
		this.appointmentService=appointmentService;
	}
	
	@GetMapping("/loginNurse")
	public String loginNurse()
	{
		return "Nurse-Login";
	}
	@PostMapping("/loginNurse")
	public String verifyNurse(@RequestParam String email,@RequestParam String password,Model model)
	{
		nurse = nurseService.getNurseByEmail(email);
		if(nurse!=null&&nurse.getPassword().equals(password))
			return "redirect:/nurseDashBoard";
		else
		{
			model.addAttribute("error", "Invalid Email/Password.");
			return "Nurse-Login";
		}
	}
	
	@PostMapping("/Nurse-SendPasswordforFP")
	public String nurtorSendOTPforFP(@RequestParam String email,Model model)
	{
		
		Nurse nur=nurseService.getNurseByEmail(email);
		nur.setPassword(Password.generatePassword());
		emailService.sendEmail(nur.getEmail(),"Your Temporary Password.","\nYour Temporary password : "+nur.getPassword()+"\n Please Change Your Password after Login for Security reasons");
		nurseService.updateNurse(nur);
		return "redirect:/loginNurse";
		
	}
	
	@GetMapping("/logoutNurse")
	public String logoutNurse(Model model)
	{
		return "redirect:/loginNurse";
	}
	@GetMapping("/nurseDashBoard")
	public String nurseDashBoard(Model model)
	{
		if(nurse==null)
			return "redirect:/loginNurse";
		model.addAttribute("appointments",appointmentService.getByFloorAndStatus(nurse.getFloor(),"admited"));
		return "Nurse-DashBoard";
	}
}
