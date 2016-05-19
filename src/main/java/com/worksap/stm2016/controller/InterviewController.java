package com.worksap.stm2016.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Interview;
import com.worksap.stm2016.service.ApplicantService;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.InterviewService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.RecruitingPlanService;
import com.worksap.stm2016.service.StaffRequirementService;
import com.worksap.stm2016.utils.CommonUtils;

@Controller
@PreAuthorize("hasAnyAuthority('RECRUITER', 'TEAM-MANAGER', 'SHORT-TERM-EMPLOYEE')")
@RequestMapping(value = "/interview")
public class InterviewController {
	
	@Autowired
	ApplicantService applicantService;
	@Autowired
	PersonService personService;
	@Autowired
	DepartmentService departmentService;
	@Autowired
	RecruitingPlanService recruitingPlanService;
	@Autowired
	StaffRequirementService staffRequirementService;
	@Autowired
	InterviewService interviewService;
	
	
	@RequestMapping(value={"/showInterviews"},  method = RequestMethod.GET)
	public String showInterviews(Model model) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Interview> interviews = null;
		
		if (curUser.getRole().getRoleId() == CommonUtils.ROLE_RECRUITER) {
			List<Integer> statuses = new ArrayList<Integer>();
			statuses.add(CommonUtils.INTERVIEW_INTERVIEWING);
			statuses.add(CommonUtils.INTERVIEW_PENDING_SCHEDULE);
			interviews = interviewService.findByRecruiterAndStatusIn(curUser.getUser(), statuses);
		} else if (curUser.getRole().getRoleId() == CommonUtils.ROLE_SHORT_TERM_EMPLOYEE) {
			interviews = interviewService.findByInterviewee(curUser.getUser());
		} else if (curUser.getUser().getDepartment() != null){
			List<Integer> statuses = new ArrayList<Integer>();
			statuses.add(CommonUtils.INTERVIEW_INTERVIEWING);
			statuses.add(CommonUtils.INTERVIEW_PENDING_SCHEDULE);
			interviews = interviewService.findByInterviewerAndStatusIn(curUser.getUser(), statuses);
		}
		model.addAttribute("interviews", interviews);
		return "interview/showInterviews";
	}
	
	
	//@PreAuthorize("@currentUserServiceImpl.canAccessHire(principal, #hireId)")
	@RequestMapping(value={"/showOneInterview"},  method = RequestMethod.GET)
	public String showOneInterview(Long interviewId, Model model) {
		Interview interview = interviewService.findOne(interviewId);
		model.addAttribute("interview", interview);
		return "interview/showOneInterview";
	}
	
}
