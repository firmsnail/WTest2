package com.worksap.stm2016.scheduledTask;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component  
@Configurable  
@EnableScheduling  
public class NotificationTasks {
	public void work(){
		//System.out.println("Schedule1 Success!");
	}
	
}
