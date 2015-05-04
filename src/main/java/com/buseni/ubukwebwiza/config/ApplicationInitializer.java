/**
 * 
 */
package com.buseni.ubukwebwiza.config;

import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author habumugisha
 *
 */
public class ApplicationInitializer implements WebApplicationInitializer {

	/* (non-Javadoc)
	 * @see org.springframework.web.WebApplicationInitializer#onStartup(javax.servlet.ServletContext)
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(WebConfiguration.class);
		//rootContext.getEnvironment().setActiveProfiles("dev");
		rootContext.setDisplayName("Ubukwe bwiza");
		servletContext.addListener(new ContextLoaderListener(rootContext));
		DispatcherServlet dispatcherServlet = new DispatcherServlet(rootContext);
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
		ServletRegistration.Dynamic dispatcher = 
				servletContext.addServlet("dispatcher", dispatcherServlet);
		dispatcher.setLoadOnStartup(1); 
		dispatcher.addMapping("/");
		 MultipartConfigElement mce = new MultipartConfigElement("", 1024*1024*1, 1024*1024*5*5, 1024*1024);
		dispatcher.setMultipartConfig(mce);
		Dynamic filter = servletContext.addFilter("characterEncodingFilter", CharacterEncodingFilter.class);
		filter.addMappingForUrlPatterns(null, false, "/*");
		filter.setInitParameter("encoding", "UTF-8");
		filter.setInitParameter("forceEncoding", "true");
		
		
		servletContext.addFilter("multipartFilter", MultipartFilter.class)
		.addMappingForUrlPatterns(null, false, "/*");
		servletContext.addFilter("corsFilter", CorsFilter.class)
			.addMappingForUrlPatterns(null, false, "/*");
		
		servletContext.addFilter("openEntityManagerInViewFilter", OpenEntityManagerInViewFilter.class)
		.addMappingForUrlPatterns(null, false, "/*");
		
		servletContext.addFilter("hiddenHttpMethodFilter", HiddenHttpMethodFilter.class)
			.addMappingForUrlPatterns(null, false, "/*");

	}

}
