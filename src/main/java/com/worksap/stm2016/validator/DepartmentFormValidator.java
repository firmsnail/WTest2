package com.worksap.stm2016.validator;

import org.springframework.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.modelForm.DepartmentForm;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.PersonService;

@Component
public class DepartmentFormValidator implements Validator {

	@Autowired
    private DepartmentService deptService;
	@Autowired
    private PersonService personService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(DepartmentForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

    	DepartmentForm form = (DepartmentForm) target;
    	if (form.getDepartmentName() != null && form.getDepartmentName().length() > 0) {
    		validateDepartmentName(errors, form);
    	}
        if (form.getManagerId() != null) {
        	validateManager(errors, form);
        }
    }

	private void validateManager(Errors errors, DepartmentForm form) {
		Person manager = personService.findById(form.getManagerId());
		if (manager == null) {
			errors.rejectValue("manager", "manager", "The manager doesn't exist!");
		}
		if (manager.getDepartment() != null) {
			errors.rejectValue("manager", "manager", "The manager already belong to other department!");
		}
	}

	private void validateDepartmentName(Errors errors, DepartmentForm form) {
		Department dept = deptService.findByDepartmentName(form.getDepartmentName());
		if (dept != null) {
			errors.rejectValue("departmentName", "departmentName", "The department already existed!");
		}
	}
    
}