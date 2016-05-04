package com.worksap.stm2016.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CommonUtils {
	
	final static public Long ROLE_HR_MANAGER=(long) 1;
	final static public Long ROLE_RECRUITER=(long) 2;
	final static public Long ROLE_CB_SPECIALIST=(long) 3;
	final static public Long ROLE_TEAM_MANAGER=(long) 4;
	final static public Long ROLE_SHORT_TERM_EMPLOYEE=(long) 5;
	
	static public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = (PasswordEncoder) new BCryptPasswordEncoder();
		return encoder;
	}
}
