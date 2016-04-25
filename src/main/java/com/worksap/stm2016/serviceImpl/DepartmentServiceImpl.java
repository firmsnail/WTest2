package com.worksap.stm2016.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.repository.DepartmentRepository;
import com.worksap.stm2016.service.DepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentRepository departmentRepository;

	@Override
	public List<Department> findAll() {
		// TODO Auto-generated method stub
		return (List<Department>) departmentRepository.findAll();
	}

	@Override
	public Page<Department> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return departmentRepository.findAll(pageable);
	}

	@Override
	public Department save(Department department) {
		// TODO Auto-generated method stub
		return departmentRepository.save(department);
	}

	@Override
	public Department findOne(Long id) {
		// TODO Auto-generated method stub
		return departmentRepository.findOne(id);
	}
	

}
