package com.worksap.stm2016.controller;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.worksap.stm2016.model.Notification;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.service.RoleService;

@Controller
public class GeneralController {
	
	@Autowired
	private PersonService personService;
	@Autowired
	private RoleService roleService;
	
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
		
		/*Notification notify = new Notification();
		notify.setContent("hhe");
		notify.setType(1);
		notify.setOwner(curP);
		notify = notitficationService.save(notify);
		Notification notify1 = new Notification();
		notify1.setContent("hhehhhhh");
		notify1.setType(2);
		notify1.setOwner(curP);
		notify1 = notitficationService.save(notify1);*/
		//Sort sort = new Sort(Direction.DESC, "personId");
		//Pageable pageable = new PageRequest(1, 10, sort);
		//return personService.findAll(pageable);
		curP = personService.findById(curP.getPersonId());
		//System.out.println("noty.size: " + curP.getNottificationList().size());
		//System.out.println("noty1: " + notitficationService.findOne(notify1.getNotificationId()));
		System.out.println("curP: " + curP);
		return curP.getNottificationList();
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
		//return "redirect:/showPersonList";
	}
	
	@RequestMapping(value={"/", "/index"})
	public String index() {
		return "index";
		//return "redirect:/showPersonList";
	}
	
	@RequestMapping(value={"/login"})
	public String login(@RequestParam(value = "error", required = false) String error) {
		if (error != null) {
			System.out.println("Invalid username and password!");
		}
		
		return "login";
		//return "redirect:/showPersonList";
	}
	
	@RequestMapping(value={"/register"})
	public String register() {
		return "register";
		//return "redirect:/showPersonList";
	}
	
	@RequestMapping(value={"/add"})
	public String add() {
		return "addUser";
		//return "redirect:/showPersonList";
	}
	
	@RequestMapping(value={"/logerror"})
	public String logerror() {
		System.out.println("Login Error!");
		return "index";
		//return "redirect:/showPersonList";
	}
	
	@RequestMapping(value={"/addAct"})
	public String addAct(Long role, String username, String password, String firstName, String lastName, Model model) {
		System.out.println("role: " + role);
		System.out.println("username: " + username);
		System.out.println("password: " + password);
		System.out.println("firstName: " + firstName);
		System.out.println("lastName: " + lastName);
		Role curRole = roleService.findOne(role);
		if (curRole == null) {
			System.out.println("role doesn't exist!");
			return "login";
		}
		if (personService.findByUserName(username) != null) {
			System.out.println("Existed!");
			return "login";
			
		}
		System.out.println("curRole: " + curRole);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		Person person = new Person();
		person.setRole(curRole);
		person.setUserName(username);
		person.setPassword(passwordEncoder.encode(password));
		person.setFirstName(firstName);
		person.setLastName(lastName);
		System.out.println("pRole:" + person.getRole().getRoleId());
		Person p = personService.save(person);
		System.out.println("person: " + p);
		return "index";
		//return "redirect:/showPersonList";
	}
}
