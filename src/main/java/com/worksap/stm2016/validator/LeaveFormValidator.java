package com.worksap.stm2016.validator;

import org.springframework.validation.Validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.worksap.stm2016.modelForm.LeaveForm;

@Component
public class LeaveFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(LeaveForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	LeaveForm form = (LeaveForm) target;

    	if (form.getStartDate() != null && form.getEndDate() != null) {
    		validateDateRange(errors, form);
    	}
    }

	private void validateDateRange(Errors errors, LeaveForm form) {
		if (form.getEndDate().before(form.getStartDate())) {
			errors.rejectValue("dateRange", "dateRange", "End date should be later than the start date!");
		}
	}
    
}