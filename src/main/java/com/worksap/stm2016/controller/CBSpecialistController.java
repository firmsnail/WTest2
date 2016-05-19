package com.worksap.stm2016.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.worksap.stm2016.model.Dismission;
import com.worksap.stm2016.model.Leave;
import com.worksap.stm2016.model.Notification;
import com.worksap.stm2016.service.DismissionService;
import com.worksap.stm2016.service.LeaveService;
import com.worksap.stm2016.service.NotificationService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.utils.CommonUtils;

@Controller
@PreAuthorize("hasAuthority('RECRUITER')")
@RequestMapping(value = "/cb-specialist")
public class CBSpecialistController {
	
	@Autowired
	private DismissionService dismissionService;
	@Autowired
	private LeaveService leaveService;
	@Autowired
	private PersonService personService;
	@Autowired
	private NotificationService notificationService;

	
	@RequestMapping(value = "/processOneDismission")
	public String processOneDismission(Long dismissionId, Model model) {
		Dismission dismission = dismissionService.findOne(dismissionId);
		if (dismission != null) {
			dismission.setStatus(CommonUtils.DISMISSION_FINISH);
			dismission.getDismissionPerson().setStatus(CommonUtils.EMPLOYEE_DISMISSION);
			dismission = dismissionService.findOne(dismissionId);
			Notification notification = new Notification();
			notification.setOwner(dismission.getDismissionPerson());
			notification.setContent("Your dismission has been approved!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_DISMISSION);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_MIDDLE);
			notification.setUrl("#");
			notification = notificationService.save(notification);
			personService.findById(dismission.getDismissionPerson().getPersonId());
		}
		return "redirect:/dismission/showDismissions";
	}
	
	@RequestMapping(value = "/processOneLeave")
	public String processOneLeave(Long leaveId, Model model) {
		Leave leave = leaveService.findOne(leaveId);
		if (leave != null) {
			leave.setStatus(CommonUtils.LEAVE_FINISH);
			leave = leaveService.findOne(leaveId);
			Notification notification = new Notification();
			notification.setOwner(leave.getLeavePerson());
			notification.setContent("Your leave has been approved!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_LEAVE);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_MIDDLE);
			notification.setUrl("#");
			notification = notificationService.save(notification);
		}
		return "redirect:/leave/showLeaves";
	}
}
