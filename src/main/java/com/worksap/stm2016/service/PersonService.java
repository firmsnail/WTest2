package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Person;

public interface PersonService {
	public List<Person> findAll();
	public Page<Person> findAll(Pageable pageable);
}
