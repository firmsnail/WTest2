package com.worksap.stm2016.controller;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.worksap.stm2016.model.Applicant;
import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.RecruitingPlan;
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.service.ApplicantService;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.RecruitingPlanService;
import com.worksap.stm2016.service.StaffRequirementService;
import com.worksap.stm2016.utils.CommonUtils;

@Controller
@PreAuthorize("hasAnyAuthority('RECRUITER', 'TEAM-MANAGER', 'SHORT-TERM-EMPLOYEE')")
@RequestMapping(value = "/applicant")
public class ApplicantController {
	
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
	
	private List<RecruitingPlan> getPlansFromDepartment(Department dept) {
		dept = departmentService.findOne(dept.getDepartmentId());
		Set<Long> planIds = new HashSet<Long>(); //REQUIREMENTS_RECRUITING
		List<StaffRequirement> requirements = staffRequirementService.findByStfrqDepartmentAndStatus(dept, CommonUtils.REQUIREMENTS_RECRUITING);
		System.out.println("requirements: ");
		for (StaffRequirement requirement : requirements ) {
			System.out.println("requirment: " + requirement.getStaffRequirementId());
			RecruitingPlan plan = requirement.getRecruitingPlan();
			planIds.add(plan.getPlanId());
		}
		List<RecruitingPlan> plans = new ArrayList<RecruitingPlan>();
		for (Long planId : planIds) {
			plans.add(recruitingPlanService.findOne(planId));
		}
		return plans;
	}
	
	@RequestMapping(value={"/showApplicants"},  method = RequestMethod.GET)
	public String showApplicants(Model model) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Applicant> applicants = null;
		
		if (curUser.getRole().getRoleId() == CommonUtils.ROLE_RECRUITER) {
			List<Integer> statuses = new ArrayList<Integer>();
			statuses.add(CommonUtils.APPLY_CHOOSED);
			statuses.add(CommonUtils.APPLY_FAILED);
			applicants = applicantService.findByPlanForApplicantPlanMakerAndStatusNotIn(curUser.getUser(), statuses);
			//hires = cUser.getHiresForRecruiter();//curUser.getUser().getHiresForRecruiter();
		} else if (curUser.getRole().getRoleId() == CommonUtils.ROLE_SHORT_TERM_EMPLOYEE) {
			applicants = applicantService.findByApplicant(curUser.getUser());
		} else if (curUser.getUser().getDepartment() != null){
			List<Integer> statuses = new ArrayList<Integer>();
			statuses.add(CommonUtils.APPLY_CHOOSED);
			statuses.add(CommonUtils.APPLY_FAILED);
			List<RecruitingPlan> plans = getPlansFromDepartment(curUser.getUser().getDepartment());
			System.out.println("plans: ");
			for (RecruitingPlan plan : plans) {
				System.out.println("plan: " + plan.getPlanId());
			}
			
			applicants = applicantService.findByPlanForApplicantInAndStatusNotIn(plans, statuses);
			
			//hires = cUser.getDepartment().getHireList();//curUser.getUser().getDepartment().getHireList();
		}
		model.addAttribute("applicants", applicants);
		return "applicant/showApplicants";
	}
	
	
	//@PreAuthorize("@currentUserServiceImpl.canAccessHire(principal, #hireId)")
	@RequestMapping(value={"/showOneApplicant"},  method = RequestMethod.GET)
	public String showOneApplicant(Long applicantId, Model model) {
		//Hire hire = hireService.findOne(hireId);
		//model.addAttribute("hire", hire);
		return "hire/showOneHire";
	}
	
}
