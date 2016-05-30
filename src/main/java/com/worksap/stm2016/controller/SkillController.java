package com.worksap.stm2016.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.service.SkillService;
import com.worksap.stm2016.utils.CommonUtils;

@Controller
@PreAuthorize("hasAnyAuthority('HR-MANAGER', 'RECRUITER', 'TEAM-MANAGER')")
@RequestMapping(value = "/skill")
public class SkillController {
	
	@Autowired
	SkillService skillService;

	@ResponseBody
    @RequestMapping(value = "/addSkill", method = RequestMethod.POST)
    public Long addSkill(String skillName, String description) {
		//System.out.println("@addSkill start!");
		if (skillName != null) {
			skillName = skillName.trim();
		}
		if (description != null) {
			description = description.trim();
		}
		
		if (CommonUtils.ContentRegex.matcher(skillName).matches() || CommonUtils.ContentRegex.matcher(description).matches()) {
			System.out.println("here");
			return (long) -3;
		}
		if (skillName.length() > 255) {
			return (long) -4;
		}
		if (skillName == null || description == null || skillName.isEmpty() || description.isEmpty()) {
			return (long) -2;
		}
		Skill skill = skillService.findBySkillName(skillName);
		if (skill != null) {
			return (long) -1;
		}
		skill = new Skill();
		skill.setSkillName(skillName);
		skill.setDescription(description);
		skill = skillService.save(skill);
		return skill.getSkillId();
    }
	
	
	
}
