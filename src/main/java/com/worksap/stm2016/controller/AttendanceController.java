package com.worksap.stm2016.controller;


import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.worksap.stm2016.model.Attendance;
import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.service.AttendanceService;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.PersonService;

@Controller
@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'C&B-SPECIALIST', 'SHORT-TERM-EMPLOYEE')")
@RequestMapping(value = "/attendance")
public class AttendanceController {
	
	@Autowired
	AttendanceService attendanceService;
	@Autowired
	PersonService personService;
	@Autowired
	DepartmentService departmentService;
	
	@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'C&B-SPECIALIST')")
	@RequestMapping(value={"/showAttendances"},  method = RequestMethod.GET)
	public String showAttendances(Long departmentId, Long personId, Date startDate, Date endDate, Model model) {
		System.out.println("@showAttendances Start!");
		List<Attendance> attendances = null;
		if (personId != null) {
			Person cUser = personService.findById(personId);
			if (cUser != null) {
				if (startDate != null && endDate != null) {
					attendances = attendanceService.findByPersonAndStartDateAndEndDate(cUser, startDate, endDate);
				} else if (startDate != null) {
					attendances = attendanceService.findByPersonAndStartDate(cUser, startDate);
				} else if (endDate != null) {
					attendances = attendanceService.findByPersonAndEndDate(cUser, endDate);
				} else {
					attendances = attendanceService.findByPerson(cUser);
				}
			}
		} else if (departmentId != null){
				Department department = departmentService.findOne(departmentId);
				if (department != null) {
					if (startDate != null && endDate != null) {
						attendances = attendanceService.findByDepartmentAndStartDateAndEndDate(department, startDate, endDate);
					} else if (startDate != null) {
						attendances = attendanceService.findByDepartmentAndStartDate(department, startDate);
					} else if (endDate != null) {
						attendances = attendanceService.findByDepartmentAndEndDate(department, endDate);
					} else {
						attendances = attendanceService.findByDepartment(department);
					}
				}
		} else {
			if (startDate != null && endDate != null) {
				attendances = attendanceService.findByStartDateAndEndDate(startDate, endDate);
			} else if (startDate != null) {
				attendances = attendanceService.findByStartDate(startDate);
			} else if (endDate != null) {
				attendances = attendanceService.findByEndDate(endDate);
			} else {
				System.out.println("here");
				attendances = attendanceService.findAll();
				System.out.println("attendances.size: " + attendances.size());
			}
		}
		if (attendances != null) {
			Collections.sort(attendances, new Comparator<Attendance>(){
				@Override
				public int compare(Attendance o1, Attendance o2) {
					if (o1.getAttendanceDate().after(o2.getAttendanceDate())) return -1;
					return 1;
				}
				
			});
			model.addAttribute("attendances", attendances);
		}
		return "attendance/showAttendances";
	}
	
	@PreAuthorize("hasAnyAuthority('SHORT-TERM-EMPLOYEE')")
	@RequestMapping(value={"/showAttendancesByPerson"},  method = RequestMethod.GET)
	public String showAttendancesByPerson(Date startDate, Date endDate, Model model) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Attendance> attendances = null;
		if (startDate != null && endDate != null) {
			attendances = attendanceService.findByPersonAndStartDateAndEndDate(curUser.getUser(), startDate, endDate);
		} else if (startDate != null) {
			attendances = attendanceService.findByPersonAndStartDate(curUser.getUser(), startDate);
		} else if (endDate != null) {
			attendances = attendanceService.findByPersonAndEndDate(curUser.getUser(), endDate);
		} else {
			attendances = attendanceService.findByPerson(curUser.getUser());
		}
		if (attendances != null) {
			Collections.sort(attendances, new Comparator<Attendance>(){
				@Override
				public int compare(Attendance o1, Attendance o2) {
					if (o1.getAttendanceDate().after(o2.getAttendanceDate())) return -1;
					return 1;
				}
				
			});
			model.addAttribute("attendances", attendances);
		}
		
		return "attendance/showAttendances";
	}
	
}
