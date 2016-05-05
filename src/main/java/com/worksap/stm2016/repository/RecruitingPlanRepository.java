package com.worksap.stm2016.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.worksap.stm2016.model.RecruitingPlan;

@Repository
public interface RecruitingPlanRepository extends PagingAndSortingRepository<RecruitingPlan, Long>{
	
	Page<RecruitingPlan> findAll(Pageable pageable);

	List<RecruitingPlan> findByStatus(Integer status);

}
