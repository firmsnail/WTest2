package com.worksap.stm2016.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Interview;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.repository.InterviewRepository;
import com.worksap.stm2016.service.InterviewService;

@Service
@Transactional
public class InterviewServiceImpl implements InterviewService{

	@Autowired
	private InterviewRepository interviewRepository;

	@Override
	public List<Interview> findAll() {
		// TODO Auto-generated method stub
		return (List<Interview>) interviewRepository.findAll();
	}

	@Override
	public Page<Interview> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return interviewRepository.findAll(pageable);
	}

	@Override
	public Interview save(Interview interview) {
		// TODO Auto-generated method stub
		return interviewRepository.save(interview);
	}

	@Override
	public Interview findOne(Long id) {
		// TODO Auto-generated method stub
		return interviewRepository.findOne(id);
	}

	@Override
	public List<Interview> findByRecruiter(Person user) {
		// TODO Auto-generated method stub
		return interviewRepository.findByPlanForInterviewPlanMaker(user);
	}
	

}
