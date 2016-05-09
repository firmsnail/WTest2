package com.worksap.stm2016.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.service.ApplicantService;
import com.worksap.stm2016.service.InterviewService;
import com.worksap.stm2016.service.PersonService;

@Controller
@PreAuthorize("hasAuthority('RECRUITER')")
@RequestMapping(value = "/recruiter")
public class RecruiterController {
	
	@Autowired
	private ApplicantService applicantService;
	@Autowired
	private InterviewService interviewService;

	@RequestMapping(value = "/showAnalyzeRequirments",  method = RequestMethod.GET)
	public String showAnalyzeRequirments(Date startDate, Date endDate, Department department, List<Skill> skills, Model model) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		/*
		 * Total req, by dept, by skill
		 * 
		 * 
		 */
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
}
