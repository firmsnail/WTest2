package com.worksap.stm2016.controller;

import java.util.List;

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
import com.worksap.stm2016.model.Dismission;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.modelForm.RequirementForm;
import com.worksap.stm2016.service.DismissionService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.RoleService;
import com.worksap.stm2016.service.SkillService;
import com.worksap.stm2016.service.StaffRequirementService;
import com.worksap.stm2016.utils.CommonUtils;
import com.worksap.stm2016.validator.RequirementFormValidator;

@Controller
@PreAuthorize("hasAuthority('TEAM-MANAGER')")
@RequestMapping(value = "/team-manager")
public class TeamManagerController {
	
	@Autowired
	private StaffRequirementService staffRequirementService;
	@Autowired
	private SkillService skillService;
	@Autowired
	private DismissionService dismissionService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PersonService personService;
	
	@Autowired
	private RequirementFormValidator  requirementFormValidator;
	
	//need add pre-authorize for check whether can add requirement
	@RequestMapping(value = "/addRequirement",  method = RequestMethod.GET)
	public String addRequirement(Model model) {
		List<Skill> skills = skillService.findAll();
		model.addAttribute("chooseSkills", skills);
		RequirementForm requirement = new RequirementForm();
		model.addAttribute("requirement", requirement);
		return "team-manager/addRequirement";
	}
	@RequestMapping(value = "/addRequirement",  method = RequestMethod.POST)
	public String addDepartment(@ModelAttribute("requirement") @Valid RequirementForm requirement, BindingResult bindingResult) {
		System.out.println("@addDepartment start!");
		//TODO Check Manager existed!
		requirementFormValidator.validate(requirement, bindingResult);
		
		if (bindingResult.hasErrors()) {
			System.out.println("Adding requirement occurs error!");
			return "team-manager/addRequirement";
		}
		try {
			System.out.println("requirement: " + requirement);
			staffRequirementService.add(requirement);
        } catch (DataIntegrityViolationException e) {
            return "team-manager/addRequirement";
        }
		return "redirect:/requirement/showStaffRequirements";
	}
	
	@PreAuthorize("@currentUserServiceImpl.canDeleteStaffRequirement(principal, #requirementId)")
	@ResponseBody
	@RequestMapping(value = "/delRequirement",  method = RequestMethod.POST)
	public String delDepartment(Long requirementId) {
		staffRequirementService.delete(requirementId);
		return "success";
	}
	
	@RequestMapping(value = "/aprroveOneDismission")
	public String aprroveOneDismission(Long dismissionId, Model model) {
		Dismission dismission = dismissionService.findOne(dismissionId);
		if (dismission != null) {
			//dismission.setDismissionHRManager(curUser.getUser());
			System.out.println("here!");
			dismission.setStatus(CommonUtils.DISMISSION_HR_MANAGER_PROCESSING);
			Role hrRole = roleService.findOne(CommonUtils.ROLE_HR_MANAGER);
			Person hrManager = personService.findByRole(hrRole).get(0);
			dismission.setDismissionHRManager(hrManager);
			dismission = dismissionService.findOne(dismissionId);
		}
		return "redirect:/dismission/showDismissions";
	}
	
	@RequestMapping(value = "/rejectOneDismission")
	public String rejectOneDismission(Long dismissionId, Model model) {
		Dismission dismission = dismissionService.findOne(dismissionId);
		if (dismission != null) {
			dismission.setStatus(CommonUtils.DISMISSION_TEAM_MANAGER_REJECT);
			dismission = dismissionService.findOne(dismissionId);
		}
		return "redirect:/dismission/showDismissions";
	}
}
