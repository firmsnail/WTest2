package com.worksap.stm2016.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.service.AttendanceService;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.RoleService;
import com.worksap.stm2016.utils.CommonUtils;

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
	@Autowired
	RoleService roleService;
	
	@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'C&B-SPECIALIST')")
	@RequestMapping(value={"/showAttendances"},  method = RequestMethod.GET)
	public String showAttendances(Long departmentId, Long personId, String strStartDate, String strEndDate, Model model) throws ParseException {
		System.out.println("@showAttendances Start!");
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null, endDate = null;
		if (strStartDate != null && !strStartDate.isEmpty()) {
			startDate = df.parse(strStartDate);
		}
		if (strEndDate != null && !strEndDate.isEmpty()) {
			endDate = df.parse(strEndDate);
		}
		
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
				System.out.println("curEmployee: " + cUser.getPersonId());
				model.addAttribute("curEmployee", cUser);
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
					System.out.println("curDept: " + department.getDepartmentId());
					model.addAttribute("curDept", department);
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
		List<Department> allDepts = departmentService.findAll();
		model.addAttribute("allDepts", allDepts);
		Role shortRole = roleService.findOne(CommonUtils.ROLE_SHORT_TERM_EMPLOYEE);
		List<Person> allEmployees = personService.findByRoleAndStatus(shortRole, CommonUtils.EMPLOYEE_WORKING);
		model.addAttribute("allEmployees", allEmployees);
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
		return "attendance/showAttendances";
	}
	
	@PreAuthorize("hasAnyAuthority('SHORT-TERM-EMPLOYEE')")
	@RequestMapping(value={"/showAttendancesByPerson"},  method = RequestMethod.GET)
	public String showAttendancesByPerson(String strStartDate, String strEndDate, Model model) throws ParseException {
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null, endDate = null;
		if (strStartDate != null && !strStartDate.isEmpty()) {
			startDate = df.parse(strStartDate);
		}
		if (strEndDate != null && !strEndDate.isEmpty()) {
			endDate = df.parse(strEndDate);
		}
		
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
		return "attendance/showAttendances";
	}
	
}
