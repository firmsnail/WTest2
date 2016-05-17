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

	List<Attendance> findByAttendancePersonAndAttendanceDateAfter(Person cUser, Date startDate);

	List<Attendance> findByAttendancePersonAndAttendanceDateBefore(Person cUser, Date endDate);

	List<Attendance> findByAttendancePerson(Person cUser);

	List<Attendance> findByAttendanceDepartmentAndAttendanceDateBetween(Department department, Date startDate, Date endDate);

	List<Attendance> findByAttendanceDepartmentAndAttendanceDateAfter(Department department, Date startDate);

	List<Attendance> findByAttendanceDepartmentAndAttendanceDateBefore(Department department, Date endDate);

	List<Attendance> findByAttendanceDepartment(Department department);

	List<Attendance> findByAttendanceDateBetween(Date startDate, Date endDate);

	List<Attendance> findByAttendanceDateAfter(Date startDate);

	List<Attendance> findByAttendanceDateBefore(Date endDate);

	List<Attendance> findByAttendancePersonAndAttendanceDate(Person user, Date today);

}
