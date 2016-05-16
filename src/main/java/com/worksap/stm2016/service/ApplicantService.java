package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Applicant;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.RecruitingPlan;

public interface ApplicantService {
	public List<Applicant> findAll();
	public Page<Applicant> findAll(Pageable pageable);
	public Applicant save(Applicant applicant);
	public Applicant findOne(Long id);
	public List<Person> findByRecruiter(Person user);
	public List<Applicant> findByApplicantAndPlanForApplicant(Person user, RecruitingPlan plan);
	public List<Applicant> findByPlanForApplicantPlanMakerAndStatusNot(Person user, Integer applyChoosed);
	public List<Applicant> findByApplicant(Person user);
	public List<Applicant> findByStatusNot(Integer applyFailed);
	public List<Applicant> findByPlanForApplicantPlanMakerAndStatusNotIn(Person user, List<Integer> statuses);
	public List<Applicant> findByStatusNotIn(List<Integer> statuses);
	public List<Applicant> findByPlanForApplicantInAndStatusNotIn(List<RecruitingPlan> plans, List<Integer> statuses);
}
