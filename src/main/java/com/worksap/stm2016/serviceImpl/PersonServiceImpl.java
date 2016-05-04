package com.worksap.stm2016.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.modelForm.UserCreateForm;
import com.worksap.stm2016.repository.DepartmentRepository;
import com.worksap.stm2016.repository.PersonRepository;
import com.worksap.stm2016.repository.RoleRepository;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.utils.CommonUtils;
import com.worksap.stm2016.utils.EmailUtils;

@Service
@Transactional
public class PersonServiceImpl implements PersonService{

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private DepartmentRepository deptRepository;
	
	@Override
	public List<Person> findAll( ) {
		return (List<Person>) personRepository.findAll();
	}
	
	@Override
	public Page<Person> findAll(Pageable pageable) {
		return personRepository.findAll(pageable);
	}

	@Override
	public Person save(Person person) {
		return personRepository.save(person);
	}

	@Override
	public Person findById(Long id) {
		return personRepository.findOne(id);
	}

	@Override
	public void deleteOne(Long personId) {
		personRepository.delete(personId);
	}

	@Override
	public Person findByUserName(String userName) {
		return personRepository.findByUserName(userName);
	}

	@Override
	public Person findByEmail(String email) {
		return personRepository.findByEmail(email);
	}

	@Override
	public Person create(UserCreateForm form) {
		Person person = new Person();
		person.setUserName(form.getUserName());
		Role role = roleRepository.findOne(form.getRole());
		person.setRole(role);
		person.setPassword(CommonUtils.passwordEncoder().encode(form.getPassword()));
		person.setFirstName(form.getFirstName());
		person.setLastName(form.getLastName());
		return personRepository.save(person);
	}

	@Override
	public List<Person> findByDepartment(Department dept) {
		return personRepository.findByDepartment(dept);
	}

	@Override
	public List<Person> findProperManager() {
		
		List<Role> roleCol = new ArrayList<Role>();
		roleCol.add(roleRepository.findOne(CommonUtils.ROLE_HR_MANAGER));
		roleCol.add(roleRepository.findOne(CommonUtils.ROLE_TEAM_MANAGER));
		return personRepository.findByDepartmentIsNullAndRoleIn(roleCol);
	}

	@Override
	public Person add(UserCreateForm form) {
		Person person = new Person();
		person.setUserName(form.getUserName());
		Role role = roleRepository.findOne(form.getRole());
		person.setRole(role);
		person.setPassword(CommonUtils.passwordEncoder().encode(form.getPassword()));
		person.setFirstName(form.getFirstName());
		person.setLastName(form.getLastName());
		person.setEmail(form.getEmail());
		Department dept = deptRepository.findOne(form.getDepartmentId());
		if (dept != null) {
			person.setDepartment(dept);
			person = personRepository.save(person);
			if (dept.getManager() == null && person.getRole().getRoleId() == CommonUtils.ROLE_TEAM_MANAGER) {
				dept.setManager(person);
			}
		} else {
			person = personRepository.save(person);
		}
		EmailUtils.notifyAddingEmployeeByEmail(form.getUserName(), form.getPassword(), form.getEmail());
		return person;
	}

}
