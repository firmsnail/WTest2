package com.worksap.stm2016.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.worksap.stm2016.model.Dismission;

@Repository
public interface DismissionRepository extends PagingAndSortingRepository<Dismission, Long>{
	
	Page<Dismission> findAll(Pageable pageable);

}
