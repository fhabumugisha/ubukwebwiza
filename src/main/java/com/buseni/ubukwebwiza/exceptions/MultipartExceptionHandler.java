package com.buseni.ubukwebwiza.exceptions;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

public class MultipartExceptionHandler extends OncePerRequestFilter {
	 public  static final Logger LOGGER = LoggerFactory.getLogger(MultipartExceptionHandler.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	LOGGER.info("MultipartExceptionHandler Filtering" );
        try {
            filterChain.doFilter(request, response);
        } catch (MaxUploadSizeExceededException e) {
        	LOGGER.debug("MultipartExceptionHandler MaxUploadSizeExceededException " );
            handle(request, response, e);
        } catch (ServletException e) {
            if(e.getRootCause() instanceof MaxUploadSizeExceededException) {
            	LOGGER.debug("MultipartExceptionHandler MaxUploadSizeExceededException " );
                handle(request, response, (MaxUploadSizeExceededException) e.getRootCause());
            } else {
                throw e;
            }
        }
    }

    private void handle(HttpServletRequest request,
            HttpServletResponse response, MaxUploadSizeExceededException e) throws ServletException, IOException {

        String redirect = UrlUtils.buildFullRequestUrl(request) + "?error";
        response.sendRedirect(redirect);
        LOGGER.info("Sending redirect to {} " , redirect);
        // forward to error page.
        /*RequestDispatcher dispatcher = request.getRequestDispatcher(errorPage);
        dispatcher.forward(request, response);*/
        
    }

}