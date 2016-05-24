package com.worksap.stm2016.validator;

import org.springframework.validation.Validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.worksap.stm2016.modelForm.LeaveForm;
import com.worksap.stm2016.utils.CommonUtils;

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
    	if (form.getReason() != null && form.getReason().length() > 0) {
    		validateReason(errors, form);
    	}
    }

	private void validateReason(Errors errors, LeaveForm form) {
		if (!CommonUtils.ContentRegex.matcher(form.getReason()).matches()) {
			errors.rejectValue("reason", "reason", "Your behavior is dangerous, please don't attempt to attack the system.");
		}
	}

	private void validateDateRange(Errors errors, LeaveForm form) {
		if (form.getEndDate().before(form.getStartDate())) {
			errors.rejectValue("dateRange", "dateRange", "End date should be later than the start date!");
		}
	}
    
}