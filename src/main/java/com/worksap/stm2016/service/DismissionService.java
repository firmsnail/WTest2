package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Dismission;

public interface DismissionService {
	public List<Dismission> findAll();
	public Page<Dismission> findAll(Pageable pageable);
	public Dismission save(Dismission dismission);
	public Dismission findOne(Long id);
}
