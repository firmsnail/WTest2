package com.worksap.stm2016.validator;

import org.springframework.validation.Validator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.modelForm.DismissionForm;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.utils.CommonUtils;

@Component
public class DismissionFormValidator implements Validator {

	@Autowired
    private PersonService personService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(DismissionForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	DismissionForm form = (DismissionForm) target;

    	if (form.getEmployeeId() != null) {
    		validateEmployee(errors, form);
    	}
    	if (form.getExpectDate() != null) {
    		validateExpectDate(errors, form);
    	}
    }

	private void validateExpectDate(Errors errors, DismissionForm form) {
		Date expectDate = form.getExpectDate();
		Date date = new Date();
		date = CommonUtils.OneWeekAfter(date);
		Calendar calendar = new GregorianCalendar(); 
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		date=calendar.getTime();
		calendar.setTime(expectDate);
		calendar.set(Calendar.HOUR, 23);
		expectDate = calendar.getTime();
		if (expectDate.before(date)) {
			errors.rejectValue("expectDate", "expectDate", "You should add dismission before a week!");
		}
	}

	private void validateEmployee(Errors errors, DismissionForm form) {
		Person employee = personService.findById(form.getEmployeeId());
		if (employee == null) {
			errors.rejectValue("employeeId", "employeeId", "Employee doesn't exist!");
		} else {
			CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (!employee.getPersonId().equals(curUser.getId()) && 
					(!curUser.getRole().getRoleId().equals(CommonUtils.ROLE_TEAM_MANAGER) || 
							!curUser.getUser().getDepartment().getDepartmentId().equals(employee.getDepartment().getDepartmentId()))) {
				errors.rejectValue("employeeId", "employeeId", "Authority not allowed!");
			}
		}
	}
    
}