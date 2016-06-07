package com.worksap.stm2016.scheduledTask;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.RoleService;
import com.worksap.stm2016.utils.CommonUtils;

@Component  
@Configurable  
@EnableScheduling  
public class EmployeeTasks {
	@Autowired
	private PersonService personService;
	@Autowired
	private RoleService roleService;

	public void work() {
		Role empRole = roleService.findOne(CommonUtils.ROLE_SHORT_TERM_EMPLOYEE);
		List<Person> employees = personService.findByRoleAndStatus(empRole, CommonUtils.EMPLOYEE_WORKING);
		for (Person employee : employees) {
			if (employee.getEndDate() != null && employee.getEndDate().before(new Date())) {
				employee.setStatus(CommonUtils.EMPLOYEE_REGISTERED);
				employee.setDepartment(null);
				employee.setStartDate(null);
				employee.setEndDate(null);
				employee.setSalary(null);
				employee = personService.findById(employee.getPersonId());
			}
		}
	}

}
