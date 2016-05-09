package com.worksap.stm2016.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.worksap.stm2016.model.Attendance;
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;

@Repository
public interface AttendanceRepository extends PagingAndSortingRepository<Attendance, Long>{
	
	Page<Attendance> findAll(Pageable pageable);

	List<Attendance> findByAttendancePersonAndAttendanceDateBetween(Person cUser, Date startDate, Date endDate);

	List<Attendance> findByAttendancePersonAndAttendanceDateNotBefore(Person cUser, Date startDate);

	List<Attendance> findByAttendancePersonAndAttendanceDateNotAfter(Person cUser, Date endDate);

	List<Attendance> findByAttendancePerson(Person cUser);

	List<Attendance> findByAttendanceDepartmentAndAttendanceDateBetween(Department department, Date startDate,
			Date endDate);

	List<Attendance> findByAttendanceDepartmentAndAttendanceDateNotBefore(Department department, Date startDate);

	List<Attendance> findByAttendanceDepartmentAndAttendanceDateNotAfter(Department department, Date endDate);

	List<Attendance> findByAttendanceDepartment(Department department);

	List<Attendance> findByAttendanceDateBetween(Date startDate, Date endDate);

	List<Attendance> findByAttendanceDateNotBefore(Date startDate);

	List<Attendance> findByAttendanceDateNotAfter(Date endDate);

}
