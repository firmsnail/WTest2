package com.worksap.stm2016.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.worksap.stm2016.model.Dismission;
import com.worksap.stm2016.model.Leave;
import com.worksap.stm2016.service.DismissionService;
import com.worksap.stm2016.service.LeaveService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.utils.CommonUtils;

@Controller
@PreAuthorize("hasAuthority('RECRUITER')")
@RequestMapping(value = "/cb-specialist")
public class CBSpecialistController {
	
	@Autowired
	private DismissionService dismissionService;
	@Autowired
	private LeaveService leaveService;
	@Autowired
	private PersonService personService;

	
	@RequestMapping(value = "/processOneDismission")
	public String processOneDismission(Long dismissionId, Model model) {
		Dismission dismission = dismissionService.findOne(dismissionId);
		if (dismission != null) {
			dismission.setStatus(CommonUtils.DISMISSION_FINISH);
			dismission.getDismissionPerson().setStatus(CommonUtils.EMPLOYEE_DISMISSION);
			dismission = dismissionService.findOne(dismissionId);
			personService.findById(dismission.getDismissionPerson().getPersonId());
		}
		return "redirect:/dismission/showDismissions";
	}
	
	@RequestMapping(value = "/processOneLeave")
	public String processOneLeave(Long leaveId, Model model) {
		Leave leave = leaveService.findOne(leaveId);
		if (leave != null) {
			leave.setStatus(CommonUtils.LEAVE_FINISH);
			leave = leaveService.findOne(leaveId);
		}
		return "redirect:/leave/showLeaves";
	}
}