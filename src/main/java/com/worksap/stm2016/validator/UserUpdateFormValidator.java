package com.worksap.stm2016.validator;

import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.worksap.stm2016.modelForm.UserUpdateForm;
import com.worksap.stm2016.utils.CommonUtils;

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
        
       if (form.getAddress() != null && form.getAddress().length() > 0) {
    	   validateAddress(errors, form);
       }
       if (form.getPhone() != null && form.getPhone().length() > 0) {
    	   validatePhone(errors, form);
       }
       validateFirstName(errors, form);
       validateLastName(errors, form);
        
    }

    private void validateLastName(Errors errors, UserUpdateForm form) {
		if (CommonUtils.ContentRegex.matcher(form.getLastName()).matches()) {
			errors.rejectValue("lastName", "lastName", "Your behavior is dangerous, please don't attempt to attack the system.");
		} else if (!CommonUtils.FieldRegex.matcher(form.getLastName()).matches()) {
			errors.rejectValue("lastName", "lastName", "You can only enter numbers and letters.");
		}
	}

	private void validateFirstName(Errors errors, UserUpdateForm form) {
		if (CommonUtils.ContentRegex.matcher(form.getFirstName()).matches()) {
			errors.rejectValue("firstName", "firstName", "Your behavior is dangerous, please don't attempt to attack the system.");
		} else if (!CommonUtils.FieldRegex.matcher(form.getFirstName()).matches()) {
			errors.rejectValue("firstName", "firstName", "You can only enter numbers and letters.");
		}
	}
    
	private void validatePhone(Errors errors, UserUpdateForm form) {
		
		if (form.getPhone() != null && form.getPhone().length() > 0 && !CommonUtils.PhoneRegex.matcher(form.getPhone()).matches()) {
			errors.rejectValue("phone", "phone", "Please enter valid phone number.");
		}
	}

	private void validateAddress(Errors errors, UserUpdateForm form) {
		if (CommonUtils.ContentRegex.matcher(form.getAddress()).matches()) {
			errors.rejectValue("address", "address", "Your behavior is dangerous, please don't attempt to attack the system.");
		}
	}

	private void validatePassword(Errors errors, UserUpdateForm form) {
		if (!form.getPassword().equals(form.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "confirmPassword", "Password doesn't match!");
		} else {
			if (!CommonUtils.ContentRegex.matcher(form.getPassword()).matches()) {
    			errors.rejectValue("password", "password", "Your behavior is dangerous, please don't attempt to attack the system.");
    		} else if (!CommonUtils.FieldRegex.matcher(form.getPassword()).matches()) {
    			errors.rejectValue("password", "password", "You can only enter numbers and letters.");
    		}
        	
        	if (!CommonUtils.ContentRegex.matcher(form.getConfirmPassword()).matches()) {
    			errors.rejectValue("confirmPassword", "confirmPassword", "Your behavior is dangerous, please don't attempt to attack the system.");
    		} else if (!CommonUtils.FieldRegex.matcher(form.getConfirmPassword()).matches()) {
    			errors.rejectValue("confirmPassword", "confirmPassword", "You can only enter numbers and letters.");
    		}
		}
	}

	private void validateEmail(Errors errors, UserUpdateForm form) {
        if (form.getEmail() == null || form.getEmail().length() <= 0 ) {
        	errors.rejectValue("email", "email", "Please enter a email.");
        }
    }
    
}