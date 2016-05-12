package com.worksap.stm2016.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Dismission;
import com.worksap.stm2016.model.Person;

@Repository
public interface DismissionRepository extends PagingAndSortingRepository<Dismission, Long>{
	
	Page<Dismission> findAll(Pageable pageable);

	List<Dismission> findByDismissionDepartment(Department department);

	List<Dismission> findByDismissionHRManager(Person user);

	List<Dismission> findByDismissionCBSpecialist(Person user);

	List<Dismission> findByDismissionPerson(Person user);

}
