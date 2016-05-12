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

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Interview;
import com.worksap.stm2016.modelForm.DismissionForm;
import com.worksap.stm2016.service.DismissionService;
import com.worksap.stm2016.service.InterviewService;
import com.worksap.stm2016.validator.DismissionFormValidator;

@Controller
@PreAuthorize("hasAuthority('SHORT-TERM-EMPLOYEE')")
@RequestMapping(value = "/short-term-employee")
public class ShortTermEmployeeController {
	
	@Autowired
	private InterviewService interviewService;
	@Autowired
	private DismissionService dismissionService;
	
	@Autowired
	private DismissionFormValidator  dismissionFormValidator;
	
	//need add pre-authorize for check whether can add requirement
	@RequestMapping(value = "/addDismission",  method = RequestMethod.GET)
	public String addRequirement(Model model) {
		DismissionForm dismission = new DismissionForm();
		model.addAttribute("dismission", dismission);
		return "short-term-employee/addDismission";
	}

	@RequestMapping(value = "/addDismission",  method = RequestMethod.POST)
	public String addDismission(@ModelAttribute("dismission") @Valid DismissionForm dismission, BindingResult bindingResult) {

		System.out.println("disDate: " + dismission.getExpectDate());
		
		dismissionFormValidator.validate(dismission, bindingResult);
		
		if (bindingResult.hasErrors()) {
			System.out.println("Adding requirement occurs error!");
			return "short-term-employee/addDismission";
		}
		try {
			dismissionService.add(dismission);
        } catch (DataIntegrityViolationException e) {
            return "short-term-employee/addDismission";
        }
		return "redirect:/dismission/showDismissions";
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
	
	@PreAuthorize("@currentUserServiceImpl.canDeleteDismission(principal, #dismissionId)")
	//@ResponseBody
	@RequestMapping(value = "/deleteOneDismission")
	public String deleteOneDismission(Long dismissionId) {
		dismissionService.delete(dismissionId);
		return "redirect:/dismission/showDismissions";
	}
	
}
