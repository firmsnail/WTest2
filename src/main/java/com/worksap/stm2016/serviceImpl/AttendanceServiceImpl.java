package com.worksap.stm2016.serviceImpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Attendance;
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.repository.AttendanceRepository;
import com.worksap.stm2016.service.AttendanceService;

@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService{

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Override
	public List<Attendance> findAll() {
		return (List<Attendance>) attendanceRepository.findAll();
	}

	@Override
	public Page<Attendance> findAll(Pageable pageable) {
		return attendanceRepository.findAll(pageable);
	}

	@Override
	public Attendance save(Attendance attendance) {
		return attendanceRepository.save(attendance);
	}

	@Override
	public Attendance findOne(Long id) {
		return attendanceRepository.findOne(id);
	}

	@Override
	public List<Attendance> findByPersonAndStartDateAndEndDate(Person cUser, Date startDate, Date endDate) {
		return attendanceRepository.findByAttendancePersonAndAttendanceDateBetween(cUser, startDate, endDate);
	}

	@Override
	public List<Attendance> findByPersonAndStartDate(Person cUser, Date startDate) {
		return attendanceRepository.findByAttendancePersonAndAttendanceDateNotBefore(cUser, startDate);
	}

	@Override
	public List<Attendance> findByPersonAndEndDate(Person cUser, Date endDate) {
		return attendanceRepository.findByAttendancePersonAndAttendanceDateNotAfter(cUser, endDate);
	}

	@Override
	public List<Attendance> findByPerson(Person cUser) {
		return attendanceRepository.findByAttendancePerson(cUser);
	}

	@Override
	public List<Attendance> findByDepartmentAndStartDateAndEndDate(Department department, Date startDate,
			Date endDate) {
		return attendanceRepository.findByAttendanceDepartmentAndAttendanceDateBetween(department, startDate, endDate);
	}

	@Override
	public List<Attendance> findByDepartmentAndStartDate(Department department, Date startDate) {
		return attendanceRepository.findByAttendanceDepartmentAndAttendanceDateNotBefore(department, startDate);
	}

	@Override
	public List<Attendance> findByDepartmentAndEndDate(Department department, Date endDate) {
		return attendanceRepository.findByAttendanceDepartmentAndAttendanceDateNotAfter(department, endDate);
	}

	@Override
	public List<Attendance> findByDepartment(Department department) {
		return attendanceRepository.findByAttendanceDepartment(department);
	}

	@Override
	public List<Attendance> findByStartDateAndEndDate(Date startDate, Date endDate) {
		return attendanceRepository.findByAttendanceDateBetween(startDate, endDate);
	}

	@Override
	public List<Attendance> findByStartDate(Date startDate) {
		return attendanceRepository.findByAttendanceDateNotBefore(startDate);
	}

	@Override
	public List<Attendance> findByEndDate(Date endDate) {
		return attendanceRepository.findByAttendanceDateNotAfter(endDate);
	}

}
