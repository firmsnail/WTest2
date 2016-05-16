package com.worksap.stm2016.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.worksap.stm2016.model.Applicant;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.RecruitingPlan;

@Repository
public interface ApplicantRepository extends PagingAndSortingRepository<Applicant, Long>{
	
	Page<Applicant> findAll(Pageable pageable);

	List<Person> findByPlanForApplicantPlanMaker(Person user);

	List<Applicant> findByApplicantAndPlanForApplicant(Person user, RecruitingPlan plan);

	List<Applicant> findByPlanForApplicantPlanMakerAndStatusNot(Person user, Integer status);

	List<Applicant> findByApplicant(Person user);

	List<Applicant> findByStatusNot(Integer status);

	List<Applicant> findByPlanForApplicantPlanMakerAndStatusNotIn(Person user, List<Integer> statuses);

	List<Applicant> findByStatusNotIn(List<Integer> statuses);

	List<Applicant> findByPlanForApplicantInAndStatusNotIn(List<RecruitingPlan> plans, List<Integer> statuses);

}
