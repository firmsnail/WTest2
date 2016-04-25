package com.worksap.stm2016.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worksap.stm2016.model.Role;

public interface RoleService {
	public List<Role> findAll();
	public Page<Role> findAll(Pageable pageable);
	public Role save(Role role);
	public Role findOne(Long id);
}
