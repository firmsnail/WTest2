package com.worksap.stm2016.validator;

import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.worksap.stm2016.modelForm.UserUpdateForm;

@Component
public class UserUpdateFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(UserUpdateForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

    	UserUpdateForm form = (UserUpdateForm) target;

        validateEmail(errors, form);
        
        if ((form.getPassword() != null && form.getPassword().length() > 0) || 
        		(form.getConfirmPassword() != null && form.getConfirmPassword().length() > 0)) {
        	validatePassword(errors, form);
        }
    }

	private void validatePassword(Errors errors, UserUpdateForm form) {
		if (!form.getPassword().equals(form.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "confirmPassword", "Password doesn't match!");
		}
	}

	private void validateEmail(Errors errors, UserUpdateForm form) {
        if (form.getEmail() == null || form.getEmail().length() <= 0 ) {
        	errors.rejectValue("email", "email", "Please enter a email.");
        }
    }
    
}