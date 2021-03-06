package com.worksap.stm2016.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.worksap.stm2016.model.Interview;
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
import com.worksap.stm2016.service.InterviewService;
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
	private InterviewService interviewService;

	@Autowired
	private UserCreateFormValidator  userCreateFormValidator;
	@Autowired
	private UserAddFormValidator  userAddFormValidator;
	@Autowired
	private DepartmentFormValidator  departmentFormValidator;
	
	@RequestMapping(value = "/analyzeEmployeeStructure",  method = RequestMethod.GET)
	public String analyzeEmployeeStructure(Model model) {
		analyzeEmployeeByDepartment(model);
		analyzeEmployeeByAge(model);
		analyzeEmployeeByGender(model);
		analyzeEmployeeBySkill(model);
		analyzeEmployeeByPeriod(model);
		return "hr-manager/analyzeEmployeeStructure";
	}
	
	@RequestMapping(value = "/analyzePayrollStructure",  method = RequestMethod.GET)
	public String analyzePayrollStructure(Model model) {
		
		analyzePayrollByDepartment(model);
		analyzePayrollByAge(model);
		analyzePayrollByGender(model);
		analyzePayrollBySkill(model);
		analyzePayrollByPeriod(model);
		return "hr-manager/analyzePayrollStructure";
	}
	
	private void analyzePayrollByPeriod(Model model) {
		List<String> kinds = new ArrayList<String>();
		List<Double> amounts = new ArrayList<Double>();
		List<Double> ratios = new ArrayList<Double>();
		Double total = 0.0;
		List<Integer> keyList = CommonUtils.getKeysByPeriod();
		//model.addAttribute("periodKeys", keyList);
		List<List<Payroll> > payrollByPeriod = new ArrayList<List<Payroll> >();
		for (int i = 0; i < keyList.size(); ++i) {
			payrollByPeriod.add(payrollService.findByPayrollEmployeePeriod(keyList.get(i)));
		}
		for (int i = 0; i < keyList.size(); ++i) {
			String kind = "";
			if (keyList.get(i) == 1) {
				kind = "OneMonth";
			} else if (keyList.get(i) == 2) {
				kind = "TwoMonths";
			} else if (keyList.get(i) == 3) {
				kind = "ThreeMonths";
			} else if (keyList.get(i) == 4) {
				kind = "FourMonths";
			} else if (keyList.get(i) == 5) {
				kind = "FiveMonths";
			} else if (keyList.get(i) == 6) {
				kind = "SixMonths";
			} else {
				kind = "Unknown";
			}
			Double amount = 0.0;
			for (Payroll payroll : payrollByPeriod.get(i)) {
				amount += payroll.getAmount();
			}
			kinds.add(kind);
			amounts.add(amount);
			total += amount;
		}
		for (int i = 0; i < kinds.size(); ++i) {
			if (total == 0.0) {
				ratios.add(0.0);
			} else {
				ratios.add(amounts.get(i)/total);
			}
		}

		model.addAttribute("total", total);
		
		model.addAttribute("periodKinds", kinds);
		model.addAttribute("periodAmounts", amounts);
		model.addAttribute("periodRatios", ratios);
	}

	private void analyzePayrollBySkill(Model model) {
		List<String> kinds = new ArrayList<String>();
		List<Double> amounts = new ArrayList<Double>();
		List<Double> ratios = new ArrayList<Double>();
		Double total = 0.0;

		List<Skill> keyList = CommonUtils.getKeysBySkill(skillService);
		if (keyList != null) {
			//model.addAttribute("skillKeys", keyList);
			//List<List<Person> > employeesBySkill = new ArrayList<List<Person> >();
			List<List<Payroll> > payrollBySkill = new ArrayList<List<Payroll> >();
			for (int i = 0; i < keyList.size(); ++i) {
				//employeesBySkill.add(personService.findBySkill(keyList.get(i)));
				payrollBySkill.add(payrollService.findByEmployeeSkill(keyList.get(i))); 
				
				kinds.add(keyList.get(i).getSkillName());
				Double amount = 0.0;
				for (Payroll payroll : payrollBySkill.get(i)) {
					amount += payroll.getAmount();
				}
				amounts.add(amount);
				total += amount;
			}
			
			kinds.add("Unknown");
			Double amount = 0.0;
			for (Payroll payroll : payrollService.findByEmployeeSkill(null)) {
				amount += payroll.getAmount();
			}
			amounts.add(amount);
			total += amount;
			
		}
		for (int i = 0; i < kinds.size(); ++i) {
			if (total == 0.0) {
				ratios.add(0.0);
			} else {
				ratios.add(amounts.get(i)/total);
			}
		}

		model.addAttribute("skillKinds", kinds);
		model.addAttribute("skillAmounts", amounts);
		model.addAttribute("skillRatios", ratios);
	}

	private void analyzePayrollByGender(Model model) {
		List<String> kinds = new ArrayList<String>();
		List<Double> amounts = new ArrayList<Double>();
		List<Double> ratios = new ArrayList<Double>();
		Double total = 0.0;
		
		Role role = roleService.findOne(CommonUtils.ROLE_SHORT_TERM_EMPLOYEE);

		List<Integer> keyList = CommonUtils.getKeysByGender();

		List<List<Payroll> > payrollsByGender = new ArrayList<List<Payroll> >();
		for (int i = 0; i < keyList.size(); ++i) {
			payrollsByGender.add(payrollService.findByPayrollEmployeeGenderAndPayrollEmployeeRole(keyList.get(i), role));
			
			String kind = "";
			if (keyList.get(i) == CommonUtils.GENDER_FEMALE) {
				kind = "Female";
			} else if (keyList.get(i) == CommonUtils.GENDER_MALE) {
				kind = "Male";
			} else {
				kind = "Unknown";
			}
			Double amount = 0.0;
			for (Payroll payroll : payrollsByGender.get(i)) {
				amount += payroll.getAmount();
			}
			kinds.add(kind);
			amounts.add(amount);
			total += amount;
		}
		for (int i = 0; i < kinds.size(); ++i) {
			if (total == 0.0) {
				ratios.add(0.0);
			} else {
				ratios.add(amounts.get(i)/total);
			}
		}

		model.addAttribute("genderKinds", kinds);
		model.addAttribute("genderAmounts", amounts);
		model.addAttribute("genderRatios", ratios);
	}

	private void analyzePayrollByAge(Model model) {
		List<String> kinds = new ArrayList<String>();
		List<Double> amounts = new ArrayList<Double>();
		List<Double> ratios = new ArrayList<Double>();
		Double total = 0.0;
		
		List<Integer> keyList = CommonUtils.getKeysByAge();

		List<List<Payroll> > payrollsByAge = new ArrayList<List<Payroll> >();
		for (int i = 0; i < keyList.size(); ++i) {
			payrollsByAge.add(payrollService.findByPayrollEmployeeAgeRange(keyList.get(i)));
			
			String kind = "";

			if (keyList.get(i) == CommonUtils.AGE_1stRANGE) {
				kind = CommonUtils.AGE_1stRANGE_MIN+"~"+CommonUtils.AGE_1stRANGE_MAX;
			} else if (keyList.get(i) == CommonUtils.AGE_2ndRANGE) {
				kind = CommonUtils.AGE_2ndRANGE_MIN+"~"+CommonUtils.AGE_2ndRANGE_MAX;
			} else if (keyList.get(i) == CommonUtils.AGE_3rdRANGE) {
				kind = CommonUtils.AGE_3rdRANGE_MIN+"~"+CommonUtils.AGE_3rdRANGE_MAX;
			} else if (keyList.get(i) == CommonUtils.AGE_4thRANGE) {
				kind = CommonUtils.AGE_4thRANGE_MIN+"~"+CommonUtils.AGE_4thRANGE_MAX;
			} else if (keyList.get(i) == CommonUtils.AGE_5thRANGE) {
				kind = CommonUtils.AGE_5thRANGE_MIN+"~"+CommonUtils.AGE_5thRANGE_MAX;
			} else if (keyList.get(i) == CommonUtils.AGE_6thRANGE) {
				kind = CommonUtils.AGE_6thRANGE_MIN+"~"+CommonUtils.AGE_6thRANGE_MAX;
			} else {
				kind = "Unknown";
			}
			
			Double amount = 0.0;
			for (Payroll payroll : payrollsByAge.get(i)) {
				amount += payroll.getAmount();
			}
			
			kinds.add(kind);
			amounts.add(amount);
			total += amount;
		}
		for (int i = 0; i < kinds.size(); ++i) {
			if (total == 0.0) {
				ratios.add(0.0);
			} else {
				ratios.add(amounts.get(i)/total);
			}
		}

		model.addAttribute("ageKinds", kinds);
		model.addAttribute("ageAmounts", amounts);
		model.addAttribute("ageRatios", ratios);
	}

	private void analyzePayrollByDepartment(Model model) {
		List<String> kinds = new ArrayList<String>();
		List<Double> amounts = new ArrayList<Double>();
		List<Double> ratios = new ArrayList<Double>();
		Double total = 0.0;


		List<Department> keyList = CommonUtils.getKeysByDepartment(departmentService, roleService, personService);
		List<List<Payroll> > payrollsByDepartment = new ArrayList<List<Payroll> >();
		if (keyList != null) {
			for (int i = 0; i < keyList.size(); ++i) {
				payrollsByDepartment.add(payrollService.findByPayrollEmployeeDepartment(keyList.get(i)));
				
				kinds.add(keyList.get(i).getDepartmentName());
				Double amount = 0.0;
				for (Payroll payroll : payrollsByDepartment.get(i)) {
					amount += payroll.getAmount();
				}
				
				amounts.add(amount);
				total += amount;
			}
			
		}
		for (int i = 0; i < kinds.size(); ++i) {
			if (total == 0.0) {
				ratios.add(0.0);
			} else {
				ratios.add(amounts.get(i)/total);
			}
		}

		model.addAttribute("departmentKinds", kinds);
		model.addAttribute("departmentAmounts", amounts);
		model.addAttribute("departmentRatios", ratios);
	}

	private void analyzeEmployeeByPeriod(Model model) {
		List<String> kinds = new ArrayList<String>();
		List<Integer> numbers = new ArrayList<Integer>();
		List<Double> ratios = new ArrayList<Double>();
		List<Integer> keyList = CommonUtils.getKeysByPeriod();
		//model.addAttribute("periodKeys", keyList);
		List<List<Person> > employeesByPeriod = new ArrayList<List<Person> >();
		for (int i = 0; i < keyList.size(); ++i) {
			employeesByPeriod.add(personService.findByPeriod(keyList.get(i)));
		}
		Integer total = 0;
		for (int i = 0; i < keyList.size(); ++i) {
			String kind = "";
			if (keyList.get(i) == 1) {
				kind = "OneMonth";
			} else if (keyList.get(i) == 2) {
				kind = "TwoMonths";
			} else if (keyList.get(i) == 3) {
				kind = "ThreeMonths";
			} else if (keyList.get(i) == 4) {
				kind = "FourMonths";
			} else if (keyList.get(i) == 5) {
				kind = "FiveMonths";
			} else if (keyList.get(i) == 6) {
				kind = "SixMonths";
			} else {
				kind = "Unknown";
			}
			kinds.add(kind);
			numbers.add(employeesByPeriod.get(i).size());
			total += employeesByPeriod.get(i).size();
		}
		for (int i = 0; i < kinds.size(); ++i) {
			if (total == 0) {
				ratios.add(0.0);
			} else {
				ratios.add((numbers.get(i)+0.0)/total);
			}
		}
		
		model.addAttribute("total", total);
		model.addAttribute("periodKinds", kinds);
		model.addAttribute("periodNumbers", numbers);
		model.addAttribute("periodRatios", ratios);
	}

	private void analyzeEmployeeBySkill(Model model) {
		List<String> kinds = new ArrayList<String>();
		List<Integer> numbers = new ArrayList<Integer>();
		List<Double> ratios = new ArrayList<Double>();
		List<Skill> keyList = CommonUtils.getKeysBySkill(skillService);
		Integer total = 0;
		if (keyList != null) {
			//model.addAttribute("skillKeys", keyList);
			List<List<Person> > employeesBySkill = new ArrayList<List<Person> >();
			for (int i = 0; i < keyList.size(); ++i) {
				employeesBySkill.add(personService.findBySkill(keyList.get(i)));
				
				kinds.add(keyList.get(i).getSkillName());
				numbers.add(employeesBySkill.get(i).size());
				total += numbers.get(i);
			}
			kinds.add("Unknown");
			numbers.add(personService.findBySkill(null).size());
			total += personService.findBySkill(null).size();
		}
		
		for (int i = 0; i < kinds.size(); ++i) {
			if (total == 0) {
				ratios.add(0.0);
			} else {
				ratios.add((numbers.get(i)+0.0)/total);
			}
		}

		model.addAttribute("skillKinds", kinds);
		model.addAttribute("skillNumbers", numbers);
		model.addAttribute("skillRatios", ratios);
	}

	private void analyzeEmployeeByGender(Model model) {
		Role role = roleService.findOne(CommonUtils.ROLE_SHORT_TERM_EMPLOYEE);
		
		List<String> kinds = new ArrayList<String>();
		List<Integer> numbers = new ArrayList<Integer>();
		List<Double> ratios = new ArrayList<Double>();
		Integer total = 0;
		List<Integer> keyList = CommonUtils.getKeysByGender();
		//model.addAttribute("genderKeys", keyList);
		List<List<Person> > employeesByGender = new ArrayList<List<Person> >();
		for (int i = 0; i < keyList.size(); ++i) {
			employeesByGender.add(personService.findByGenderAndRole(keyList.get(i), role));
			String kind = "";
			if (keyList.get(i) == CommonUtils.GENDER_FEMALE) {
				kind = "Female";
			} else if (keyList.get(i) == CommonUtils.GENDER_MALE) {
				kind = "Male";
			} else {
				kind = "Unknown";
			}
			kinds.add(kind);
			numbers.add(employeesByGender.get(i).size());
			total += numbers.get(i);
		}
		for (int i = 0; i < kinds.size(); ++i) {
			if (total == 0) {
				ratios.add(0.0);
			} else {
				ratios.add((numbers.get(i)+0.0)/total);
			}
		}

		model.addAttribute("genderKinds", kinds);
		model.addAttribute("genderNumbers", numbers);
		model.addAttribute("genderRatios", ratios);
	}

	private void analyzeEmployeeByAge(Model model) {
		List<String> kinds = new ArrayList<String>();
		List<Integer> numbers = new ArrayList<Integer>();
		List<Double> ratios = new ArrayList<Double>();
		Integer total = 0;
		List<Integer> keyList = CommonUtils.getKeysByAge();
		//model.addAttribute("ageKeys", keyList);
		List<List<Person> > employeesByAge = new ArrayList<List<Person> >();
		for (int i = 0; i < keyList.size(); ++i) {
			employeesByAge.add(personService.findByAgeRange(keyList.get(i)));
			String kind = "";
			if (keyList.get(i) == CommonUtils.AGE_1stRANGE) {
				kind = CommonUtils.AGE_1stRANGE_MIN+"~"+CommonUtils.AGE_1stRANGE_MAX;
			} else if (keyList.get(i) == CommonUtils.AGE_2ndRANGE) {
				kind = CommonUtils.AGE_2ndRANGE_MIN+"~"+CommonUtils.AGE_2ndRANGE_MAX;
			} else if (keyList.get(i) == CommonUtils.AGE_3rdRANGE) {
				kind = CommonUtils.AGE_3rdRANGE_MIN+"~"+CommonUtils.AGE_3rdRANGE_MAX;
			} else if (keyList.get(i) == CommonUtils.AGE_4thRANGE) {
				kind = CommonUtils.AGE_4thRANGE_MIN+"~"+CommonUtils.AGE_4thRANGE_MAX;
			} else if (keyList.get(i) == CommonUtils.AGE_5thRANGE) {
				kind = CommonUtils.AGE_5thRANGE_MIN+"~"+CommonUtils.AGE_5thRANGE_MAX;
			} else if (keyList.get(i) == CommonUtils.AGE_6thRANGE) {
				kind = CommonUtils.AGE_6thRANGE_MIN+"~"+CommonUtils.AGE_6thRANGE_MAX;
			} else {
				kind = "Unknown";
			}
			kinds.add(kind);
			numbers.add(employeesByAge.get(i).size());
			total += numbers.get(i);
		}
		for (int i = 0; i < kinds.size(); ++i) {
			if (total == 0) {
				ratios.add(0.0);
			} else {
				ratios.add((numbers.get(i)+0.0)/total);
			}
		}

		model.addAttribute("ageKinds", kinds);
		model.addAttribute("ageNumbers", numbers);
		model.addAttribute("ageRatios", ratios);
	}

	private void analyzeEmployeeByDepartment(Model model) {
		List<String> kinds = new ArrayList<String>();
		List<Integer> numbers = new ArrayList<Integer>();
		List<Double> ratios = new ArrayList<Double>();
		Integer total = 0;
		List<Department> keyList = CommonUtils.getKeysByDepartment(departmentService, roleService, personService);
		List<List<Person> > employeesByDepartment = new ArrayList<List<Person> >();
		if (keyList != null) {
			for (int i = 0; i < keyList.size(); ++i) {
				employeesByDepartment.add(personService.findByDepartmentAndRole(keyList.get(i)));
				
				kinds.add(keyList.get(i).getDepartmentName());
				numbers.add(employeesByDepartment.get(i).size());
				total += numbers.get(i);
			}
		}
		for (int i = 0; i < kinds.size(); ++i) {
			if (total == 0) {
				ratios.add(0.0);
			} else {
				ratios.add((numbers.get(i)+0.0)/total);
			}
		}

		model.addAttribute("departmentKinds", kinds);
		model.addAttribute("departmentNumbers", numbers);
		model.addAttribute("departmentRatios", ratios);
	}

	@RequestMapping(value={"/ajaxAnalyzeEmployeeByPeriod"}, method = RequestMethod.GET)
	@ResponseBody
	public String ajaxAnalyzeEmployeeByPeriod(Model model) throws JsonProcessingException {
		List<PieData> data = new ArrayList<PieData>();
		List<Integer> keyList = CommonUtils.getKeysByPeriod();
		//model.addAttribute("periodKeys", keyList);
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

	@RequestMapping(value={"/ajaxAnalyzeEmployeeBySkill"}, method = RequestMethod.GET)
	@ResponseBody
	public String ajaxAnalyzeEmployeeBySkill(Model model) throws JsonProcessingException {
		List<PieData> data = new ArrayList<PieData>();
		List<Skill> keyList = CommonUtils.getKeysBySkill(skillService);
		if (keyList != null) {
			//model.addAttribute("skillKeys", keyList);
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

	@RequestMapping(value={"/ajaxAnalyzeEmployeeByGender"}, method = RequestMethod.GET)
	@ResponseBody
	public String ajaxAnalyzeEmployeeByGender(Model model) throws JsonProcessingException {
		Role role = roleService.findOne(CommonUtils.ROLE_SHORT_TERM_EMPLOYEE);
		List<PieData> data = new ArrayList<PieData>();
		List<Integer> keyList = CommonUtils.getKeysByGender();
		//model.addAttribute("genderKeys", keyList);
		List<List<Person> > employeesByGender = new ArrayList<List<Person> >();
		for (int i = 0; i < keyList.size(); ++i) {
			employeesByGender.add(personService.findByGenderAndRole(keyList.get(i), role));
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

	@RequestMapping(value={"/ajaxAnalyzeEmployeeByAge"}, method = RequestMethod.GET)
	@ResponseBody
	public String ajaxAnalyzeEmployeeByAge(Model model) throws JsonProcessingException {
		List<PieData> data = new ArrayList<PieData>();
		List<Integer> keyList = CommonUtils.getKeysByAge();
		//model.addAttribute("ageKeys", keyList);
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

	@RequestMapping(value={"/ajaxAnalyzeEmployeeByDepartment"}, method = RequestMethod.GET)
	@ResponseBody
	public String ajaxAnalyzeEmployeeByDepartment(Model model) throws JsonProcessingException {
		List<PieData> data = new ArrayList<PieData>();
		List<Department> keyList = CommonUtils.getKeysByDepartment(departmentService, roleService, personService);
		List<List<Person> > employeesByDepartment = new ArrayList<List<Person> >();
		if (keyList != null) {
			//model.addAttribute("deptKeys", keyList);
			for (int i = 0; i < keyList.size(); ++i) {
				employeesByDepartment.add(personService.findByDepartmentAndRole(keyList.get(i)));
				
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
	
	@RequestMapping(value={"/ajaxAnalyzePayrollByPeriod"}, method = RequestMethod.GET)
	@ResponseBody
	public String ajaxAnalyzePayrollByPeriod(Model model) throws JsonProcessingException {
		List<PieData> data = new ArrayList<PieData>();
		List<Integer> keyList = CommonUtils.getKeysByPeriod();
		//model.addAttribute("periodKeys", keyList);
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
			} else {
				oneData.setName("Unknown");
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

	@RequestMapping(value={"/ajaxAnalyzePayrollBySkill"}, method = RequestMethod.GET)
	@ResponseBody
	public String ajaxAnalyzePayrollBySkill(Model model) throws JsonProcessingException {
		List<PieData> data = new ArrayList<PieData>();
		List<Skill> keyList = CommonUtils.getKeysBySkill(skillService);
		if (keyList != null) {
			//model.addAttribute("skillKeys", keyList);
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
			
			PieData oneData = new PieData();
			oneData.setName("Unknown");
			Double total = 0.0;
			for (Payroll payroll : payrollService.findByEmployeeSkill(null)) {
				total += payroll.getAmount();
			}
			oneData.setY(total);
			data.add(oneData);
			
			//model.addAttribute("employeesBySkill", employeesBySkill);
		}
		ObjectMapper mapper = new ObjectMapper();  
		String json = mapper.writeValueAsString(data);
		System.out.println("json: " + json);
		return json;
	}

	@RequestMapping(value={"/ajaxAnalyzePayrollByGender"}, method = RequestMethod.GET)
	@ResponseBody
	public String ajaxAnalyzePayrollByGender(Model model) throws JsonProcessingException {
		Role role = roleService.findOne(CommonUtils.ROLE_SHORT_TERM_EMPLOYEE);
		List<PieData> data = new ArrayList<PieData>();
		List<Integer> keyList = CommonUtils.getKeysByGender();
		//model.addAttribute("genderKeys", keyList);
		//List<List<Person> > employeesByGender = new ArrayList<List<Person> >();
		List<List<Payroll> > payrollsByGender = new ArrayList<List<Payroll> >();
		for (int i = 0; i < keyList.size(); ++i) {
			payrollsByGender.add(payrollService.findByPayrollEmployeeGenderAndPayrollEmployeeRole(keyList.get(i), role));
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

	@RequestMapping(value={"/ajaxAnalyzePayrollByAge"}, method = RequestMethod.GET)
	@ResponseBody
	public String ajaxAnalyzePayrollByAge(Model model) throws JsonProcessingException {
		List<PieData> data = new ArrayList<PieData>();
		List<Integer> keyList = CommonUtils.getKeysByAge();
		//model.addAttribute("ageKeys", keyList);
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

	@RequestMapping(value={"/ajaxAnalyzePayrollByDepartment"}, method = RequestMethod.GET)
	@ResponseBody
	public String ajaxAnalyzePayrollByDepartment(Model model) throws JsonProcessingException {
		List<PieData> data = new ArrayList<PieData>();
		List<Department> keyList = CommonUtils.getKeysByDepartment(departmentService, roleService, personService);
		List<List<Payroll> > payrollsByDepartment = new ArrayList<List<Payroll> >();
		//List<List<Person> > employeesByDepartment = new ArrayList<List<Person> >();
		if (keyList != null) {
			//model.addAttribute("ageKeys", keyList);
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
			
			Notification notification1 = new Notification();
			notification1.setOwner(hire.getHireDepartment().getManager());
			notification1.setContent("A hire of yours have been approved!");
			notification1.setIssueTime(new Date());
			notification1.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification1.setType(CommonUtils.NOTIFICATION_TYPE_HIRE);
			notification1.setUrgency(CommonUtils.NOTIFICATION_URGENCY_MIDDLE);
			notification1.setUrl("/hire/showHires");
			notification1 = notificationService.save(notification1);
		}
		return "redirect:/hire/showHires";
	}
	
	@RequestMapping(value = "/rejectOneHire")
	public String rejectOneHire(Long hireId, Model model) {
		Hire hire = hireService.findOne(hireId);
		if (hire != null) {
			hire.setStatus(CommonUtils.HIRE_HR_MANAGER_REJECT);
			hire = hireService.findOne(hireId);
			
			Person employee = personService.findById(hire.getHirePerson().getPersonId());
			employee.setStatus(CommonUtils.EMPLOYEE_REGISTERED);
			employee = personService.findById(employee.getPersonId());
			
			Notification notification = new Notification();
			notification.setOwner(hire.getHireDepartment().getManager());
			notification.setContent("A hire of yours have been rejected!");
			notification.setIssueTime(new Date());
			notification.setStatus(CommonUtils.NOTIFICATION_STATUS_UNREAD);
			notification.setType(CommonUtils.NOTIFICATION_TYPE_HIRE);
			notification.setUrgency(CommonUtils.NOTIFICATION_URGENCY_LOW);
			notification.setUrl("/hire/showHires");
			notification = notificationService.save(notification);
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
	public String addDepartment(@ModelAttribute("department") @Valid DepartmentForm department, BindingResult bindingResult, Model model) {
		if (!bindingResult.hasErrors()) {
			departmentFormValidator.validate(department, bindingResult);
		}
		if (bindingResult.hasErrors()) {
			System.out.println("Adding department occurs error!");
			List<Person> managers = personService.findProperManager();
			model.addAttribute("managers", managers);
			return "hr-manager/addDepartment";
		}
		try {
			departmentService.create(department);
        } catch (DataIntegrityViolationException e) {
        	System.out.println("DataIntegrityViolationException Errors!");
			List<Person> managers = personService.findProperManager();
			model.addAttribute("managers", managers);
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
	public String addUser(@ModelAttribute("user") @Valid UserCreateForm user, BindingResult bindingResult, Model model) {
		if (!bindingResult.hasErrors()) {
			userCreateFormValidator.validate(user, bindingResult);
		}
		if (!bindingResult.hasErrors()) {
			userAddFormValidator.validate(user, bindingResult);
		}
		if (bindingResult.hasErrors()) {
			System.out.println("Adding user occurs error!");
			List<Department> departments = departmentService.findAll();
			model.addAttribute("departments", departments);
			return "hr-manager/addUser";
		}
		try {
			personService.add(user);
        } catch (DataIntegrityViolationException e) {
    		List<Department> departments = departmentService.findAll();
    		model.addAttribute("departments", departments);
            return "hr-manager/addUser";
        }
		return "redirect:/user/showEmployees";
	}

	@RequestMapping(value={"/editOneEmployee"}, method = RequestMethod.POST)
	public String editOneEmployee(Long employeeId, Long departmentId, Double salary, String strEndDate) throws ParseException {
		System.out.println("@editOneEmployee employeeId: " + employeeId + " departmentId: " + departmentId);
		
		if (employeeId != null && departmentId != null) {
			Person emp = personService.findById(employeeId);
			Department dept = departmentService.findOne(departmentId);
			if (emp != null && emp.getRole().getRoleId() == CommonUtils.ROLE_SHORT_TERM_EMPLOYEE && dept != null) {
				emp.setDepartment(dept);
				emp.setSalary(salary);
				if (emp.getEndDate() == null) {
					emp.setStartDate(new Date());
				}
				SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
				Date endDate = df.parse(strEndDate);
				emp.setEndDate(endDate);
				emp.setStatus(CommonUtils.EMPLOYEE_WORKING);
				emp = personService.findById(employeeId);
			}
		}
		return "redirect:/user/showEmployees";
	}
	
	@RequestMapping(value={"/editOneDepartment"}, method = RequestMethod.POST)
	public String editOneDepartment(Long departmentId, Long managerId) {
		System.out.println("@editOneDepartment departmentId: " + departmentId + " managerId: " + managerId);
		if (departmentId != null && managerId != null) {
			Department dept = departmentService.findOne(departmentId);
			Person oriManager = dept.getManager();
			Person manager = personService.findById(managerId);
			if (dept != null && manager != null && manager.getRole().getRoleId() != CommonUtils.ROLE_HR_MANAGER) {
				if (oriManager != null && oriManager.getRole().getRoleId() != CommonUtils.ROLE_HR_MANAGER && oriManager.getPersonId() != manager.getPersonId()) {
					oriManager = personService.findById(oriManager.getPersonId());
					List<Integer> stats = new ArrayList<Integer>();
					stats.add(CommonUtils.INTERVIEW_INTERVIEWING);
					stats.add(CommonUtils.INTERVIEW_PENDING_SCHEDULE);
					List<Interview> interviews = interviewService.findByInterviewerAndStatusIn(oriManager, stats);
					for (Interview inter : interviews) {
						inter.setInterviewer(manager);
						inter = interviewService.findOne(inter.getInterviewId());
					}
					oriManager.setStatus(CommonUtils.EMPLOYEE_REGISTERED);
					oriManager.setDepartment(null);
					manager.setStatus(CommonUtils.EMPLOYEE_WORKING);
					manager.setDepartment(dept);
				} else if (oriManager == null) {
					manager.setStatus(CommonUtils.EMPLOYEE_WORKING);
					manager.setDepartment(dept);
				}
				manager = personService.findById(managerId);
				dept.setManager(manager);
				dept = departmentService.findOne(dept.getDepartmentId());
			}
		}
		return "redirect:/department/showDepartments";
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
