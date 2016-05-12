package com.worksap.stm2016.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Leave;
import com.worksap.stm2016.model.Person;

@Repository
public interface LeaveRepository extends PagingAndSortingRepository<Leave, Long>{
	
	Page<Leave> findAll(Pageable pageable);

	List<Leave> findByLeavePerson(Person user);

	List<Leave> findByLeaveDepartment(Department department);

}
