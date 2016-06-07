package com.worksap.stm2016.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Notification;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.repository.NotificationRepository;
import com.worksap.stm2016.service.NotificationService;
import com.worksap.stm2016.utils.CommonUtils;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService{

	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public List<Notification> findAll() {
		return (List<Notification>) notificationRepository.findAll();
	}

	@Override
	public Page<Notification> findAll(Pageable pageable) {
		return notificationRepository.findAll(pageable);
	}

	@Override
	public Notification save(Notification notification) {
		return notificationRepository.save(notification);
	}

	@Override
	public Notification findOne(Long id) {
		return notificationRepository.findOne(id);
	}

	@Override
	public List<Notification> findByOwner(Person user) {
		return notificationRepository.findByOwner(user);
	}

	@Override
	public List<Notification> findUnreadByOwner(Person user) {
		return notificationRepository.findByOwnerAndStatus(user, CommonUtils.NOTIFICATION_STATUS_UNREAD);
	}
	
}
