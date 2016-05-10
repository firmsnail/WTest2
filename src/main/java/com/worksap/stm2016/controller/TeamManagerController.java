package com.worksap.stm2016.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Interview;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.modelForm.DepartmentForm;
import com.worksap.stm2016.modelForm.RequirementForm;
import com.worksap.stm2016.service.ApplicantService;
import com.worksap.stm2016.service.InterviewService;
import com.worksap.stm2016.service.SkillService;
import com.worksap.stm2016.service.StaffRequirementService;
import com.worksap.stm2016.utils.CommonUtils;
import com.worksap.stm2016.validator.DepartmentFormValidator;
import com.worksap.stm2016.validator.RequirementFormValidator;

@Controller
@PreAuthorize("hasAuthority('TEAM-MANAGER')")
@RequestMapping(value = "/team-manager")
public class TeamManagerController {
	
	@Autowired
	private ApplicantService applicantService;
	@Autowired
	private InterviewService interviewService;
	@Autowired
	private StaffRequirementService staffRequirementService;
	@Autowired
	private SkillService skillService;
	
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
		requirementFormValidator.validate(requirement, bindingResult);
		
		if (bindingResult.hasErrors()) {
			System.out.println("Adding requirement occurs error!");
			return "team-manager/addRequirement";
		}
		try {
			staffRequirementService.add(requirement);
        } catch (DataIntegrityViolationException e) {
            return "team-manager/addRequirement";
        }
		return "redirect:/requirement/showStaffRequirements";
	}
}
