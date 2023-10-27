package com.pro.hms.repository;




import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pro.hms.entity.LabTechnician;

public interface LabTechnicianRepository extends JpaRepository<LabTechnician, Long> {
	
	List<LabTechnician> findTop4ByOrderByDateAsc(Pageable pageable);
	public LabTechnician findByEmail(String email);
	public List<LabTechnician> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrTestingDepartmentContainingIgnoreCase(String name, String email,String department);

}
