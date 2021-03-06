package com.worksap.stm2016.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Payroll;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.Role;

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

	@Query("select p from Payroll p where p.payrollEmployee.role.roleId=5 and p.payrollEmployee.status=2 and EXTRACT(month from age(p.payrollEmployee.endDate, payrollEmployee.startDate)) <= ?1 and EXTRACT(month from age(p.payrollEmployee.endDate, payrollEmployee.startDate)) > ?2")
	List<Payroll> findByPayrollEmployeePeriodMonth(Integer months, Integer months1);

	List<Payroll> findByPayrollEmployeeGender(Integer gender);

	List<Payroll> findByPayrollEmployeeAgeIsBetween(Integer startAge, Integer endAge);

	List<Payroll> findByPayrollEmployeeDepartment(Department department);

	List<Payroll> findByPayrollEmployeeRole(Role role);

	@Query("select p from Payroll p where p.payrollEmployee.role.roleId=5 and p.payrollEmployee.status=2 and p.payrollEmployee.startDate=null and p.payrollEmployee.endDate=null")
	List<Payroll> findByPayrollEmployeePeriodUnknown();

	List<Payroll> findByPayrollEmployeeRoleAndPayrollEmployeeStatus(Role role, Integer status);

	List<Payroll> findByPayrollEmployeeRoleAndPayrollEmployeeStatusAndPayrollEmployeeGenderIsNull(Role role,
			Integer status);

	List<Payroll> findByPayrollEmployeeGenderAndPayrollEmployeeRoleAndPayrollEmployeeStatus(Integer gender, Role role,
			Integer status);

	List<Payroll> findByPayrollEmployeeRoleAndPayrollEmployeeStatusAndPayrollEmployeeAgeIsNull(Role role,
			Integer status);

	List<Payroll> findByPayrollEmployeeRoleAndPayrollEmployeeStatusAndPayrollEmployeeAgeIsBetween(Role role,
			Integer status, Integer startAge, Integer endAge);

	List<Payroll> findByPayrollEmployeeDepartmentAndPayrollEmployeeStatusAndPayrollEmployeeRole(Department department,
			Integer status, Role role);

}
