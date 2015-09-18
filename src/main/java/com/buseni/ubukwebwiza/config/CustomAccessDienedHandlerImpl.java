package com.buseni.ubukwebwiza.config;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;

public class CustomAccessDienedHandlerImpl implements AccessDeniedHandler{
	// ~ Static fields/initializers
	// =====================================================================================

	protected static final Log logger = LogFactory.getLog(CustomAccessDienedHandlerImpl.class);

	// ~ Instance fields
	// ================================================================================================

	private String errorPage;

	// ~ Methods
	// ========================================================================================================

	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException,
			ServletException {
		if (!response.isCommitted()) {
			
			
			if (accessDeniedException instanceof MissingCsrfTokenException
			        || accessDeniedException instanceof InvalidCsrfTokenException) {

				 HttpSession currentSession = request.getSession(false);
				 if(currentSession  != null){
					 currentSession.setAttribute("browserSessionTimeout", true);
				 }
			    if(request.getRequestURI().contains("profile")){
			        response.sendRedirect(request.getContextPath()+"/profile/login");                                        
			    }
			    if(request.getRequestURI().contains("admin")){
			        response.sendRedirect(request.getContextPath()+"/admin/signin");                                        
			    }
			    if(request.getRequestURI().contains("signup")){
			    	
			        response.sendRedirect(request.getContextPath()+"/signup?timeout");                                        
			    }
			    if(request.getRequestURI().contains("contactus")){
			        response.sendRedirect(request.getContextPath()+"/contactus?timeout");                                        
			    }
			}else{
				
			
			if (errorPage != null) {
				// Put exception into request scope (perhaps of use to a view)
				request.setAttribute(WebAttributes.ACCESS_DENIED_403,
						accessDeniedException);

				// Set the 403 status code.
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);

				// forward to error page.
				RequestDispatcher dispatcher = request.getRequestDispatcher(errorPage);
				dispatcher.forward(request, response);
			}
			else {
				response.sendError(HttpServletResponse.SC_FORBIDDEN,
						accessDeniedException.getMessage());
			}}
		}
	}

	/**
	 * The error page to use. Must begin with a "/" and is interpreted relative to the
	 * current context root.
	 *
	 * @param errorPage the dispatcher path to display
	 *
	 * @throws IllegalArgumentException if the argument doesn't comply with the above
	 * limitations
	 */
	public void setErrorPage(String errorPage) {
		if ((errorPage != null) && !errorPage.startsWith("/")) {
			throw new IllegalArgumentException("errorPage must begin with '/'");
		}

		this.errorPage = errorPage;
	}
}
