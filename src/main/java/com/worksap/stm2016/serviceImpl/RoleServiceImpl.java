package com.worksap.stm2016.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Role;
import com.worksap.stm2016.repository.RoleRepository;
import com.worksap.stm2016.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<Role> findAll() {
		// TODO Auto-generated method stub
		return (List<Role>) roleRepository.findAll();
	}

	@Override
	public Page<Role> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return roleRepository.findAll(pageable);
	}

	@Override
	public Role save(Role role) {
		// TODO Auto-generated method stub
		return roleRepository.save(role);
	}

	@Override
	public Role findOne(Long id) {
		// TODO Auto-generated method stub
		return roleRepository.findOne(id);
	}
	

}
