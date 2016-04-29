package com.worksap.stm2016.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.model.UserCreateForm;
import com.worksap.stm2016.repository.PersonRepository;
import com.worksap.stm2016.repository.RoleRepository;
import com.worksap.stm2016.service.PersonService;

@Service
@Transactional
public class PersonServiceImpl implements PersonService{

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public List<Person> findAll( ) {
		// TODO Auto-generated method stub
		return (List<Person>) personRepository.findAll();
	}
	
	@Override
	public Page<Person> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return personRepository.findAll(pageable);
	}

	@Override
	public Person save(Person person) {
		// TODO Auto-generated method stub
		return personRepository.save(person);
	}

	@Override
	public Person findById(Long id) {
		// TODO Auto-generated method stub
		return personRepository.findOne(id);
	}

	@Override
	public void deleteOne(Long personId) {
		// TODO Auto-generated method stub
		personRepository.delete(personId);
	}

	@Override
	public Person findByUserName(String userName) {
		// TODO Auto-generated method stub
		return personRepository.findByUserName(userName);
	}

	@Override
	public Person findByEmail(String email) {
		// TODO Auto-generated method stub
		return personRepository.findByEmail(email);
	}

	@Override
	public Person create(UserCreateForm form) {
		Person person = new Person();
		person.setUserName(form.getUserName());
		Role role = roleRepository.findOne(form.getRole());
		person.setRole(role);
		person.setPassword(new BCryptPasswordEncoder().encode(form.getPassword()));
		person.setFirstName(form.getFirstName());
		person.setLastName(form.getLastName());
		return personRepository.save(person);
	}

}
