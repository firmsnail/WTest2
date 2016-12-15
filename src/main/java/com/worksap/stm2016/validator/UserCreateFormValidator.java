package com.worksap.stm2016.validator;

import org.springframework.validation.Validator;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.modelForm.UserCreateForm;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.utils.CommonUtils;

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
        validateUserName(errors, form);
        validateFirstName(errors, form);
        validateLastName(errors, form);
        
    }

    private void validateLastName(Errors errors, UserCreateForm form) {
		if (CommonUtils.ContentRegex.matcher(form.getLastName()).matches()) {
			errors.rejectValue("lastName", "lastName", "Your behavior is dangerous, please don't attempt to attack the system.");
		} else if (form.getLastName().trim().length() <= 0) {
			errors.rejectValue("lastName", "lastName", "The last name can not be empty.");
		}
	}

	private void validateFirstName(Errors errors, UserCreateForm form) {
		if (CommonUtils.ContentRegex.matcher(form.getFirstName()).matches()) {
			errors.rejectValue("firstName", "firstName", "Your behavior is dangerous, please don't attempt to attack the system.");
		} else if (form.getFirstName().trim().length() <= 0) {
			errors.rejectValue("firstName", "firstName", "The first name can not be empty.");
		}
	}
    
    private void validateUserName(Errors errors, UserCreateForm form) {
		if (CommonUtils.ContentRegex.matcher(form.getUserName()).matches()) {
			errors.rejectValue("userName", "userName", "Your behavior is dangerous, please don't attempt to attack the system.");
		} else if (!CommonUtils.FieldRegex.matcher(form.getUserName()).matches()) {
			errors.rejectValue("userName", "userName", "You can only enter underscore, numbers and letters.");
		} else {
			Person per = userService.findByUserName(form.getUserName());
			if (per != null) {
				errors.rejectValue("userName", "userName", "Username already exist, please change the username.");
			}
		}
	}
    
    private void validateDepartment(Errors errors, UserCreateForm form) {
		if (form.getDepartmentId() != null && deptService.findOne(form.getDepartmentId()) == null) {
			errors.rejectValue("departmentId", "departmentId", "Department is not existed!");
		}
	}
    
	private void validatePasswords(Errors errors, UserCreateForm form) {
        if (!form.getPassword().equals(form.getConfirmPassword())) {
        	errors.rejectValue("confirmPassword", "confirmPassword", "Passwords do not match");
        } else {
        	if (CommonUtils.ContentRegex.matcher(form.getPassword()).matches()) {
    			errors.rejectValue("password", "password", "Your behavior is dangerous, please don't attempt to attack the system.");
    		} else if (!CommonUtils.FieldRegex.matcher(form.getPassword()).matches()) {
    			errors.rejectValue("password", "password", "You can only enter underscore, numbers and letters.");
    		}
        	
        	if (CommonUtils.ContentRegex.matcher(form.getConfirmPassword()).matches()) {
    			errors.rejectValue("confirmPassword", "confirmPassword", "Your behavior is dangerous, please don't attempt to attack the system.");
    		} else if (!CommonUtils.FieldRegex.matcher(form.getConfirmPassword()).matches()) {
    			errors.rejectValue("confirmPassword", "confirmPassword", "You can only enter underscore, numbers and letters.");
    		}
        }
    }

    private void validateEmail(Errors errors, UserCreateForm form) {
    	CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (form.getEmail() != null && form.getEmail().length() > 0 && userService.findByEmail(form.getEmail()) != null && userService.findByEmail(form.getEmail()).getPersonId() != curUser.getId()) {
        	errors.rejectValue("email", "email", "User with this email already exists.");
        }
    }
    
}