package com.worksap.stm2016.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.worksap.stm2016.model.Notification;
import com.worksap.stm2016.model.Person;

@Repository
public interface NotificationRepository extends PagingAndSortingRepository<Notification, Long>{
	
	Page<Notification> findAll(Pageable pageable);

	List<Notification> findByOwner(Person user);

	List<Notification> findByOwnerAndStatus(Person user, Integer status);

}
