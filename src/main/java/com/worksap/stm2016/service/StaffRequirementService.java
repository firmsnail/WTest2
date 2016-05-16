package com.worksap.stm2016.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.modelForm.RequirementForm;

public interface StaffRequirementService {
	public List<StaffRequirement> findAll();
	public Page<StaffRequirement> findAll(Pageable pageable);
	public StaffRequirement save(StaffRequirement staffRequirement);
	public StaffRequirement findOne(Long id);
	public List<StaffRequirement> findByStatus(int status);
	public List<StaffRequirement> findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateBetween(Person recruiter, Department department,
			Integer status, Date startDate, Date endDate);
	public List<StaffRequirement> findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateNotBefore(Person recruiter, Department department,
			Integer status, Date startDate);
	public List<StaffRequirement> findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateNotAfter(Person recruiter, Department department,
			Integer status, Date endDate);
	public List<StaffRequirement> findByRecruiterAndStfrqDepartmentAndStatus(Person recruiter, Department department,
			Integer status);
	public List<StaffRequirement> findByRecruiterAndStatusAndExpectDateBetween(Person recruiter, Integer status, Date startDate,
			Date endDate);
	public List<StaffRequirement> findByRecruiterAndStatusAndExpectDateNotBefore(Person recruiter, Integer status,
			Date startDate);
	public List<StaffRequirement> findByRecruiterAndStatusAndExpectDateNotAfter(Person recruiter, Integer status, Date endDate);
	public List<StaffRequirement> findByRecruiterAndStatus(Person recruiter, Integer status);
	public StaffRequirement add(RequirementForm requirement);
	public List<StaffRequirement> findByDepartment(Department department);
	public List<StaffRequirement> findByHRManager(Person cUser);
	public void delete(Long requirementId);
	public List<StaffRequirement> findByStfrqDepartmentAndStatus(Department dept, Integer status);
}
