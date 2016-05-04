package com.worksap.stm2016.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.worksap.stm2016.model.StaffRequirement;

@Repository
public interface StaffRequirementRepository extends PagingAndSortingRepository<StaffRequirement, Long>{
	
	Page<StaffRequirement> findAll(Pageable pageable);

	List<StaffRequirement> findByStatus(int status);

}
