package com.worksap.stm2016;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import com.worksap.stm2016.utils.EmailUtils;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
//@SpringBootApplication
public class Application extends SpringBootServletInitializer {
/*
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
        return application.sources(Application.class);
    }*/
	
    public static void main(String[] args) {
    	EmailUtils.InitEmailUtils();
        SpringApplication.run(Application.class, args);
    }
}
