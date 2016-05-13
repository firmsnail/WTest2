package com.worksap.stm2016.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Leave;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.modelForm.LeaveForm;
import com.worksap.stm2016.repository.LeaveRepository;
import com.worksap.stm2016.repository.PersonRepository;
import com.worksap.stm2016.service.LeaveService;
import com.worksap.stm2016.utils.CommonUtils;

@Service
@Transactional
public class LeaveServiceImpl implements LeaveService{

	@Autowired
	private LeaveRepository leaveRepository;
	@Autowired
	private PersonRepository personRepository;

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

	@Override
	public Leave add(LeaveForm leave) {
		System.out.println("leave.date: " + leave.getStartDate() + " " + leave.getEndDate());
		Leave curLeave = new Leave();
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Person employee = personRepository.findOne(curUser.getId());
		curLeave.setLeavePerson(employee);
		if (employee.getDepartment() != null) {
			curLeave.setLeaveDepartment(employee.getDepartment());
		}
		curLeave.setReason(leave.getReason());
		curLeave.setStartDate(leave.getStartDate());
		curLeave.setEndDate(leave.getEndDate());
		curLeave.setStatus(CommonUtils.LEAVE_TEAM_MANAGER_PROCESSING);
		return leaveRepository.save(curLeave);
	}
	

}
