package com.worksap.stm2016.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;

@Repository
public interface DepartmentRepository extends PagingAndSortingRepository<Department, Long>{
	
	Page<Department> findAll(Pageable pageable);

	Department findByDepartmentName(String departmentName);

	List<Department> findByManagerIsNot(Person manager);

}
