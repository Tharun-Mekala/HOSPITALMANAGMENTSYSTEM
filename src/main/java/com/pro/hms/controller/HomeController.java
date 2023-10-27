package com.pro.hms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pro.hms.service.DoctorService;

@Controller
public class HomeController {
	private DoctorService doctorService;
	
	
	public HomeController(DoctorService doctorService) {
		super();
		this.doctorService = doctorService;
	}


	@GetMapping("/")
	public String homePage(Model model)
	{
		model.addAttribute("doctors",doctorService.getAllDoctors());
		return "Home";
	}
	@GetMapping("/contact")
	public String contact(Model model)
	{
		return "Contact";
	}
}
