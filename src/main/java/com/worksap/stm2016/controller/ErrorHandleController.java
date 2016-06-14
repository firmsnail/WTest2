package com.worksap.stm2016.controller;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.worksap.stm2016.model.CurrentUser;


@Controller  
public class ErrorHandleController implements ErrorController {  
  
	private static Logger Log = Logger.getLogger(ErrorHandleController.class);  
	
	private static final String ERROR_PATH = "/error";  
   
	@RequestMapping(value=ERROR_PATH)  
	public String handleError(){  
		SecurityContext ctx =  SecurityContextHolder.getContext();
		if (ctx != null) {
			System.out.println("ctx : " + ctx.toString());
		} else {
			System.out.println("ctx: null");
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
			Log.info("@handleError loged-in!");
			CurrentUser curUser = (CurrentUser) auth.getPrincipal();
			if (curUser != null) {
				Log.info("@handleError user: " + curUser.getId());
			} else {
				Log.info("@handleError user: null");
			}
	        //return "redirect:/user/profile?userId="+curUser.getId();
		}
        return "error/general-error";  
    }

	@Override  
	public String getErrorPath() {  
		return ERROR_PATH;  
	}  
}  