package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Hire;
import com.worksap.stm2016.model.Test0;

public interface Test0Service {
	public List<Test0> findAll();
	public Page<Test0> findAll(Pageable pageable);
	public Test0 save(Test0 t0);
	public Test0 findOne(Long id);
}
