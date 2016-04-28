package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.worksap.stm2016.model.Person;

public interface PersonService  extends UserDetailsService{
	UserDetails loadUserByUsername(final String username);
	public List<Person> findAll();
	public Page<Person> findAll(Pageable pageable);
	public Person save(Person person);
	public Person findById(Long id);
	public void deleteOne(Long personId);
	public Person findByUserName(String userName);
}
