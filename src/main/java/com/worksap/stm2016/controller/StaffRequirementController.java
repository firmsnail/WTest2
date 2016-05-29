package com.worksap.stm2016.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.StaffRequirementService;
import com.worksap.stm2016.utils.CommonUtils;

@Controller
@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'RECRUITER', 'TEAM-MANAGER')")
@RequestMapping(value = "/requirement")
public class StaffRequirementController {
	
	@Autowired
	StaffRequirementService staffRequirementService;
	@Autowired
	PersonService personService;
	
	@RequestMapping(value={"/showStaffRequirements"},  method = RequestMethod.GET)
	public String showStaffRequirements(Model model) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Person cUser = personService.findById(curUser.getId());
		List<StaffRequirement> requirements = staffRequirementService.findAll();
		if (cUser.getRole().getRoleId() == CommonUtils.ROLE_HR_MANAGER) {
			//requirements = cUser.getRequirementsForHRM();
			requirements = staffRequirementService.findByHRManager(cUser);
		} else if (cUser.getRole().getRoleId() == CommonUtils.ROLE_RECRUITER) {
			requirements = cUser.getRequirementsForRecruiter();
		} else if (cUser.getDepartment() != null){
			requirements = staffRequirementService.findByDepartment(cUser.getDepartment());
		}
		if (requirements != null) {
			Collections.sort(requirements, new Comparator<StaffRequirement>() {

				@Override
				public int compare(StaffRequirement o1, StaffRequirement o2) {
					if (o1.getExpectDate().before(o2.getExpectDate())) return -1;
					else if (o1.getExpectDate().after(o2.getExpectDate())) return 1;
					else if (o1.getSubmitDate().before(o2.getSubmitDate())) return -1;
					else return 1;
				}
				
			});
		}
		model.addAttribute("requirements", requirements);
		return "requirement/showStaffRequirements";
	}
	
	@PreAuthorize("@currentUserServiceImpl.canAccessStaffRequirement(principal, #requirementId)")
	@RequestMapping(value={"/showOneStaffRequirement"},  method = RequestMethod.GET)
	public String showOneStaffRequirement(Long requirementId, Model model) {
		StaffRequirement requirement = staffRequirementService.findOne(requirementId);
		List<Skill> skills = requirement.getStfrqSkillList();
		model.addAttribute("requirement", requirement);
		model.addAttribute("skills", skills);
		return "requirement/showOneRequirement";
	}
	
}
