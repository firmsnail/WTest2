package com.worksap.stm2016.controller;

import java.util.Date;
import java.util.List;
import java.util.Random;

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

import com.worksap.stm2016.model.Applicant;
import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Dismission;
import com.worksap.stm2016.model.Hire;
import com.worksap.stm2016.model.Interview;
import com.worksap.stm2016.model.Leave;
import com.worksap.stm2016.model.Notification;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.modelForm.RequirementForm;
import com.worksap.stm2016.service.ApplicantService;
import com.worksap.stm2016.service.DismissionService;
import com.worksap.stm2016.service.HireService;
import com.worksap.stm2016.service.InterviewService;
import com.worksap.stm2016.service.LeaveService;
import com.worksap.stm2016.service.NotificationService;
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
	private HireService hireService;
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private RequirementFormValidator requirementFormValidator;
	
	@PreAuthorize("@currentUserServiceImpl.canAddRequirement(principal)")
	@RequestMapping(value = "/addRequirement",  method = RequestMethod.GET)
	public String addRequirement(Model model) {
		List<Skill> skills = skillService.findAll();
		model.addAttribute("chooseSkills", skills);
		RequirementForm requirement = new RequirementForm();
		model.addAttribute("requirement", requirement);
		return "team-manager/addRequirement";
	}
	
	@PreAuthorize("@currentUserServiceImpl.canAddRequirement(principal)")
	@RequestMapping(value = "/addRequirement",  method = RequestMethod.POST)
	public String addRequirement(@ModelAttribute("requirement") @Valid RequirementForm requirement, BindingResult bindingResult, Model model) {
		System.out.println("@addDepartment start!");
		requirementFormValidator.validate(requirement, bindingResult);
		
		if (bindingResult.hasErrors()) {
			System.out.println("Adding requirement occurs error!");
			List<Skill> skills = skillService.findAll();
			model.addAttribute("chooseSkills", skills);
			return "team-manager/addRequirement";
		}
		try {
			System.out.println("requirement: " + requirement);
			staffRequirementService.add(requirement);
			
			Notification notification = new Notification();
			Role hrmRole = roleService.findOne(CommonUtils.ROLE_HR_MANAGER);
			List<Person> pers = personService.findByRole(hrmRole);
			Person hrManager = pers.get(0);
			notification.setOwner(hrManager);
			notification.setContent("You have a staffing requirement need to process!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_REQUIREMENT);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_MIDDLE);
			notification.setUrl("/requirement/showStaffRequirements");
			notification = notificationService.save(notification);
			
			
        } catch (DataIntegrityViolationException e) {
        	List<Skill> skills = skillService.findAll();
			model.addAttribute("chooseSkills", skills);
            return "team-manager/addRequirement";
        }
		return "redirect:/requirement/showStaffRequirements";
	}
	
	@PreAuthorize("@currentUserServiceImpl.canDeleteStaffRequirement(principal, #requirementId)")
	@ResponseBody
	@RequestMapping(value = "/delRequirement",  method = RequestMethod.POST)
	public String delDepartment(Long requirementId) {
		System.out.println("@delDepartment start!");
		staffRequirementService.delete(requirementId);
		return "success";
	}
	
	@RequestMapping(value = "/aprroveOneDismission")
	public String aprroveOneDismission(Long dismissionId, Model model) {
		Dismission dismission = dismissionService.findOne(dismissionId);
		if (dismission != null) {
			//dismission.setDismissionHRManager(curUser.getUser());
			dismission.setStatus(CommonUtils.DISMISSION_HR_MANAGER_PROCESSING);
			Role hrRole = roleService.findOne(CommonUtils.ROLE_HR_MANAGER);
			Person hrManager = personService.findByRole(hrRole).get(0);
			dismission.setDismissionHRManager(hrManager);
			dismission = dismissionService.findOne(dismissionId);
			
			Notification notification = new Notification();
			notification.setOwner(hrManager);
			notification.setContent("You have a dismission need to process!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_DISMISSION);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_HIGH);
			notification.setUrl("/dismission/showDismissions");
			notification = notificationService.save(notification);
		}
		return "redirect:/dismission/showDismissions";
	}
	
	@RequestMapping(value = "/rejectOneDismission")
	public String rejectOneDismission(Long dismissionId, Model model) {
		Dismission dismission = dismissionService.findOne(dismissionId);
		if (dismission != null) {
			dismission.setStatus(CommonUtils.DISMISSION_TEAM_MANAGER_REJECT);
			dismission = dismissionService.findOne(dismissionId);
			
			Notification notification = new Notification();
			notification.setOwner(dismission.getDismissionPerson());
			notification.setContent("Your dismission has been rejected!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_DISMISSION);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_LOW);
			notification.setUrl("/dismission/showDismissions");
			notification = notificationService.save(notification);
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
			
			Notification notification = new Notification();
			notification.setOwner(cbSpecialist);
			notification.setContent("You have a leaving request that needed to process!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_LEAVE);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_HIGH);
			notification.setUrl("/leave/showLeaves");
			notification = notificationService.save(notification);
		}
		return "redirect:/leave/showLeaves";
	}
	
	@RequestMapping(value = "/rejectOneLeave")
	public String rejectOneLeave(Long leaveId, Model model) {
		Leave leave = leaveService.findOne(leaveId);
		if (leave != null) {
			leave.setStatus(CommonUtils.LEAVE_REJECT);
			leave = leaveService.findOne(leaveId);
			
			Notification notification = new Notification();
			notification.setOwner(leave.getLeavePerson());
			notification.setContent("Your leaving request has been rejected!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_LEAVE);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_LOW);
			notification.setUrl("/leave/showLeaves");
			notification = notificationService.save(notification);
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
		
		interview = interviewService.save(interview);
		
		Notification notification = new Notification();
		notification.setOwner(interview.getPlanForInterview().getPlanMaker());
		notification.setContent("You have a interview to schedule!");
		notification.setIssueTime(new Date());
		notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
		notification.setType(CommonUtils.NOTIFICATION_TYPE_INTERVIEW);
		notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_HIGH);
		notification.setUrl("/interview/showInterviews");
		notification = notificationService.save(notification);
		
		return "redirect:/applicant/showApplicants";
	}
	
	@RequestMapping(value = "/passOneInterview")
	public String passOneInterview(Long interviewId, Double salary, Integer period, Model model) {
		Interview interview = interviewService.findOne(interviewId);
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (interview != null) {
			interview.setStatus(CommonUtils.INTERVIEW_PASSED);
			interview.setTurns(interview.getTurns()+1);
			interview.setInterviewTime(null);
			interview.setUpdateTime(new Date());
			interview = interviewService.findOne(interviewId);
			
			Hire hire = new Hire();
			hire.setHireDepartment(curUser.getUser().getDepartment());
			//hire.setHireHRManager(curUser.getUser());
			hire.setHirePerson(interview.getInterviewee());
			hire.setHirePlan(interview.getPlanForInterview());
			hire.setRequirementForHire(interview.getRequirementForInterview());
			hire.setHireRecruiter(interview.getPlanForInterview().getPlanMaker());
			hire.setPeriod(period);
			hire.setSalary(salary);
			hire.setStatus(CommonUtils.HIRE_RECRUITER_PROCESSING);
			hire.setSubmitDate(new Date());
			
			hire = hireService.save(hire);
			
			Person employee = personService.findById(hire.getHirePerson().getPersonId());
			employee.setStatus(CommonUtils.EMPLOYEE_CANDIDATE);
			employee = personService.findById(employee.getPersonId());
			
			
			Notification notification = new Notification();
			notification.setOwner(interview.getPlanForInterview().getPlanMaker());
			notification.setContent("You have a hire to verify!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_HIRE);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_MIDDLE);
			notification.setUrl("/hire/showHires");
			notification = notificationService.save(notification);
		}
		return "redirect:/interview/showInterviews";
	}
	
	@RequestMapping(value = "/addOneInterview")
	public String addOneInterview(Long interviewId, Model model) {
		Interview interview = interviewService.findOne(interviewId);
		if (interview != null) {
			interview.setStatus(CommonUtils.INTERVIEW_PENDING_SCHEDULE);
			interview.setTurns(interview.getTurns()+1);
			interview.setInterviewTime(null);
			interview.setUpdateTime(new Date());
			interview = interviewService.findOne(interviewId);
			
			Notification notification = new Notification();
			notification.setOwner(interview.getPlanForInterview().getPlanMaker());
			notification.setContent("You have a interview to schedule!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_INTERVIEW);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_HIGH);
			notification.setUrl("/interview/showInterviews");
			notification = notificationService.save(notification);
		}
		return "redirect:/interview/showInterviews";
	}
	
	@RequestMapping(value = "/failOneInterview")
	public String failOneInterview(Long interviewId, Model model) {
		Interview interview = interviewService.findOne(interviewId);
		if (interview != null) {
			interview.setStatus(CommonUtils.INTERVIEW_FAILED);
			interview.setTurns(interview.getTurns()+1);
			interview.setInterviewTime(null);
			interview.setUpdateTime(new Date());
			interview = interviewService.findOne(interviewId);
			
		}
		return "redirect:/interview/showInterviews";
	}
	
}
