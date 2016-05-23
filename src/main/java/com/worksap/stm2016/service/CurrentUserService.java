package com.worksap.stm2016.service;

import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.service.CurrentUserService;

public interface CurrentUserService {

	boolean canAccessUser(CurrentUser currentUser, Long userId);
	boolean canAccessDepartment(CurrentUser currentUser, Long departmentId);
	boolean canAddRequirement(CurrentUser currentUser);
	boolean canAccessStaffRequirement(CurrentUser currentUser, Long requirementId);
	boolean canDeleteStaffRequirement(CurrentUser currentUser, Long requirementId);
	boolean canAccessRecruitingPlan(CurrentUser currentUser, Long planId);
	boolean canApplyRecruitingPlan(CurrentUser currentUser, Long planId);
	boolean canDeletePlan(CurrentUser currentUser, Long planId);
	boolean canPostPlan(CurrentUser currentUser, Long planId);
	boolean canAccessHire(CurrentUser currentUser, Long hireId);
	boolean canAddDismission(CurrentUser currentUser);
	boolean canAccessDismission(CurrentUser currentUser, Long dismissionId);
	boolean canDeleteDismission(CurrentUser currentUser, Long dismissionId);
	boolean canAddLeave(CurrentUser currentUser);
	boolean canAccessLeave(CurrentUser currentUser, Long leaveId);
	boolean canDeleteLeave(CurrentUser currentUser, Long leaveId);
	boolean canOperateApplicant(CurrentUser currentUser, Long applicantId);
	boolean hasLogIn(CurrentUser currentUser);

}
