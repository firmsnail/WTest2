package com.worksap.stm2016.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worksap.stm2016.chartData.PieData;
import com.worksap.stm2016.model.Notification;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.modelForm.UserCreateForm;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.validator.UserCreateFormValidator;

@Controller
public class GeneralController {
	
	@Autowired
	private PersonService personService;
	@Autowired
	private UserCreateFormValidator  userCreateFormValidator;
	/*
	@InitBinder("user")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCreateFormValidator);
    }
	*/
	@RequestMapping("/showPerson")
	@ResponseBody
	public Page<Person> ShowPerson(Model model) {
		Sort sort = new Sort(Direction.DESC, "personId");
		Pageable pageable = new PageRequest(1, 10, sort);
		return personService.findAll(pageable);
	}
	
	@RequestMapping("/newPerson")
	@ResponseBody
	public List<Notification> NewPerson(HttpServletRequest request, Model model) {
		request.getRemoteUser();
		Person curP = new Person();
		Random rd = new Random();
		
		curP.setFirstName("hehe"+rd.nextInt());
		curP.setLastName("hehe"+rd.nextInt());
		curP.setUserName("hehe"+rd.nextInt());
		curP.setPassword("hehe");
		curP = personService.save(curP);

		curP = personService.findById(curP.getPersonId());
		System.out.println("curP: " + curP);
		return curP.getNotificationList();
	}
	
	@RequestMapping("/onePerson")
	@ResponseBody
	public Person OnePerson(Model model) {
		return personService.findById((long) 8);
	}
	
	@RequestMapping("/showPersonList")
	@ResponseBody
	public List<Person> ShowPersonList(Long personId, Model model) {
		if (personId != null) {
			personService.deleteOne(personId);
		}
		Sort sort = new Sort(Direction.DESC, "personId");
		Pageable pageable = new PageRequest(0, 10, sort);
		return personService.findAll(pageable).getContent();
	}
	
	@RequestMapping(value={"/help"})
	public String help() {
		return "help";
	}
	
	@RequestMapping(value={"/", "/index"}, method = RequestMethod.GET)
	public String index() {
		/*Person hrManager = new Person();
		hrManager.setUserName("admin");
		hrManager.setPassword(CommonUtils.passwordEncoder().encode("admin"));
		hrManager.setFirstName("Xuanzhi");
		hrManager.setLastName("Gu");
		hrManager.setEmail("ascorpior@gmail.com");
		Role hrmRole = roleService.findOne(CommonUtils.ROLE_HR_MANAGER);
		hrManager.setRole(hrmRole);
		hrManager.setStatus(CommonUtils.EMPLOYEE_WORKING);
		hrManager = personService.save(hrManager);*/
		//Pattern ContentRegex = Pattern.compile("*<script>*</script>*");
		return "index";
	}
	
	@RequestMapping(value={"/testChart"}, method = RequestMethod.GET)
	@ResponseBody
	public String testChart() throws JsonProcessingException {
		List<PieData> data = new ArrayList<PieData>();
		for (int i = 0; i < 5; ++i) {
			PieData oneData = new PieData();
			oneData.setName("label"+i);
			oneData.setY(i+1.0);
			//mapData.put(oneData.getLabel(), oneData.getValue());
			data.add(oneData);
		}
		ObjectMapper mapper = new ObjectMapper();  
		//mapper.
		String json = mapper.writeValueAsString(data);
		System.out.println("json: " + json);
		return json;
	}
	@PreAuthorize("@currentUserServiceImpl.canAddRequirement(principal)")
	@RequestMapping(value={"/testAuth"}, method = RequestMethod.GET)
	@ResponseBody
	public String testAuth()   {
		return "hehe";
	}
	
	@RequestMapping(value={"/login"})
	public String login(@RequestParam(value = "error", required = false) String error, Model model) {
		if (error != null) {
			System.out.println("Invalid username and password!");
			model.addAttribute("loginerr", true);
		}
		
		return "login";
	}
	
	@RequestMapping(value={"/register"})
	public String register(Model model) {
		model.addAttribute("user", new UserCreateForm());
		return "register";
	}
	
	@RequestMapping(value={"/registerAct"})
	public String registerAct(@ModelAttribute("user") @Valid UserCreateForm user, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			userCreateFormValidator.validate(user, bindingResult);
		}
		if (bindingResult.hasErrors()) {
			System.out.println("register has error!");
			return "register";
		}
		try {
            personService.create(user);
        } catch (DataIntegrityViolationException e) {
            return "register";
        }
		return "login";
	}
	
	@RequestMapping(value={"/logerror"})
	public String logerror() {
		System.out.println("Login Error!");
		return "index";
	}
	
}
