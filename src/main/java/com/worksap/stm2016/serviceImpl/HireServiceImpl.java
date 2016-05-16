package com.worksap.stm2016.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Hire;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.repository.HireRepository;
import com.worksap.stm2016.service.HireService;

@Service
@Transactional
public class HireServiceImpl implements HireService{

	@Autowired
	private HireRepository hireRepository;

	@Override
	public List<Hire> findAll() {
		// TODO Auto-generated method stub
		return (List<Hire>) hireRepository.findAll();
	}

	@Override
	public Page<Hire> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return hireRepository.findAll(pageable);
	}

	@Override
	public Hire save(Hire hire) {
		// TODO Auto-generated method stub
		return hireRepository.save(hire);
	}

	@Override
	public Hire findOne(Long id) {
		// TODO Auto-generated method stub
		return hireRepository.findOne(id);
	}

	@Override
	public List<Hire> findByHireHRManager(Person hrManager) {
		return hireRepository.findByHireHRManager(hrManager);
	}

	@Override
	public List<Hire> findByHireRecruiter(Person recruiter) {
		return hireRepository.findByHireRecruiter(recruiter);
	}

	@Override
	public List<Hire> findByHireDepartment(Department department) {
		return hireRepository.findByHireDepartment(department);
	}
	

}
