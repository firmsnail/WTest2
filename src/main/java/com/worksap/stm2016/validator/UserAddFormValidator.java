package com.worksap.stm2016.validator;

import org.springframework.validation.Validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.worksap.stm2016.modelForm.UserCreateForm;

@Component
public class UserAddFormValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserCreateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserCreateForm form = (UserCreateForm) target;

        validateEmail(errors, form);
    }

	private void validateEmail(Errors errors, UserCreateForm form) {
        if (form.getEmail() == null || form.getEmail().length() <= 0 ) {
        	errors.rejectValue("email", "email", "Please enter a email.");
        }
    }
    
}