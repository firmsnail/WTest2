package com.worksap.stm2016.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.PersonService;

@Controller
@RequestMapping(value = "/user")
public class PersonController {
	@Autowired
	PersonService userService;
	@Autowired
	DepartmentService deptService;
	
	@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'RECRUITER', 'C&B-SPECIALIST')")
	@RequestMapping(value = "/showEmployees")
	public String showEmployees(Long departmentId, Model model) {
		List<Person> employeeList = null;
		if (departmentId == null) {
			employeeList = userService.findAll();
		} else {
			Department dept = deptService.findOne(departmentId);
			employeeList = userService.findByDepartment(dept);
		}
		model.addAttribute("employees", employeeList);
		return "user/showEmployees";
	}
	
	@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'RECRUITER', 'C&B-SPECIALIST') or @currentUserServiceImpl.canAccessUser(principal, #personId)")
	@RequestMapping(value = "/showOneEmployee")
	public String showOneEmployee(Long personId, Model model) {
		Person employee = userService.findById(personId);
		model.addAttribute("employee", employee);
		return "user/showOneEmployee";
	}
}
