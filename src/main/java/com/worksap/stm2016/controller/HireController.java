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
import com.worksap.stm2016.model.Hire;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.service.HireService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.utils.CommonUtils;

@Controller
@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'RECRUITER', 'TEAM-MANAGER')")
@RequestMapping(value = "/hire")
public class HireController {
	
	@Autowired
	HireService hireService;
	@Autowired
	PersonService personService;
	
	@RequestMapping(value={"/showHires"},  method = RequestMethod.GET)
	public String showHires(Model model) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Person cUser = personService.findById(curUser.getId());
		List<Hire> hires = null;
		if (curUser.getRole().getRoleId() == CommonUtils.ROLE_HR_MANAGER) {
			hires = hireService.findByHireHRManager(cUser);
			//hires = cUser.getHiresForHRM();//curUser.getUser().getHiresForHRM();
		} else if (curUser.getRole().getRoleId() == CommonUtils.ROLE_RECRUITER) {
			hires = hireService.findByHireRecruiter(cUser);
			//hires = cUser.getHiresForRecruiter();//curUser.getUser().getHiresForRecruiter();
		} else if (curUser.getUser().getDepartment() != null){
			hires = hireService.findByHireDepartment(cUser.getDepartment());
			//hires = cUser.getDepartment().getHireList();//curUser.getUser().getDepartment().getHireList();
		}
		if (hires != null) {
			Collections.sort(hires, new Comparator<Hire>(){

				@Override
				public int compare(Hire o1, Hire o2) {
					if (o1.getSubmitDate().before(o2.getSubmitDate())) return -1;
					else return 1;
				}
				
			});
		}
		model.addAttribute("hires", hires);
		return "hire/showHires";
	}
	
	@PreAuthorize("@currentUserServiceImpl.canAccessHire(principal, #hireId)")
	@RequestMapping(value={"/showOneHire"},  method = RequestMethod.GET)
	public String showOneHire(Long hireId, Model model) {
		Hire hire = hireService.findOne(hireId);
		model.addAttribute("hire", hire);
		return "hire/showOneHire";
	}
	
}
