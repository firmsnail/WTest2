package com.worksap.stm2016.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.repository.StaffRequirementRepository;
import com.worksap.stm2016.service.StaffRequirementService;

@Service
@Transactional
public class StaffRequirementServiceImpl implements StaffRequirementService{

	@Autowired
	private StaffRequirementRepository staffRequirementRepository;

	@Override
	public List<StaffRequirement> findAll() {
		return (List<StaffRequirement>) staffRequirementRepository.findAll();
	}

	@Override
	public Page<StaffRequirement> findAll(Pageable pageable) {
		return staffRequirementRepository.findAll(pageable);
	}

	@Override
	public StaffRequirement save(StaffRequirement staffRequirement) {
		return staffRequirementRepository.save(staffRequirement);
	}

	@Override
	public StaffRequirement findOne(Long id) {
		return staffRequirementRepository.findOne(id);
	}

	@Override
	public List<StaffRequirement> findByStatus(int status) {
		
		return staffRequirementRepository.findByStatus(status);
	}
	

}
