package com.worksap.stm2016.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Attendance;
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;


public interface AttendanceService {
	public List<Attendance> findAll();
	public Page<Attendance> findAll(Pageable pageable);
	public Attendance save(Attendance attendance);
	public Attendance findOne(Long id);
	public List<Attendance> findByPersonAndStartDateAndEndDate(Person cUser, Date startDate, Date endDate);
	public List<Attendance> findByPersonAndStartDate(Person cUser, Date startDate);
	public List<Attendance> findByPersonAndEndDate(Person cUser, Date endDate);
	public List<Attendance> findByPerson(Person cUser);
	public List<Attendance> findByDepartmentAndStartDateAndEndDate(Department department, Date startDate, Date endDate);
	public List<Attendance> findByDepartmentAndStartDate(Department department, Date startDate);
	public List<Attendance> findByDepartmentAndEndDate(Department department, Date endDate);
	public List<Attendance> findByDepartment(Department department);
	public List<Attendance> findByStartDateAndEndDate(Date startDate, Date endDate);
	public List<Attendance> findByStartDate(Date startDate);
	public List<Attendance> findByEndDate(Date endDate);
}
