package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Skill;

public interface SkillService {
	public List<Skill> findAll();
	public Page<Skill> findAll(Pageable pageable);
	public Skill save(Skill skill);
	public Skill findOne(Long id);
	public Skill findBySkillName(String skillName);
}
