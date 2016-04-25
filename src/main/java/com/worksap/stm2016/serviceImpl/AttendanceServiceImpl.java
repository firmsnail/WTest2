package com.worksap.stm2016.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Attendance;
import com.worksap.stm2016.repository.AttendanceRepository;
import com.worksap.stm2016.service.AttendanceService;

@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService{

	@Autowired
	private AttendanceRepository attendanceRepository;

	@Override
	public List<Attendance> findAll() {
		// TODO Auto-generated method stub
		return (List<Attendance>) attendanceRepository.findAll();
	}

	@Override
	public Page<Attendance> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return attendanceRepository.findAll(pageable);
	}

	@Override
	public Attendance save(Attendance attendance) {
		// TODO Auto-generated method stub
		return attendanceRepository.save(attendance);
	}

	@Override
	public Attendance findOne(Long id) {
		// TODO Auto-generated method stub
		return attendanceRepository.findOne(id);
	}
	

}
