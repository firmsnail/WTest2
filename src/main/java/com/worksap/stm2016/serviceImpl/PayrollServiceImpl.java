package com.worksap.stm2016.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Payroll;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.repository.PayrollRepository;
import com.worksap.stm2016.repository.PersonRepository;
import com.worksap.stm2016.repository.RoleRepository;
import com.worksap.stm2016.service.PayrollService;
import com.worksap.stm2016.utils.CommonUtils;

@Service
@Transactional
public class PayrollServiceImpl implements PayrollService{

	@Autowired
	private PayrollRepository payrollRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PersonRepository personRepository;

	@Override
	public List<Payroll> findAll() {
		return (List<Payroll>) payrollRepository.findAll();
	}

	@Override
	public Page<Payroll> findAll(Pageable pageable) {
		return payrollRepository.findAll(pageable);
	}

	@Override
	public Payroll save(Payroll payroll) {
		return payrollRepository.save(payroll);
	}

	@Override
	public Payroll findOne(Long id) {
		return payrollRepository.findOne(id);
	}

	@Override
	public List<Payroll> findByPersonAndStartDateAndEndDate(Person cUser, Date startDate, Date endDate) {
		return payrollRepository.findByPayrollEmployeeAndIssueDateBetween(cUser, startDate, endDate);
	}

	@Override
	public List<Payroll> findByPersonAndStartDate(Person cUser, Date startDate) {
		Date beforeDay = CommonUtils.OneDayBefore(startDate);
		return payrollRepository.findByPayrollEmployeeAndIssueDateAfter(cUser, beforeDay);
	}

	@Override
	public List<Payroll> findByPersonAndEndDate(Person cUser, Date endDate) {
		Date afterDay = CommonUtils.OneDayAfter(endDate);
		return payrollRepository.findByPayrollEmployeeAndIssueDateBefore(cUser, afterDay);
	}

	@Override
	public List<Payroll> findByPerson(Person cUser) {
		return payrollRepository.findByPayrollEmployee(cUser);
	}
	
	@Override
	public List<Payroll> findByDepartmentAndStartDateAndEndDate(Department department, Date startDate, Date endDate) {
		return payrollRepository.findByPayrollDepartmentAndIssueDateBetween(department, startDate, endDate);
	}

	@Override
	public List<Payroll> findByDepartmentAndStartDate(Department department, Date startDate) {
		Date beforeDay = CommonUtils.OneDayBefore(startDate);
		return payrollRepository.findByPayrollDepartmentAndIssueDateAfter(department, beforeDay);
	}

	@Override
	public List<Payroll> findByDepartmentAndEndDate(Department department, Date endDate) {
		Date afterDay = CommonUtils.OneDayAfter(endDate);
		return payrollRepository.findByPayrollDepartmentAndIssueDateBefore(department, afterDay);
	}

	@Override
	public List<Payroll> findByDepartment(Department department) {
		return payrollRepository.findByPayrollDepartment(department);
	}

	@Override
	public List<Payroll> findByStartDateAndEndDate(Date startDate, Date endDate) {
		return payrollRepository.findByIssueDateBetween(startDate, endDate);
	}

	@Override
	public List<Payroll> findByStartDate(Date startDate) {
		Date beforeDay = CommonUtils.OneDayBefore(startDate);
		return payrollRepository.findByIssueDateAfter(beforeDay);
	}

	@Override
	public List<Payroll> findByEndDate(Date endDate) {
		Date afterDay = CommonUtils.OneDayAfter(endDate);
		return payrollRepository.findByIssueDateBefore(afterDay);
	}

	@Override
	public List<Payroll> findByPayrollEmployeePeriod(Integer months) {
		return payrollRepository.findByPayrollEmployeePeriodMonth(months);
	}

	@Override
	public List<Payroll> findByPayrollEmployeeGender(Integer gender) {
		return payrollRepository.findByPayrollEmployeeGender(gender);
	}

	@Override
	public List<Payroll> findByPayrollEmployeeAgeRange(Integer ageRange) {
		
		Role role = roleRepository.findOne(CommonUtils.ROLE_SHORT_TERM_EMPLOYEE);
		if (ageRange.equals(CommonUtils.AGE_UNKNOWN_RANGE)) return payrollRepository.findByPayrollEmployeeRole(role);
		Integer startAge = CommonUtils.AGE_MIN, endAge = CommonUtils.AGE_MAX;
		if (ageRange.equals(CommonUtils.AGE_1stRANGE)) {
			startAge = CommonUtils.AGE_1stRANGE_MIN;
			endAge = CommonUtils.AGE_1stRANGE_MAX;
		} else if (ageRange.equals(CommonUtils.AGE_2ndRANGE)) {
			startAge = CommonUtils.AGE_2ndRANGE_MIN;
			endAge = CommonUtils.AGE_2ndRANGE_MAX;
		} else if (ageRange.equals(CommonUtils.AGE_3rdRANGE)) {
			startAge = CommonUtils.AGE_3rdRANGE_MIN;
			endAge = CommonUtils.AGE_3rdRANGE_MAX;
		} else if (ageRange.equals(CommonUtils.AGE_4thRANGE)) {
			startAge = CommonUtils.AGE_4thRANGE_MIN;
			endAge = CommonUtils.AGE_4thRANGE_MAX;
		} else if (ageRange.equals(CommonUtils.AGE_5thRANGE)) {
			startAge = CommonUtils.AGE_5thRANGE_MIN;
			endAge = CommonUtils.AGE_5thRANGE_MAX;
		} else {
			startAge = CommonUtils.AGE_6thRANGE_MIN;
			endAge = CommonUtils.AGE_6thRANGE_MAX;
		}

		return payrollRepository.findByPayrollEmployeeAgeIsBetween(startAge, endAge);
		
	}

	@Override
	public List<Payroll> findByPayrollEmployeeDepartment(Department department) {
		return payrollRepository.findByPayrollEmployeeDepartment(department);
	}

	@Override
	public List<Payroll> findByEmployeeSkill(Skill skill) {
		
		List<Payroll> tmpPayrolls = (List<Payroll>) payrollRepository.findAll();
		List<Payroll> payrolls = new ArrayList<Payroll>();
		for (Payroll payroll : tmpPayrolls) {
			List<Skill> skills = payroll.getPayrollEmployee().getUserSkillList();
			boolean exists = false;
			for (Skill oneSkill : skills) {
				if (oneSkill.getSkillId().equals(skill.getSkillId())) {
					exists = true;
					break;
				}
			}
			if (exists) {
				payrolls.add(payroll);
			}
		}
		
		return payrolls;
	}
	

}
