package com.worksap.stm2016.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Role;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, Long>, JpaSpecificationExecutor<Person> {
	
	Page<Person> findAll(Pageable pageable);

	Person findByUserName(String username);

	Person findByEmail(String email);

	List<Person> findByDepartment(Department dept);

	List<Person> findByDepartmentIsNullAndRoleIn(List<Role> roleCol);

	@Query("select u from Person u where EXTRACT(month from age(u.endTime, u.startTime)) <= ?1")
	List<Person> findByPeriodMonth(Integer months);

	List<Person> findByRole(Role role);

	List<Person> findByRoleAndGender(Role role, Integer gender);

	List<Person> findByAgeIsBetween(Integer startAge, Integer endAge);
	

}
