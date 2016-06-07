package com.worksap.stm2016.scheduledTask;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ParseException;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class PlanScheduleConfiguration {
	
	@Bean(name = "detailFactoryBeanForPlan")  
    public MethodInvokingJobDetailFactoryBean detailFactoryBeanForPlan(PlanTasks scheduledTasks){  
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean ();  
        //这儿设置对应的Job对象  
        bean.setTargetObject (scheduledTasks);  
        //这儿设置对应的方法名  SchedledConfiguration.java与执行具体任务调度类中的方法名对应  
        bean.setTargetMethod ("work");  
        bean.setConcurrent (false);  
        return bean;  
    }
  
    @Bean(name = "cronTriggerBeanForPlan")  
    public CronTriggerFactoryBean cronTriggerBeanForPlan(@Qualifier("detailFactoryBeanForPlan") MethodInvokingJobDetailFactoryBean detailFactoryBeanForPlan){  
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean ();  
        trigger.setJobDetail (detailFactoryBeanForPlan.getObject ());  
        try {  
            trigger.setCronExpression ("0 0 0 * * ?");//Executed in the first day of every months.
        	//trigger.setCronExpression ("0/50 * * ? * *");
        } catch (ParseException e) {  
            e.printStackTrace ();  
        }  
        return trigger;  
  
    }  
  
    @Bean  
    public SchedulerFactoryBean schedulerFactory(@Qualifier("cronTriggerBeanForPlan") CronTriggerFactoryBean cronTriggerBeanForPlan){  
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean ();  
        schedulerFactory.setTriggers(cronTriggerBeanForPlan.getObject());  
        return schedulerFactory;  
    }  
}
