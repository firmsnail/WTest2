package com.worksap.stm2016.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Dismission;
import com.worksap.stm2016.model.Notification;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.modelForm.DismissionForm;
import com.worksap.stm2016.repository.DismissionRepository;
import com.worksap.stm2016.repository.PersonRepository;
import com.worksap.stm2016.repository.RoleRepository;
import com.worksap.stm2016.service.DismissionService;
import com.worksap.stm2016.utils.CommonUtils;

@Service
@Transactional
public class DismissionServiceImpl implements DismissionService{

	@Autowired
	private DismissionRepository dismissionRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<Dismission> findAll() {
		return (List<Dismission>) dismissionRepository.findAll();
	}

	@Override
	public Page<Dismission> findAll(Pageable pageable) {
		return dismissionRepository.findAll(pageable);
	}

	@Override
	public Dismission save(Dismission dismission) {
		return dismissionRepository.save(dismission);
	}

	@Override
	public Dismission findOne(Long id) {
		return dismissionRepository.findOne(id);
	}

	@Override
	public Dismission add(DismissionForm dismission) {
		
		Dismission curDismission = new Dismission();
		CurrentUser curUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Person employee = personRepository.findOne(dismission.getEmployeeId());
		curDismission.setDismissionPerson(employee);
		if (employee.getDepartment() != null) {
			curDismission.setDismissionDepartment(employee.getDepartment());
		}
		curDismission.setComment(dismission.getComment());
		curDismission.setExpectDate(dismission.getExpectDate());
		curDismission.setSubmitDate(new Date());
		if (curUser.getRole().getRoleId().equals(CommonUtils.ROLE_SHORT_TERM_EMPLOYEE)) {
			curDismission.setStatus(CommonUtils.DISMISSION_TEAM_MANAGER_PROCESSING);
		} else if (curUser.getRole().getRoleId().equals(CommonUtils.ROLE_TEAM_MANAGER)) {
			curDismission.setStatus(CommonUtils.DISMISSION_HR_MANAGER_PROCESSING);
			Role hrManagerRole = roleRepository.findOne(CommonUtils.ROLE_HR_MANAGER);
			Person hrManager = personRepository.findByRole(hrManagerRole).get(0);
			curDismission.setDismissionHRManager(hrManager);
		}
		return dismissionRepository.save(curDismission);
	}

	@Override
	public void delete(Long dismissionId) {
		Dismission dis = dismissionRepository.findOne(dismissionId);
		dis.getDismissionPerson().setDismission(null);
		dismissionRepository.delete(dis);
		dis = dismissionRepository.findOne(dismissionId);
	}

	@Override
	public List<Dismission> findByDismissionDepartment(Department department) {
		return dismissionRepository.findByDismissionDepartment(department);
	}

	@Override
	public List<Dismission> findByDismissionHRManager(Person user) {
		return dismissionRepository.findByDismissionHRManager(user);
	}

	@Override
	public List<Dismission> findByDismissionCBSpecialist(Person user) {
		return dismissionRepository.findByDismissionCBSpecialist(user);
	}

	@Override
	public List<Dismission> findByDismissionPerson(Person user) {
		return dismissionRepository.findByDismissionPerson(user);
	}
	

}
