package com.worksap.stm2016.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.worksap.stm2016.model.Applicant;
import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.RecruitingPlan;
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.service.ApplicantService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.RecruitingPlanService;
import com.worksap.stm2016.utils.CommonUtils;

@Controller
@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'RECRUITER', 'SHORT-TERM-EMPLOYEE')")
@RequestMapping(value = "/plan")
public class RecruitingPlanController {
	
	@Autowired
	RecruitingPlanService recruitingPlanService;
	@Autowired
	PersonService personService;
	@Autowired
	ApplicantService applicantService;
	
	@RequestMapping(value={"/showRecruitingPlans"},  method = RequestMethod.GET)
	public String showRecruitingPlans(Model model) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//Person cUser = personService.findById(curUser.getId());
		List<RecruitingPlan> plans = null;
		if (curUser.getRole().getRoleId() == CommonUtils.ROLE_HR_MANAGER) {
			plans = recruitingPlanService.findByPlanHRManager(curUser.getUser());
			//plans = cUser.getPlansForHRM();//curUser.getUser().getPlansForHRM();
		} else if (curUser.getRole().getRoleId() == CommonUtils.ROLE_RECRUITER) {
			plans = recruitingPlanService.findByPlanMaker(curUser.getUser());
			//plans = cUser.getPlansForRecruiter();//curUser.getUser().getPlansForRecruiter();
		} else {
			Map<Long, Boolean> isApply = new HashMap<Long, Boolean>();
			plans = recruitingPlanService.findByStatus(CommonUtils.PLAN_RECRUITING);
			boolean applied = false;
			for (RecruitingPlan plan : plans) {
				List<Applicant> applicants = applicantService.findByApplicantAndPlanForApplicant(curUser.getUser(), plan);
				if (applicants != null && applicants.size() > 0) {
					isApply.put(plan.getPlanId(), true);
					applied = true;
				} else {
					isApply.put(plan.getPlanId(), false);
				}
			}
			model.addAttribute("applied", applied);
			model.addAttribute("isApply", isApply);
		}
		
		model.addAttribute("plans", plans);
		return "plan/showPlans";
	}
	
	@PreAuthorize("@currentUserServiceImpl.canAccessRecruitingPlan(principal, #planId)")
	@RequestMapping(value={"/showOneRecruitingPlan"},  method = RequestMethod.GET)
	public String showOneRecruitingPlan(Long planId, Model model) {
		RecruitingPlan plan = recruitingPlanService.findOne(planId);
		List<Skill> skills = plan.getPlanSkillList();
		model.addAttribute("plan", plan);
		model.addAttribute("skills", skills);
		return "plan/showOnePlan";
	}
	
}
