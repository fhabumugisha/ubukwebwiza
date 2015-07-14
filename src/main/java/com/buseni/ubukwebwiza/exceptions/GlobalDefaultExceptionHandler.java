package com.buseni.ubukwebwiza.exceptions;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.view.RedirectView;

import com.buseni.ubukwebwiza.breadcrumbs.navigation.NavigationEntry;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "frontend/error";
    public static final String DEFAULT_AJAX_ERROR_VIEW = "frontend/ajaxError";
    public static final String NOT_FOUND_ERROR_VIEW = "frontend/404notfound";
    public static final String NAVIGATION_PATH = "navigationPath";
   
    public  static final Logger LOGGER = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);
    
    private String ajaxDefaultErrorMessage = "An error has occurred";
    private boolean ajaxShowTechMessage = true;
   
    
    
    @ExceptionHandler({NoHandlerFoundException.class,ResourceNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ModelAndView handleExceptiond (HttpServletRequest request) {
            ModelAndView mav = new ModelAndView();
           // mav.addObject("exception", ex);
           // mav.addObject("url", req.getRequestURL());
            HttpSession currentSession = request.getSession();
            currentSession.setAttribute(NAVIGATION_PATH, new ArrayList<NavigationEntry>());
            mav.setViewName(NOT_FOUND_ERROR_VIEW);
            return mav;
    }
  
    @ExceptionHandler({CookieTheftException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public RedirectView handleCookieTheftException (HttpServletRequest request) {
    	LOGGER.debug("IN : handleCookieTheftException, redirecting to home page");
            RedirectView  redirectView = new RedirectView("/", true);           
            HttpSession currentSession = request.getSession();
            currentSession.setAttribute(NAVIGATION_PATH, new ArrayList<NavigationEntry>());
            return redirectView;
    }

    
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, HttpServletResponse response,Exception e) throws Exception {
       LOGGER.error("IN: defaultErrorHandler :" + e.getMessage());
       
    	// If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it 
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }
        if( isAjax(req) ) {
            String exceptionMessage = ajaxDefaultErrorMessage;
            if( ajaxShowTechMessage ){
                exceptionMessage += "\n" + getExceptionMessage(e);
            }
            
            ModelAndView m = new ModelAndView(DEFAULT_AJAX_ERROR_VIEW);
            m.addObject("exceptionMessage", exceptionMessage);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return m;
        }

        // Otherwise setup and send the user to a default error-view.
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        HttpSession currentSession = req.getSession();
        currentSession.setAttribute(NAVIGATION_PATH, new ArrayList<NavigationEntry>());
        
        return mav;
    }
    
    private boolean isAjax(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
    
    private String getExceptionMessage(Throwable e) {
        String message = "";
        while( e != null ) {
            message += e.getMessage() + "\n";
            e = e.getCause();
        }
        return message;
    }

}