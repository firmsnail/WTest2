package com.worksap.stm2016.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


@Controller
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        //If exception has a ResponseStatus annotation then use its response code
        ResponseStatus responseStatusAnnotation = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
        System.out.println("@exceptionHandler here!");
        
        return buildModelAndViewErrorPage(request, response, ex, responseStatusAnnotation != null ? responseStatusAnnotation.value() : HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @RequestMapping("*")
    public String fallbackHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	System.out.println("@fallbackHandler start!");
        return buildModelAndViewErrorPage(request, response, null, HttpStatus.NOT_FOUND);
    }

    private String buildModelAndViewErrorPage(HttpServletRequest request, HttpServletResponse response, Exception ex, HttpStatus httpStatus) {
        response.setStatus(httpStatus.value());
        
        if (httpStatus.is5xxServerError()) {
        	return "/error/permission-denied";
        } else {
        	return "/error/general-error";
        }
        
        //System.out.println("request: " + request.getParameterMap());
        //System.out.println("httpStatus.value: " + httpStatus.value());
        //System.out.println("httpStatus.other: " + httpStatus.is4xxClientError());
        
        /*
        ModelAndView mav = new ModelAndView("error.html");
        if (ex != null) {
            mav.addObject("title", ex);
        }
        mav.addObject("content", request.getRequestURL());
        
        */
        //return "/error/permission-denied";
    }

}