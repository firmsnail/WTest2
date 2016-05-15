package com.worksap.stm2016.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Hire;
import com.worksap.stm2016.model.Test0;
import com.worksap.stm2016.repository.HireRepository;
import com.worksap.stm2016.service.HireService;
import com.worksap.stm2016.service.Test0Service;

@Service
@Transactional
public class Test0ServiceImpl implements Test0Service{

	@Override
	public List<Test0> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Test0> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Test0 save(Test0 t0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Test0 findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
