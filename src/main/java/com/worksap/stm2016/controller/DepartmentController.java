package com.worksap.stm2016.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Dismission;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.DismissionService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.utils.CommonUtils;

@Controller
//@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'RECRUITER', 'C&B-SPECIALIST', 'TEAM-MANAGER')")
@RequestMapping(value = "/department")
public class DepartmentController {
	
	@Autowired
	DepartmentService departmentService;
	@Autowired
	PersonService personService;
	@Autowired
	DismissionService dismissionService;
	
	@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'RECRUITER', 'C&B-SPECIALIST')")
	@RequestMapping(value = "/showDepartments")
	public String showDepartments(Model model) {
		List<Department> departmentList = departmentService.findAll();
		model.addAttribute("departments", departmentList);
		List<Person> managers = personService.findProperManager();
		model.addAttribute("managers", managers);
		return "department/showDepartments";
	}
	
	//@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'RECRUITER', 'C&B-SPECIALIST') or @currentUserServiceImpl.canAccessDepartment(principal, #departmentId)")
	@PreAuthorize("@currentUserServiceImpl.canAccessDepartment(principal, #departmentId)")
	@RequestMapping(value = "/showOneDepartment")
	public String showOneDepartment(Long departmentId, Model model) {
		Department department = departmentService.findOne(departmentId);
		List<Person> employees = department.getEmployees();
		model.addAttribute("department", department);
		model.addAttribute("employees", employees);
		Map<Long, Boolean> disMap = new HashMap<Long, Boolean>();
		for (Person emp : employees) {
			List<Integer> statuses = new ArrayList<Integer>();
			statuses.add(CommonUtils.DISMISSION_HR_MANAGER_PROCESSING);
			statuses.add(CommonUtils.DISMISSION_TEAM_MANAGER_PROCESSING);
			statuses.add(CommonUtils.DISMISSION_CB_SPECIALIST_PROCESSING);
			List<Dismission> dises = dismissionService.findByDismissionPersonAndStatusIn(emp, statuses);
			if (dises.size() > 0) {
				disMap.put(emp.getPersonId(), true);
			} else {
				disMap.put(emp.getPersonId(), false);
			}
		}
		model.addAttribute("disMap", disMap);
		return "department/showOneDepartment";
	}
	
}
