package com.worksap.stm2016.scheduledTask;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.worksap.stm2016.model.Attendance;
import com.worksap.stm2016.model.Payroll;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.service.AttendanceService;
import com.worksap.stm2016.service.PayrollService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.RoleService;
import com.worksap.stm2016.utils.CommonUtils;

@Component  
@Configurable  
@EnableScheduling  
public class PayrollTasks {
	@Autowired
	private PersonService personService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private PayrollService payrollService;

	public void work() {
		//System.out.println("PayrollTasks start!");
		Role role = roleService.findOne(CommonUtils.ROLE_SHORT_TERM_EMPLOYEE);
		List<Person> employees = personService.findByRoleAndStatus(role, CommonUtils.EMPLOYEE_WORKING);
		Date today = new Date();
		Date startDate = CommonUtils.OneMonthBefore(today);
		Date endDate = CommonUtils.OneDayBefore(today);
		for (Person employee : employees) {
			List<Attendance> attendances = attendanceService.findByPersonAndStartDateAndEndDate(employee, startDate, endDate);
			Double payment = figurePayrollForOneMonth(employee, attendances);
			Integer normalAttends = countNormalAttendance(attendances);
			Integer unNormalAttends = countUnNormalAttendance(attendances);
			Payroll payroll = new Payroll();
			payroll.setAmount(payment);
			payroll.setIssueDate(today);
			payroll.setPayrollEmployee(employee);
			payroll.setPayrollDepartment(employee.getDepartment());
			payroll.setNormalAttends(normalAttends);
			payroll.setUnNormalAttends(unNormalAttends);
			payroll.setBaseSalary(employee.getSalary());
			payrollService.save(payroll);
		}
	}

	private Double figurePayrollForOneMonth(Person employee, List<Attendance> attendances) {
		Double total = 0.0, salaryOneDay = employee.getSalary();
		for (Attendance attend : attendances) {
			if (attend.getType().equals(CommonUtils.ATTENDANCE_NORMAL)) {
				total += salaryOneDay;
			} else {
				total += salaryOneDay/2;
			}
		}
		return total;
	}
	
	private Integer countNormalAttendance(List<Attendance> attendances) {
		Integer days = 0;
		for (Attendance attend : attendances) {
			if (attend.getType().equals(CommonUtils.ATTENDANCE_NORMAL)) {
				++days;
			}
		}
		return days;
	}
	
	private Integer countUnNormalAttendance(List<Attendance> attendances) {
		Integer days = 0;
		for (Attendance attend : attendances) {
			if (!attend.getType().equals(CommonUtils.ATTENDANCE_NORMAL)) {
				++days;
			}
		}
		return days;
	}
	
}
