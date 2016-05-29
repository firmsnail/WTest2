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
import com.worksap.stm2016.model.Leave;
import com.worksap.stm2016.service.LeaveService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.utils.CommonUtils;

@Controller
@PreAuthorize("hasAnyAuthority('C&B-SPECIALIST', 'TEAM-MANAGER', 'SHORT-TERM-EMPLOYEE')")
@RequestMapping(value = "/leave")
public class LeaveController {
	
	@Autowired
	LeaveService leaveService;
	@Autowired
	PersonService personService;
	
	@RequestMapping(value={"/showLeaves"},  method = RequestMethod.GET)
	public String showLeaves(Model model) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//Person cUser = personService.findById(curUser.getId());
		List<Leave> leaves = null;
		if (curUser.getRole().getRoleId() == CommonUtils.ROLE_CB_SPECIALIST) {
			leaves = leaveService.findAll();
		} else if (curUser.getRole().getRoleId() == CommonUtils.ROLE_SHORT_TERM_EMPLOYEE) {
			leaves = leaveService.findByLeavePerson(curUser.getUser());
		} else if (curUser.getUser().getDepartment() != null){
			leaves = leaveService.findByLeaveDepartment(curUser.getUser().getDepartment());
		}
		if (leaves != null) {
			Collections.sort(leaves, new Comparator<Leave>(){

				@Override
				public int compare(Leave o1, Leave o2) {
					if (o1.getStartDate().before(o2.getStartDate())) return -1;
					return 1;
				}
				
			});
		}
		model.addAttribute("leaves", leaves);
		return "leave/showLeaves";
	}
	
	@PreAuthorize("hasAnyAuthority('C&B-SPECIALIST') or @currentUserServiceImpl.canAccessLeave(principal, #leaveId)")
	@RequestMapping(value={"/showOneLeave"},  method = RequestMethod.GET)
	public String showOneLeave(Long leaveId, Model model) {
		Leave leave = leaveService.findOne(leaveId);
		model.addAttribute("leave", leave);
		return "leave/showOneLeave";
	}
	
}
