package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Leave;
import com.worksap.stm2016.model.Person;

public interface LeaveService {
	public List<Leave> findAll();
	public Page<Leave> findAll(Pageable pageable);
	public Leave save(Leave leave);
	public Leave findOne(Long id);
	public List<Leave> findByLeavePerson(Person user);
	public List<Leave> findByLeaveDepartment(Department department);
	public void delete(Long leaveId);
}
