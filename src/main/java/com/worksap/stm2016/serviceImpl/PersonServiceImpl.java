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
import com.worksap.stm2016.model.Skill;
import com.worksap.stm2016.modelForm.UserCreateForm;
import com.worksap.stm2016.repository.DepartmentRepository;
import com.worksap.stm2016.repository.PersonRepository;
import com.worksap.stm2016.repository.RoleRepository;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.specification.PersonSpecification;
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

	@Override
	public List<Person> findByPeriod(Integer months) {
		return personRepository.findByPeriodMonth(months);
	}

	@Override
	public List<Person> findBySkill(Skill skill) {
		return personRepository.findAll(PersonSpecification.hasSkill(skill));
	}

	@Override
	public List<Person> findByGender(Integer gender) {
		Role role = roleRepository.findOne(CommonUtils.ROLE_SHORT_TERM_EMPLOYEE);
		if (gender.equals(CommonUtils.GENDER_UNKNOWN)) {
			return personRepository.findByRole(role);
		}
		else {
			return personRepository.findByRoleAndGender(role, gender);
		}
	}

	@Override
	public List<Person> findByAgeRange(Integer ageRange) {
		Role role = roleRepository.findOne(CommonUtils.ROLE_SHORT_TERM_EMPLOYEE);
		if (ageRange.equals(CommonUtils.AGE_UNKNOWN_RANGE)) return personRepository.findByRole(role);
		Integer startAge = CommonUtils.AGE_MIN, endAge = CommonUtils.AGE_MAX;
		if (ageRange.equals(CommonUtils.AGE_1stRANGE)) {
			startAge = CommonUtils.AGE_1stRANGE_MIN;
			endAge = CommonUtils.AGE_1stRANGE_MAX;
		} else if (ageRange.equals(CommonUtils.AGE_2ndRANGE)) {
			startAge = CommonUtils.AGE_2ndRANGE_MIN;
			endAge = CommonUtils.AGE_2ndRANGE_MAX;
		} else if (ageRange.equals(CommonUtils.AGE_3rdRANGE)) {
			startAge = CommonUtils.AGE_3rdRANGE_MIN;
			endAge = CommonUtils.AGE_3rdRANGE_MAX;
		} else if (ageRange.equals(CommonUtils.AGE_4thRANGE)) {
			startAge = CommonUtils.AGE_4thRANGE_MIN;
			endAge = CommonUtils.AGE_4thRANGE_MAX;
		} else if (ageRange.equals(CommonUtils.AGE_5thRANGE)) {
			startAge = CommonUtils.AGE_5thRANGE_MIN;
			endAge = CommonUtils.AGE_5thRANGE_MAX;
		} else {
			startAge = CommonUtils.AGE_6thRANGE_MIN;
			endAge = CommonUtils.AGE_6thRANGE_MAX;
		}
		return personRepository.findByAgeIsBetween(startAge, endAge);
	}

}

