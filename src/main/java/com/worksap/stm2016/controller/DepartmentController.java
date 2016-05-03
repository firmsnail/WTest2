package com.worksap.stm2016.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.service.DepartmentService;

@Controller
@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'RECRUITER', 'C&B-SPECIALIST', 'TEAM-MANAGER')")
@RequestMapping(value = "/department")
public class DepartmentController {
	
	@Autowired
	DepartmentService departmentService;
	
	@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'RECRUITER', 'C&B-SPECIALIST')")
	@RequestMapping(value = "/showDepartments")
	public String showDepartments(HttpServletRequest request, Model model) {
		List<Department> departmentList = departmentService.findAll();
		model.addAttribute("departments", departmentList);
		return "showDepartments";
	}
	
	@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'RECRUITER', 'C&B-SPECIALIST') or @currentUserServiceImpl.canAccessUser(principal, #id)")
	@RequestMapping(value = "/showDepartments")
	public String showOneDepartments(Long departmentId, HttpServletRequest request, Model model) {
		Department department = departmentService.findOne(departmentId);
		model.addAttribute("department", department);
		return "showDepartments";
	}
	
}
