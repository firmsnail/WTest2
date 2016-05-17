package com.worksap.stm2016.scheduledTask;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.expression.ParseException;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class NotificationSchedleConfiguration {
	
	@Bean(name = "detailFactoryBeanForNotification")  
    public MethodInvokingJobDetailFactoryBean detailFactoryBean1(NotificationTasks scheduledTasks){  
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean ();  
        //这儿设置对应的Job对象  
        bean.setTargetObject (scheduledTasks);  
        //这儿设置对应的方法名  与执行具体任务调度类中的方法名对应  
        bean.setTargetMethod ("work");  
        bean.setConcurrent (false);  
        return bean;  
    }
  
    @Bean(name = "cronTriggerBean1")  
    public CronTriggerFactoryBean cronTriggerBeanForNotification(@Qualifier("detailFactoryBeanForNotification") MethodInvokingJobDetailFactoryBean detailFactoryBeanForNotification){  
        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean ();  
        trigger.setJobDetail (detailFactoryBeanForNotification.getObject ());  
        try {  
            trigger.setCronExpression ("0/5 * * ? * *");//每5秒执行一次  
        } catch (ParseException e) {  
            e.printStackTrace ();  
        }  
        return trigger;  
  
    }
	
    @Bean  
    public SchedulerFactoryBean schedulerFactory1(@Qualifier("cronTriggerBeanForNotification") CronTriggerFactoryBean cronTriggerBeanForNotification){  
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean ();  
        schedulerFactory.setTriggers(cronTriggerBeanForNotification.getObject());  
        return schedulerFactory;  
    }
}
