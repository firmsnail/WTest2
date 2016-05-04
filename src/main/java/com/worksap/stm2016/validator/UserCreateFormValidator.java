package com.worksap.stm2016.validator;

import org.springframework.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.worksap.stm2016.modelForm.UserCreateForm;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.PersonService;

@Component
public class UserCreateFormValidator implements Validator {

	@Autowired
    private PersonService userService;
	
	@Autowired
    private DepartmentService deptService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserCreateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserCreateForm form = (UserCreateForm) target;
        
        validateDepartment(errors, form);
        validatePasswords(errors, form);
        validateEmail(errors, form);
    }

    private void validateDepartment(Errors errors, UserCreateForm form) {
		// TODO Auto-generated method stub
		if (form.getDepartmentId() != null && deptService.findOne(form.getDepartmentId()) == null) {
			errors.rejectValue("departmentId", "departmentId", "Department is not existed!");
		}
	}
    
	private void validatePasswords(Errors errors, UserCreateForm form) {
        if (!form.getPassword().equals(form.getConfirmPassword())) {
        	errors.rejectValue("confirmPassword", "confirmPassword", "Passwords do not match");
        }
    }

    private void validateEmail(Errors errors, UserCreateForm form) {
        if (form.getEmail() != null && form.getEmail().length() > 0 && userService.findByEmail(form.getEmail()) != null) {
        	errors.rejectValue("email", "email", "User with this email already exists.");
        }
    }
    
}