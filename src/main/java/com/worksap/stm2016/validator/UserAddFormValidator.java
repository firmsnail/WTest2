package com.worksap.stm2016.validator;

import org.springframework.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.modelForm.UserCreateForm;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.utils.CommonUtils;

@Component
public class UserAddFormValidator implements Validator {

	@Autowired
    private DepartmentService deptService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserCreateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserCreateForm form = (UserCreateForm) target;

        validateEmail(errors, form);
        
        if (form.getDepartmentId() != null) {
        	validateDepartment(errors, form);
        }
    }

	private void validateDepartment(Errors errors, UserCreateForm form) {
		Department dept = deptService.findOne(form.getDepartmentId());
    	if (dept == null) {
    		errors.rejectValue("departmentId", "departmentId", "Please choose a correct department.");
    	} else if (dept.getManager() != null && form.getRole() == CommonUtils.ROLE_TEAM_MANAGER) {
    		errors.rejectValue("departmentId", "departmentId", "The department already has a manager!");
    	}
	}

	private void validateEmail(Errors errors, UserCreateForm form) {
        if (form.getEmail() == null || form.getEmail().length() <= 0 ) {
        	errors.rejectValue("email", "email", "Please enter a email.");
        }
    }
    
}