package com.worksap.stm2016.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.SkillService;

@Component
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
	
	final static public Integer EMPLOYEE_REGISTERED = 0;
	final static public Integer EMPLOYEE_CANDIDATE = 1;
	final static public Integer EMPLOYEE_WORKING = 2;
	final static public Integer EMPLOYEE_DISMISSION = 3;
	
	final static public Integer MAX_PERIOD = 6;
	
	final static public Integer PERIOD_1MONTH = 1;
	final static public Integer PERIOD_2MONTH = 2;
	final static public Integer PERIOD_3MONTH = 3;
	final static public Integer PERIOD_4MONTH = 4;
	final static public Integer PERIOD_5MONTH = 5;
	final static public Integer PERIOD_6MONTH = 6;
	
	final static public Integer GENDERS = 3;
	
	final static public Integer GENDER_UNKNOWN = 0;
	final static public Integer GENDER_MALE = 1;
	final static public Integer GENDER_FEMALE = 2;
	
	final static public Integer AGE_RANGES = 6;
	
	final static public Integer AGE_MIN = 0;
	final static public Integer AGE_MAX = 10000000;
	
	final static public Integer AGE_UNKNOWN_RANGE = 0;
	final static public Integer AGE_1stRANGE = 1;		//18 ~ 25
	final static public Integer AGE_2ndRANGE = 2;		//26 ~ 30
	final static public Integer AGE_3rdRANGE = 3;		//31 ~ 35
	final static public Integer AGE_4thRANGE = 4;		//36 ~ 40
	final static public Integer AGE_5thRANGE = 5;		//41 ~ 50
	final static public Integer AGE_6thRANGE = 6;		//51 ~ 60
	
	final static public Integer AGE_1stRANGE_MIN = 18;
	final static public Integer AGE_1stRANGE_MAX = 25;
	final static public Integer AGE_2ndRANGE_MIN = 26;
	final static public Integer AGE_2ndRANGE_MAX = 30;
	final static public Integer AGE_3rdRANGE_MIN = 31;
	final static public Integer AGE_3rdRANGE_MAX = 35;
	final static public Integer AGE_4thRANGE_MIN = 36;
	final static public Integer AGE_4thRANGE_MAX = 40;
	final static public Integer AGE_5thRANGE_MIN = 41;
	final static public Integer AGE_5thRANGE_MAX = 50;
	final static public Integer AGE_6thRANGE_MIN = 51;
	final static public Integer AGE_6thRANGE_MAX = 60;
	
	static public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = (PasswordEncoder) new BCryptPasswordEncoder();
		return encoder;
	}

	static public List<Integer> getKeysByPeriod() {
		List<Integer> keys = new ArrayList<Integer>();
		for (int i = 1; i <= MAX_PERIOD; ++i) {
			keys.add(i);
		}
		return keys;
	}

	static public List<Skill> getKeysBySkill(SkillService skillService) {
		return skillService.findAll();
	}

	static public List<Integer> getKeysByGender() {
		List<Integer> genders = new ArrayList<Integer>();
		for (int i = 1; i <= GENDERS; ++i) {
			genders.add(i);
		}
		return genders;
	}

	static public List<Integer> getKeysByAge() {
		List<Integer> ranges = new ArrayList<Integer>();
		for (int i = 1; i <= AGE_RANGES; ++i) {
			ranges.add(i);
		}
		return ranges;
	}

	static public List<Department> getKeysByDepartment(DepartmentService departmentService) {
		if (departmentService == null) {
			System.out.println("NULLLLLLLLLLLLLLL");
		} else {
			System.out.println("Not NuLL!");
		}
		return departmentService.findAll();
	}
}
