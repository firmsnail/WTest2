package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Notification;

public interface NotificationService {
	public List<Notification> findAll();
	public Page<Notification> findAll(Pageable pageable);
	public Notification save(Notification person);
	public Notification findOne(Long id);
}
