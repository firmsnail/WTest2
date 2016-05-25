package com.worksap.stm2016.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.worksap.stm2016.model.Applicant;
import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Hire;
import com.worksap.stm2016.model.Interview;
import com.worksap.stm2016.model.Notification;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.RecruitingPlan;
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.modelForm.PlanForm;
import com.worksap.stm2016.service.ApplicantService;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.HireService;
import com.worksap.stm2016.service.InterviewService;
import com.worksap.stm2016.service.NotificationService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.RecruitingPlanService;
import com.worksap.stm2016.service.RoleService;
import com.worksap.stm2016.service.SkillService;
import com.worksap.stm2016.service.StaffRequirementService;
import com.worksap.stm2016.utils.CommonUtils;
import com.worksap.stm2016.validator.PlanFormValidator;

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
	private HireService hireService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PersonService personService;
	@Autowired
	private NotificationService notificationService;

	
	@Autowired
	private PlanFormValidator planFormValidator;

	@RequestMapping(value = "/showAnalyzeRequirments",  method = RequestMethod.GET)
	public String showAnalyzeRequirments(String strStartDate, String strEndDate, Long departmentId, HttpServletRequest request, Model model) throws ParseException {
		
		List<Long> skills = new ArrayList<Long>();
		String [] arr = request.getParameterValues("skills");
		if (arr != null) {
			for (int i = 0; i < arr.length; ++i) {
				skills.add(Long.parseLong(arr[i]));
			}
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
	public String addPlan(@ModelAttribute("plan") @Valid PlanForm plan, BindingResult bindingResult, Model model) {
		System.out.println("@addPlan start!");
		//TODO Check Manager existed!
		planFormValidator.validate(plan, bindingResult);
		
		if (bindingResult.hasErrors()) {

			System.out.println("Adding plan occurs error!");
			
			List<Skill> skills = skillService.findAll();
			model.addAttribute("chooseSkills", skills);
			CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
			List<StaffRequirement> requirements = staffRequirementService.findByRecruiterAndStatus(curUser.getUser(), CommonUtils.REQUIREMENTS_PENDING_RECRUITE);
			model.addAttribute("chooseRequirements", requirements);
			
			return "recruiter/addPlan";
		}
		try {
			recruitingPlanService.add(plan);
			
			Notification notification = new Notification();
			Role hrmRole = roleService.findOne(CommonUtils.ROLE_HR_MANAGER);
			List<Person> pers = personService.findByRole(hrmRole);
			Person hrManager = pers.get(0);
			notification.setOwner(hrManager);
			notification.setContent("You have a recruiting plan to process!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_PLAN);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_MIDDLE);
			notification.setUrl("/plan/showRecruitingPlans");
			notification = notificationService.save(notification);
        } catch (DataIntegrityViolationException e) {
        	List<Skill> skills = skillService.findAll();
			model.addAttribute("chooseSkills", skills);
			CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();		
			List<StaffRequirement> requirements = staffRequirementService.findByRecruiterAndStatus(curUser.getUser(), CommonUtils.REQUIREMENTS_PENDING_RECRUITE);
			model.addAttribute("chooseRequirements", requirements);
            return "recruiter/addPlan";
        }
		return "redirect:/plan/showRecruitingPlans";
	}
	
	@PreAuthorize("@currentUserServiceImpl.canPostPlan(principal, #planId)")
	//@ResponseBody
	@RequestMapping(value = "/postOnePlan")
	public String postOnePlan(Long planId) {
		RecruitingPlan plan = recruitingPlanService.findOne(planId);
		//List<StaffRequirement> requirement = plan.getRequirements();
		plan.setStatus(CommonUtils.PLAN_RECRUITING);
		recruitingPlanService.findOne(planId);
		List<StaffRequirement> requirements = plan.getRequirements();
		for (StaffRequirement requirement : requirements) {
			requirement.setStatus(CommonUtils.REQUIREMENTS_RECRUITING);
			
			Notification notification = new Notification();
			notification.setOwner(requirement.getStfrqDepartment().getManager());
			notification.setContent("Your staffing requirement is being recruiting!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_REQUIREMENT);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_MIDDLE);
			notification.setUrl("/requirement/showStaffRequirements");
			notification = notificationService.save(notification);
			
			staffRequirementService.findOne(requirement.getStaffRequirementId());
		}
		return "redirect:/plan/showRecruitingPlans";
	}
	
	@RequestMapping(value = "/scheduleOneInterview",  method = RequestMethod.POST)
	public String scheduleOneInterview(Long interviewId, String interviewTime) throws ParseException {
		System.out.println("@scheduleOneInterview start!");
		System.out.println("interviewId: " + interviewId);
		System.out.println("interviewTime: " + interviewTime);
		if (interviewTime == null || interviewTime.length() <= 0) return "redirect:/interview/showInterviews";
		System.out.println("@scheduleOneInterview here");
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm");
		Date interviewT = df.parse(interviewTime);
		Interview interview = interviewService.findOne(interviewId);
		interview.setStatus(CommonUtils.INTERVIEW_INTERVIEWING);
		interview.setInterviewTime(interviewT);
		interview.setUpdateTime(new Date());
		interview = interviewService.findOne(interviewId);
		
		Notification notification = new Notification();
		notification.setOwner(interview.getInterviewer());
		notification.setContent("You have an interview!");
		notification.setIssueTime(new Date());
		notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
		notification.setType(CommonUtils.NOTIFICATION_TYPE_INTERVIEW);
		notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_HIGH);
		notification.setUrl("/interview/showInterviews");
		notification = notificationService.save(notification);
		
		Notification notification1 = new Notification();
		notification1.setOwner(interview.getInterviewee());
		notification1.setContent("You have an interview!");
		notification1.setIssueTime(new Date());
		notification1.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
		notification1.setType(CommonUtils.NOTIFICATION_TYPE_INTERVIEW);
		notification1.setUrgency(CommonUtils.NOTIFICATION_URGENCY_HIGH);
		notification1.setUrl("/interview/showInterviews");
		notification1 = notificationService.save(notification1);
		
		return "redirect:/interview/showInterviews";
	}
	
	@PreAuthorize("@currentUserServiceImpl.canDeletePlan(principal, #planId)")
	//@ResponseBody
	@RequestMapping(value = "/deleteOnePlan")
	public String deleteOnePlan(Long planId) {
		RecruitingPlan plan = recruitingPlanService.findOne(planId);
		List<StaffRequirement> requirements = plan.getRequirements();
		for (StaffRequirement requirement : requirements) {
			requirement.setStatus(CommonUtils.REQUIREMENTS_PENDING_RECRUITE);
			requirement.setRecruitingPlan(null);
			requirement = staffRequirementService.findOne(requirement.getStaffRequirementId());
		}
		recruitingPlanService.delete(planId);
		return "redirect:/plan/showRecruitingPlans";
	}
	
	@PreAuthorize("@currentUserServiceImpl.canOperateApplicant(principal, #applicantId)")
	//@ResponseBody
	@RequestMapping(value = "/passOneApplicant")
	public String passOneApplicant(Long applicantId) {
		Applicant applicant = applicantService.findOne(applicantId);
		applicant.setStatus(CommonUtils.APPLY_PASS_FILTER);
		applicant = applicantService.findOne(applicantId);
		return "redirect:/applicant/showApplicants";
	}
	@PreAuthorize("@currentUserServiceImpl.canOperateApplicant(principal, #applicantId)")
	//@ResponseBody
	@RequestMapping(value = "/failOneApplicant")
	public String failOneApplicant(Long applicantId) {
		Applicant applicant = applicantService.findOne(applicantId);
		applicant.setStatus(CommonUtils.APPLY_FAILED);
		applicant = applicantService.findOne(applicantId);
		return "redirect:/applicant/showApplicants";
	}
	
	@RequestMapping(value = "/aprroveOneHire")
	public String aprroveOneHire(Long hireId, Model model) {
		Hire hire = hireService.findOne(hireId);
		//CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (hire != null) {
			hire.setStatus(CommonUtils.HIRE_HR_MANAGER_PROCESSING);
			Role hrManagerRole = roleService.findOne(CommonUtils.ROLE_HR_MANAGER);
			Person hrManager = personService.findByRole(hrManagerRole).get(0);
			hire.setHireHRManager(hrManager);
			hire = hireService.findOne(hireId);
			
			Notification notification = new Notification();
			notification.setOwner(hrManager);
			notification.setContent("You have a hire need to verify!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_HIRE);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_HIGH);
			notification.setUrl("/hire/showHires");
			notification = notificationService.save(notification);
		}
		return "redirect:/hire/showHires";
	}
	
	@RequestMapping(value = "/rejectOneHire")
	public String rejectOneHire(Long hireId, Model model) {
		Hire hire = hireService.findOne(hireId);
		if (hire != null) {
			hire.setStatus(CommonUtils.HIRE_RECRUITER_REJECT);
			hire = hireService.findOne(hireId);
		}
		return "redirect:/hire/showHires";
	}
}
