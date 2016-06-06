package com.worksap.stm2016.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.modelForm.UserUpdateForm;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.NotificationService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.RoleService;
import com.worksap.stm2016.service.SkillService;
import com.worksap.stm2016.utils.CommonUtils;
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
	RoleService roleService;
	
	
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
		Role hrmRole = roleService.findOne(CommonUtils.ROLE_HR_MANAGER);
		List<Person> pers = userService.findByRole(hrmRole);
		Person HRM = pers.get(0);
		List<Department> depts = deptService.findByManagerIsNot(HRM);
		model.addAttribute("departments", depts);
		model.addAttribute("employees", employeeList);
		return "user/showEmployees";
	}
	
	//@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'RECRUITER', 'C&B-SPECIALIST') or @currentUserServiceImpl.canAccessUser(principal, #personId)")
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #personId)")
	@RequestMapping(value = "/showOneEmployee")
	public String showOneEmployee(Long personId, Model model) {
		//Person employee = userService.findById(personId);
		//model.addAttribute("employee", employee);
		return "redirect:/user/profile?userId="+personId;
	}
	
	//@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'RECRUITER', 'C&B-SPECIALIST', 'TEAM-MANAGER') or @currentUserServiceImpl.canAccessUser(principal, #userId)")
	@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #personId)")
	@RequestMapping(value={"/profile"}, method = RequestMethod.GET)
	public String profile(Long userId, Model model) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Person user = null;
		if (userId == null) user = userService.findById(curUser.getId());
		else user = userService.findById(userId);
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
		Map<Long, Boolean> isRead = new HashMap<Long, Boolean>();
		if (userId == curUser.getId()) {
			for (Notification notification : notifications) {
				if (notification.getStatus().equals(CommonUtils.NOTIFICATION_STATUS_READ)) {
					isRead.put(notification.getNotificationId(), true);
				} else {
					isRead.put(notification.getNotificationId(), false);
				}
				notification.setStatus(CommonUtils.NOTIFICATION_STATUS_READ);
				notification = notificationService.findOne(notification.getNotificationId());
			}
			notifications = notificationService.findByOwner(user);
		}
		model.addAttribute("isRead", isRead);
		if (notifications != null) {
			Collections.sort(notifications, new Comparator<Notification>() {
				public int compare(Notification noti0, Notification noti1) {
					if (noti0.getIssueTime().after(noti1.getIssueTime())) return -1;
					else if (noti0.getIssueTime().before(noti1.getIssueTime())) return 1;
					else return 0;
				}
			});
		}
		model.addAttribute("notifications", notifications);
		
		return "user/profile";
	}
	
	@PreAuthorize("@currentUserServiceImpl.hasLogIn(principal)")
	@RequestMapping(value = "/edit",  method = RequestMethod.POST)
	public String edit(@ModelAttribute("userForm") @Valid UserUpdateForm userForm, BindingResult bindingResult, Model model, HttpServletRequest request) {
		System.out.println("@edit start!");
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("curUserId", curUser.getId());
		if (!bindingResult.hasErrors()) {
			userUpdateFormValidator.validate(userForm, bindingResult);
		}
		
		if (bindingResult.hasErrors()) {
			//model.addAttribute("isSet", true);
			Long userId = curUser.getId();
			Person user = null;
			if (userId == null) user = userService.findById(curUser.getId());
			else user = userService.findById(userId);
			List<Skill> curSkillsOb = user.getUserSkillList();
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
			
			List<Notification> notifications = notificationService.findByOwner(user);
			Map<Long, Boolean> isRead = new HashMap<Long, Boolean>();
			if (userId == curUser.getId()) {
				for (Notification notification : notifications) {
					if (notification.getStatus().equals(CommonUtils.NOTIFICATION_STATUS_READ)) {
						isRead.put(notification.getNotificationId(), true);
					} else {
						isRead.put(notification.getNotificationId(), false);
					}
					notification.setStatus(CommonUtils.NOTIFICATION_STATUS_READ);
					notification = notificationService.findOne(notification.getNotificationId());
				}
				notifications = notificationService.findByOwner(user);
			}
			model.addAttribute("isRead", isRead);
			if (notifications != null) {
				Collections.sort(notifications, new Comparator<Notification>() {
					public int compare(Notification noti0, Notification noti1) {
						if (noti0.getIssueTime().after(noti1.getIssueTime())) return -1;
						else if (noti0.getIssueTime().before(noti1.getIssueTime())) return 1;
						else return 0;
					}
				});
			}
			model.addAttribute("notifications", notifications);
			model.addAttribute("isSet", true);
			
			
			return "user/profile";
		}
		try {
			userService.update(userForm);
        } catch (DataIntegrityViolationException e) {
        	
        	Long userId = curUser.getId();
			Person user = null;
			if (userId == null) user = userService.findById(curUser.getId());
			else user = userService.findById(userId);
			List<Skill> curSkillsOb = user.getUserSkillList();
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
			
			List<Notification> notifications = notificationService.findByOwner(user);
			Map<Long, Boolean> isRead = new HashMap<Long, Boolean>();
			if (userId == curUser.getId()) {
				for (Notification notification : notifications) {
					if (notification.getStatus().equals(CommonUtils.NOTIFICATION_STATUS_READ)) {
						isRead.put(notification.getNotificationId(), true);
					} else {
						isRead.put(notification.getNotificationId(), false);
					}
					notification.setStatus(CommonUtils.NOTIFICATION_STATUS_READ);
					notification = notificationService.findOne(notification.getNotificationId());
				}
				notifications = notificationService.findByOwner(user);
			}
			model.addAttribute("isRead", isRead);
			if (notifications != null) {
				Collections.sort(notifications, new Comparator<Notification>() {
					public int compare(Notification noti0, Notification noti1) {
						if (noti0.getIssueTime().after(noti1.getIssueTime())) return -1;
						else if (noti0.getIssueTime().before(noti1.getIssueTime())) return 1;
						else return 0;
					}
				});
			}
			model.addAttribute("notifications", notifications);
			model.addAttribute("isSet", true);
        	
        	
            return "user/profile";
        }
		return "redirect:/user/profile?userId="+curUser.getId();
	}
	
	@PreAuthorize("@currentUserServiceImpl.hasLogIn(principal)")
	@RequestMapping(value = "/ajaxNotify")
	@ResponseBody
	public Integer ajaxNotify() {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return notificationService.findUnreadByOwner(curUser.getUser()).size();
	}
}
