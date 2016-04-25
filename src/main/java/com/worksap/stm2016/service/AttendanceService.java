package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Attendance;


public interface AttendanceService {
	public List<Attendance> findAll();
	public Page<Attendance> findAll(Pageable pageable);
	public Attendance save(Attendance attendance);
	public Attendance findOne(Long id);
}
