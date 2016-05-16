package com.worksap.stm2016.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Applicant;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.RecruitingPlan;
import com.worksap.stm2016.repository.ApplicantRepository;
import com.worksap.stm2016.service.ApplicantService;

@Service
@Transactional
public class ApplicantServiceImpl implements ApplicantService{

	@Autowired
	ApplicantRepository applicantRepository;

	@Override
	public List<Applicant> findAll() {
		return (List<Applicant>) applicantRepository.findAll();
	}

	@Override
	public Page<Applicant> findAll(Pageable pageable) {
		return applicantRepository.findAll(pageable);
	}

	@Override
	public Applicant save(Applicant interview) {
		return applicantRepository.save(interview);
	}

	@Override
	public Applicant findOne(Long id) {
		return applicantRepository.findOne(id);
	}

	@Override
	public List<Person> findByRecruiter(Person user) {
		return applicantRepository.findByPlanForApplicantPlanMaker(user);
	}

	@Override
	public List<Applicant> findByApplicantAndPlanForApplicant(Person user, RecruitingPlan plan) {
		return applicantRepository.findByApplicantAndPlanForApplicant(user, plan);
	}

	@Override
	public List<Applicant> findByPlanForApplicantPlanMakerAndStatusNot(Person user, Integer status) {
		return applicantRepository.findByPlanForApplicantPlanMakerAndStatusNot(user, status);
	}

	@Override
	public List<Applicant> findByApplicant(Person user) {
		return applicantRepository.findByApplicant(user);
	}

	@Override
	public List<Applicant> findByStatusNot(Integer status) {
		return applicantRepository.findByStatusNot(status);
	}

	@Override
	public List<Applicant> findByPlanForApplicantPlanMakerAndStatusNotIn(Person user, List<Integer> statuses) {
		return applicantRepository.findByPlanForApplicantPlanMakerAndStatusNotIn(user, statuses);
	}

	@Override
	public List<Applicant> findByStatusNotIn(List<Integer> statuses) {
		return applicantRepository.findByStatusNotIn(statuses);
	}

	@Override
	public List<Applicant> findByPlanForApplicantInAndStatusNotIn(List<RecruitingPlan> plans, List<Integer> statuses) {
		return applicantRepository.findByPlanForApplicantInAndStatusNotIn(plans, statuses);
	}
	

}
