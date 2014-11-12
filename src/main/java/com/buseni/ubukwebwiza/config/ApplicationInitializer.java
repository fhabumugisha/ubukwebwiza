/**
 * 
 */
package com.buseni.ubukwebwiza.config;

import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author habumugisha
 *
 */
public class ApplicationInitializer implements WebApplicationInitializer {

	/* (non-Javadoc)
	 * @see org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet.ServletContext)
	 */
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(ApplicationConfiguration.class);
		rootContext.setDisplayName("Ubukwe bwiza");
		servletContext.addListener(new ContextLoaderListener(rootContext));
		ServletRegistration.Dynamic dispatcher = 
				servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));
		dispatcher.setLoadOnStartup(1); 
		dispatcher.addMapping("/");
		
		Dynamic filter = servletContext.addFilter("characterEncodingFilter", CharacterEncodingFilter.class);
		filter.addMappingForUrlPatterns(null, false, "/*");
		filter.setInitParameter("encoding", "UTF-8");
		filter.setInitParameter("forceEncoding", "true");
		
		servletContext.addFilter("corsFilter", CorsFilter.class)
			.addMappingForUrlPatterns(null, false, "/*");
		
		servletContext.addFilter("openEntityManagerInViewFilter", OpenEntityManagerInViewFilter.class)
		.addMappingForUrlPatterns(null, false, "/*");
		
		servletContext.addFilter("hiddenHttpMethodFilter", HiddenHttpMethodFilter.class)
			.addMappingForUrlPatterns(null, false, "/*");

	}

}
