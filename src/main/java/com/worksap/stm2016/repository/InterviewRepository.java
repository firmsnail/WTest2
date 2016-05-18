package com.worksap.stm2016.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.worksap.stm2016.model.Interview;
import com.worksap.stm2016.model.Person;

@Repository
public interface InterviewRepository extends PagingAndSortingRepository<Interview, Long>{
	
	Page<Interview> findAll(Pageable pageable);

	List<Interview> findByPlanForInterviewPlanMaker(Person user);

	List<Interview> findByPlanForInterviewPlanMakerAndStatusIn(Person recruiter, List<Integer> statuses);

	List<Interview> findByInterviewee(Person interviewee);

	List<Interview> findByInterviewerAndStatusIn(Person interviewer, List<Integer> statuses);

	List<Interview> findByStatus(Integer status);

}
