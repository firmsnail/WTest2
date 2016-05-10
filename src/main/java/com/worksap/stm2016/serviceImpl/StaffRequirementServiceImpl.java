package com.worksap.stm2016.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.repository.StaffRequirementRepository;
import com.worksap.stm2016.service.StaffRequirementService;

@Service
@Transactional
public class StaffRequirementServiceImpl implements StaffRequirementService{

	@Autowired
	private StaffRequirementRepository staffRequirementRepository;

	@Override
	public List<StaffRequirement> findAll() {
		return (List<StaffRequirement>) staffRequirementRepository.findAll();
	}

	@Override
	public Page<StaffRequirement> findAll(Pageable pageable) {
		return staffRequirementRepository.findAll(pageable);
	}

	@Override
	public StaffRequirement save(StaffRequirement staffRequirement) {
		return staffRequirementRepository.save(staffRequirement);
	}

	@Override
	public StaffRequirement findOne(Long id) {
		return staffRequirementRepository.findOne(id);
	}

	@Override
	public List<StaffRequirement> findByStatus(int status) {
		
		return staffRequirementRepository.findByStatus(status);
	}

	@Override
	public List<StaffRequirement> findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateBetween(Person recruiter, Department department, Integer status, Date startDate, Date endDate) {
		return staffRequirementRepository.findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateBetween(recruiter, department, status, startDate, endDate);
	}

	@Override
	public List<StaffRequirement> findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateNotBefore(Person recruiter, Department department, Integer status, Date startDate) {
		return staffRequirementRepository.findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateNotBefore(recruiter, department, status, startDate);
	}

	@Override
	public List<StaffRequirement> findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateNotAfter(Person recruiter, Department department, Integer status, Date endDate) {
		return staffRequirementRepository.findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateNotAfter(recruiter, department, status, endDate);
	}

	@Override
	public List<StaffRequirement> findByRecruiterAndStfrqDepartmentAndStatus(Person recruiter, Department department, Integer status) {
		return staffRequirementRepository.findByRecruiterAndStfrqDepartmentAndStatus(recruiter, department, status);
	}

	@Override
	public List<StaffRequirement> findByRecruiterAndStatusAndExpectDateBetween(Person recruiter, Integer status, Date startDate, Date endDate) {
		return staffRequirementRepository.findByRecruiterAndStatusAndExpectDateBetween(recruiter, status, startDate, endDate);
	}

	@Override
	public List<StaffRequirement> findByRecruiterAndStatusAndExpectDateNotBefore(Person recruiter, Integer status, Date startDate) {
		return staffRequirementRepository.findByRecruiterAndStatusAndExpectDateNotBefore(recruiter, status, startDate);
	}

	@Override
	public List<StaffRequirement> findByRecruiterAndStatusAndExpectDateNotAfter(Person recruiter, Integer status, Date endDate) {
		return staffRequirementRepository.findByRecruiterAndStatusAndExpectDateNotAfter(recruiter, status, endDate);
	}

	@Override
	public List<StaffRequirement> findByRecruiterAndStatus(Person recruiter, Integer status) {
		return staffRequirementRepository.findByRecruiterAndStatus(recruiter, status);
	}
	

}
