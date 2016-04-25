package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Hire;

public interface HireService {
	public List<Hire> findAll();
	public Page<Hire> findAll(Pageable pageable);
	public Hire save(Hire hire);
	public Hire findOne(Long id);
}
