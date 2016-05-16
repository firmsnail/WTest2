package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Interview;
import com.worksap.stm2016.model.Person;

public interface InterviewService {
	public List<Interview> findAll();
	public Page<Interview> findAll(Pageable pageable);
	public Interview save(Interview interview);
	public Interview findOne(Long id);
	public List<Interview> findByRecruiter(Person user);
	public List<Interview> findByRecruiterAndStatusIn(Person recruiter, List<Integer> statuses);
	public List<Interview> findByInterviewee(Person interviewee);
	public List<Interview> findByInterviewerAndStatusIn(Person interviewer, List<Integer> statuses);
}
