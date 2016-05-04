package com.worksap.stm2016.controller;

import java.util.List;

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

import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.modelForm.DepartmentForm;
import com.worksap.stm2016.modelForm.UserCreateForm;
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
		List<Person> managers = personService.findProperManager();
		model.addAttribute("managers", managers);
		return "hr-manager/addDepartment";
	}
	@RequestMapping(value = "/addDept",  method = RequestMethod.POST)
	public String addDepartment(@ModelAttribute("department") @Valid DepartmentForm department, BindingResult bindingResult) {
		
		departmentFormValidator.validate(department, bindingResult);
		if (bindingResult.hasErrors()) {
			System.out.println("Adding department occurs error!");
			return "redirect:/hr-manager/addDept";
		}
		try {
			departmentService.create(department);
        } catch (DataIntegrityViolationException e) {
            // probably email already exists - very rare case when multiple admins are adding same user
            // at the same time and form validation has passed for more than one of them.
            return "redirect:/hr-manager/addDept";
        }
		return "redirect:/department/showDepartments";
	}
	
	@RequestMapping(value={"/addUser"},  method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("user", new UserCreateForm());
		return "addUser";
	}
	@RequestMapping(value={"/addUser"},  method = RequestMethod.POST)
	public String addAct(@ModelAttribute("user") @Valid UserCreateForm user, BindingResult bindingResult) {
		
		userCreateFormValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			System.out.println("Adding user occurs error!");
			return "redirect:/hr-manager/addUser";
		}
		try {
			personService.create(user);
        } catch (DataIntegrityViolationException e) {
            // probably email already exists - very rare case when multiple admins are adding same user
            // at the same time and form validation has passed for more than one of them.
            return "redirect:/hr-manager/addUser";
        }
		return "redirect:/user/showUsers";
		//return "redirect:/showPersonList";
	}
}
