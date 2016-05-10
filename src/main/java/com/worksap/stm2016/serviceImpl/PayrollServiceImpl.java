package com.worksap.stm2016.serviceImpl;

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
import com.worksap.stm2016.repository.PayrollRepository;
import com.worksap.stm2016.service.PayrollService;
import com.worksap.stm2016.utils.CommonUtils;

@Service
@Transactional
public class PayrollServiceImpl implements PayrollService{

	@Autowired
	private PayrollRepository payrollRepository;

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
	

}
