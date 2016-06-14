package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Dismission;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.modelForm.DismissionForm;

public interface DismissionService {
	public List<Dismission> findAll();
	public Page<Dismission> findAll(Pageable pageable);
	public Dismission save(Dismission dismission);
	public Dismission findOne(Long id);
	public Dismission add(DismissionForm dismission);
	public void delete(Long dismissionId);
	public List<Dismission> findByDismissionDepartment(Department department);
	public List<Dismission> findByDismissionHRManager(Person user);
	public List<Dismission> findByDismissionCBSpecialist(Person user);
	public List<Dismission> findByDismissionPerson(Person user);
	public List<Dismission> findByDismissionPersonAndStatusIn(Person emp, List<Integer> statuses);
}
