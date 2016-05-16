package com.worksap.stm2016.controller;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;
import com.worksap.stm2016.model.Applicant;
import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Dismission;
import com.worksap.stm2016.model.Interview;
import com.worksap.stm2016.model.Leave;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.RecruitingPlan;
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.modelForm.RequirementForm;
import com.worksap.stm2016.service.ApplicantService;
import com.worksap.stm2016.service.DismissionService;
import com.worksap.stm2016.service.InterviewService;
import com.worksap.stm2016.service.LeaveService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.RoleService;
import com.worksap.stm2016.service.SkillService;
import com.worksap.stm2016.service.StaffRequirementService;
import com.worksap.stm2016.utils.CommonUtils;
import com.worksap.stm2016.validator.RequirementFormValidator;

@Controller
@PreAuthorize("hasAuthority('TEAM-MANAGER')")
@RequestMapping(value = "/team-manager")
public class TeamManagerController {
	
	@Autowired
	private StaffRequirementService staffRequirementService;
	@Autowired
	private SkillService skillService;
	@Autowired
	private DismissionService dismissionService;
	@Autowired
	private LeaveService leaveService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PersonService personService;
	@Autowired
	private ApplicantService applicantService;
	@Autowired
	private InterviewService interviewService;
	
	@Autowired
	private RequirementFormValidator requirementFormValidator;
	
	//TODO need add pre-authorize for check whether can add requirement
	@RequestMapping(value = "/addRequirement",  method = RequestMethod.GET)
	public String addRequirement(Model model) {
		List<Skill> skills = skillService.findAll();
		model.addAttribute("chooseSkills", skills);
		RequirementForm requirement = new RequirementForm();
		model.addAttribute("requirement", requirement);
		return "team-manager/addRequirement";
	}
	@RequestMapping(value = "/addRequirement",  method = RequestMethod.POST)
	public String addRequirement(@ModelAttribute("requirement") @Valid RequirementForm requirement, BindingResult bindingResult) {
		System.out.println("@addDepartment start!");
		//TODO Check Manager existed!
		requirementFormValidator.validate(requirement, bindingResult);
		
		if (bindingResult.hasErrors()) {
			System.out.println("Adding requirement occurs error!");
			return "team-manager/addRequirement";
		}
		try {
			System.out.println("requirement: " + requirement);
			staffRequirementService.add(requirement);
        } catch (DataIntegrityViolationException e) {
            return "team-manager/addRequirement";
        }
		return "redirect:/requirement/showStaffRequirements";
	}
	
	@PreAuthorize("@currentUserServiceImpl.canDeleteStaffRequirement(principal, #requirementId)")
	@ResponseBody
	@RequestMapping(value = "/delRequirement",  method = RequestMethod.POST)
	public String delDepartment(Long requirementId) {
		staffRequirementService.delete(requirementId);
		return "success";
	}
	
	@RequestMapping(value = "/aprroveOneDismission")
	public String aprroveOneDismission(Long dismissionId, Model model) {
		Dismission dismission = dismissionService.findOne(dismissionId);
		if (dismission != null) {
			//dismission.setDismissionHRManager(curUser.getUser());
			System.out.println("here!");
			dismission.setStatus(CommonUtils.DISMISSION_HR_MANAGER_PROCESSING);
			Role hrRole = roleService.findOne(CommonUtils.ROLE_HR_MANAGER);
			Person hrManager = personService.findByRole(hrRole).get(0);
			dismission.setDismissionHRManager(hrManager);
			dismission = dismissionService.findOne(dismissionId);
		}
		return "redirect:/dismission/showDismissions";
	}
	
	@RequestMapping(value = "/rejectOneDismission")
	public String rejectOneDismission(Long dismissionId, Model model) {
		Dismission dismission = dismissionService.findOne(dismissionId);
		if (dismission != null) {
			dismission.setStatus(CommonUtils.DISMISSION_TEAM_MANAGER_REJECT);
			dismission = dismissionService.findOne(dismissionId);
		}
		return "redirect:/dismission/showDismissions";
	}
	
	@RequestMapping(value = "/aprroveOneLeave")
	public String aprroveOneLeave(Long leaveId, Model model) {
		Leave leave = leaveService.findOne(leaveId);
		Role cbRole = roleService.findOne(CommonUtils.ROLE_CB_SPECIALIST);
		List<Person> cbSpecialists = personService.findByRole(cbRole);
		if (leave != null && cbSpecialists != null && cbSpecialists.size() > 0) {
			Random rand = new Random();
			Integer indx = rand.nextInt(cbSpecialists.size());
			Person cbSpecialist = cbSpecialists.get(indx);
			leave.setStatus(CommonUtils.LEAVE_CB_SPECIALIST_PROCESSING);
			leave.setLeaveCBSpecialist(cbSpecialist);
			leave = leaveService.findOne(leaveId);
		}
		return "redirect:/leave/showLeaves";
	}
	
	@RequestMapping(value = "/rejectOneLeave")
	public String rejectOneLeave(Long leaveId, Model model) {
		Leave leave = leaveService.findOne(leaveId);
		if (leave != null) {
			leave.setStatus(CommonUtils.LEAVE_REJECT);
			leave = leaveService.findOne(leaveId);
		}
		return "redirect:/leave/showLeaves";
	}
	
	@RequestMapping(value = "/chooseOneApplicant")
	public String chooseOneApplicant(Long applicantId, Model model) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Applicant applicant = applicantService.findOne(applicantId);
		applicant.setStatus(CommonUtils.APPLY_CHOOSED);
		applicant = applicantService.findOne(applicantId);
		Interview interview = new Interview();
		interview.setInterviewee(applicant.getApplicant());
		interview.setInterviewer(curUser.getUser());
		interview.setPlanForInterview(applicant.getPlanForApplicant());
		interview.setStatus(CommonUtils.INTERVIEW_PENDING_SCHEDULE);
		interview.setTurns(0);
		interview.setUpdateTime(new Date());
		
		List<StaffRequirement> requirements = staffRequirementService.findByDepartmentAndStatusAndRecruitingPlan(curUser.getUser().getDepartment(), CommonUtils.REQUIREMENTS_RECRUITING, applicant.getPlanForApplicant());
		
		if (requirements != null && requirements.size() > 0) {
			Random rand = new Random();
			int idx = rand.nextInt(requirements.size());
			StaffRequirement requirement = requirements.get(idx);
			interview.setRequirementForInterview(requirement);
		}
		
		interviewService.save(interview);
		return "redirect:/applicant/showApplicants";
	}
	
}
