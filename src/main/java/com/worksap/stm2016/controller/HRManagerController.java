package com.worksap.stm2016.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.worksap.stm2016.ModelForm.DepartmentForm;
import com.worksap.stm2016.ModelForm.UserCreateForm;
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.validator.DepartmentFormValidator;
import com.worksap.stm2016.validator.UserCreateFormValidator;

@Controller
@PreAuthorize("hasAuthority('HR-MANAGER')")
@RequestMapping(value = "/hr-manager")
public class HRManagerController {
	
	@Autowired
	DepartmentService departmentService;
	@Autowired
	PersonService personService;
	@Autowired
	private UserCreateFormValidator  userCreateFormValidator;
	@Autowired
	private DepartmentFormValidator  departmentFormValidator;
	
	@RequestMapping(value = "/addDept",  method = RequestMethod.GET)
	public String addDepartment(Model model) {
		model.addAttribute("department", new DepartmentForm());
		return "addDept";
	}
	@RequestMapping(value = "/addDept",  method = RequestMethod.POST)
	public String addDepartment(@ModelAttribute("department") @Valid DepartmentForm department, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			System.out.println("Adding department occurs error!");
			return "redirect:/addDept";
		}
		
		try {
			departmentService.create(department);
        } catch (DataIntegrityViolationException e) {
            // probably email already exists - very rare case when multiple admins are adding same user
            // at the same time and form validation has passed for more than one of them.
            return "redirect:/addDept";
        }
		return "redirect:/department/showDepartments";
	}
	
	
	@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'RECRUITER', 'C&B-SPECIALIST') or @currentUserServiceImpl.canAccessDepartment(principal, #departmentId)")
	@RequestMapping(value = "/showOneDepartment")
	public String showOneDepartment(Long departmentId, HttpServletRequest request, Model model) {
		Department department = departmentService.findOne(departmentId);
		model.addAttribute("department", department);
		return "showOneDepartment";
	}
	
}
