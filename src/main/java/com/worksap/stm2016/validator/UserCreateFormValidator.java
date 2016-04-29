package com.worksap.stm2016.validator;

import org.springframework.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.worksap.stm2016.model.UserCreateForm;
import com.worksap.stm2016.service.PersonService;

@Component
public class UserCreateFormValidator implements Validator {

	@Autowired
    private PersonService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserCreateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserCreateForm form = (UserCreateForm) target;
        validateRole(errors, form);
        validateUserName(errors, form);
        validatePasswords(errors, form);
        validateEmail(errors, form);
    }

    private void validateRole(Errors errors, UserCreateForm form) {
		// TODO Auto-generated method stub
		if (form.getRole() < 1 || form.getRole() > 5) {
			errors.reject("role", "Role Error!");
		}
	}

	private void validateUserName(Errors errors, UserCreateForm form) {
    	if (userService.findByUserName(form.getUserName()) != null) {
            errors.reject("email.exists", "User with this email already exists");
        }
	}

	private void validatePasswords(Errors errors, UserCreateForm form) {
        if (!form.getPassword().equals(form.getConfirmPassword())) {
            errors.reject("password.no_match", "Passwords do not match");
        }
    }

    private void validateEmail(Errors errors, UserCreateForm form) {
        if (userService.findByEmail(form.getEmail()) != null) {
            errors.reject("email.exists", "User with this email already exists");
        }
    }
    
}