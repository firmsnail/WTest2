package com.worksap.stm2016.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.worksap.stm2016.modelForm.DepartmentForm;
import com.worksap.stm2016.modelForm.UserCreateForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worksap.stm2016.chartData.PieData;
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Dismission;
import com.worksap.stm2016.model.Hire;
import com.worksap.stm2016.model.Notification;
import com.worksap.stm2016.model.Payroll;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.RecruitingPlan;
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.DismissionService;
import com.worksap.stm2016.service.HireService;
import com.worksap.stm2016.service.NotificationService;
import com.worksap.stm2016.service.PayrollService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.RecruitingPlanService;
import com.worksap.stm2016.service.RoleService;
import com.worksap.stm2016.service.SkillService;
import com.worksap.stm2016.service.StaffRequirementService;
import com.worksap.stm2016.utils.CommonUtils;
import com.worksap.stm2016.validator.DepartmentFormValidator;
import com.worksap.stm2016.validator.UserAddFormValidator;
import com.worksap.stm2016.validator.UserCreateFormValidator;

@Controller
@PreAuthorize("hasAuthority('HR-MANAGER')")
@RequestMapping(value = "/hr-manager")
public class HRManagerController {
	
	@Autowired
	private SkillService skillService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private PersonService personService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private StaffRequirementService staffRequirementService;
	@Autowired
	private RecruitingPlanService recruitingPlanService;
	@Autowired
	private HireService hireService;
	@Autowired
	private DismissionService dismissionService;
	@Autowired
	private PayrollService payrollService;
	@Autowired
	private NotificationService notificationService;

	@Autowired
	private UserCreateFormValidator  userCreateFormValidator;
	@Autowired
	private UserAddFormValidator  userAddFormValidator;
	@Autowired
	private DepartmentFormValidator  departmentFormValidator;
	
	@RequestMapping(value = "/analyzeEmployeeStructure",  method = RequestMethod.GET)
	public String analyzeEmployeeStructure(Model model) {
		/*
		analyzeEmployeeByDepartment(model);
		analyzeEmployeeByAge(model);
		analyzeEmployeeByGender(model);
		analyzeEmployeeBySkill(model);
		analyzeEmployeeByPeriod(model);
		*/
		return "hr-manager/analyzeEmployeeStructure";
	}
	
	@RequestMapping(value = "/analyzePayrollStructure",  method = RequestMethod.GET)
	public String analyzePayrollStructure(Model model) {
		/*
		analyzeEmployeeByDepartment(model);
		analyzeEmployeeByAge(model);
		analyzeEmployeeByGender(model);
		analyzeEmployeeBySkill(model);
		analyzeEmployeeByPeriod(model);
		*/
		return "hr-manager/analyzePayrollStructure";
	}
	
	@RequestMapping(value={"/analyzeEmployeeByPeriod"}, method = RequestMethod.GET)
	@ResponseBody
	public String analyzeEmployeeByPeriod(Model model) throws JsonProcessingException {
		List<PieData> data = new ArrayList<PieData>();
		List<Integer> keyList = CommonUtils.getKeysByPeriod();
		model.addAttribute("periodKeys", keyList);
		List<List<Person> > employeesByPeriod = new ArrayList<List<Person> >();
		for (int i = 0; i < keyList.size(); ++i) {
			employeesByPeriod.add(personService.findByPeriod(keyList.get(i)));
		}
		for (int i = 0; i < keyList.size(); ++i) {
			PieData oneData = new PieData();
			if (keyList.get(i) == 1) {
				oneData.setName("OneMonth");
			} else if (keyList.get(i) == 2) {
				oneData.setName("TwoMonths");
			} else if (keyList.get(i) == 3) {
				oneData.setName("ThreeMonths");
			} else if (keyList.get(i) == 4) {
				oneData.setName("FourMonths");
			} else if (keyList.get(i) == 5) {
				oneData.setName("FiveMonths");
			} else if (keyList.get(i) == 6) {
				oneData.setName("SixMonths");
			} else {
				oneData.setName("Unknown");
			}
			oneData.setY(employeesByPeriod.get(i).size()+0.0);
			data.add(oneData);
		}
		ObjectMapper mapper = new ObjectMapper();  
		String json = mapper.writeValueAsString(data);
		System.out.println("json: " + json);
		//model.addAttribute("employeesByPeriod", employeesByPeriod);
		return json;
	}

	@RequestMapping(value={"/analyzeEmployeeBySkill"}, method = RequestMethod.GET)
	@ResponseBody
	public String analyzeEmployeeBySkill(Model model) throws JsonProcessingException {
		List<PieData> data = new ArrayList<PieData>();
		List<Skill> keyList = CommonUtils.getKeysBySkill(skillService);
		if (keyList != null) {
			model.addAttribute("skillKeys", keyList);
			List<List<Person> > employeesBySkill = new ArrayList<List<Person> >();
			for (int i = 0; i < keyList.size(); ++i) {
				employeesBySkill.add(personService.findBySkill(keyList.get(i)));
				
				PieData oneData = new PieData();
				oneData.setName(keyList.get(i).getSkillName());
				oneData.setY(employeesBySkill.get(i).size()+0.0);
				data.add(oneData);
			}
			PieData oneData = new PieData();
			oneData.setName("Unknown");
			oneData.setY(personService.findBySkill(null).size()+0.0);
			data.add(oneData);
			//model.addAttribute("employeesBySkill", employeesBySkill);
		}
		ObjectMapper mapper = new ObjectMapper();  
		String json = mapper.writeValueAsString(data);
		System.out.println("json: " + json);
		return json;
	}

	@RequestMapping(value={"/analyzeEmployeeByGender"}, method = RequestMethod.GET)
	@ResponseBody
	public String analyzeEmployeeByGender(Model model) throws JsonProcessingException {
		List<PieData> data = new ArrayList<PieData>();
		List<Integer> keyList = CommonUtils.getKeysByGender();
		model.addAttribute("genderKeys", keyList);
		List<List<Person> > employeesByGender = new ArrayList<List<Person> >();
		for (int i = 0; i < keyList.size(); ++i) {
			employeesByGender.add(personService.findByGender(keyList.get(i)));
			PieData oneData = new PieData();
			if (keyList.get(i) == CommonUtils.GENDER_FEMALE) {
				oneData.setName("Female");
			} else if (keyList.get(i) == CommonUtils.GENDER_MALE) {
				oneData.setName("Male");
			} else {
				oneData.setName("Unknown");
			}
			oneData.setY(employeesByGender.get(i).size()+0.0);
			data.add(oneData);
		}
		//model.addAttribute("employeesByGender", employeesByGender);
		ObjectMapper mapper = new ObjectMapper();  
		String json = mapper.writeValueAsString(data);
		System.out.println("json: " + json);
		return json;
	}

	//TODO improve it.
	@RequestMapping(value={"/analyzeEmployeeByAge"}, method = RequestMethod.GET)
	@ResponseBody
	public String analyzeEmployeeByAge(Model model) throws JsonProcessingException {
		List<PieData> data = new ArrayList<PieData>();
		List<Integer> keyList = CommonUtils.getKeysByAge();
		model.addAttribute("ageKeys", keyList);
		List<List<Person> > employeesByAge = new ArrayList<List<Person> >();
		for (int i = 0; i < keyList.size(); ++i) {
			employeesByAge.add(personService.findByAgeRange(keyList.get(i)));
			
			PieData oneData = new PieData();
			if (keyList.get(i) == CommonUtils.AGE_1stRANGE) {
				oneData.setName(CommonUtils.AGE_1stRANGE_MIN+"~"+CommonUtils.AGE_1stRANGE_MAX);
			} else if (keyList.get(i) == CommonUtils.AGE_2ndRANGE) {
				oneData.setName(CommonUtils.AGE_2ndRANGE_MIN+"~"+CommonUtils.AGE_2ndRANGE_MAX);
			} else if (keyList.get(i) == CommonUtils.AGE_3rdRANGE) {
				oneData.setName(CommonUtils.AGE_3rdRANGE_MIN+"~"+CommonUtils.AGE_3rdRANGE_MAX);
			} else if (keyList.get(i) == CommonUtils.AGE_4thRANGE) {
				oneData.setName(CommonUtils.AGE_4thRANGE_MIN+"~"+CommonUtils.AGE_4thRANGE_MAX);
			} else if (keyList.get(i) == CommonUtils.AGE_5thRANGE) {
				oneData.setName(CommonUtils.AGE_5thRANGE_MIN+"~"+CommonUtils.AGE_5thRANGE_MAX);
			} else if (keyList.get(i) == CommonUtils.AGE_6thRANGE) {
				oneData.setName(CommonUtils.AGE_6thRANGE_MIN+"~"+CommonUtils.AGE_6thRANGE_MAX);
			} else {
				oneData.setName("Unknown");
			}
			oneData.setY(employeesByAge.get(i).size()+0.0);
			
			data.add(oneData);
		}
		//model.addAttribute("employeesByAge", employeesByAge);
		ObjectMapper mapper = new ObjectMapper();  
		String json = mapper.writeValueAsString(data);
		System.out.println("json: " + json);
		return json;
	}

	@RequestMapping(value={"/analyzeEmployeeByDepartment"}, method = RequestMethod.GET)
	@ResponseBody
	public String analyzeEmployeeByDepartment(Model model) throws JsonProcessingException {
		List<PieData> data = new ArrayList<PieData>();
		List<Department> keyList = CommonUtils.getKeysByDepartment(departmentService);
		List<List<Person> > employeesByDepartment = new ArrayList<List<Person> >();
		if (keyList != null) {
			model.addAttribute("ageKeys", keyList);
			for (int i = 0; i < keyList.size(); ++i) {
				employeesByDepartment.add(personService.findByDepartment(keyList.get(i)));
				
				PieData oneData = new PieData();
				oneData.setName(keyList.get(i).getDepartmentName());
				oneData.setY(employeesByDepartment.get(i).size()+0.0);
				
				data.add(oneData);
			}
			//model.addAttribute("employeesByDepartment", employeesByDepartment);
		}
		ObjectMapper mapper = new ObjectMapper();  
		String json = mapper.writeValueAsString(data);
		System.out.println("json: " + json);
		return json;
	}
	
	
	@RequestMapping(value={"/analyzePayrollByPeriod"}, method = RequestMethod.GET)
	@ResponseBody
	public String analyzePayrollByPeriod(Model model) throws JsonProcessingException {
		List<PieData> data = new ArrayList<PieData>();
		List<Integer> keyList = CommonUtils.getKeysByPeriod();
		model.addAttribute("periodKeys", keyList);
		List<List<Payroll> > payrollByPeriod = new ArrayList<List<Payroll> >();
		for (int i = 0; i < keyList.size(); ++i) {
			payrollByPeriod.add(payrollService.findByPayrollEmployeePeriod(keyList.get(i)));
			//employeesByPeriod.add(personService.findByPeriod(keyList.get(i)));
		}
		for (int i = 0; i < keyList.size(); ++i) {
			PieData oneData = new PieData();
			if (keyList.get(i) == 1) {
				oneData.setName("OneMonth");
			} else if (keyList.get(i) == 2) {
				oneData.setName("TwoMonths");
			} else if (keyList.get(i) == 3) {
				oneData.setName("ThreeMonths");
			} else if (keyList.get(i) == 4) {
				oneData.setName("FourMonths");
			} else if (keyList.get(i) == 5) {
				oneData.setName("FiveMonths");
			} else if (keyList.get(i) == 6) {
				oneData.setName("SixMonths");
			}
			Double total = 0.0;
			for (Payroll payroll : payrollByPeriod.get(i)) {
				total += payroll.getAmount();
			}
			oneData.setY(total);
			data.add(oneData);
		}
		ObjectMapper mapper = new ObjectMapper();  
		String json = mapper.writeValueAsString(data);
		System.out.println("json: " + json);
		//model.addAttribute("employeesByPeriod", employeesByPeriod);
		return json;
	}

	@RequestMapping(value={"/analyzePayrollBySkill"}, method = RequestMethod.GET)
	@ResponseBody
	public String analyzePayrollBySkill(Model model) throws JsonProcessingException {
		List<PieData> data = new ArrayList<PieData>();
		List<Skill> keyList = CommonUtils.getKeysBySkill(skillService);
		if (keyList != null) {
			model.addAttribute("skillKeys", keyList);
			//List<List<Person> > employeesBySkill = new ArrayList<List<Person> >();
			List<List<Payroll> > payrollBySkill = new ArrayList<List<Payroll> >();
			for (int i = 0; i < keyList.size(); ++i) {
				//employeesBySkill.add(personService.findBySkill(keyList.get(i)));
				payrollBySkill.add(payrollService.findByEmployeeSkill(keyList.get(i))); 
				
				PieData oneData = new PieData();
				oneData.setName(keyList.get(i).getSkillName());
				Double total = 0.0;
				for (Payroll payroll : payrollBySkill.get(i)) {
					total += payroll.getAmount();
				}
				oneData.setY(total);
				data.add(oneData);
			}
			//model.addAttribute("employeesBySkill", employeesBySkill);
		}
		ObjectMapper mapper = new ObjectMapper();  
		String json = mapper.writeValueAsString(data);
		System.out.println("json: " + json);
		return json;
	}

	@RequestMapping(value={"/analyzePayrollByGender"}, method = RequestMethod.GET)
	@ResponseBody
	public String analyzePayrollByGender(Model model) throws JsonProcessingException {
		List<PieData> data = new ArrayList<PieData>();
		List<Integer> keyList = CommonUtils.getKeysByGender();
		model.addAttribute("genderKeys", keyList);
		//List<List<Person> > employeesByGender = new ArrayList<List<Person> >();
		List<List<Payroll> > payrollsByGender = new ArrayList<List<Payroll> >();
		for (int i = 0; i < keyList.size(); ++i) {
			payrollsByGender.add(payrollService.findByPayrollEmployeeGender(keyList.get(i)));
			//employeesByGender.add(personService.findByGender(keyList.get(i)));
			PieData oneData = new PieData();
			if (keyList.get(i) == CommonUtils.GENDER_FEMALE) {
				oneData.setName("Female");
			} else if (keyList.get(i) == CommonUtils.GENDER_MALE) {
				oneData.setName("Male");
			} else {
				oneData.setName("Unknown");
			}
			Double total = 0.0;
			for (Payroll payroll : payrollsByGender.get(i)) {
				total += payroll.getAmount();
			}
			oneData.setY(total);
			data.add(oneData);
		}
		//model.addAttribute("employeesByGender", employeesByGender);
		ObjectMapper mapper = new ObjectMapper();  
		String json = mapper.writeValueAsString(data);
		System.out.println("json: " + json);
		return json;
	}

	@RequestMapping(value={"/analyzePayrollByAge"}, method = RequestMethod.GET)
	@ResponseBody
	public String analyzePayrollByAge(Model model) throws JsonProcessingException {
		List<PieData> data = new ArrayList<PieData>();
		List<Integer> keyList = CommonUtils.getKeysByAge();
		model.addAttribute("ageKeys", keyList);
		//List<List<Person> > employeesByAge = new ArrayList<List<Person> >();
		List<List<Payroll> > payrollsByAge = new ArrayList<List<Payroll> >();
		for (int i = 0; i < keyList.size(); ++i) {
			payrollsByAge.add(payrollService.findByPayrollEmployeeAgeRange(keyList.get(i)));
			
			PieData oneData = new PieData();
			if (keyList.get(i) == CommonUtils.AGE_1stRANGE) {
				oneData.setName(CommonUtils.AGE_1stRANGE_MIN+"~"+CommonUtils.AGE_1stRANGE_MAX);
			} else if (keyList.get(i) == CommonUtils.AGE_2ndRANGE) {
				oneData.setName(CommonUtils.AGE_2ndRANGE_MIN+"~"+CommonUtils.AGE_2ndRANGE_MAX);
			} else if (keyList.get(i) == CommonUtils.AGE_3rdRANGE) {
				oneData.setName(CommonUtils.AGE_3rdRANGE_MIN+"~"+CommonUtils.AGE_3rdRANGE_MAX);
			} else if (keyList.get(i) == CommonUtils.AGE_4thRANGE) {
				oneData.setName(CommonUtils.AGE_4thRANGE_MIN+"~"+CommonUtils.AGE_4thRANGE_MAX);
			} else if (keyList.get(i) == CommonUtils.AGE_5thRANGE) {
				oneData.setName(CommonUtils.AGE_5thRANGE_MIN+"~"+CommonUtils.AGE_5thRANGE_MAX);
			} else if (keyList.get(i) == CommonUtils.AGE_6thRANGE) {
				oneData.setName(CommonUtils.AGE_6thRANGE_MIN+"~"+CommonUtils.AGE_6thRANGE_MAX);
			} else {
				oneData.setName("Unknown");
			}
			
			Double total = 0.0;
			for (Payroll payroll : payrollsByAge.get(i)) {
				total += payroll.getAmount();
			}
			oneData.setY(total);
			
			data.add(oneData);
		}
		//model.addAttribute("employeesByAge", employeesByAge);
		ObjectMapper mapper = new ObjectMapper();  
		String json = mapper.writeValueAsString(data);
		System.out.println("json: " + json);
		return json;
	}

	@RequestMapping(value={"/analyzePayrollByDepartment"}, method = RequestMethod.GET)
	@ResponseBody
	public String analyzePayrollByDepartment(Model model) throws JsonProcessingException {
		List<PieData> data = new ArrayList<PieData>();
		List<Department> keyList = CommonUtils.getKeysByDepartment(departmentService);
		List<List<Payroll> > payrollsByDepartment = new ArrayList<List<Payroll> >();
		//List<List<Person> > employeesByDepartment = new ArrayList<List<Person> >();
		if (keyList != null) {
			model.addAttribute("ageKeys", keyList);
			for (int i = 0; i < keyList.size(); ++i) {
				payrollsByDepartment.add(payrollService.findByPayrollEmployeeDepartment(keyList.get(i)));
				//employeesByDepartment.add(personService.findByDepartment(keyList.get(i)));
				
				PieData oneData = new PieData();
				oneData.setName(keyList.get(i).getDepartmentName());
				Double total = 0.0;
				for (Payroll payroll : payrollsByDepartment.get(i)) {
					total += payroll.getAmount();
				}
				oneData.setY(total);
				
				data.add(oneData);
			}
			//model.addAttribute("employeesByDepartment", employeesByDepartment);
		}
		ObjectMapper mapper = new ObjectMapper();  
		String json = mapper.writeValueAsString(data);
		System.out.println("json: " + json);
		return json;
	}
	

	@RequestMapping(value = "/aprroveOneRequirement")
	public String aprroveOneRequirement(Long requirementId, Model model) {
		StaffRequirement requirement = staffRequirementService.findOne(requirementId);
		Role recruiterRole = roleService.findOne(CommonUtils.ROLE_RECRUITER);
		List<Person> recruiters = personService.findByRole(recruiterRole);
		System.out.println("recruiter.size: " + recruiters.size());
		if (requirement != null && recruiters != null && recruiters.size() > 0) {
			Random rand = new Random();
			Integer indx = rand.nextInt(recruiters.size());
			Person recruiter = recruiters.get(indx);
			requirement.setStatus(CommonUtils.REQUIREMENTS_RECRUITER_PROCESSING);
			requirement.setRecruiter(recruiter);
			requirement = staffRequirementService.findOne(requirementId);
			
			Notification notification = new Notification();
			notification.setOwner(recruiter);
			notification.setContent("You have a staffing requirement need to process!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_REQUIREMENT);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_MIDDLE);
			notification.setUrl("/requirement/showStaffRequirements");
			notification = notificationService.save(notification);
			
		}
		return "redirect:/requirement/showStaffRequirements";
	}
	
	@RequestMapping(value = "/rejectOneRequirement")
	public String rejectOneRequirement(Long requirementId, Model model) {
		StaffRequirement requirement = staffRequirementService.findOne(requirementId);
		if (requirement != null) {
			requirement.setStatus(CommonUtils.REQUIREMENTS_REJECT);
			requirement = staffRequirementService.findOne(requirementId);
			
			Notification notification = new Notification();
			notification.setOwner(requirement.getStfrqDepartment().getManager());
			notification.setContent("Your requirement has been rejected!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_REQUIREMENT);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_LOW);
			notification.setUrl("/requirement/showStaffRequirements");
			notification = notificationService.save(notification);
		}
		return "redirect:/requirement/showStaffRequirements";
	}
	
	@RequestMapping(value = "/aprroveOnePlan")
	public String aprroveOnePlan(Long planId, Model model) {
		RecruitingPlan plan = recruitingPlanService.findOne(planId);
		if (plan != null) {
			plan.setStatus(CommonUtils.PLAN_VERIFIED);
			plan = recruitingPlanService.findOne(planId);
			
			Notification notification = new Notification();
			notification.setOwner(plan.getPlanMaker());
			notification.setContent("Your recruiting plan has been approved!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_PLAN);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_MIDDLE);
			notification.setUrl("/plan/showRecruitingPlans");
			notification = notificationService.save(notification);
		}
		return "redirect:/plan/showRecruitingPlans";
	}
	
	@RequestMapping(value = "/rejectOnePlan")
	public String rejectOnePlan(Long planId, Model model) {
		RecruitingPlan plan = recruitingPlanService.findOne(planId);
		if (plan != null) {
			plan.setStatus(CommonUtils.PLAN_REJECT);
			plan = recruitingPlanService.findOne(planId);
			
			Notification notification = new Notification();
			notification.setOwner(plan.getPlanMaker());
			notification.setContent("Your recruiting plan has been rejected!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_PLAN);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_LOW);
			notification.setUrl("/plan/showRecruitingPlans");
			notification = notificationService.save(notification);
		}
		return "redirect:/plan/showRecruitingPlans";
	}
	
	@RequestMapping(value = "/aprroveOneHire")
	public String aprroveOneHire(Long hireId, Model model) {
		Hire hire = hireService.findOne(hireId);
		//CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (hire != null) {
			hire.setStatus(CommonUtils.HIRE_FINISH);
			Date now = new Date();
			hire.setHireDate(now);
			RecruitingPlan plan = hire.getHirePlan();
			plan.setHiredNum(plan.getHiredNum()+1);
			if (plan.getHiredNum().equals(plan.getPlanNum())) {
				plan.setStatus(CommonUtils.PLAN_FINISH);
			}
			plan = recruitingPlanService.findOne(plan.getPlanId());
			StaffRequirement requirement = hire.getRequirementForHire();
			requirement.setHiredNum(requirement.getHiredNum()+1);
			if (requirement.getHiredNum().equals(requirement.getRequireNum())) {
				requirement.setStatus(CommonUtils.REQUIREMENTS_FINISH);
			}
			requirement = staffRequirementService.findOne(requirement.getStaffRequirementId());
			Person employee = hire.getHirePerson();
			employee.setStatus(CommonUtils.EMPLOYEE_WORKING);
			employee.setStartDate(now);
			employee.setEndDate(CommonUtils.kMonthAfter(now, hire.getPeriod()));
			employee.setSalary(hire.getSalary());
			employee.setDepartment(hire.getHireDepartment());
			employee = personService.findById(employee.getPersonId());
			hire = hireService.findOne(hireId);
			
			Notification notification = new Notification();
			notification.setOwner(employee);
			notification.setContent("Your have been hired!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_HIRE);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_MIDDLE);
			notification.setUrl("/user/profile?userId=" + employee.getPersonId());
			notification = notificationService.save(notification);
		}
		return "redirect:/hire/showHires";
	}
	
	@RequestMapping(value = "/rejectOneHire")
	public String rejectOneHire(Long hireId, Model model) {
		Hire hire = hireService.findOne(hireId);
		if (hire != null) {
			hire.setStatus(CommonUtils.HIRE_HR_MANAGER_REJECT);
			hire = hireService.findOne(hireId);
			
		}
		return "redirect:/hire/showHires";
	}
	
	@RequestMapping(value = "/aprroveOneDismission")
	public String aprroveOneDismission(Long dismissionId, Model model) {
		Dismission dismission = dismissionService.findOne(dismissionId);
		Role cbRole = roleService.findOne(CommonUtils.ROLE_CB_SPECIALIST);
		List<Person> cbSpecialists = personService.findByRole(cbRole);
		if (dismission != null && cbSpecialists != null && cbSpecialists.size() > 0) {
			Random rand = new Random();
			Integer indx = rand.nextInt(cbSpecialists.size());
			Person cbSpecialist = cbSpecialists.get(indx);
			dismission.setStatus(CommonUtils.DISMISSION_CB_SPECIALIST_PROCESSING);
			dismission.setDismissionCBSpecialist(cbSpecialist);
			dismission = dismissionService.findOne(dismissionId);
			
			Notification notification = new Notification();
			notification.setOwner(cbSpecialist);
			notification.setContent("You have a dismission need to process!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_DISMISSION);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_HIGH);
			notification.setUrl("/dismission/showDismissions");
			notification = notificationService.save(notification);
		}
		return "redirect:/dismission/showDismissions";
	}
	
	@RequestMapping(value = "/rejectOneDismission")
	public String rejectOneDismission(Long dismissionId, Model model) {
		Dismission dismission = dismissionService.findOne(dismissionId);
		if (dismission != null) {
			dismission.setStatus(CommonUtils.DISMISSION_HR_MANAGER_REJECT);
			dismission = dismissionService.findOne(dismissionId);
			
			Notification notification = new Notification();
			notification.setOwner(dismission.getDismissionPerson());
			notification.setContent("Your dismission has been rejected!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_DISMISSION);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_LOW);
			notification.setUrl("/dismission/showDismissions");
			notification = notificationService.save(notification);
		}
		return "redirect:/dismission/showDismissions";
	}
	
	@RequestMapping(value = "/addDept",  method = RequestMethod.GET)
	public String addDepartment(Model model) {
		model.addAttribute("department", new DepartmentForm());
		List<Person> managers = personService.findProperManager();
		model.addAttribute("managers", managers);
		return "hr-manager/addDepartment";
	}
	@RequestMapping(value = "/addDept",  method = RequestMethod.POST)
	public String addDepartment(@ModelAttribute("department") @Valid DepartmentForm department, BindingResult bindingResult) {
		
		departmentFormValidator.validate(department, bindingResult);
		if (bindingResult.hasErrors()) {
			System.out.println("Adding department occurs error!");
			return "hr-manager/addDepartment";
		}
		try {
			departmentService.create(department);
        } catch (DataIntegrityViolationException e) {
            return "hr-manager/addDepartment";
        }
		return "redirect:/department/showDepartments";
	}
	
	@RequestMapping(value={"/addUser"},  method = RequestMethod.GET)
	public String addUser(Model model) {
		model.addAttribute("user", new UserCreateForm());
		List<Department> departments = departmentService.findAll();
		model.addAttribute("departments", departments);
		return "hr-manager/addUser";
	}
	@RequestMapping(value={"/addUser"},  method = RequestMethod.POST)
	public String addUser(@ModelAttribute("user") @Valid UserCreateForm user, BindingResult bindingResult) {
		
		userCreateFormValidator.validate(user, bindingResult);
		userAddFormValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			System.out.println("Adding user occurs error!");
			return "hr-manager/addUser";
		}
		try {
			personService.add(user);
        } catch (DataIntegrityViolationException e) {
            return "hr-manager/addUser";
        }
		return "redirect:/user/showEmployees";
	}
	/*
	@ResponseBody
	@RequestMapping(value = "/approveRequirement",  method = RequestMethod.POST)
	public String delDepartment(Long requirementId) {
		StaffRequirement requirement = staffRequirementService.findOne(requirementId);
		Role recruiterRole = roleService.findOne(CommonUtils.ROLE_RECRUITER);
		List<Person> recruiters = personService.findByRole(recruiterRole);
		if (requirement != null && recruiters != null && recruiters.size() > 0) {
			requirement.setStatus(CommonUtils.REQUIREMENTS_RECRUITER_PROCESSING);
			Random rand = new Random();
			Integer indx = rand.nextInt(recruiters.size());
			Person recruiter = recruiters.get(indx);
			requirement.setRecruiter(recruiter);
		}
		//staffRequirementService.delete(requirementId);
		return "success";
	}*/
}
