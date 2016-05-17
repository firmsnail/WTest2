package com.worksap.stm2016.scheduledTask;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component  
@Configurable  
@EnableScheduling  
public class NotificationTasks {
	public void work(){
		NotifyForHRManager();
		NotifyForRecruiter();
		NotifyForCBSpecialist();
		NotifyForTeamManager();
		NotifyForShortTermEmployee();
	}

	private void NotifyForShortTermEmployee() {
		// TODO Auto-generated method stub
		
	}

	private void NotifyForTeamManager() {
		// TODO Auto-generated method stub
		
	}

	private void NotifyForCBSpecialist() {
		// TODO Auto-generated method stub
		
	}

	private void NotifyForRecruiter() {
		// TODO Auto-generated method stub
		
	}

	private void NotifyForHRManager() {
		// TODO Auto-generated method stub
		
	}
	
}
