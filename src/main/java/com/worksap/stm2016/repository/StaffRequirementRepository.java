package com.worksap.stm2016.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.RecruitingPlan;
import com.worksap.stm2016.model.StaffRequirement;

@Repository
public interface StaffRequirementRepository extends PagingAndSortingRepository<StaffRequirement, Long>{
	
	Page<StaffRequirement> findAll(Pageable pageable);

	List<StaffRequirement> findByStatus(int status);

	List<StaffRequirement> findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateBetween(Person recruiter, Department department, Integer status,
			Date startDate, Date endDate);

	List<StaffRequirement> findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateAfter(Person recruiter, Department department, Integer status,
			Date startDate);

	List<StaffRequirement> findByRecruiterAndStfrqDepartmentAndStatusAndExpectDateBefore(Person recruiter, Department department, Integer status,
			Date endDate);

	List<StaffRequirement> findByRecruiterAndStfrqDepartmentAndStatus(Person recruiter, Department department, Integer status);

	List<StaffRequirement> findByRecruiterAndStatusAndExpectDateBetween(Person recruiter, Integer status, Date startDate, Date endDate);

	List<StaffRequirement> findByRecruiterAndStatusAndExpectDateAfter(Person recruiter, Integer status, Date startDate);

	List<StaffRequirement> findByRecruiterAndStatusAndExpectDateBefore(Person recruiter, Integer status, Date endDate);

	List<StaffRequirement> findByRecruiterAndStatus(Person recruiter, Integer status);

	List<StaffRequirement> findByStfrqDepartment(Department department);

	List<StaffRequirement> findByHrManager(Person hrManager);

	List<StaffRequirement> findByStfrqDepartmentAndStatus(Department dept, Integer status);

	List<StaffRequirement> findByStfrqDepartmentAndStatusAndRecruitingPlan(Department department, Integer status,
			RecruitingPlan plan);



}
