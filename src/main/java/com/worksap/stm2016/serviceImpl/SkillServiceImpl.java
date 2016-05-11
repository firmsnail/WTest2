package com.worksap.stm2016.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.repository.SkillRepository;
import com.worksap.stm2016.service.SkillService;

@Service
@Transactional
public class SkillServiceImpl implements SkillService{

	@Autowired
	private SkillRepository skillRepository;

	@Override
	public List<Skill> findAll() {
		// TODO Auto-generated method stub
		return (List<Skill>) skillRepository.findAll();
	}

	@Override
	public Page<Skill> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return skillRepository.findAll(pageable);
	}

	@Override
	public Skill save(Skill skill) {
		// TODO Auto-generated method stub
		return skillRepository.save(skill);
	}

	@Override
	public Skill findOne(Long id) {
		// TODO Auto-generated method stub
		return skillRepository.findOne(id);
	}

	@Override
	public Skill findBySkillName(String skillName) {
		return skillRepository.findBySkillName(skillName);
	}
	

}
