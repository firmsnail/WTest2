package com.worksap.stm2016.controller;


import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Payroll;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.PayrollService;
import com.worksap.stm2016.service.PersonService;

@Controller
@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'C&B-SPECIALIST', 'SHORT-TERM-EMPLOYEE')")
@RequestMapping(value = "/payroll")
public class PayrollController {
	
	@Autowired
	PayrollService payrollService;
	@Autowired
	PersonService personService;
	@Autowired
	DepartmentService departmentService;
	
	@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'C&B-SPECIALIST')")
	@RequestMapping(value={"/showPayrolls"},  method = RequestMethod.GET)
	public String showPayrolls(Long departmentId, Long personId, Date startDate, Date endDate, Model model) {
		List<Payroll> payrolls = null;
		if (personId != null) {
			Person cUser = personService.findById(personId);
			if (cUser != null) {
				if (startDate != null && endDate != null) {
					payrolls = payrollService.findByPersonAndStartDateAndEndDate(cUser, startDate, endDate);
				} else if (startDate != null) {
					payrolls = payrollService.findByPersonAndStartDate(cUser, startDate);
				} else if (endDate != null) {
					payrolls = payrollService.findByPersonAndEndDate(cUser, endDate);
				} else {
					payrolls = payrollService.findByPerson(cUser);
				}
			}
		} else if (departmentId != null){
				Department department = departmentService.findOne(departmentId);
				if (department != null) {
					if (startDate != null && endDate != null) {
						payrolls = payrollService.findByDepartmentAndStartDateAndEndDate(department, startDate, endDate);
					} else if (startDate != null) {
						payrolls = payrollService.findByDepartmentAndStartDate(department, startDate);
					} else if (endDate != null) {
						payrolls = payrollService.findByDepartmentAndEndDate(department, endDate);
					} else {
						payrolls = payrollService.findByDepartment(department);
					}
				}
		} else {
			if (startDate != null && endDate != null) {
				payrolls = payrollService.findByStartDateAndEndDate(startDate, endDate);
			} else if (startDate != null) {
				payrolls = payrollService.findByStartDate(startDate);
			} else if (endDate != null) {
				payrolls = payrollService.findByEndDate(endDate);
			} else {
				payrolls = payrollService.findAll();
			}
		}
		if (payrolls != null) {
			model.addAttribute("payrolls", payrolls);
		}
		return "payroll/showPayrolls";
	}
	
	@PreAuthorize("hasAnyAuthority('SHORT-TERM-EMPLOYEE')")
	@RequestMapping(value={"/showPayrollsByPerson"},  method = RequestMethod.GET)
	public String showPayrollsByPerson(Date startDate, Date endDate, Model model) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Payroll> payrolls = null;
		if (startDate != null && endDate != null) {
			payrolls = payrollService.findByPersonAndStartDateAndEndDate(curUser.getUser(), startDate, endDate);
		} else if (startDate != null) {
			payrolls = payrollService.findByPersonAndStartDate(curUser.getUser(), startDate);
		} else if (endDate != null) {
			payrolls = payrollService.findByPersonAndEndDate(curUser.getUser(), endDate);
		} else {
			payrolls = payrollService.findByPerson(curUser.getUser());
		}
		model.addAttribute("payrolls", payrolls);
		return "payroll/showPayrolls";
	}
	
}
