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

	List<Payroll> findByPayrollEmployeeAndIssueDateAfter(Person cUser, Date startDate);

	List<Payroll> findByPayrollEmployeeAndIssueDateBefore(Person cUser, Date endDate);

	List<Payroll> findByPayrollEmployee(Person cUser);

	List<Payroll> findByPayrollDepartmentAndIssueDateBetween(Department department, Date startDate, Date endDate);

	List<Payroll> findByPayrollDepartmentAndIssueDateAfter(Department department, Date startDate);

	List<Payroll> findByPayrollDepartmentAndIssueDateBefore(Department department, Date endDate);

	List<Payroll> findByPayrollDepartment(Department department);

	List<Payroll> findByIssueDateBetween(Date startDate, Date endDate);

	List<Payroll> findByIssueDateAfter(Date startDate);

	List<Payroll> findByIssueDateBefore(Date endDate);

}
