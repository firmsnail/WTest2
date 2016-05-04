package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.modelForm.UserCreateForm;

public interface PersonService {
	public List<Person> findAll();
	public Page<Person> findAll(Pageable pageable);
	public Person save(Person person);
	public Person findById(Long id);
	public void deleteOne(Long personId);
	public Person findByUserName(String userName);
	public Person findByEmail(String email);
	public Person create(UserCreateForm form);
	public List<Person> findByDepartment(Department dept);
	public List<Person> findProperManager();
	public Person add(UserCreateForm user);
}
