package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Interview;

public interface InterviewService {
	public List<Interview> findAll();
	public Page<Interview> findAll(Pageable pageable);
	public Interview save(Interview interview);
	public Interview findOne(Long id);
}
