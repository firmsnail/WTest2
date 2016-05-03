package com.worksap.stm2016.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.service.CurrentUserDetailsService;
import com.worksap.stm2016.service.CurrentUserService;
import com.worksap.stm2016.service.PersonService;
import com.worksap.stm2016.utils.CommonUtils;

@Service
@Transactional
public class CurrentUserServiceImpl implements CurrentUserService{

	@Override
	public boolean canAccessUser(CurrentUser currentUser, Long userId) {
		// TODO Auto-generated method stub
		if (currentUser == null || !currentUser.getId().equals(userId)) return false;
		return true;
	}

	@Override
	public boolean canAccessDepartment(CurrentUser currentUser, Long departmentId) {
		if (currentUser == null) return false;
		Department belongDept = currentUser.getUser().getDepartment();
		if (belongDept == null) return false;
		Person depManager = belongDept.getManager();
		if (depManager == null || !depManager.getPersonId().equals(currentUser.getId())) return false;
		return true;
	}

	

}
