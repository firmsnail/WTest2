package com.worksap.stm2016.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Dismission;
import com.worksap.stm2016.repository.DismissionRepository;
import com.worksap.stm2016.service.DismissionService;

@Service
@Transactional
public class DismissionServiceImpl implements DismissionService{

	@Autowired
	private DismissionRepository dismissionRepository;

	@Override
	public List<Dismission> findAll() {
		// TODO Auto-generated method stub
		return (List<Dismission>) dismissionRepository.findAll();
	}

	@Override
	public Page<Dismission> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return dismissionRepository.findAll(pageable);
	}

	@Override
	public Dismission save(Dismission dismission) {
		// TODO Auto-generated method stub
		return dismissionRepository.save(dismission);
	}

	@Override
	public Dismission findOne(Long id) {
		// TODO Auto-generated method stub
		return dismissionRepository.findOne(id);
	}
	

}
