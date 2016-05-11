package com.worksap.stm2016.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Interview;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.service.ApplicantService;
import com.worksap.stm2016.service.InterviewService;
import com.worksap.stm2016.service.StaffRequirementService;
import com.worksap.stm2016.utils.CommonUtils;

@Controller
@PreAuthorize("hasAuthority('RECRUITER')")
@RequestMapping(value = "/recruiter")
public class RecruiterController {
	
	@Autowired
	private ApplicantService applicantService;
	@Autowired
	private InterviewService interviewService;
	@Autowired
	private StaffRequirementService staffRequirementService;

	@RequestMapping(value = "/showAnalyzeRequirments",  method = RequestMethod.GET)
	public String showAnalyzeRequirments(Date startDate, Date endDate, Department department, List<Skill> skills, Model model) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<StaffRequirement> staffingRequirements = null;
		List<StaffRequirement> tempRequirements = null;
		if (department != null) {
			if (startDate != null && endDate != null) {
				tempRequirements = staffRequirementService.findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateBetween(curUser.getUser(), department, CommonUtils.REQUIREMENTS_PENDING_RECRUITE, startDate, endDate);
			} else if (startDate != null) {
				tempRequirements = staffRequirementService.findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateNotBefore(curUser.getUser(), department, CommonUtils.REQUIREMENTS_PENDING_RECRUITE, startDate);
			} else if (endDate != null) {
				tempRequirements = staffRequirementService.findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateNotAfter(curUser.getUser(), department, CommonUtils.REQUIREMENTS_PENDING_RECRUITE, endDate);
			} else {
				tempRequirements = staffRequirementService.findByRecruiterAndStfrqDepartmentAndStatus(curUser.getUser(), department, CommonUtils.REQUIREMENTS_PENDING_RECRUITE);
			}
		} else {
			if (startDate != null && endDate != null) {
				tempRequirements = staffRequirementService.findByRecruiterAndStatusAndExpectDateBetween(curUser.getUser(), CommonUtils.REQUIREMENTS_PENDING_RECRUITE, startDate, endDate);
			} else if (startDate != null) {
				tempRequirements = staffRequirementService.findByRecruiterAndStatusAndExpectDateNotBefore(curUser.getUser(), CommonUtils.REQUIREMENTS_PENDING_RECRUITE, startDate);
			} else if (endDate != null) {
				tempRequirements = staffRequirementService.findByRecruiterAndStatusAndExpectDateNotAfter(curUser.getUser(), CommonUtils.REQUIREMENTS_PENDING_RECRUITE, endDate);
			} else {
				tempRequirements = staffRequirementService.findByRecruiterAndStatus(curUser.getUser(), CommonUtils.REQUIREMENTS_PENDING_RECRUITE);
			}
		}
		
		if (skills != null && skills.size() > 0) {
			staffingRequirements = new ArrayList<StaffRequirement>();
			for (StaffRequirement requirement : tempRequirements) {
				if (CommonUtils.RequirementContainSkills(requirement, skills)) {
					staffingRequirements.add(requirement);
				}
			}
		} else {
			staffingRequirements = tempRequirements;
		}
		
		model.addAttribute("requirements", staffingRequirements);
		return "recruiter/showAnalyzeRequirments";
	}
	
	@RequestMapping(value = "/showApplicants",  method = RequestMethod.GET)
	public String showApplicants(Model model) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (curUser != null) {
			List<Person> applicants = applicantService.findByRecruiter(curUser.getUser());
			model.addAttribute("applicants", applicants);
		}
		return "recruiter/showApplicants";
	}
	
	@RequestMapping(value = "/showInterviews",  method = RequestMethod.GET)
	public String showInterviews(Model model) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (curUser != null) {
			List<Interview> interviews = interviewService.findByRecruiter(curUser.getUser());
			model.addAttribute("interviews", interviews);
		}
		return "recruiter/showInterviews";
	}
	
	@RequestMapping(value = "/processOneRequirement")
	public String processOneRequirement(Long requirementId, Model model) {
		StaffRequirement requirement = staffRequirementService.findOne(requirementId);
		if (requirement != null) {
			requirement.setStatus(CommonUtils.REQUIREMENTS_PENDING_RECRUITE);
			requirement = staffRequirementService.findOne(requirementId);
		}
		return "redirect:/requirement/showStaffRequirements";
	}
}
