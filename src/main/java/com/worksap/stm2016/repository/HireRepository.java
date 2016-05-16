package com.worksap.stm2016.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Hire;
import com.worksap.stm2016.model.Person;

@Repository
public interface HireRepository extends PagingAndSortingRepository<Hire, Long>{
	
	Page<Hire> findAll(Pageable pageable);

	List<Hire> findByHireHRManager(Person hrManager);

	List<Hire> findByHireRecruiter(Person recruiter);

	List<Hire> findByHireDepartment(Department department);

}
