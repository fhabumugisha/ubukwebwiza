package com.buseni.ubukwebwiza.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;


public class AjaxSessionExpirationFilter extends  OncePerRequestFilter {
	public  static final Logger LOGGER = LoggerFactory.getLogger(AjaxSessionExpirationFilter.class);
	private int customSessionExpiredErrorCode = 901;



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpSession currentSession = ((HttpServletRequest) request).getSession(false);
		if (currentSession == null) {
			String ajaxHeader = ((HttpServletRequest) request).getHeader("X-Requested-With");
			if ("XMLHttpRequest".equals(ajaxHeader)) {				
				LOGGER.info("Ajax call detected, send {} error code", this.customSessionExpiredErrorCode);
				 if(request.getRequestURI().contains("profile")){
					 response.setHeader("redirectUrl", request.getContextPath()+"/profile/signin");
				 }else if(request.getRequestURI().contains("admin")){
					 response.setHeader("redirectUrl", request.getContextPath()+"/admin/signin");
				 }else{
					 response.setHeader("redirectUrl", request.getContextPath()+"/");
				 }
				
				HttpServletResponse resp = (HttpServletResponse) response;
				resp.sendError(this.customSessionExpiredErrorCode);
			} else {
				filterChain.doFilter(request, response);
			}
		} else {
			filterChain.doFilter(request, response);
		}
		
	}
}