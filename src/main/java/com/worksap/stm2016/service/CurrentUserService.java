package com.worksap.stm2016.service;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.service.CurrentUserService;

public interface CurrentUserService {

	boolean canAccessUser(CurrentUser currentUser, Long userId);
	boolean canAccessDepartment(CurrentUser currentUser, Long departmentId);
	boolean canAccessStaffRequirement(CurrentUser currentUser, Long requirementId);
	boolean canAccessRecruitingPlan(CurrentUser currentUser, Long planId);
	boolean canAccessHire(CurrentUser currentUser, Long hireId);
	boolean canAccessDismission(CurrentUser currentUser, Long dismissionId);

}
