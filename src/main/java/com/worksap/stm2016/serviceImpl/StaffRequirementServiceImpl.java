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
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.modelForm.RequirementForm;
import com.worksap.stm2016.repository.DepartmentRepository;
import com.worksap.stm2016.repository.PersonRepository;
import com.worksap.stm2016.repository.RoleRepository;
import com.worksap.stm2016.repository.SkillRepository;
import com.worksap.stm2016.repository.StaffRequirementRepository;
import com.worksap.stm2016.service.StaffRequirementService;
import com.worksap.stm2016.utils.CommonUtils;

@Service
@Transactional
public class StaffRequirementServiceImpl implements StaffRequirementService{

	@Autowired
	private StaffRequirementRepository staffRequirementRepository;
	@Autowired
	private DepartmentRepository deptRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private SkillRepository skillRepository;
	@Autowired
	private RoleRepository roleRepository;

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
		Date beforeDay = CommonUtils.OneDayBefore(startDate);
		return staffRequirementRepository.findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateAfter(recruiter, department, status, beforeDay);
	}

	@Override
	public List<StaffRequirement> findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateNotAfter(Person recruiter, Department department, Integer status, Date endDate) {
		Date afterDay = CommonUtils.OneDayAfter(endDate);
		return staffRequirementRepository.findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateBefore(recruiter, department, status, afterDay);
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
		Date beforeDay = CommonUtils.OneDayBefore(startDate);
		return staffRequirementRepository.findByRecruiterAndStatusAndExpectDateAfter(recruiter, status, beforeDay);
	}

	@Override
	public List<StaffRequirement> findByRecruiterAndStatusAndExpectDateNotAfter(Person recruiter, Integer status, Date endDate) {
		Date afterDay = CommonUtils.OneDayAfter(endDate);
		return staffRequirementRepository.findByRecruiterAndStatusAndExpectDateBefore(recruiter, status, afterDay);
	}

	@Override
	public List<StaffRequirement> findByRecruiterAndStatus(Person recruiter, Integer status) {
		return staffRequirementRepository.findByRecruiterAndStatus(recruiter, status);
	}

	@Override
	public StaffRequirement add(RequirementForm requirement) {
		StaffRequirement curRequirement = new StaffRequirement();
		
		curRequirement.setReason(requirement.getReason());
		Department department = deptRepository.findOne(requirement.getDepartmentId());
		if (department != null) {
			curRequirement.setStfrqDepartment(department);
		}
		curRequirement.setRequireNum(requirement.getRequireNum());
		curRequirement.setExpectDate(requirement.getExpectDate());
		curRequirement.setSubmitDate(new Date());
		
		for (Long skillId : requirement.getSkills()) {
			curRequirement.getStfrqSkillList().add(skillRepository.findOne(skillId));
		}
		
		Role hrManagerRole = roleRepository.findOne(CommonUtils.ROLE_HR_MANAGER);
		Person hrManager = personRepository.findByRole(hrManagerRole).get(0);
		curRequirement.setHrManager(hrManager);
		
		curRequirement.setStatus(CommonUtils.REQUIREMENTS_HR_MANAGER_PROCESSING);
		staffRequirementRepository.save(curRequirement);
		return curRequirement;
	}

	@Override
	public List<StaffRequirement> findByDepartment(Department department) {
		return staffRequirementRepository.findByStfrqDepartment(department);
	}

	@Override
	public List<StaffRequirement> findByHRManager(Person cUser) {
		return staffRequirementRepository.findByHrManager(cUser);
	}

	@Override
	public void delete(Long requirementId) {
		staffRequirementRepository.delete(requirementId);
	}

}
