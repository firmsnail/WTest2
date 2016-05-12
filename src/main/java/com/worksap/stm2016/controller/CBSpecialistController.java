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
import com.worksap.stm2016.model.Dismission;
import com.worksap.stm2016.model.Interview;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.service.ApplicantService;
import com.worksap.stm2016.service.DismissionService;
import com.worksap.stm2016.service.InterviewService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.StaffRequirementService;
import com.worksap.stm2016.utils.CommonUtils;

@Controller
@PreAuthorize("hasAuthority('RECRUITER')")
@RequestMapping(value = "/cb-specialist")
public class CBSpecialistController {
	
	@Autowired
	private DismissionService dismissionService;
	@Autowired
	private PersonService personService;

	
	@RequestMapping(value = "/processOneDismission")
	public String processOneDismission(Long dismissionId, Model model) {
		Dismission dismission = dismissionService.findOne(dismissionId);
		if (dismission != null) {
			dismission.setStatus(CommonUtils.DISMISSION_FINISH);
			dismission.getDismissionPerson().setStatus(CommonUtils.EMPLOYEE_DISMISSION);
			dismission = dismissionService.findOne(dismissionId);
			Person cb = personService.findById(dismission.getDismissionPerson().getPersonId());
		}
		return "redirect:/dismission/showDismissions";
	}
}
