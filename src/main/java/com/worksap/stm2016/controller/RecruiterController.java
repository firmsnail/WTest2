package com.worksap.stm2016.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
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
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Interview;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.RecruitingPlan;
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.modelForm.PlanForm;
import com.worksap.stm2016.modelForm.RequirementForm;
import com.worksap.stm2016.service.ApplicantService;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.InterviewService;
import com.worksap.stm2016.service.RecruitingPlanService;
import com.worksap.stm2016.service.SkillService;
import com.worksap.stm2016.service.StaffRequirementService;
import com.worksap.stm2016.utils.CommonUtils;
import com.worksap.stm2016.validator.PlanFormValidator;
import com.worksap.stm2016.validator.RequirementFormValidator;

@Controller
@PreAuthorize("hasAuthority('RECRUITER')")
@RequestMapping(value = "/recruiter")
public class RecruiterController {
	
	@Autowired
	private ApplicantService applicantService;
	@Autowired
	private InterviewService interviewService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private SkillService skillService;
	@Autowired
	private StaffRequirementService staffRequirementService;
	@Autowired
	private RecruitingPlanService recruitingPlanService;
	
	@Autowired
	private PlanFormValidator planFormValidator;

	@RequestMapping(value = "/showAnalyzeRequirments",  method = RequestMethod.GET)
	public String showAnalyzeRequirments(String strStartDate, String strEndDate, Long departmentId, HttpServletRequest request, Model model) throws ParseException {
		
		List<Long> skills = new ArrayList<Long>();
		String [] arr = request.getParameterValues("skills");
		for (int i = 0; i < arr.length; ++i) {
			skills.add(Long.parseLong(arr[i]));
		}
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null, endDate = null;
		if (strStartDate != null && !strStartDate.isEmpty()) {
			startDate = df.parse(strStartDate);
		}
		if (strEndDate != null && !strEndDate.isEmpty()) {
			endDate = df.parse(strEndDate);
		}
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Department department = null;
		if (departmentId != null) {
			department = departmentService.findOne(departmentId);
		}
		List<StaffRequirement> staffingRequirements = null;
		List<StaffRequirement> tempRequirements = null;
		if (department != null) {
			if (startDate != null && endDate != null) {
				tempRequirements = staffRequirementService.findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateBetween(curUser.getUser(), department, CommonUtils.REQUIREMENTS_PENDING_RECRUITE, startDate, endDate);
			} else if (startDate != null) {
				tempRequirements = staffRequirementService.findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateNotBefore(curUser.getUser(), department, CommonUtils.REQUIREMENTS_PENDING_RECRUITE, startDate);
			} else if (endDate != null) {
				tempRequirements = staffRequirementService.findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateNotAfter(curUser.getUser(), department, CommonUtils.REQUIREMENTS_PENDING_RECRUITE, endDate);
			} else {
				tempRequirements = staffRequirementService.findByRecruiterAndStfrqDepartmentAndStatus(curUser.getUser(), department, CommonUtils.REQUIREMENTS_PENDING_RECRUITE);
			}
		} else {
			if (startDate != null && endDate != null) {
				tempRequirements = staffRequirementService.findByRecruiterAndStatusAndExpectDateBetween(curUser.getUser(), CommonUtils.REQUIREMENTS_PENDING_RECRUITE, startDate, endDate);
			} else if (startDate != null) {
				tempRequirements = staffRequirementService.findByRecruiterAndStatusAndExpectDateNotBefore(curUser.getUser(), CommonUtils.REQUIREMENTS_PENDING_RECRUITE, startDate);
			} else if (endDate != null) {
				tempRequirements = staffRequirementService.findByRecruiterAndStatusAndExpectDateNotAfter(curUser.getUser(), CommonUtils.REQUIREMENTS_PENDING_RECRUITE, endDate);
			} else {
				tempRequirements = staffRequirementService.findByRecruiterAndStatus(curUser.getUser(), CommonUtils.REQUIREMENTS_PENDING_RECRUITE);
			}
		}
		
		if (skills != null && skills.size() > 0) {
			staffingRequirements = new ArrayList<StaffRequirement>();
			for (StaffRequirement requirement : tempRequirements) {
				if (CommonUtils.RequirementContainSkills(requirement, skills, skillService)) {
					staffingRequirements.add(requirement);
				}
			}
		} else {
			staffingRequirements = tempRequirements;
		}
		
		List<Department> involveDepartments = CommonUtils.extractDepartmentsByRequirements(staffingRequirements, departmentService);
		List<Skill> involveSkills = CommonUtils.extractSkillsByRequirements(staffingRequirements, skillService);
		
		Map<Long, List<StaffRequirement>> mapRequirementByDepartment = new HashMap<Long, List<StaffRequirement>>();
		Map<Long, List<StaffRequirement>> mapRequirementBySkill = new HashMap<Long, List<StaffRequirement>>();
		
		Map<Long, Integer> mapTotalStaffByDepartment = new HashMap<Long, Integer>();
		Map<Long, Integer> mapTotalStaffBySkill = new HashMap<Long, Integer>();
		
		for (StaffRequirement requirement : staffingRequirements) {
			if (requirement.getStfrqDepartment() != null) {
				if (!mapRequirementByDepartment.containsKey(requirement.getStfrqDepartment().getDepartmentId())) {
					mapRequirementByDepartment.put(requirement.getStfrqDepartment().getDepartmentId(), new ArrayList<StaffRequirement>());
					mapTotalStaffByDepartment.put(requirement.getStfrqDepartment().getDepartmentId(), 0);
				}
				mapRequirementByDepartment.get(requirement.getStfrqDepartment().getDepartmentId()).add(requirement);
				mapTotalStaffByDepartment.put(requirement.getStfrqDepartment().getDepartmentId(), mapTotalStaffByDepartment.get(requirement.getStfrqDepartment().getDepartmentId())+requirement.getRequireNum());
			}
			if (requirement.getStfrqSkillList() != null) {
				for (Skill skill : requirement.getStfrqSkillList()) {
					if (!mapRequirementBySkill.containsKey(skill.getSkillId())) {
						mapRequirementBySkill.put(skill.getSkillId(), new ArrayList<StaffRequirement>());
						mapTotalStaffBySkill.put(skill.getSkillId(), 0);
					}
					mapRequirementBySkill.get(skill.getSkillId()).add(requirement);
					mapTotalStaffBySkill.put(skill.getSkillId(), mapTotalStaffBySkill.get(skill.getSkillId())+requirement.getRequireNum());
				}
			}
		}
		List<Department> allDepts = departmentService.findAll();
		model.addAttribute("allDepts", allDepts);
		List<Skill> allSkills = skillService.findAll();
		model.addAttribute("allSkills", allSkills);
		if (startDate != null) {
			df=new SimpleDateFormat("yyyy-MM-dd");
			String curStartDate = df.format(startDate);
			model.addAttribute("curStartDate", curStartDate);
		}
		if (endDate != null) {
			df=new SimpleDateFormat("yyyy-MM-dd");
			String curEndDate = df.format(endDate);
			model.addAttribute("curEndDate", curEndDate);
		}
		if (department != null) {
			model.addAttribute("curDept", department);
		}
		if (skills != null) {
			model.addAttribute("curSkills", skills);
		}
		
		model.addAttribute("involveDepartments", involveDepartments);
		model.addAttribute("involveSkills", involveSkills);
		model.addAttribute("mapRequirementByDepartment", mapRequirementByDepartment);
		model.addAttribute("mapRequirementBySkill", mapRequirementBySkill);
		model.addAttribute("mapTotalStaffByDepartment", mapTotalStaffByDepartment);
		model.addAttribute("mapTotalStaffBySkill", mapTotalStaffBySkill);
		model.addAttribute("requirements", staffingRequirements);
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
	
	@RequestMapping(value = "/processOneRequirement")
	public String processOneRequirement(Long requirementId, Model model) {
		StaffRequirement requirement = staffRequirementService.findOne(requirementId);
		if (requirement != null) {
			requirement.setStatus(CommonUtils.REQUIREMENTS_PENDING_RECRUITE);
			requirement = staffRequirementService.findOne(requirementId);
		}
		return "redirect:/requirement/showStaffRequirements";
	}
	
	@RequestMapping(value = "/addPlan",  method = RequestMethod.GET)
	public String addPlan(Model model) {
		List<Skill> skills = skillService.findAll();
		model.addAttribute("chooseSkills", skills);
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
		List<StaffRequirement> requirements = staffRequirementService.findByRecruiterAndStatus(curUser.getUser(), CommonUtils.REQUIREMENTS_PENDING_RECRUITE);
		model.addAttribute("chooseRequirements", requirements);
		PlanForm plan = new PlanForm();
		model.addAttribute("plan", plan);
		return "recruiter/addPlan";
	}
	@RequestMapping(value = "/addPlan",  method = RequestMethod.POST)
	public String addPlan(@ModelAttribute("plan") @Valid PlanForm plan, BindingResult bindingResult) {
		System.out.println("@addPlan start!");
		//TODO Check Manager existed!
		planFormValidator.validate(plan, bindingResult);
		
		if (bindingResult.hasErrors()) {
			System.out.println("expectDate: " + plan.getExpectDate());
			System.out.println("invalidDate: " + plan.getInvalidDate());
			System.out.println("Adding plan occurs error!");
			return "recruiter/addPlan";
		}
		try {
			recruitingPlanService.add(plan);
        } catch (DataIntegrityViolationException e) {
            return "recruiter/addPlan";
        }
		return "redirect:/plan/showRecruitingPlans";
	}
	
	@PreAuthorize("@currentUserServiceImpl.canDeletePlan(principal, #planId)")
	//@ResponseBody
	@RequestMapping(value = "/deleteOnePlan")
	public String deleteOnePlan(Long planId) {
		//RecruitingPlan plan = recruitingPlanService.findOne(planId);
		//List<StaffRequirement> requirement = plan.getRequirements();
		recruitingPlanService.delete(planId);
		return "redirect:/plan/showRecruitingPlans";
	}
}
