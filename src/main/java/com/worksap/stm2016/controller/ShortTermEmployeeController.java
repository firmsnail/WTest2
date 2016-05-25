package com.worksap.stm2016.controller;

import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.worksap.stm2016.model.Applicant;
import com.worksap.stm2016.model.Attendance;
import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Dismission;
import com.worksap.stm2016.model.Interview;
import com.worksap.stm2016.model.Leave;
import com.worksap.stm2016.model.Notification;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.RecruitingPlan;
import com.worksap.stm2016.modelForm.DismissionForm;
import com.worksap.stm2016.modelForm.LeaveForm;
import com.worksap.stm2016.service.ApplicantService;
import com.worksap.stm2016.service.AttendanceService;
import com.worksap.stm2016.service.DismissionService;
import com.worksap.stm2016.service.InterviewService;
import com.worksap.stm2016.service.LeaveService;
import com.worksap.stm2016.service.NotificationService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.RecruitingPlanService;
import com.worksap.stm2016.utils.CommonUtils;
import com.worksap.stm2016.validator.DismissionFormValidator;
import com.worksap.stm2016.validator.LeaveFormValidator;

@Controller
@PreAuthorize("hasAuthority('SHORT-TERM-EMPLOYEE')")
@RequestMapping(value = "/short-term-employee")
public class ShortTermEmployeeController {
	
	@Autowired
	private InterviewService interviewService;
	@Autowired
	private DismissionService dismissionService;
	@Autowired
	private LeaveService leaveService;
	@Autowired
	private RecruitingPlanService recruitingPlanService;
	@Autowired
	private PersonService personService;
	@Autowired
	private ApplicantService applicantService;
	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private DismissionFormValidator  dismissionFormValidator;
	@Autowired
	private LeaveFormValidator  leaveFormValidator;
	
	@PreAuthorize("@currentUserServiceImpl.canAddDismission(principal)")
	@RequestMapping(value = "/addDismission",  method = RequestMethod.GET)
	public String addDismission(Model model) {
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
			Dismission oneDis = dismissionService.add(dismission);
			
			Notification notification = new Notification();
			notification.setOwner(oneDis.getDismissionDepartment().getManager());
			notification.setContent("You have a dismission need to process!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_DISMISSION);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_HIGH);
			notification.setUrl("/dismission/showDismissions");
			notification = notificationService.save(notification);
			
        } catch (DataIntegrityViolationException e) {
            return "short-term-employee/addDismission";
        }
		return "redirect:/dismission/showDismissions";
	}
	
	@PreAuthorize("@currentUserServiceImpl.canAddLeave(principal)")
	@RequestMapping(value = "/addLeave",  method = RequestMethod.GET)
	public String addLeave(Model model) {
		LeaveForm leave = new LeaveForm();
		model.addAttribute("leave", leave);
		return "short-term-employee/addLeave";
	}
	
	@RequestMapping(value = "/addLeave",  method = RequestMethod.POST)
	public String addLeave(@ModelAttribute("leave") @Valid LeaveForm leave, BindingResult bindingResult) {

		leaveFormValidator.validate(leave, bindingResult);
		
		if (bindingResult.hasErrors()) {
			System.out.println("Adding requirement occurs error!");
			return "short-term-employee/addLeave";
		}
		try {
			Leave oneLeave = leaveService.add(leave);
			
			Notification notification = new Notification();
			notification.setOwner(oneLeave.getLeaveDepartment().getManager());
			notification.setContent("You have a leaving request need to process!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_LEAVE);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_HIGH);
			notification.setUrl("/leave/showLeaves");
			notification = notificationService.save(notification);
        } catch (DataIntegrityViolationException e) {
            return "short-term-employee/addLeave";
        }
		return "redirect:/leave/showLeaves";
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
	
	@PreAuthorize("@currentUserServiceImpl.canDeleteLeave(principal, #leaveId)")
	//@ResponseBody
	@RequestMapping(value = "/deleteOneLeave")
	public String deleteOneLeave(Long leaveId) {
		leaveService.delete(leaveId);
		return "redirect:/leave/showLeaves";
	}
	
	@PreAuthorize("@currentUserServiceImpl.canApplyRecruitingPlan(principal, #planId)")
	//@ResponseBody
	@RequestMapping(value = "/applyOnePlan")
	public String applyOnePlan(Long planId) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RecruitingPlan plan = recruitingPlanService.findOne(planId);
		Person user = personService.findById(curUser.getId());
		Applicant applicant = new Applicant();
		applicant.setApplicant(user);
		applicant.setPlanForApplicant(plan);
		applicant.setStatus(CommonUtils.APPLY_PENDING_FILTER);
		applicant.setApplyDate(new Date());
		applicantService.save(applicant);
		//leaveService.delete(planId);
		return "redirect:/plan/showRecruitingPlans";
	}
	
	@RequestMapping(value = "/sign")
	@ResponseBody
	public String sign() {
		Date today = new Date();
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Attendance> attendances = attendanceService.findByAttendancePersonAndAttendanceDate(curUser.getUser(), today);
		Attendance todayAttendance = null;
		if (attendances != null && attendances.size() > 0) {
			todayAttendance = attendances.get(0);
			todayAttendance.setLeaveTime(today);
			Date startTime = CommonUtils.getDayStartTime(todayAttendance.getAttendanceDate());
			Date endTime = CommonUtils.getDayEndTime(todayAttendance.getAttendanceDate());
			if (todayAttendance.getAttendanceTime().after(startTime)) {
				todayAttendance.setType(CommonUtils.ATTENDANCE_ATTEND_LATE);
			} else if (todayAttendance.getLeaveTime().before(endTime)) {
				todayAttendance.setType(CommonUtils.ATTENDANCE_LEAVE_EARLY);
			} else {
				todayAttendance.setType(CommonUtils.ATTENDANCE_NORMAL);
			}
			todayAttendance = attendanceService.findOne(todayAttendance.getAttendanceId());
		} else {
			todayAttendance = new Attendance();
			todayAttendance.setAttendanceTime(today);
			todayAttendance.setType(CommonUtils.ATTENDANCE_LEAVE_NOT_RECORD);
			todayAttendance.setAttendancePerson(curUser.getUser());
			todayAttendance.setAttendanceDepartment(curUser.getUser().getDepartment());
			todayAttendance.setAttendanceDate(today);
			attendanceService.save(todayAttendance);
		}
		return "success";
	}
}
