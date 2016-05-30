package com.worksap.stm2016.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.RoleService;
import com.worksap.stm2016.service.SkillService;

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
	final static public Integer HIRE_RECRUITER_REJECT = 4;
	final static public Integer HIRE_HR_MANAGER_REJECT = 5;
	
	final static public Integer APPLY_PENDING_FILTER = 1;
	final static public Integer APPLY_PASS_FILTER = 2;
	final static public Integer APPLY_FAILED = 3;
	final static public Integer APPLY_CHOOSED = 4;
	
	final static public Integer INTERVIEW_PENDING_SCHEDULE = 1;
	final static public Integer INTERVIEW_INTERVIEWING = 2;
	final static public Integer INTERVIEW_FAILED = 3;
	final static public Integer INTERVIEW_PASSED = 4;

	final static public Integer DISMISSION_ALL = 0;
	final static public Integer DISMISSION_TEAM_MANAGER_PROCESSING = 1;
	final static public Integer DISMISSION_HR_MANAGER_PROCESSING = 2;
	final static public Integer DISMISSION_CB_SPECIALIST_PROCESSING = 3;
	final static public Integer DISMISSION_FINISH = 4;
	final static public Integer DISMISSION_TEAM_MANAGER_REJECT = 5;
	final static public Integer DISMISSION_HR_MANAGER_REJECT = 6;
	
	final static public Integer EMPLOYEE_REGISTERED = 0;
	final static public Integer EMPLOYEE_CANDIDATE = 1;
	final static public Integer EMPLOYEE_WORKING = 2;
	final static public Integer EMPLOYEE_DISMISSION = 3;
	
	final static public Integer ATTENDANCE_NORMAL = 1;
	final static public Integer ATTENDANCE_ATTEND_LATE = 2;
	final static public Integer ATTENDANCE_LEAVE_EARLY = 3;
	final static public Integer ATTENDANCE_ATTEND_NOT_RECORD = 4;
	final static public Integer ATTENDANCE_LEAVE_NOT_RECORD = 5;
	
	final static public Integer LEAVE_TEAM_MANAGER_PROCESSING = 1;
	final static public Integer LEAVE_CB_SPECIALIST_PROCESSING = 2;
	final static public Integer LEAVE_FINISH = 3;
	final static public Integer LEAVE_REJECT = 4;
	
	final static public Integer NOTIFICATION_TYPE_REQUIREMENT = 1;
	final static public Integer NOTIFICATION_TYPE_PLAN = 2;
	final static public Integer NOTIFICATION_TYPE_HIRE = 3;
	final static public Integer NOTIFICATION_TYPE_DISMISSION = 4;
	final static public Integer NOTIFICATION_TYPE_LEAVE = 5;
	final static public Integer NOTIFICATION_TYPE_INTERVIEW = 6;
	
	final static public Integer NOTIFICATION_URGENCY_LOW = 1;
	final static public Integer NOTIFICATION_URGENCY_MIDDLE = 2;
	final static public Integer NOTIFICATION_URGENCY_HIGH = 3;
	
	final static public Integer NOTIFICATION_STATUS_UNREAD = 1;
	final static public Integer NOTIFICATION_STATUS_READ = 2;
	
	final static public Integer MAX_PERIOD = 6;
	
	final static public Integer PERIOD_UNKNOWN = 0;
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
	
	final static public Pattern ContentRegex = Pattern.compile("[\\w\\W]*<script[\\w\\W]*>[\\w\\W]*</script>[\\w\\W]*");
	final static public Pattern FieldRegex = Pattern.compile("\\w+");
	final static public Pattern PhoneRegex = Pattern.compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");//Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");//
	static public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = (PasswordEncoder) new BCryptPasswordEncoder();
		return encoder;
	}

	static public List<Integer> getKeysByPeriod() {
		List<Integer> keys = new ArrayList<Integer>();
		keys.add(PERIOD_UNKNOWN);
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
		for (int i = 0; i < GENDERS; ++i) {
			genders.add(i);
		}
		return genders;
	}

	static public List<Integer> getKeysByAge() {
		List<Integer> ranges = new ArrayList<Integer>();
		for (int i = 0; i <= AGE_RANGES; ++i) {
			ranges.add(i);
		}
		return ranges;
	}

	static public List<Department> getKeysByDepartment(DepartmentService departmentService, RoleService roleService, PersonService personService) {
		Role hrManagerRole = roleService.findOne(CommonUtils.ROLE_HR_MANAGER);
		List<Person> pers = personService.findByRole(hrManagerRole);
		return departmentService.findByManagerIsNot(pers.get(0));
	}

	public static boolean RequirementContainSkills(StaffRequirement requirement, List<Skill> skills) {
		List<Skill> reqSkills = requirement.getStfrqSkillList();
		for (Skill skill : skills) {
			if (!reqSkills.contains(skill)) {
				return false;
			}
		}
		return true;
	}

	public static Date OneDayBefore(Date startDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		Integer day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day-1);
		return c.getTime();
	}
	
	public static Date OneDayAfter(Date afterDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(afterDate);
		Integer day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day+1);
		return c.getTime();
	}
	
	public static Date OneWeekAfter(Date afterDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(afterDate);
		Integer day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day+7);
		return c.getTime();
	}
	
	public static Date OneMonthAfter(Date afterDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(afterDate);
		c.add(Calendar.MONTH, 1);
		return c.getTime();
	}
	
	public static Date kMonthAfter(Date afterDate, Integer k) {
		Calendar c = Calendar.getInstance();
		c.setTime(afterDate);
		c.add(Calendar.MONTH, k);
		return c.getTime();
	}
	
	public static Date OneMonthBefore(Date beforeDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(beforeDate);
		c.add(Calendar.MONTH, -1);
		return c.getTime();
	}
	
	public static Date kMonthBefore(Date beforeDate, Integer k) {
		Calendar c = Calendar.getInstance();
		c.setTime(beforeDate);
		c.add(Calendar.MONTH, -k);
		return c.getTime();
	}

	public static boolean RequirementContainSkills(StaffRequirement requirement, List<Long> skills, SkillService skillService) {
		List<Skill> reqSkills = requirement.getStfrqSkillList();
		for (Long skillId : skills) {
			Skill skill = skillService.findOne(skillId);
			if (!reqSkills.contains(skill)) {
				return false;
			}
		}
		return true;
	}

	public static List<Department> extractDepartmentsByRequirements(List<StaffRequirement> staffingRequirements, DepartmentService departmentService) {
		Set<Long> depIdSet = new HashSet<Long>();
		for (StaffRequirement requirement : staffingRequirements) {
			depIdSet.add(requirement.getStfrqDepartment().getDepartmentId());
		}
		List<Department> departments = new ArrayList<Department>();
		for (Long depId : depIdSet) {
			departments.add(departmentService.findOne(depId));
		}
		return departments;
	}

	public static List<Skill> extractSkillsByRequirements(List<StaffRequirement> staffingRequirements, SkillService skillService) {
		Set<Long> skillIdSet = new HashSet<Long>();
		for (StaffRequirement requirement : staffingRequirements) {
			for (Skill skill : requirement.getStfrqSkillList()) {
				skillIdSet.add(skill.getSkillId());
			}
		}
		List<Skill> skills = new ArrayList<Skill>();
		for (Long skillId : skillIdSet) {
			skills.add(skillService.findOne(skillId));
		}
		return skills;
	}

	public static Date getDayStartTime(Date attendanceDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(attendanceDate);
		c.set(Calendar.HOUR, 9);
		c.set(Calendar.MINUTE, 30);
		return c.getTime();
	}

	public static Date getDayEndTime(Date attendanceDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(attendanceDate);
		c.set(Calendar.HOUR, 18);
		c.set(Calendar.MINUTE, 30);
		return c.getTime();
	}
}
