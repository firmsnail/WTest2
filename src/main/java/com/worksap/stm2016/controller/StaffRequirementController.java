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
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.service.StaffRequirementService;
import com.worksap.stm2016.utils.CommonUtils;

@Controller
@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'RECRUITER', 'TEAM-MANAGER')")
@RequestMapping(value = "/requirement")
public class StaffRequirementController {
	
	@Autowired
	StaffRequirementService staffRequirementService;
	
	@RequestMapping(value={"/showStaffRequirements"},  method = RequestMethod.GET)
	public String showStaffRequirements(Model model) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<StaffRequirement> requirements = null;
		if (curUser.getRole().getRoleId() == CommonUtils.ROLE_HR_MANAGER) {
			requirements = curUser.getUser().getRequirementsForHRM();
		} else if (curUser.getRole().getRoleId() == CommonUtils.ROLE_RECRUITER) {
			requirements = curUser.getUser().getRequirementsForRecruiter();
		} else if (curUser.getUser().getDepartment() != null){
			requirements = curUser.getUser().getDepartment().getStaffRequirementList();
		}
		model.addAttribute("requirements", requirements);
		return "requirement/showStaffRequirements";
	}
	
	@PreAuthorize("@currentUserServiceImpl.canAccessStaffRequirement(principal, #requirementId)")
	@RequestMapping(value={"/showOneStaffRequirement"},  method = RequestMethod.GET)
	public String showOneStaffRequirement(Long requirementId, Model model) {
		StaffRequirement requirement = staffRequirementService.findOne(requirementId);
		model.addAttribute("requirement", requirement);
		return "requirement/showOneStaffRequirement";
	}
	
}
