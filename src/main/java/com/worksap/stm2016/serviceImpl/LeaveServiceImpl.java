package com.worksap.stm2016.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Leave;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.repository.LeaveRepository;
import com.worksap.stm2016.service.LeaveService;

@Service
@Transactional
public class LeaveServiceImpl implements LeaveService{

	@Autowired
	private LeaveRepository leaveRepository;

	@Override
	public List<Leave> findAll() {
		// TODO Auto-generated method stub
		return (List<Leave>) leaveRepository.findAll();
	}

	@Override
	public Page<Leave> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return leaveRepository.findAll(pageable);
	}

	@Override
	public Leave save(Leave leave) {
		// TODO Auto-generated method stub
		return leaveRepository.save(leave);
	}

	@Override
	public Leave findOne(Long id) {
		// TODO Auto-generated method stub
		return leaveRepository.findOne(id);
	}

	@Override
	public List<Leave> findByLeavePerson(Person user) {
		return leaveRepository.findByLeavePerson(user);
	}

	@Override
	public List<Leave> findByLeaveDepartment(Department department) {
		return leaveRepository.findByLeaveDepartment(department);
	}

	@Override
	public void delete(Long leaveId) {
		leaveRepository.delete(leaveId);
	}
	

}
