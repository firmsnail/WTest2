package com.worksap.stm2016.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.worksap.stm2016.model.Applicant;
import com.worksap.stm2016.model.Person;

@Repository
public interface ApplicantRepository extends PagingAndSortingRepository<Applicant, Long>{
	
	Page<Applicant> findAll(Pageable pageable);

	List<Person> findByPlanForApplicantPlanMaker(Person user);

}
