package com.worksap.stm2016.validator;

import org.springframework.validation.Validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.modelForm.RequirementForm;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.SkillService;

@Component
public class RequirementFormValidator implements Validator {

	@Autowired
    private SkillService skillService;
	@Autowired
    private DepartmentService departmentService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(RequirementForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	RequirementForm form = (RequirementForm) target;
    	if (form.getSkills() != null && form.getSkills().size() > 0) {
    		validateSkills(errors, form);
    	}
    	if (form.getDepartmentId() != null && form.getDepartmentId() >= 0) {
    		validateDepartment(errors, form);
    	}
    }

	private void validateDepartment(Errors errors, RequirementForm form) {
		Department dept = departmentService.findOne(form.getDepartmentId());
		if (dept == null) {
			errors.rejectValue("departmentId", "departmentId", "Please choose correct department!");
		}
	}

	private void validateSkills(Errors errors, RequirementForm form) {
		List<Long> skills = form.getSkills();
		for (Long skillId : skills) {
			if (skillService.findOne(skillId) == null) {
				errors.rejectValue("skills", "skills", "Please choose correct skills!");
				return;
			}
		}
	}
    
}