package com.worksap.stm2016.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.worksap.stm2016.model.Interview;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.service.InterviewService;
import com.worksap.stm2016.service.PersonService;

@Controller
@PreAuthorize("hasAuthority('RECRUITER')")
@RequestMapping(value = "/recruiter")
public class RecruiterController {
	
	@Autowired
	private PersonService personService;
	@Autowired
	private InterviewService interviewService;

	
	@RequestMapping(value = "/showApplicants",  method = RequestMethod.GET)
	public String showApplicants(Model model) {
		List<Person> applicants = new ArrayList<Person>();
		
		model.addAttribute("applicants", applicants);
		return "recruiter/showApplicants";
	}
	
	@RequestMapping(value = "/showInterviews",  method = RequestMethod.GET)
	public String showInterviews(Model model) {
		List<Interview> interviews = new ArrayList<Interview>();
		
		model.addAttribute("interviews", interviews);
		return "recruiter/showInterviews";
	}
}
