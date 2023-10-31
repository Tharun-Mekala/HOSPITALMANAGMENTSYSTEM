package com.pro.hms.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
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
import com.pro.hms.entity.LabTechnician;
import com.pro.hms.entity.Patient;
import com.pro.hms.entity.Test;
import com.pro.hms.resources.Password;
import com.pro.hms.service.AppointmentService;
import com.pro.hms.service.DoctorService;
import com.pro.hms.service.EmailService;
import com.pro.hms.service.LabTechnicianService;
import com.pro.hms.service.PatientService;
import com.pro.hms.service.TestService;

@Controller
public class LabTechnicianController {
	
	private LabTechnicianService labTechnicianService;
	private DoctorService doctorService;
	private TestService testService;
	private PatientService patientService;
	private LabTechnician labTechnician;
	private AppointmentService appointmentService;
	
	@Autowired
	private EmailService emailService;
	public LabTechnicianController(LabTechnicianService labTechnicianService,DoctorService doctorService,TestService testService,PatientService patientService,AppointmentService appointmentService) {
		super();
		this.labTechnicianService = labTechnicianService;
		this.doctorService=doctorService;
		this.testService=testService;
		this.patientService=patientService;
		this.appointmentService=appointmentService;
	}
	
	
		
	@GetMapping("/LoginLabTechnician")
	public String loginLT(Model model)
	{
		return "Labtechnician-Login";
	}
	@PostMapping("/LoginLabTechnician")
	public String validateLoginLT(@RequestParam String email,@RequestParam String password,Model model)
	{
		LabTechnician lt= labTechnicianService.getLabTechnicianByEmail(email);
		if(lt==null)
			return "redirect:/LoginLabTechnician?error";
		else
		{
			if(lt.getPassword().equals(password))
			{
				labTechnician =lt;
				return "redirect:/labTechnicianDashBoard";
			}
			else 
			{
				model.addAttribute("error", "Invalid Email/Password.");
				return "LabTechnician-Login";
			}
				
		}
	}
	@PostMapping("/labTechnicianSendNewPassword")
	public String labTechnicianSendNewPassword(@RequestParam String email,Model model)
	{
		LabTechnician lt=labTechnicianService.getLabTechnicianByEmail(email);
		lt.setPassword(Password.generatePassword());
		emailService.sendEmail(lt.getEmail(),"Your Temporary Password.","\nYour Temporary password : "+lt.getPassword()+"\n Please Change Your Password after Login for Security reasons");
		labTechnicianService.saveLabTechnician(lt);
		return "redirect:/LoginLabTechnician";
	}
	@GetMapping("/labTechnicianDashBoard")
	public String LTDashBoard(Model model)
	{
		if(labTechnician==null)
			return "redirect:/LoginLabTechnician";
		List<Test> tests=testService.getByDepartmentAndStatus(labTechnician.getTestingDepartment(), "InCompleted");
		model.addAttribute("tests",tests);
		model.addAttribute("LabTechnician",labTechnician);
		model.addAttribute("doctors",doctorService.getAllDoctors());
        LocalDate maxDate =  LocalDate.now();
        model.addAttribute("maxDate",maxDate);
		return "Labtechnician-DashBoard";
	}
	@PostMapping("/filterListofTests")
	public String filterListofTests(@RequestParam String date,@RequestParam String doctor,@RequestParam String status,Model model)
	{
		List<Test> tests=new ArrayList<>();
		Date selectedDate=null;
		String SelectedDoctor=null,SelectedStatus=null;
		if(!date.isEmpty())
				selectedDate=Date.valueOf(date);
		if(!doctor.equals("Select Doctor"))
			SelectedDoctor=doctor;
		if(!status.equals("Select Status"))
			SelectedStatus=status;
		System.out.println(selectedDate+" "+SelectedDoctor+" "+SelectedStatus);
		if(selectedDate!=null&&SelectedDoctor!=null&&SelectedStatus!=null)
			tests=testService.getByTestDateAndDoctorNameAndStatus(selectedDate, SelectedDoctor, SelectedStatus);
		else if(selectedDate==null&&SelectedDoctor!=null&&SelectedStatus!=null)
			tests=testService.getByDoctorNameAndStatus(SelectedDoctor, SelectedStatus);
		else if(selectedDate!=null&&SelectedDoctor==null&&SelectedStatus!=null)
			tests=testService.getByTestDateAndStatus(selectedDate, SelectedStatus);
		else if(selectedDate!=null&&SelectedDoctor!=null&&SelectedStatus==null)
			tests=testService.getByDoctorNameAndTestDate(SelectedDoctor, selectedDate);
		else if(selectedDate==null&&SelectedDoctor==null&&SelectedStatus!=null)
			tests=testService.getByStatus(SelectedStatus);
		else if(selectedDate==null&&SelectedDoctor!=null&&SelectedStatus==null)
			tests=testService.getByDoctorName(SelectedDoctor);
		else if(selectedDate!=null&&SelectedDoctor==null&&SelectedStatus==null)
			tests=testService.getByTestDate(selectedDate);
		else
			tests=testService.getByDepartmentAndStatus(labTechnician.getTestingDepartment(), "Incompleted");
		model.addAttribute("tests",tests);
		model.addAttribute("doctors",doctorService.getAllDoctors());
		LocalDate maxDate =  LocalDate.now();
	    model.addAttribute("maxDate",maxDate);
		return "Labtechnician-DashBoard";
	}
	@PostMapping("/uploadReportbyLabTechnician/{testId}/{patId}/{aptId}")
	public String uploadReportsByLabtechnician(@PathVariable Long testId,@PathVariable Long patId,@PathVariable Long aptId,@RequestParam MultipartFile[] report,Model model)
	{
		System.out.println("HII");
		Patient p=patientService.getPatientById(patId);
		Appointment ap=appointmentService.getAppointmentById(aptId);
		Test test=testService.getTestById(testId);
		test.setStatus("Completed");
		testService.saveTest(test);
		for(MultipartFile file:report)
		{
			p.addReport(file.getOriginalFilename());
			ap.addReport(file.getOriginalFilename());
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
		return "redirect:/labTechnicianDashBoard";
	}
}
