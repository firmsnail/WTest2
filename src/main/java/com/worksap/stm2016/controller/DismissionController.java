package com.worksap.stm2016.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Dismission;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.service.DismissionService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.utils.CommonUtils;

@Controller
@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'C&B-SPECIALIST', 'TEAM-MANAGER')")
@RequestMapping(value = "/dismission")
public class DismissionController {
	
	@Autowired
	DismissionService dismissionService;
	@Autowired
	PersonService personService;
	
	@RequestMapping(value={"/showDismissions"},  method = RequestMethod.GET)
	public String showDismissions(Model model) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Person cUser = personService.findById(curUser.getId());
		List<Dismission> dismissions = null;
		if (curUser.getRole().getRoleId() == CommonUtils.ROLE_HR_MANAGER) {
			dismissions = dismissionService.findByDismissionHRManager(curUser.getUser());
			//dismissions = cUser.getDismissionsForHRM();//curUser.getUser().getDismissionsForHRM();
		} else if (curUser.getRole().getRoleId() == CommonUtils.ROLE_CB_SPECIALIST) {
			dismissions = dismissionService.findByDismissionCBSpecialist(curUser.getUser());
			//dismissions = cUser.getDismissionsForCBSpecialist();//curUser.getUser().getDismissionsForCBSpecialist();
		} else if (curUser.getRole().getRoleId() == CommonUtils.ROLE_TEAM_MANAGER){
			if (curUser.getUser().getDepartment() != null) {
				dismissions = dismissionService.findByDismissionDepartment(curUser.getUser().getDepartment());
			}
			//dismissions = cUser.getDepartment().getDismissionList();//curUser.getUser().getDepartment().getDismissionList();
		} else {
			dismissions = dismissionService.findByDismissionPerson(curUser.getUser());
		}
		model.addAttribute("dismissions", dismissions);
		return "dismission/showDismissions";
	}
	
	@PreAuthorize("@currentUserServiceImpl.canAccessDismission(principal, #dismissionId)")
	@RequestMapping(value={"/showOneDismission"},  method = RequestMethod.GET)
	public String showOneDismission(Long dismissionId, Model model) {
		Dismission dismission = dismissionService.findOne(dismissionId);
		model.addAttribute("dismission", dismission);
		return "dismission/showOneDismission";
	}
	
}
