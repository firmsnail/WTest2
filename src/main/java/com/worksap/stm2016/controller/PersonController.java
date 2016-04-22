package com.worksap.stm2016.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.worksap.stm2016.model.Notification;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.service.NotificationService;
import com.worksap.stm2016.service.PersonService;

@Controller
public class PersonController {
	
	@Autowired
	private PersonService personService;
	@Autowired
	private NotificationService notitficationService;
	
	@RequestMapping("/showPerson")
	@ResponseBody
	public Page<Person> ShowPerson(Model model) {
		Sort sort = new Sort(Direction.DESC, "personId");
		Pageable pageable = new PageRequest(1, 10, sort);
		return personService.findAll(pageable);
	}
	
	@RequestMapping("/newPerson")
	@ResponseBody
	public List<Notification> NewPerson(Model model) {
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
	
	@RequestMapping("/")
	public String index() {
		return "redirect:/showPersonList";
	}
}
