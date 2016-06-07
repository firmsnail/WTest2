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

	@Query("select u from Person u where u.role.roleId=5 and u.status=2 and EXTRACT(month from age(u.endDate, u.startDate)) <= ?1 and EXTRACT(month from age(u.endDate, u.startDate)) > ?2")
	List<Person> findByPeriodMonth(Integer months, Integer months1);

	List<Person> findByRole(Role role);

	List<Person> findByRoleAndGender(Role role, Integer gender);

	List<Person> findByAgeIsBetween(Integer startAge, Integer endAge);

	List<Person> findByRoleAndStatus(Role role, Integer status);

	@Query("select u from Person u where u.role.roleId=5 and u.status=2 and u.startDate=null and u.endDate=null")
	List<Person> findByPeriodUnknown();

	List<Person> findByRoleAndGenderAndStatus(Role role, Integer gender, Integer status);

	List<Person> findByRoleAndStatusAndAgeIsBetween(Role role, Integer status, Integer startAge,
			Integer endAge);

	List<Person> findByDepartmentAndRoleAndStatus(Department dept, Role role, Integer status);

	List<Person> findByGenderAndStatus(Integer gender, Integer status);

	List<Person> findByRoleAndStatusAndGenderIsNull(Role role, Integer status);

	List<Person> findByStatus(Integer status);

	List<Person> findByDepartmentAndStatus(Department dept, Integer status);

	

}
