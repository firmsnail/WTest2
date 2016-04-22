package com.worksap.stm2016.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Notification;
import com.worksap.stm2016.repository.NotificationRepository;
import com.worksap.stm2016.service.NotificationService;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService{

	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public List<Notification> findAll() {
		// TODO Auto-generated method stub
		return (List<Notification>) notificationRepository.findAll();
	}

	@Override
	public Page<Notification> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return notificationRepository.findAll(pageable);
	}

	@Override
	public Notification save(Notification notification) {
		// TODO Auto-generated method stub
		return notificationRepository.save(notification);
	}

	@Override
	public Notification findOne(Long id) {
		// TODO Auto-generated method stub
		return notificationRepository.findOne(id);
	}
	

}
