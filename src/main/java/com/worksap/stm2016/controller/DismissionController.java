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
import com.worksap.stm2016.service.DismissionService;
import com.worksap.stm2016.utils.CommonUtils;

@Controller
@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'C&B-SPECIALIST', 'TEAM-MANAGER')")
@RequestMapping(value = "/dismission")
public class DismissionController {
	
	@Autowired
	DismissionService dismissionService;
	
	@RequestMapping(value={"/showDismissions"},  method = RequestMethod.GET)
	public String showDismissions(Model model) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Dismission> dismissions = null;
		if (curUser.getRole().getRoleId() == CommonUtils.ROLE_HR_MANAGER) {
			dismissions = curUser.getUser().getDismissionsForHRM();
		} else if (curUser.getRole().getRoleId() == CommonUtils.ROLE_CB_SPECIALIST) {
			dismissions = curUser.getUser().getDismissionsForCBSpecialist();
		} else if (curUser.getUser().getDepartment() != null){
			dismissions = curUser.getUser().getDepartment().getDismissionList();
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
