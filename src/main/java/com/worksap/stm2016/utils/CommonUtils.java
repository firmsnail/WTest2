package com.worksap.stm2016.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CommonUtils {
	
	final static public Long ROLE_HR_MANAGER = (long) 1;
	final static public Long ROLE_RECRUITER = (long) 2;
	final static public Long ROLE_CB_SPECIALIST = (long) 3;
	final static public Long ROLE_TEAM_MANAGER = (long) 4;
	final static public Long ROLE_SHORT_TERM_EMPLOYEE = (long) 5;
	
	final static public Integer REQUIREMENTS_ALL = 0;
	final static public Integer REQUIREMENTS_HR_MANAGER_PROCESSING = 1;
	final static public Integer REQUIREMENTS_RECRUITER_PROCESSING = 2;
	final static public Integer REQUIREMENTS_PENDING_RECRUITE = 3;
	final static public Integer REQUIREMENTS_RECRUITING = 4;
	final static public Integer REQUIREMENTS_FINISH = 5;
	final static public Integer REQUIREMENTS_REJECT = 6;
	
	final static public Integer PLAN_ALL = 0;
	final static public Integer PLAN_PENDING_VERIFY = 1;
	final static public Integer PLAN_VERIFIED = 2;
	final static public Integer PLAN_RECRUITING = 3;
	final static public Integer PLAN_FINISH = 4;
	final static public Integer PLAN_REJECT = 5;
	
	final static public Integer HIRE_ALL = 0;
	final static public Integer HIRE_RECRUITER_PROCESSING = 1;
	final static public Integer HIRE_HR_MANAGER_PROCESSING = 2;
	final static public Integer HIRE_FINISH = 3;
	final static public Integer HIRE_REJECT = 4;

	final static public Integer DISMISSION_ALL = 0;
	final static public Integer DISMISSION_TEAM_MANAGER_PROCESSING = 1;
	final static public Integer DISMISSION_CB_SPECIALIST_PROCESSING = 2;
	final static public Integer DISMISSION_HR_MANAGER_PROCESSING = 3;
	final static public Integer DISMISSION_FINISH = 4;
	final static public Integer DISMISSION_REJECT = 5;
	
	static public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = (PasswordEncoder) new BCryptPasswordEncoder();
		return encoder;
	}
}
