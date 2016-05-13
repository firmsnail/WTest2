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
    private SkillService skillService;
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
    	
    	if (form.getSkills() != null && form.getSkills().size() > 0) {
    		validateSkills(errors, form);
    	}
    	
    	if (form.getExpectDate() != null && form.getInvalidDate() != null) {
    		validateDate(errors, form);
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

	private void validateSkills(Errors errors, PlanForm form) {
		List<Long> skills = form.getSkills();
		for (Long skillId : skills) {
			if (skillService.findOne(skillId) == null) {
				errors.rejectValue("skills", "skills", "Please choose correct skills!");
				return;
			}
		}
	}
    
}