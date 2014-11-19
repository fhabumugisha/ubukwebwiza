package com.buseni.ubukwebwiza.config;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//@ActiveProfiles("embedded")
@Configuration
@ComponentScan(basePackages = {"com.buseni.ubukwebwiza"})
@PropertySource("classpath:application.properties")
@EnableWebMvc
@Import({PersistenceMySqlConfig.class, ServiceConfiguration.class, ViewConfiguration.class, ControllerConfiguration.class})
public class ApplicationConfiguration extends WebMvcConfigurerAdapter{
	// Maps resources path to webapp/resources
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		}
		
		@Override
		public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
			//converters.add(jsonHttpMessageConverter());
			converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		}
		
		@Override
		public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
			PageableHandlerMethodArgumentResolver resolver =	new PageableHandlerMethodArgumentResolver();
			resolver.setFallbackPageable(new PageRequest(1, 1));
			resolver.setMaxPageSize(1);
			
			argumentResolvers.add(resolver);
		}
		/*
		@Bean
		public OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor() {
			return new OpenEntityManagerInViewInterceptor();
		}*/
}
