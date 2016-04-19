package com.worksap.stm2016.controller;

import java.util.List;

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

import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.service.PersonService;

@Controller
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@RequestMapping("/showPerson")
	@ResponseBody
	public Page<Person> ShowPerson(Model model) {
		Sort sort = new Sort(Direction.DESC, "personId");
		Pageable pageable = new PageRequest(1, 10, sort);
		return personService.findAll(pageable);
	}
	
	@RequestMapping("/showPersonList")
	@ResponseBody
	public List<Person> ShowPersonList(Model model) {
		Sort sort = new Sort(Direction.DESC, "personId");
		Pageable pageable = new PageRequest(0, 10, sort);
		return personService.findAll(pageable).getContent();
	}
	
	@RequestMapping("/")
	public String index() {
		return "redirect:/showPersonList";
	}
}
