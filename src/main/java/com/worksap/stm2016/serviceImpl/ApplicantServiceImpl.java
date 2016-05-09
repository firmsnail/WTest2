package com.worksap.stm2016.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Applicant;
import com.worksap.stm2016.model.Person;
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
	

}
