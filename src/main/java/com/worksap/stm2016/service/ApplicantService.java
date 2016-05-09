package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Applicant;
import com.worksap.stm2016.model.Person;

public interface ApplicantService {
	public List<Applicant> findAll();
	public Page<Applicant> findAll(Pageable pageable);
	public Applicant save(Applicant applicant);
	public Applicant findOne(Long id);
	public List<Person> findByRecruiter(Person user);
}
