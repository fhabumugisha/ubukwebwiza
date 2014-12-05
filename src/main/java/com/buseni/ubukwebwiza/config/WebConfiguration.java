package com.buseni.ubukwebwiza.config;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.buseni.ubukwebwiza.breadcrumbs.interceptor.SetUpNavigationPathInterceptor;
//@ActiveProfiles("embedded")
@Configuration
@ComponentScan(basePackages = {"com.buseni.ubukwebwiza"})
@PropertySource("classpath:application.properties")
@EnableWebMvc
@EnableWebMvcSecurity
@Import({PersistenceMySqlConfig.class, ServiceConfiguration.class, ViewConfiguration.class, ControllerConfiguration.class, SecurityConfig.class})
public class WebConfiguration extends WebMvcConfigurerAdapter{
	
	@Autowired
	private Environment env;
	// Maps resources path to webapp/resources
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
			registry.addResourceHandler("/images/**").addResourceLocations("file:"+env.getProperty("files.location"));
		}
		
		@Override
		public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
			//converters.add(jsonHttpMessageConverter());
			converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		}
		
		@Override
		public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
			PageableHandlerMethodArgumentResolver resolver =	new PageableHandlerMethodArgumentResolver();
			resolver.setFallbackPageable(new PageRequest(1, 3));
			resolver.setMaxPageSize(3);
			
			argumentResolvers.add(resolver);
		}
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(new SetUpNavigationPathInterceptor());
		}
		
	/*	@Bean(name="simpleMappingExceptionResolver")
	    public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
	        SimpleMappingExceptionResolver r =
	              new SimpleMappingExceptionResolver();

	        Properties mappings = new Properties();
	        mappings.setProperty("BusinessException", "businessException");
	        mappings.setProperty("DatabaseException", "databaseError");
	        mappings.setProperty("InvalidCreditCardException", "creditCardError");

	        r.setExceptionMappings(mappings);  // None by default
	        r.setDefaultErrorView("error");    // No default
	        r.setExceptionAttribute("ex");     // Default is "exception"
	       // r.setWarnLogCategory("example.MvcLogger");     // No default
	        return r;
	    }*/
		@Bean
		public static PropertySourcesPlaceholderConfigurer properties(){
		   PropertySourcesPlaceholderConfigurer pspc =
		      new PropertySourcesPlaceholderConfigurer();
		   Resource[] resources = new ClassPathResource[ ]
		      { new ClassPathResource( "application.properties" ) };
		  pspc.setLocations( resources );
		  pspc.setIgnoreUnresolvablePlaceholders( true );
		  return pspc;
		}
}
