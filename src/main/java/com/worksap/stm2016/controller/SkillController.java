package com.worksap.stm2016.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.service.SkillService;

@Controller
@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'RECRUITER', 'TEAM-MANAGER')")
@RequestMapping(value = "/skill")
public class SkillController {
	
	@Autowired
	SkillService skillService;

	@ResponseBody
    @RequestMapping(value = "/addSkill", method = RequestMethod.POST)
    public String addSkill(String skillName, String description) {
		//System.out.println("@addSkill start!");
		if (skillName != null) {
			skillName = skillName.trim();
		}
		if (description != null) {
			description = description.trim();
		}
		if (skillName == null || description == null || skillName.isEmpty() || description.isEmpty()) {
			return "empty";
		}
		Skill skill = skillService.findBySkillName(skillName);
		if (skill != null) {
			return "exist";
		}
		skill = new Skill();
		skill.setSkillName(skillName);
		skill.setDescription(description);
		skillService.save(skill);
		return "success";
    }
	
	
	
}
