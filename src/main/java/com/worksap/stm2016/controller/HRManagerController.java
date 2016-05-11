package com.worksap.stm2016.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.worksap.stm2016.modelForm.DepartmentForm;
import com.worksap.stm2016.modelForm.UserCreateForm;
import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Dismission;
import com.worksap.stm2016.model.Hire;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.RecruitingPlan;
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.service.DepartmentService;
import com.worksap.stm2016.service.DismissionService;
import com.worksap.stm2016.service.HireService;
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
	
	private void analyzeEmployeeByPeriod(Model model) {
		List<Integer> keyList = CommonUtils.getKeysByPeriod();
		model.addAttribute("periodKeys", keyList);
		List<List<Person> > employeesByPeriod = new ArrayList<List<Person> >();
		for (int i = 0; i < keyList.size(); ++i) {
			employeesByPeriod.add(personService.findByPeriod(keyList.get(i)));
		}
		model.addAttribute("employeesByPeriod", employeesByPeriod);
	}

	private void analyzeEmployeeBySkill(Model model) {
		List<Skill> keyList = CommonUtils.getKeysBySkill(skillService);
		if (keyList != null) {
			model.addAttribute("skillKeys", keyList);
			List<List<Person> > employeesBySkill = new ArrayList<List<Person> >();
			for (int i = 0; i < keyList.size(); ++i) {
				employeesBySkill.add(personService.findBySkill(keyList.get(i)));
			}
			model.addAttribute("employeesBySkill", employeesBySkill);
		}
		
	}

	private void analyzeEmployeeByGender(Model model) {
		List<Integer> keyList = CommonUtils.getKeysByGender();
		model.addAttribute("genderKeys", keyList);
		List<List<Person> > employeesByGender = new ArrayList<List<Person> >();
		for (int i = 0; i < keyList.size(); ++i) {
			employeesByGender.add(personService.findByGender(keyList.get(i)));
		}
		model.addAttribute("employeesByGender", employeesByGender);
	}

	private void analyzeEmployeeByAge(Model model) {
		List<Integer> keyList = CommonUtils.getKeysByAge();
		model.addAttribute("ageKeys", keyList);
		List<List<Person> > employeesByAge = new ArrayList<List<Person> >();
		for (int i = 0; i < keyList.size(); ++i) {
			employeesByAge.add(personService.findByAgeRange(keyList.get(i)));
		}
		model.addAttribute("employeesByAge", employeesByAge);
	}

	private void analyzeEmployeeByDepartment(Model model) {
		List<Department> keyList = CommonUtils.getKeysByDepartment(departmentService);
		List<List<Person> > employeesByDepartment = new ArrayList<List<Person> >();
		if (keyList != null) {
			model.addAttribute("ageKeys", keyList);
			for (int i = 0; i < keyList.size(); ++i) {
				employeesByDepartment.add(personService.findByDepartment(keyList.get(i)));
			}
			model.addAttribute("employeesByDepartment", employeesByDepartment);
		}
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
		}
		return "redirect:/requirement/showStaffRequirements";
	}
	
	@RequestMapping(value = "/rejectOneRequirement")
	public String rejectOneRequirement(Long requirementId, Model model) {
		StaffRequirement requirement = staffRequirementService.findOne(requirementId);
		if (requirement != null) {
			requirement.setStatus(CommonUtils.REQUIREMENTS_REJECT);
			requirement = staffRequirementService.findOne(requirementId);
		}
		return "redirect:/requirement/showStaffRequirements";
	}
	
	@RequestMapping(value = "/aprroveOnePlan",  method = RequestMethod.POST)
	public String aprroveOnePlan(Long planId, Model model) {
		RecruitingPlan plan = recruitingPlanService.findOne(planId);
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (plan != null) {
			plan.setPlanHRManager(curUser.getUser());
			plan.setStatus(CommonUtils.PLAN_VERIFIED);
		}
		return "redirect:/plan/showRecruitingPlans";
	}
	
	@RequestMapping(value = "/rejectOnePlan",  method = RequestMethod.POST)
	public String rejectOnePlan(Long planId, Model model) {
		RecruitingPlan plan = recruitingPlanService.findOne(planId);
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (plan != null) {
			plan.setPlanHRManager(curUser.getUser());
			plan.setStatus(CommonUtils.PLAN_REJECT);
		}
		return "redirect:/plan/showRecruitingPlans";
	}
	
	@RequestMapping(value = "/aprroveOneHire",  method = RequestMethod.POST)
	public String aprroveOneHire(Long hireId, Model model) {
		Hire hire = hireService.findOne(hireId);
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (hire != null) {
			hire.setHireHRManager(curUser.getUser());
			hire.setStatus(CommonUtils.HIRE_FINISH);
		}
		return "redirect:/hire/showHires";
	}
	
	@RequestMapping(value = "/rejectOneHire",  method = RequestMethod.POST)
	public String rejectOneHire(Long hireId, Model model) {
		Hire hire = hireService.findOne(hireId);
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (hire != null) {
			hire.setHireHRManager(curUser.getUser());
			hire.setStatus(CommonUtils.HIRE_REJECT);
		}
		return "redirect:/hire/showHires";
	}
	
	@RequestMapping(value = "/aprroveOneDismission",  method = RequestMethod.POST)
	public String aprroveOneDismission(Long dismissionId, Model model) {
		Dismission dismission = dismissionService.findOne(dismissionId);
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (dismission != null) {
			dismission.setDismissionHRManager(curUser.getUser());
			dismission.setStatus(CommonUtils.DISMISSION_FINISH);
			Person disPerson = dismission.getDismissionPerson();
			personService.deleteOne(disPerson.getPersonId());
		}
		return "redirect:/dismission/showDismissions";
	}
	
	@RequestMapping(value = "/rejectOneDismission",  method = RequestMethod.POST)
	public String rejectOneDismission(Long dismissionId, Model model) {
		Dismission dismission = dismissionService.findOne(dismissionId);
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (dismission != null) {
			dismission.setDismissionHRManager(curUser.getUser());
			dismission.setStatus(CommonUtils.DISMISSION_REJECT);
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
