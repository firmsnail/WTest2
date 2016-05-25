package com.worksap.stm2016.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Notification;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.RecruitingPlan;
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.modelForm.PlanForm;
import com.worksap.stm2016.repository.PersonRepository;
import com.worksap.stm2016.repository.RecruitingPlanRepository;
import com.worksap.stm2016.repository.RoleRepository;
import com.worksap.stm2016.repository.SkillRepository;
import com.worksap.stm2016.repository.StaffRequirementRepository;
import com.worksap.stm2016.service.RecruitingPlanService;
import com.worksap.stm2016.utils.CommonUtils;

@Service
@Transactional
public class RecruitingPlanServiceImpl implements RecruitingPlanService{

	@Autowired
	private RecruitingPlanRepository recruitingPlanRepository;
	@Autowired
	private SkillRepository skillRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private StaffRequirementRepository staffRequirementRepository;
	
	@Override
	public List<RecruitingPlan> findAll() {
		return (List<RecruitingPlan>) recruitingPlanRepository.findAll();
	}

	@Override
	public Page<RecruitingPlan> findAll(Pageable pageable) {
		return recruitingPlanRepository.findAll(pageable);
	}

	@Override
	public RecruitingPlan save(RecruitingPlan recruitingPlan) {
		return recruitingPlanRepository.save(recruitingPlan);
	}

	@Override
	public RecruitingPlan findOne(Long id) {
		return recruitingPlanRepository.findOne(id);
	}

	@Override
	public List<RecruitingPlan> findByStatus(Integer status) {
		return recruitingPlanRepository.findByStatus(status);
	}

	@Override
	public RecruitingPlan add(PlanForm plan) {
		
		RecruitingPlan curPlan = new RecruitingPlan();
		curPlan.setReason(plan.getReason());
		curPlan.setPlanNum(plan.getPlanNum());
		curPlan.setExpectDate(plan.getExpectDate());
		curPlan.setInvalidDate(plan.getInvalidDate());
		curPlan.setSubmitDate(new Date());
		
		for (Long skillId : plan.getSkills()) {
			curPlan.getPlanSkillList().add(skillRepository.findOne(skillId));
		}
		
		Role hrManagerRole = roleRepository.findOne(CommonUtils.ROLE_HR_MANAGER);
		Person hrManager = personRepository.findByRole(hrManagerRole).get(0);
		curPlan.setPlanHRManager(hrManager);
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		curPlan.setPlanMaker(curUser.getUser());
		curPlan.setStatus(CommonUtils.PLAN_PENDING_VERIFY);
		curPlan.setHiredNum(0);
		curPlan = recruitingPlanRepository.save(curPlan);
		
		for (Long requirementId : plan.getRequirements()) {
			StaffRequirement requirement = staffRequirementRepository.findOne(requirementId);
			requirement.setRecruitingPlan(curPlan);
			requirement = staffRequirementRepository.findOne(requirementId);
		}
		
		return curPlan;
	}

	@Override
	public void delete(Long planId) {
		recruitingPlanRepository.delete(planId);
	}

	@Override
	public List<RecruitingPlan> findByPlanHRManager(Person user) {
		return recruitingPlanRepository.findByPlanHRManager(user);
	}

	@Override
	public List<RecruitingPlan> findByPlanMaker(Person user) {
		return recruitingPlanRepository.findByPlanMaker(user);
	}
	
}
