package com.worksap.stm2016.controller;


import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.worksap.stm2016.model.CurrentUser;

@ControllerAdvice
public class CurrentUserControllerAdvice {
	
	@ModelAttribute("currentUser")
    public CurrentUser getCurrentUser(Authentication authentication) {
		
        return (authentication == null) ? null : (CurrentUser) authentication.getPrincipal();
    }
	
}
