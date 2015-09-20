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

import com.buseni.ubukwebwiza.filters.CorsFilter;
import com.github.dandelion.core.web.DandelionFilter;
import com.github.dandelion.core.web.DandelionServlet;

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
		// Register the Root application context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(WebConfiguration.class);
		//rootContext.getEnvironment().setActiveProfiles("dev");
		rootContext.setDisplayName("Ubukwe bwiza");
		// Context loader listener
		servletContext.addListener(new ContextLoaderListener(rootContext));
		
		
		// Register the Spring dispatcher servlet
		DispatcherServlet dispatcherServlet = new DispatcherServlet(rootContext);
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);		
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet);
		dispatcher.setLoadOnStartup(1); 
		dispatcher.addMapping("/");
		
		// Register the Dandelion servlet
		ServletRegistration.Dynamic dandelionServlet = servletContext.addServlet("dandelionServlet",new DandelionServlet());
		dandelionServlet.setLoadOnStartup(2);
		dandelionServlet.addMapping("/dandelion-assets/*");
		
		// File upload config
		/*MultipartConfigElement mce = new MultipartConfigElement("", 1024*1024*1, 1024*1024*5*5, 1024*1024);
		dispatcher.setMultipartConfig(mce);*/
		
		Dynamic filter = servletContext.addFilter("characterEncodingFilter", CharacterEncodingFilter.class);
		filter.addMappingForUrlPatterns(null, false, "/*");
		filter.setInitParameter("encoding", "UTF-8");
		filter.setInitParameter("forceEncoding", "true");
		
		// Register the multipartExceptionHandler
		//servletContext.addFilter("multipartExceptionHandler", MultipartExceptionHandler.class).addMappingForUrlPatterns(null, false, "/*");
				
		// Register the multipartFilter
		//servletContext.addFilter("multipartFilter", MultipartFilter.class).addMappingForUrlPatterns(null, false, "/*");
	
		// Register the corsFilter
		servletContext.addFilter("corsFilter", CorsFilter.class).addMappingForUrlPatterns(null, false, "/*");
		
		// Register the Dandelion filter
		Dynamic dandelionFilter = servletContext.addFilter("dandelionFilter", new DandelionFilter());
		dandelionFilter.addMappingForUrlPatterns(null, false, "/*");
		
		// Register the openEntityManagerInViewFilter
		servletContext.addFilter("openEntityManagerInViewFilter", OpenEntityManagerInViewFilter.class).addMappingForUrlPatterns(null, false, "/*");
		
		// Register the hiddenHttpMethodFilter
		servletContext.addFilter("hiddenHttpMethodFilter", HiddenHttpMethodFilter.class).addMappingForUrlPatterns(null, false, "/*");
		
		/*// Register the AjaxSessionExpirationFilter
				servletContext.addFilter("ajaxSessionExpirationFilter", AjaxSessionExpirationFilter.class).addMappingForUrlPatterns(null, false, "/*");*/
		
		

	}

	
}
