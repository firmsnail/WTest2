package com.worksap.stm2016.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.modelForm.DepartmentForm;
import com.worksap.stm2016.modelForm.UserCreateForm;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.DismissionService;
import com.worksap.stm2016.service.HireService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.RecruitingPlanService;
import com.worksap.stm2016.service.StaffRequirementService;
import com.worksap.stm2016.utils.CommonUtils;
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
	StaffRequirementService staffRequirementService;
	@Autowired
	RecruitingPlanService recruitingPlanService;
	@Autowired
	HireService hireService;
	@Autowired
	DismissionService dismissionService;

	@Autowired
	private UserCreateFormValidator  userCreateFormValidator;
	@Autowired
	private UserCreateFormValidator  userAddFormValidator;
	@Autowired
	private DepartmentFormValidator  departmentFormValidator;
	
	@RequestMapping(value = "/addDept",  method = RequestMethod.GET)
	public String addDepartment(Model model) {
		model.addAttribute("department", new DepartmentForm());
		List<Person> managers = personService.findProperManager();
		model.addAttribute("managers", managers);
		return "hr-manager/addDepartment";
	}
	@RequestMapping(value = "/addDept",  method = RequestMethod.POST)
	public String addDepartment(@ModelAttribute("department") @Valid DepartmentForm department, BindingResult bindingResult) {
		
		departmentFormValidator.validate(department, bindingResult);
		if (bindingResult.hasErrors()) {
			System.out.println("Adding department occurs error!");
			
			for (ObjectError obj : bindingResult.getAllErrors()) {
				System.out.println("error: " + obj);
			}
			return "hr-manager/addDepartment";
		}
		try {
			departmentService.create(department);
        } catch (DataIntegrityViolationException e) {
            return "hr-manager/addDepartment";
        }
		return "redirect:/department/showDepartments";
	}
	
	@RequestMapping(value={"/showStaffRequirements"},  method = RequestMethod.GET)
	public String showStaffRequirements(Model model) {
		List<StaffRequirement> requirements = staffRequirementService.findByStatus(CommonUtils.REQUIREMENTS_HR_MANAGER_PROCESSING);
		model.addAttribute("requirements", requirements);
		return "hr-manager/showStaffRequirements";
	}
	
	@RequestMapping(value={"/addUser"},  method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("user", new UserCreateForm());
		List<Department> departments = departmentService.findAll();
		model.addAttribute("departments", departments);
		return "hr-manager/addUser";
	}
	@RequestMapping(value={"/addUser"},  method = RequestMethod.POST)
	public String addAct(@ModelAttribute("user") @Valid UserCreateForm user, BindingResult bindingResult) {
		
		userCreateFormValidator.validate(user, bindingResult);
		userAddFormValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			System.out.println("Adding user occurs error!");
			return "hr-manager/addUser";
		}
		try {
			personService.add(user);
        } catch (DataIntegrityViolationException e) {
            return "hr-manager/addUser";
        }
		return "redirect:/user/showEmployees";
	}
}
