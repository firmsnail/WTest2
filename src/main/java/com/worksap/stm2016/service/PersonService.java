package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.modelForm.UserCreateForm;
import com.worksap.stm2016.modelForm.UserUpdateForm;

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
	public List<Person> findByPeriod(Integer integer);
	public List<Person> findBySkill(Skill skill);
	public List<Person> findByGender(Integer integer);
	public List<Person> findByAgeRange(Integer integer);
	public List<Person> findByRole(Role role);
	public List<Person> findByRoleAndStatus(Role role, Integer status);
	public void update(UserUpdateForm userForm);
	public List<Person> findByDepartmentAndRole(Department department);
	public List<Person> findByGenderAndRole(Integer gender, Role role);
	public List<Person> findByStatus(Integer employeeWorking);
	public List<Person> findByDepartmentAndStatus(Department dept, Integer employeeWorking);
}
