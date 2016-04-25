package com.worksap.stm2016.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.RecruitingPlan;
import com.worksap.stm2016.repository.RecruitingPlanRepository;
import com.worksap.stm2016.service.RecruitingPlanService;

@Service
@Transactional
public class RecruitingPlanServiceImpl implements RecruitingPlanService{

	@Autowired
	private RecruitingPlanRepository recruitingPlanRepository;

	@Override
	public List<RecruitingPlan> findAll() {
		// TODO Auto-generated method stub
		return (List<RecruitingPlan>) recruitingPlanRepository.findAll();
	}

	@Override
	public Page<RecruitingPlan> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return recruitingPlanRepository.findAll(pageable);
	}

	@Override
	public RecruitingPlan save(RecruitingPlan recruitingPlan) {
		// TODO Auto-generated method stub
		return recruitingPlanRepository.save(recruitingPlan);
	}

	@Override
	public RecruitingPlan findOne(Long id) {
		// TODO Auto-generated method stub
		return recruitingPlanRepository.findOne(id);
	}
	

}
