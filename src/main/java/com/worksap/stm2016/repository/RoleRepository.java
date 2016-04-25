package com.worksap.stm2016.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.worksap.stm2016.model.Role;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Long>{
	
	Page<Role> findAll(Pageable pageable);

}
