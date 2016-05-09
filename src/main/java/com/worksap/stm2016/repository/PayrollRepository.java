package com.worksap.stm2016.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Payroll;
import com.worksap.stm2016.model.Person;

@Repository
public interface PayrollRepository extends PagingAndSortingRepository<Payroll, Long>{
	
	Page<Payroll> findAll(Pageable pageable);

	List<Payroll> findByPayrollEmployeeAndIssueDateBetween(Person cUser, Date startDate, Date endDate);

	List<Payroll> findByPayrollEmployeeAndIssueDateNotBefore(Person cUser, Date startDate);

	List<Payroll> findByPayrollEmployeeAndIssueDateNotAfter(Person cUser, Date endDate);

	List<Payroll> findByPayrollEmployee(Person cUser);

	List<Payroll> findByPayrollDepartmentAndIssueDateBetween(Department department, Date startDate, Date endDate);

	List<Payroll> findByPayrollDepartmentAndIssueDateNotBefore(Department department, Date startDate);

	List<Payroll> findByPayrollDepartmentAndIssueDateNotAfter(Department department, Date endDate);

	List<Payroll> findByPayrollDepartment(Department department);

	List<Payroll> findByIssueDateBetween(Date startDate, Date endDate);

	List<Payroll> findByIssueDateNotBefore(Date startDate);

	List<Payroll> findByIssueDateNotAfter(Date endDate);

}
