package com.worksap.stm2016.scheduledTask;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.worksap.stm2016.model.RecruitingPlan;
import com.worksap.stm2016.model.StaffRequirement;
import com.worksap.stm2016.service.RecruitingPlanService;
import com.worksap.stm2016.service.StaffRequirementService;
import com.worksap.stm2016.utils.CommonUtils;

@Component  
@Configurable  
@EnableScheduling  
public class PlanTasks {

	@Autowired
	private RecruitingPlanService recruitingPlanService;
	@Autowired
	private StaffRequirementService staffRequirementService;

	public void work() {
		List<RecruitingPlan> plans = recruitingPlanService.findByStatusIsNot(CommonUtils.PLAN_REJECT);
		for (RecruitingPlan plan : plans) {
			if (plan.getInvalidDate() != null && !plan.getInvalidDate().after(new Date())) {
				List<StaffRequirement> requirements = plan.getRequirements();
				for (StaffRequirement requirement : requirements) {
					requirement.setStatus(CommonUtils.REQUIREMENTS_FINISH);
					requirement = staffRequirementService.findOne(requirement.getStaffRequirementId()); 
				}
				plan.setStatus(CommonUtils.PLAN_FINISH);
				plan = recruitingPlanService.findOne(plan.getPlanId());
			}
		}
	}

}
