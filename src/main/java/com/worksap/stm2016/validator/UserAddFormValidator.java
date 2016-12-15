package com.worksap.stm2016.validator;

import org.springframework.validation.Validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.modelForm.UserCreateForm;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.RoleService;
import com.worksap.stm2016.utils.CommonUtils;

@Component
public class UserAddFormValidator implements Validator {

	@Autowired
    private DepartmentService deptService;
	@Autowired
    private RoleService roleService;
	@Autowired
    private PersonService personService;

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
        validateUserName(errors, form);
        validatePassword(errors, form);
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

	private void validatePassword(Errors errors, UserCreateForm form) {
		if (CommonUtils.ContentRegex.matcher(form.getPassword()).matches()) {
			errors.rejectValue("password", "password", "Your behavior is dangerous, please don't attempt to attack the system.");
		} else if (!CommonUtils.FieldRegex.matcher(form.getPassword()).matches()) {
			errors.rejectValue("password", "password", "You can only enter underscore, numbers and letters.");
		}
	}

	private void validateUserName(Errors errors, UserCreateForm form) {
		if (CommonUtils.ContentRegex.matcher(form.getUserName()).matches()) {
			errors.rejectValue("userName", "userName", "Your behavior is dangerous, please don't attempt to attack the system.");
		} else if (!CommonUtils.FieldRegex.matcher(form.getUserName()).matches()) {
			errors.rejectValue("userName", "userName", "You can only enter underscore, numbers and letters.");
		}
	}

	private void validateDepartment(Errors errors, UserCreateForm form) {
		Department dept = deptService.findOne(form.getDepartmentId());
    	if (dept == null) {
    		errors.rejectValue("departmentId", "departmentId", "Please choose a correct department.");
    	} else if (dept.getManager() != null && form.getRole() == CommonUtils.ROLE_TEAM_MANAGER) {
    		errors.rejectValue("departmentId", "departmentId", "The department already has a manager!");
    	} else {
    		String roleName = "";
			Role hrmRole = roleService.findOne(CommonUtils.ROLE_HR_MANAGER);
			List<Person> pers = personService.findByRole(hrmRole);
			Person HRM = pers.get(0);
    		if (form.getRole() > 3) {
    			if (form.getRole() == CommonUtils.ROLE_TEAM_MANAGER) {
    				roleName = "Staffing Team Manager";
    			} else {
    				roleName = "Short-term Employee";
    			}
    			if (form.getDepartmentId() == HRM.getDepartment().getDepartmentId()) {
    				errors.rejectValue("departmentId", "departmentId", "The " + roleName + " can not be added to " + HRM.getDepartment().getDepartmentName() + "!");
    			}
    		} else {
    			if (form.getRole() == CommonUtils.ROLE_CB_SPECIALIST) {
    				roleName = "C&B Specialist";
    			} else {
    				roleName = "Recruiter";
    			}
    			if (form.getDepartmentId() != HRM.getDepartment().getDepartmentId()) {
    				errors.rejectValue("departmentId", "departmentId", "The " + roleName + " can not be added to " + dept.getDepartmentName() + "!");
    			}
    		}
    	}
	}

	private void validateEmail(Errors errors, UserCreateForm form) {
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (form.getEmail() == null || form.getEmail().length() <= 0 ) {
        	errors.rejectValue("email", "email", "Please enter a email.");
        } else if (personService.findByEmail(form.getEmail()).getPersonId() != curUser.getId()) {
        	errors.rejectValue("email", "email", "User with this email already exists.");
        }
    }
    
}