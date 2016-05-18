package com.worksap.stm2016.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Notification;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.modelForm.UserUpdateForm;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.NotificationService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.SkillService;
import com.worksap.stm2016.validator.UserUpdateFormValidator;

@Controller
@RequestMapping(value = "/user")
public class PersonController {
	@Autowired
	PersonService userService;
	@Autowired
	DepartmentService deptService;
	@Autowired
	SkillService skillService;
	@Autowired
	NotificationService notificationService;
	
	
	@Autowired
	UserUpdateFormValidator userUpdateFormValidator;
	
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
	
	@PreAuthorize("@currentUserServiceImpl.hasLogIn(principal)")
	@RequestMapping(value={"/profile"}, method = RequestMethod.GET)
	public String profile(Long userId, Model model) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Person user = userService.findById(curUser.getId());
		List<Skill> curSkillsOb = user.getUserSkillList();
		System.out.println("skill.size: " + curSkillsOb.size());
		if (curSkillsOb != null && curSkillsOb.size() > 0) {
			List<Long> curSkills = new ArrayList<Long>();
			for (Skill oneSkill : curSkillsOb) {
				curSkills.add(oneSkill.getSkillId());
			}
			model.addAttribute("curSkillsOb", curSkillsOb);
			model.addAttribute("curSkills", curSkills);
		}
		if (userId != null) {
			model.addAttribute("curUserId", userId);
		}
		model.addAttribute("user", user);
		List<Skill> allSkills = skillService.findAll();
		model.addAttribute("allSkills", allSkills);
		UserUpdateForm userForm = new UserUpdateForm();
		model.addAttribute("userForm", userForm);
		
		List<Notification> notifications = notificationService.findByOwner(user);
		model.addAttribute("notifications", notifications);
		
		return "user/profile";
	}
	
	@PreAuthorize("@currentUserServiceImpl.hasLogIn(principal)")
	@RequestMapping(value = "/edit",  method = RequestMethod.POST)
	public String edit(@ModelAttribute("userForm") @Valid UserUpdateForm userForm, BindingResult bindingResult, Model model) {
		System.out.println("@edit start!");
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("curUserId", curUser.getId());
		if (!bindingResult.hasErrors()) {
			userUpdateFormValidator.validate(userForm, bindingResult);
		}
		
		if (bindingResult.hasErrors()) {
			System.out.println("Editing user occurs error!");
			return "user/profile";
		}
		try {
			userService.update(userForm);
        } catch (DataIntegrityViolationException e) {
            return "user/profile";
        }
		return "redirect:/user/profile?userId="+curUser.getId();
	}
	
	@RequestMapping(value = "/ajaxNotify")
	@ResponseBody
	public Integer ajaxNotify() {
		System.out.println("@ajaxNotify start!");
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return notificationService.findUnreadByOwner(curUser.getUser()).size();
	}
}
