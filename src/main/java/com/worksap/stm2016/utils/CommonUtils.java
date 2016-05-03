package com.worksap.stm2016.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CommonUtils {
	
	final static public Integer ROLE_HR_MANAGER=1;
	final static public Integer ROLE_RECRUITER=2;
	final static public Integer ROLE_CB_SPECIALIST=3;
	final static public Integer ROLE_TEAM_MANAGER=4;
	final static public Integer ROLE_SHORT_TERM_EMPLOYEE=5;
	
	static public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = (PasswordEncoder) new BCryptPasswordEncoder();
		return encoder;
	}
}
