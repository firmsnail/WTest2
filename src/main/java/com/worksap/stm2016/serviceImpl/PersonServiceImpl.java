package com.worksap.stm2016.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.repository.PersonRepository;
import com.worksap.stm2016.service.PersonService;

@Service
@Transactional
public class PersonServiceImpl implements PersonService{

	@Autowired
	private PersonRepository personRepository;
	
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

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) {
		Person user = personRepository.findByUserName(username);

		// if username not found, throw exception
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User with name=%s was not found", username));
		}

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		setAuths.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));

		List<GrantedAuthority> authroities = new ArrayList<GrantedAuthority>(setAuths);

		return buildUserForAuthentication(user, authroities);
	}

	private UserDetails buildUserForAuthentication(Person user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				true, true, true, true, authorities);
	}

	@Override
	public Person findByUserName(String userName) {
		// TODO Auto-generated method stub
		return personRepository.findByUserName(userName);
	}

}
