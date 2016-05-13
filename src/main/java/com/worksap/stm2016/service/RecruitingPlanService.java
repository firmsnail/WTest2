package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.RecruitingPlan;
import com.worksap.stm2016.modelForm.PlanForm;

public interface RecruitingPlanService {
	public List<RecruitingPlan> findAll();
	public Page<RecruitingPlan> findAll(Pageable pageable);
	public RecruitingPlan save(RecruitingPlan recruitingPlan);
	public RecruitingPlan findOne(Long id);
	public List<RecruitingPlan> findByStatus(Integer status);
	public RecruitingPlan add(PlanForm plan);
	public void delete(Long planId);
}
