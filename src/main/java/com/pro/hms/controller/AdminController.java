package com.pro.hms.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

import com.pro.hms.entity.Department;
import com.pro.hms.entity.Doctor;
import com.pro.hms.entity.LabTechnician;
import com.pro.hms.entity.Nurse;
import com.pro.hms.resources.Password;
import com.pro.hms.service.DepartmentService;
import com.pro.hms.service.DoctorService;
import com.pro.hms.service.EmailService;
import com.pro.hms.service.LabTechnicianService;
import com.pro.hms.service.NurseService;

@Controller
public class AdminController {
	private DoctorService doctorService;
	private NurseService nurseService;
	private DepartmentService departmentService;
	private LabTechnicianService labTechnicianService;
	
	private String admin="";
	@Autowired
	private EmailService emailService;
	
	
	public AdminController(DoctorService doctorrService,NurseService nurseService,DepartmentService departmentService,LabTechnicianService labTechnicianService) {
		super();
		this.doctorService = doctorrService;
		this.nurseService=nurseService;
		this.departmentService=departmentService;
		this.labTechnicianService=labTechnicianService;
	}
	@GetMapping("/loginAdmin")
	public String admin(Model model)
	{
		return "Admin-Login";
	}
	@PostMapping("/loginAdmin")
	public String loginAdmin(@RequestParam String email,@RequestParam String password,Model model) {
		System.out.println(email+" "+password);
		
		if(email.equals("user")&&password.equals("user"))
		{
			admin="on";
			return "redirect:/AdminDashBoard";
		}
		return "redirect:/loginAdmin?error";
	}
	@GetMapping("/AdminDashBoard")
	public String AdminDashBoard(Model model)
	{
		if(!admin.equals("on"))
			return "redirect:/loginAdmin";
		model.addAttribute("labTechnicians",labTechnicianService.getTop4LabTechniciansWithMostExperience());
		model.addAttribute("nurses",nurseService.getTop4NursesWithMostExperience());
		model.addAttribute("doctors",doctorService.getTop4DoctorsWithMostExperience());
		model.addAttribute("cardiology",departmentService.getDepartmentByName("CARDIOLOGY"));
		model.addAttribute("general",departmentService.getDepartmentByName("GENERAL"));
		model.addAttribute("icu",departmentService.getDepartmentByName("ICU"));
		model.addAttribute("emergency",departmentService.getDepartmentByName("EMERGENCY"));
		model.addAttribute("neurology",departmentService.getDepartmentByName("NEUROLOGY"));
		model.addAttribute("gyenocology",departmentService.getDepartmentByName("GYNECOLOGY"));
		return "Admin-DashBoard";
	}
	@GetMapping("/logoutAdmin")
	public String logoutAdmin()
	{
		admin=null;
		return "Admin-Login";
	}
	
	//Doctor all functions
	@GetMapping("/doctors")
	public String listDoctor(Model model)
	{
		if(!admin.equals("on"))
			return "redirect:/loginAdmin";
		ArrayList<Doctor> doctor=(ArrayList<Doctor>) doctorService.getAllDoctors();
		Collections.sort(doctor,new Comparator<Doctor>() {
            @Override
            public int compare(Doctor d1, Doctor d2) {
                return d1.getDate().compareTo(d2.getDate());
            }
        });
		model.addAttribute("doctors",doctor );
		return "Admin-Doctor-List";
	}
	@PostMapping("/doctors")
	public String searchDoctor(Model model,@RequestParam String key)
	{
		model.addAttribute("doctors", doctorService.getAllDoctorsByNameOrEmail(key));
		return "Admin-Doctor-List";
	}
	@GetMapping("/addDoctor")
	public String addDoctor(Model model)
	{
		
		if(!admin.equals("on"))
			return "redirect:/loginAdmin";
		Doctor doctor = new Doctor();
		model.addAttribute("doctor", doctor);
		model.addAttribute("departments",departmentService.getAllDepartment());
		return "Doctor-Register";
	}
	@PostMapping("/addDoctor")
	public String saveDoctor(@ModelAttribute Doctor doctor,@RequestParam MultipartFile profilePhoto)
	{      
		    doctor.setProfile_photo(profilePhoto.getOriginalFilename());
		    doctor.setPassword(Password.generatePassword());
	        if(doctor!=null)
	        {
	        	try {
	        		File file=new ClassPathResource("static/Photos").getFile();
	        		Path path=Paths.get(file.getAbsolutePath()+File.separator+profilePhoto.getOriginalFilename());
	        		Files.copy(profilePhoto.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
	        	}
	        	catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	        }
	        Department dep=departmentService.getDepartmentByName(doctor.getDepartment());
	        dep.setDoctors(dep.getDoctors()+1);
	        departmentService.updateDepartment(dep);
	        doctorService.saveDoctor(doctor);
	       //emailService.sendEmail(doctor.getEmail(),"Successfully Account Created","Your user id : "+doctor.getEmail()+"\nYour password : "+doctor.getPassword()+"\n");
	        return "redirect:/doctors";
	}
	@GetMapping("/doctors/edit/{id}")
	public String editDoctor(@PathVariable Long id, Model model)
	{
		if(!admin.equals("on"))
			return "redirect:/loginAdmin";
		model.addAttribute("doctor",doctorService.getDoctorById(id));
		model.addAttribute("departments",departmentService.getAllDepartment());
		return "Doctor-Edit";
	}
	//update function
	@PostMapping("/doctors/{id}")
	public String updateDoctor(@PathVariable Long id,
			@ModelAttribute("doctor") Doctor doctor,Model model,@RequestParam MultipartFile profilePhoto) {
		Doctor ed=doctorService.getDoctorById(id);
		if(ed.getDepartment()!=doctor.getDepartment())
		{
			Department dep=departmentService.getDepartmentByName(ed.getDepartment());
			dep.setDoctors(dep.getDoctors()-1);
			departmentService.updateDepartment(dep);
			dep=departmentService.getDepartmentByName(doctor.getDepartment());
			dep.setDoctors(dep.getDoctors()+1);
			departmentService.updateDepartment(dep);
		}
		ed.setId(id);
		ed.setName(doctor.getName());
		ed.setEmail(doctor.getEmail());
		if(doctor.getGender()!=null)
		ed.setGender(doctor.getGender());
		ed.setPassword(doctor.getPassword());
		ed.setPhone(doctor.getPhone());
		ed.setDepartment(doctor.getDepartment());
		if(!profilePhoto.isEmpty())
        {
			ed.setProfile_photo(profilePhoto.getOriginalFilename());
        	try {
        		File file=new ClassPathResource("static/Photos").getFile();
        		Path path=Paths.get(file.getAbsolutePath()+File.separator+profilePhoto.getOriginalFilename());
        		Files.copy(profilePhoto.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
		doctorService.updateDoctor(ed);
		return "redirect:/doctors";
	}
	//delete function
	@GetMapping("/doctors/{id}")
	public String deleteDoctor(@PathVariable Long id) {
		if(!admin.equals("on"))
			return "redirect:/loginAdmin";
		String profilePhoto = doctorService.getDoctorById(id).getProfile_photo();
		try 
		{
    		File file=new ClassPathResource("static/Photos").getFile();
    		Path path=Paths.get(file.getAbsolutePath()+File.separator+profilePhoto);
    		Files.delete(path);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		doctorService.deleteDoctorById(id);
		return "redirect:/doctors";
	}
	
	//Nurse all Functions
	
	@GetMapping("/nurses")
	public String listNurse(Model model)
	{
		if(!admin.equals("on"))
			return "redirect:/loginAdmin";
		model.addAttribute("nurses",nurseService.getAllNurses());
		return "Admin-Nurse-List";
	}
	@PostMapping("/nurses")
	public String searchNurse(Model model)
	{
		model.addAttribute("nurses",nurseService.getAllNurses());
		return "Admin-Nurse-List";
	}
	@GetMapping("/addNurse")
	public String addNurse(Model model)
	{
		if(!admin.equals("on"))
			return "redirect:/loginAdmin";
		Nurse nurse=new Nurse();
		model.addAttribute("nurse",nurse);
		return "Nurse-Register";
	}
	@PostMapping("/addNurse")
	public String saveNurse(@ModelAttribute Nurse nurse,@RequestParam MultipartFile profilePhoto)
	{
		 nurse.setProfile_photo(profilePhoto.getOriginalFilename());
		    nurse.setPassword(Password.generatePassword());
	        nurseService.saveNurse(nurse);
	        if(nurse!=null)
	        {
	        	try {
	        		File file=new ClassPathResource("static/Photos").getFile();
	        		Path path=Paths.get(file.getAbsolutePath()+File.separator+profilePhoto.getOriginalFilename());
	        		Files.copy(profilePhoto.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
	        	}
	        	catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	        }
	    emailService.sendEmail(nurse.getEmail(),"Successfully Account Created","Your user id : "+nurse.getEmail()+"\nYour password : "+nurse.getPassword()+"\n");
		return "redirect:/nurses";
	}
	@GetMapping("/nurses/edit/{id}")
	public String editNurse(@PathVariable Long id, Model model)
	{
		if(!admin.equals("on"))
			return "redirect:/loginAdmin";
		model.addAttribute("nurse",nurseService.getNurseById(id));
		return "Nurse-Edit";
	}
	@PostMapping("/nurses/{id}")
	public String updatenoctor(@PathVariable Long id,
			@ModelAttribute("nurse") Nurse nurse,
			Model model,@RequestParam MultipartFile profilePhoto) {
		Nurse en=nurseService.getNurseById(id);
		en.setId(id);
		en.setName(nurse.getName());
		en.setEmail(nurse.getEmail());
		if(nurse.getGender()!=null)
		en.setGender(nurse.getGender());
		en.setPhone(nurse.getPhone());
		en.setShift(nurse.getShift());
		en.setPassword(nurse.getPassword());
		if(!profilePhoto.isEmpty())
        {
			en.setProfile_photo(profilePhoto.getOriginalFilename());
        	try {
        		File file=new ClassPathResource("static/Photos").getFile();
        		Path path=Paths.get(file.getAbsolutePath()+File.separator+profilePhoto.getOriginalFilename());
        		Files.copy(profilePhoto.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
		nurseService.updateNurse(en);
		return "redirect:/nurses";	
	}
	@GetMapping("/nurses/{id}")
	public String deleteNurse(@PathVariable Long id) {
		if(!admin.equals("on"))
			return "redirect:/loginAdmin";
		String profilePhoto = nurseService.getNurseById(id).getProfile_photo();
		try 
		{
    		File file=new ClassPathResource("static/Photos").getFile();
    		Path path=Paths.get(file.getAbsolutePath()+File.separator+profilePhoto);
    		Files.delete(path);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		nurseService.deleteNurseById(id);
		return "redirect:/nurses";
	}
	@PostMapping("/viewAllNursesBySearch")
	public String viewAllNursesBySearch(@RequestParam String Key,Model model)
	{
		if(Key.isEmpty())
			model.addAttribute("nurses",nurseService.getAllNurses());
		model.addAttribute("nurses",nurseService.getAllNurseByNameOrEmailOrShift(Key));
		return "Admin-Nurse-List";
	}
	
	//Lab Technician all Functions 
	

	@GetMapping("/addLabTechnician")
	public String addLabTechnician(Model model)
	{
		if(!admin.equals("on"))
			return "redirect:/loginAdmin";
		LabTechnician lt=new LabTechnician();
		model.addAttribute("labTechnician",lt);
		List<String> departments=new ArrayList<>();
		departments.add("PHLEBOTOMY");
		departments.add("X-RAY");
		departments.add("MRI");
		departments.add("CT-SCAN");
		departments.add("2D-ECHO");
		departments.add("ECG");
		departments.add("ENDOSCOPY");
		model.addAttribute("labDepartments",departments);
		return "LabTechnician-Register";
	}
	@PostMapping("/addLabTechnician")
	public String saveLabtechnician(@ModelAttribute LabTechnician  labTechnician,@RequestParam MultipartFile profilePhoto,Model model)
	{
		labTechnician.setProfile_photo(profilePhoto.getOriginalFilename());
		labTechnician.setPassword(Password.generatePassword());
		 if(labTechnician!=null)
	        {
	        	try {
	        		File file=new ClassPathResource("static/Photos").getFile();
	        		Path path=Paths.get(file.getAbsolutePath()+File.separator+profilePhoto.getOriginalFilename());
	        		Files.copy(profilePhoto.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
	        	}
	        	catch(Exception e)
	        	{
	        		e.printStackTrace();
	        	}
	        }
		labTechnicianService.saveLabTechnician(labTechnician);
		return "redirect:/addLabTechnician";
	}

	@GetMapping("/editLabTechnician/{id}")
	public String editLabTechnician(@PathVariable Long id,Model model)
	{
		if(!admin.equals("on"))
			return "redirect:/loginAdmin";
		model.addAttribute("labtechnician",labTechnicianService.getLabTechnicianById(id));
		List<String> departments=new ArrayList<>();
		departments.add("PHLEBOTOMY");
		departments.add("X-RAY");
		departments.add("MRI");
		departments.add("CT-SCAN");
		departments.add("2D-ECHO");
		departments.add("ECG");
		departments.add("ENDOSCOPY");
		model.addAttribute("departments",departments);

		return "LabTechnician-Edit";
	}
	@PostMapping("/editLabTechnician/{id}")
	public String updateLabtechnician(@PathVariable Long id,@ModelAttribute LabTechnician  labTechnician,@RequestParam MultipartFile profilePhoto,Model model)
	{
		LabTechnician lt = labTechnicianService.getLabTechnicianById(id);
		if(lt.getTestingDepartment()!=labTechnician.getTestingDepartment())
			lt.setTestingDepartment(labTechnician.getTestingDepartment());
		lt.setDate(labTechnician.getDate());
		lt.setEmail(labTechnician.getEmail());
		lt.setExperience(labTechnician.getExperience());
		lt.setGender(labTechnician.getGender());
		lt.setName(labTechnician.getName());
		lt.setPassword(labTechnician.getPassword());
		lt.setPhone(labTechnician.getPhone());
		if(!profilePhoto.isEmpty())
        {
        	try {
        		File file=new ClassPathResource("static/Photos").getFile();
        		Path path=Paths.get(file.getAbsolutePath()+File.separator+lt.getProfile_photo());
        		Files.delete(path);
        		path=Paths.get(file.getAbsolutePath()+File.separator+profilePhoto.getOriginalFilename());
        		lt.setProfile_photo(profilePhoto.getOriginalFilename());
        		Files.copy(profilePhoto.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
        	}
        	catch(Exception e)
        	{
        		e.printStackTrace();
        	}
        }
		labTechnicianService.updateLabTechnician(lt);
		return "redirect:/viewAllLabTechnicians";
	}
	
	@GetMapping("/deleteLabtechnician/{Id}")
	public String deleteLabTechnician(@PathVariable Long Id,Model model)
	{
		LabTechnician lt=labTechnicianService.getLabTechnicianById(Id);
		try {
			File file=new ClassPathResource("static/Photos").getFile();
    		Path path=Paths.get(file.getAbsolutePath()+File.separator+lt.getProfile_photo());
    		Files.delete(path);
		}
		catch(Exception e)
    	{
    		e.printStackTrace();
    	}
		labTechnicianService.deleteLabTechnicianById(Id);
		return "redirect:/viewAllLabTechnicians";
	}
	
	@GetMapping("/viewAllLabTechnicians")
	public String ListOfLabTechnician(Model model)
	{
		if(!admin.equals("on"))
			return "redirect:/loginAdmin";
		model.addAttribute("labTechnicians",labTechnicianService.getAllLabTechnicians());
		return "Admin-LabTechnician-List";
	}
	@PostMapping("/viewAllLabTechniciansBySearch")
	public String SearchLabTechnician(@RequestParam String Key,Model model)
	{
		if(Key.isEmpty())
			model.addAttribute("labTechnicians",labTechnicianService.getAllLabTechnicians());
		else
			model.addAttribute("labTechnicians",labTechnicianService.getAllLabTechniciansByNameOrEmailOrDepartment(Key));
		return "Admin-LabTechnician-List";
	}
	
}
