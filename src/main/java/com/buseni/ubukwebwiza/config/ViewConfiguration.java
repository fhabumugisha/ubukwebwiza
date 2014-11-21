package com.buseni.ubukwebwiza.config;

import nz.net.ultraq.thymeleaf.LayoutDialect;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration 
public class ViewConfiguration {
	
	 private static final String MESSAGE_SOURCE = "/WEB-INF/i18n/messages";
	 private static final String VIEWS = "/WEB-INF/views/";
	@Bean 
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
		resolver.setPrefix(VIEWS);
		resolver.setSuffix(".html");
		resolver.setTemplateMode("HTML5");
		resolver.setOrder(1);
		resolver.addTemplateAlias("frontendHeader", "frontend/fragments/header");
		resolver.addTemplateAlias("frontendFooter", "frontend/fragments/footer");
		resolver.addTemplateAlias("frontendSidebar", "frontend/fragments/sidebar");
		resolver.addTemplateAlias("frontendPaginator", "frontend/fragments/paginator");
		return resolver;
	}
	
	@Bean 
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(templateResolver());
		engine.addDialect(new LayoutDialect());
		engine.addDialect(new SpringSecurityDialect());
		return engine;
	}
	
	@Bean 
	public ThymeleafViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		return resolver;
	}
	
	 @Bean(name = "messageSource")
	    public MessageSource messageSource() {
	        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	        messageSource.setBasename(MESSAGE_SOURCE);
	        messageSource.setCacheSeconds(5);
	        return messageSource;
	    }
}
