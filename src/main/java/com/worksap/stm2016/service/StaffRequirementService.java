package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.StaffRequirement;

public interface StaffRequirementService {
	public List<StaffRequirement> findAll();
	public Page<StaffRequirement> findAll(Pageable pageable);
	public StaffRequirement save(StaffRequirement staffRequirement);
	public StaffRequirement findOne(Long id);
	public List<StaffRequirement> findByStatus(int status);
}
