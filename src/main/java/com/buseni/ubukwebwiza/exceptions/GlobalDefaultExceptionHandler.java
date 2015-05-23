package com.buseni.ubukwebwiza.exceptions;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.buseni.ubukwebwiza.breadcrumbs.navigation.NavigationEntry;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "frontend/error";
    public static final String NOT_FOUND_ERROR_VIEW = "frontend/404notfound";
    public static final String NAVIGATION_PATH = "navigationPath";
   
    public  static final Logger LOGGER = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);
    
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
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
   // @ResponseBody
	public ModelAndView handleMaxUploadSizeExceededException(
			final HttpServletRequest request,
			final HttpServletResponse response, final Exception e)	 {
	   ModelAndView mav = new ModelAndView();
		LOGGER.error(e.getMessage());
		LOGGER.debug(e.getMessage());
		System.out.println(e.getMessage());
		System.out.println(UrlUtils.buildFullRequestUrl(request));
		LOGGER.debug(UrlUtils.buildFullRequestUrl(request));
		CustomError c = new CustomError("error.file.maxsizeexceeded");
		c.setErrorArgs(new String[] { ((MaxUploadSizeExceededException) e)
				.getMaxUploadSize() + " byte." });
		 mav.setViewName("adminpanel/provider/editPhoto");
		 mav.addObject("errors", "File size should be less then " + ((MaxUploadSizeExceededException) e).getMaxUploadSize()+ " byte.");
		return mav;
	}
    
/*    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
   // @ResponseBody
	protected ModelAndView handleGenericMultipartException(
			final HttpServletRequest request,
			final HttpServletResponse response, final Exception e)
			 {
		LOGGER.error(e.getMessage());
		CustomError c = new CustomError("error.internaluploadexception");
		 ModelAndView mav = new ModelAndView();
		 mav.addObject("errors", "File upload error");
		return mav;
	}*/
    
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
       LOGGER.error(e.getMessage());
    	// If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;
        // Otherwise setup and send the user to a default error-view.
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        HttpSession currentSession = req.getSession();
        currentSession.setAttribute(NAVIGATION_PATH, new ArrayList<NavigationEntry>());
        /*if (e instanceof MaxUploadSizeExceededException) {
        	LOGGER.error("errors : File size should be less then " + ((MaxUploadSizeExceededException) e).getMaxUploadSize()+ " byte.");

		}*/
        return mav;
    }
}