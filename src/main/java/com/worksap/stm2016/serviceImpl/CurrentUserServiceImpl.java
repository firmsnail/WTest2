package com.worksap.stm2016.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.worksap.stm2016.model.Applicant;
import com.worksap.stm2016.model.CurrentUser;
import com.worksap.stm2016.model.Department;
import com.worksap.stm2016.model.Dismission;
import com.worksap.stm2016.model.Hire;
import com.worksap.stm2016.model.Leave;
import com.worksap.stm2016.model.Person;
import com.worksap.stm2016.model.RecruitingPlan;
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.service.ApplicantService;
import com.worksap.stm2016.service.CurrentUserService;
import com.worksap.stm2016.service.DismissionService;
import com.worksap.stm2016.service.HireService;
import com.worksap.stm2016.service.LeaveService;
import com.worksap.stm2016.service.RecruitingPlanService;
import com.worksap.stm2016.service.StaffRequirementService;
import com.worksap.stm2016.utils.CommonUtils;

@Service
@Transactional
public class CurrentUserServiceImpl implements CurrentUserService{

	@Autowired
	StaffRequirementService staffRequirementService;
	@Autowired
	RecruitingPlanService recruitingPlanService;
	@Autowired
	HireService hireService;
	@Autowired
	DismissionService dismissionService;
	@Autowired
	LeaveService leaveService;
	@Autowired
	ApplicantService applicantService;
	
	@Override
	public boolean canAccessUser(CurrentUser currentUser, Long userId) {
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
	
	@Override
	public boolean canAccessStaffRequirement(CurrentUser currentUser, Long requirementId) {
		if (currentUser == null) return false;
		StaffRequirement requirement = staffRequirementService.findOne(requirementId);
		if (requirement == null) return false;
		if (currentUser.getRole().getRoleId() == CommonUtils.ROLE_HR_MANAGER) {
			return true;
		} else if (currentUser.getRole().getRoleId() == CommonUtils.ROLE_RECRUITER) {
			if (requirement.getStatus() == CommonUtils.REQUIREMENTS_RECRUITER_PROCESSING) return true;
			else if (requirement.getStatus() == CommonUtils.REQUIREMENTS_PENDING_RECRUITE || requirement.getStatus() == CommonUtils.REQUIREMENTS_RECRUITING) {
				return requirement.getRecruiter().getPersonId().equals(currentUser.getId());
			} else {
				return false;
			}
		} else {
			return requirement.getStfrqDepartment().getDepartmentId().equals(currentUser.getUser().getDepartment().getDepartmentId());
		}
	}
	
	@Override
	public boolean canAccessRecruitingPlan(CurrentUser currentUser, Long planId) {
		if (currentUser == null) return false;
		RecruitingPlan plan = recruitingPlanService.findOne(planId);
		if (plan == null) return false;
		if (currentUser.getRole().getRoleId() == CommonUtils.ROLE_HR_MANAGER) {
			return true;
		} else if (currentUser.getRole().getRoleId() == CommonUtils.ROLE_RECRUITER) {
			return plan.getPlanMaker().getPersonId().equals(currentUser.getId());
		} else {
			return plan.getStatus().equals(CommonUtils.PLAN_RECRUITING);
		}
	}
	
	@Override
	public boolean canAccessHire(CurrentUser currentUser, Long hireId) {
		if (currentUser == null) return false;
		Hire hire = hireService.findOne(hireId);
		if (hire == null) return false;
		if (currentUser.getRole().getRoleId() == CommonUtils.ROLE_HR_MANAGER) {
			return true;
		} else if (currentUser.getRole().getRoleId() == CommonUtils.ROLE_RECRUITER) {
			return hire.getHireRecruiter().getPersonId().equals(currentUser.getId());
		} else {
			return hire.getHireDepartment().getDepartmentId().equals(currentUser.getUser().getDepartment().getDepartmentId());
		}
	}
	
	@Override
	public boolean canAccessDismission(CurrentUser currentUser, Long dismissionId) {
		if (currentUser == null) return false;
		Dismission dismission = dismissionService.findOne(dismissionId);
		if (dismission == null) return false;
		if (currentUser.getRole().getRoleId() == CommonUtils.ROLE_HR_MANAGER) {
			return true;
		} else if (currentUser.getRole().getRoleId() == CommonUtils.ROLE_CB_SPECIALIST) {
			return dismission.getDismissionCBSpecialist().getPersonId().equals(currentUser.getId());
		} else {
			return dismission.getDismissionDepartment().getDepartmentId().equals(currentUser.getUser().getDepartment().getDepartmentId());
		}
	}
	
	@Override
	public boolean canAccessLeave(CurrentUser currentUser, Long leaveId) {
		if (currentUser == null) return false;
		Leave leave = leaveService.findOne(leaveId);
		if (leave == null) return false;
		if (currentUser.getRole().getRoleId() == CommonUtils.ROLE_CB_SPECIALIST) {
			return true;
		} else if (currentUser.getRole().getRoleId() == CommonUtils.ROLE_TEAM_MANAGER) {
			if (leave.getLeaveDepartment() == null || currentUser.getUser().getDepartment() == null) return false;
			return leave.getLeaveDepartment().getDepartmentId().equals(currentUser.getUser().getDepartment().getDepartmentId());
		} else {
			if (leave.getLeavePerson() == null) return false;
			return leave.getLeavePerson().getPersonId().equals(currentUser.getId());
		}
	}

	@Override
	public boolean canDeleteStaffRequirement(CurrentUser currentUser, Long requirementId) {
		if (currentUser == null) return false;
		StaffRequirement requirement = staffRequirementService.findOne(requirementId);
		if (requirement == null) return false;
		if (currentUser.getRole().getRoleId() == CommonUtils.ROLE_HR_MANAGER) {
			return requirement.getStfrqDepartment().getDepartmentId().equals(currentUser.getUser().getDepartment().getDepartmentId());
		} else {
			return false;
		}
	}

	@Override
	public boolean canDeleteDismission(CurrentUser currentUser, Long dismissionId) {
		if (currentUser == null) return false;
		Dismission dismission = dismissionService.findOne(dismissionId);
		return dismission.getDismissionPerson() != null && dismission.getDismissionPerson().getPersonId().equals(currentUser.getId());
	}

	@Override
	public boolean canDeleteLeave(CurrentUser currentUser, Long leaveId) {
		if (currentUser == null) return false;
		Leave leave = leaveService.findOne(leaveId);
		return leave.getLeavePerson() != null && leave.getLeavePerson().getPersonId().equals(currentUser.getId());
	}

	@Override
	public boolean canDeletePlan(CurrentUser currentUser, Long planId) {
		if (currentUser == null) return false;
		RecruitingPlan plan = recruitingPlanService.findOne(planId);
		return plan != null && plan.getStatus() == CommonUtils.PLAN_PENDING_VERIFY && plan.getPlanMaker() != null && plan.getPlanMaker().getPersonId().equals(currentUser.getId());
	}

	@Override
	public boolean canPostPlan(CurrentUser currentUser, Long planId) {
		if (currentUser == null) return false;
		RecruitingPlan plan = recruitingPlanService.findOne(planId);
		return plan != null && plan.getStatus() == CommonUtils.PLAN_VERIFIED && plan.getPlanMaker() != null && plan.getPlanMaker().getPersonId().equals(currentUser.getId());
	}

	@Override
	public boolean canOperateApplicant(CurrentUser currentUser, Long applicantId) {
		if (currentUser == null) return false;
		Applicant applicant = applicantService.findOne(applicantId);
		if (applicant.getPlanForApplicant() == null || applicant.getPlanForApplicant().getPlanMaker() == null) return false;
		return currentUser.getId().equals(applicant.getPlanForApplicant().getPlanMaker().getPersonId());
	}

	@Override
	public boolean hasLogIn(CurrentUser currentUser) {
		return currentUser != null;
	}

	@Override
	public boolean canAddDismission(CurrentUser currentUser) {
		if (currentUser == null) return false;
		if (currentUser.getUser().getRole().getRoleId().equals(CommonUtils.ROLE_SHORT_TERM_EMPLOYEE)) {
			return currentUser.getUser().getStatus().equals(CommonUtils.EMPLOYEE_WORKING);
		} else if (currentUser.getUser().getRole().getRoleId().equals(CommonUtils.ROLE_TEAM_MANAGER)) {
			return currentUser.getUser().getDepartment() != null;
		} else {
			return false;
		}
	}

	@Override
	public boolean canAddLeave(CurrentUser currentUser) {
		if (currentUser == null) return false;
		if (currentUser.getUser().getRole().getRoleId().equals(CommonUtils.ROLE_SHORT_TERM_EMPLOYEE)) {
			return currentUser.getUser().getStatus().equals(CommonUtils.EMPLOYEE_WORKING);
		} else {
			return false;
		}
	}

	@Override
	public boolean canApplyRecruitingPlan(CurrentUser currentUser, Long planId) {
		RecruitingPlan plan = recruitingPlanService.findOne(planId);
		if (currentUser == null || plan == null || !plan.getStatus().equals(CommonUtils.PLAN_RECRUITING)) return false;
		if (currentUser.getUser().getRole().getRoleId().equals(CommonUtils.ROLE_SHORT_TERM_EMPLOYEE)) {
			return currentUser.getUser().getStatus().equals(CommonUtils.EMPLOYEE_REGISTERED);
		} else {
			return false;
		}
	}

	@Override
	public boolean canAddRequirement(CurrentUser currentUser) {
		if (currentUser == null) return false;
		if (!currentUser.getRole().getRoleId().equals(CommonUtils.ROLE_TEAM_MANAGER)) {
			return false;
		} else {
			if (currentUser.getUser().getDepartment() != null && currentUser.getUser().getDepartment().getManager().getPersonId().equals(currentUser.getId())) {
				return true;
			} else {
				return false;
			}
		}
	}

}
