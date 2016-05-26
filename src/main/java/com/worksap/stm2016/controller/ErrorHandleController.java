package com.worksap.stm2016.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller  
public class ErrorHandleController implements ErrorController {  
  
 private static final String ERROR_PATH = "/error";  
   
 @RequestMapping(value=ERROR_PATH)  
    public String handleError(){  
        return "error/general-error";  
    }  
   
 @Override  
 public String getErrorPath() {  
  return ERROR_PATH;  
 }  
  
}  