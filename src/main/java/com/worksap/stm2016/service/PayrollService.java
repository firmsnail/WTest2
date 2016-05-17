package com.worksap.stm2016.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Payroll;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Skill;

public interface PayrollService {
	public List<Payroll> findAll();
	public Page<Payroll> findAll(Pageable pageable);
	public Payroll save(Payroll payroll);
	public Payroll findOne(Long id);
	public List<Payroll> findByPersonAndStartDateAndEndDate(Person cUser, Date startDate, Date endDate);
	public List<Payroll> findByPersonAndStartDate(Person cUser, Date startDate);
	public List<Payroll> findByPersonAndEndDate(Person cUser, Date endDate);
	public List<Payroll> findByPerson(Person cUser);
	public List<Payroll> findByDepartmentAndStartDateAndEndDate(Department department, Date startDate, Date endDate);
	public List<Payroll> findByDepartmentAndStartDate(Department department, Date startDate);
	public List<Payroll> findByDepartmentAndEndDate(Department department, Date endDate);
	public List<Payroll> findByDepartment(Department department);
	public List<Payroll> findByStartDateAndEndDate(Date startDate, Date endDate);
	public List<Payroll> findByStartDate(Date startDate);
	public List<Payroll> findByEndDate(Date endDate);
	public List<Payroll> findByPayrollEmployeePeriod(Integer months);
	public List<Payroll> findByPayrollEmployeeGender(Integer gender);
	public List<Payroll> findByPayrollEmployeeAgeRange(Integer ageRange);
	public List<Payroll> findByPayrollEmployeeDepartment(Department department);
	public List<Payroll> findByEmployeeSkill(Skill skill);
}
