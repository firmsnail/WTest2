package com.worksap.stm2016.validator;

import org.springframework.validation.Validator;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.worksap.stm2016.modelForm.PlanForm;
import com.worksap.stm2016.modelForm.RequirementForm;
import com.worksap.stm2016.service.SkillService;
import com.worksap.stm2016.service.StaffRequirementService;
import com.worksap.stm2016.utils.CommonUtils;

@Component
public class PlanFormValidator implements Validator {

	@Autowired
    private StaffRequirementService staffRequirementService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(RequirementForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	PlanForm form = (PlanForm) target;
    	
    	if (form.getRequirements() != null && form.getRequirements().size() > 0) {
    		validateRequirements(errors, form);
    	}
    	
    	if (form.getExpectDate() != null && form.getInvalidDate() != null) {
    		validateDate(errors, form);
    	}
    	if (form.getReason() != null && form.getReason().length() > 0) {
    		validateReason(errors, form);
    	}
    }

    private void validateReason(Errors errors, PlanForm form) {
		if (CommonUtils.ContentRegex.matcher(form.getReason()).matches()) {
			errors.rejectValue("reason", "reason", "Your behavior is dangerous, please don't attempt to attack the system.");
		}
	}
    
	private void validateDate(Errors errors, PlanForm form) {
		Date expectDate = form.getExpectDate(), invalidDate = form.getInvalidDate();
		expectDate = CommonUtils.OneWeekAfter(expectDate);
		if (invalidDate.before(expectDate)) {
			errors.rejectValue("invalidDate", "invalidDate", "Invalid date must be a week later than expect date!");
		}
	}

	private void validateRequirements(Errors errors, PlanForm form) {
		List<Long> requirements = form.getRequirements();
		for (Long requirementId : requirements) {
			if (staffRequirementService.findOne(requirementId) == null) {
				errors.rejectValue("requirements", "requirements", "Please choose correct requirements!");
				return;
			}
		}
	}

    
}