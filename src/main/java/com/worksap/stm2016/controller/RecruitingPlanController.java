package com.worksap.stm2016.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.RecruitingPlan;
import com.worksap.stm2016.service.RecruitingPlanService;
import com.worksap.stm2016.utils.CommonUtils;

@Controller
@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'RECRUITER', 'SHORT-TERM-EMPLOYEE')")
@RequestMapping(value = "/plan")
public class RecruitingPlanController {
	
	@Autowired
	RecruitingPlanService recruitingPlanService;
	
	@RequestMapping(value={"/showRecruitingPlans"},  method = RequestMethod.GET)
	public String showRecruitingPlans(Model model) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<RecruitingPlan> plans = null;
		if (curUser.getRole().getRoleId() == CommonUtils.ROLE_HR_MANAGER) {
			plans = curUser.getUser().getPlansForHRM();
		} else if (curUser.getRole().getRoleId() == CommonUtils.ROLE_RECRUITER) {
			plans = curUser.getUser().getPlansForRecruiter();
		} else {
			plans = recruitingPlanService.findByStatus(CommonUtils.PLAN_RECRUITING);
		}
		model.addAttribute("plans", plans);
		return "plan/showRecruitingPlans";
	}
	
	@PreAuthorize("@currentUserServiceImpl.canAccessRecruitingPlan(principal, #planId)")
	@RequestMapping(value={"/showOneRecruitingPlan"},  method = RequestMethod.GET)
	public String showOneRecruitingPlan(Long planId, Model model) {
		RecruitingPlan plan = recruitingPlanService.findOne(planId);
		model.addAttribute("plan", plan);
		return "plan/showOneRecruitingPlan";
	}
	
}
