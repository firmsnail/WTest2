package com.worksap.stm2016.service;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.service.CurrentUserService;

public interface CurrentUserService {

	boolean canAccessUser(CurrentUser currentUser, Long userId);
	boolean canAccessDepartment(CurrentUser currentUser, Long departmentId);

}
