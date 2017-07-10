package com.buseni.ubukwebwiza.config;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.dialect.springdata.SpringDataDialect;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;


import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingStrategy;

@Configuration 
@PropertySource("classpath:email.properties")
public class ViewConfiguration  {
	
	 private static final String MESSAGE_SOURCE = "/WEB-INF/i18n/messages";
	 private static final String VIEWS = "/WEB-INF/views/";
	 private static final String EMAILS = "mails-templates/";

	 @Autowired
	private Environment env;
	 
	 

	 @Bean 
	public ITemplateResolver templateResolver() {
		 SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setPrefix(VIEWS);
		resolver.setSuffix(".html");
		resolver.setTemplateMode("HTML5");
		resolver.setCharacterEncoding("UTF-8");
		
		resolver.setOrder(1);
		resolver.addTemplateAlias("frontendHeader", "frontend/fragments/header");
		resolver.addTemplateAlias("frontendFooter", "frontend/fragments/footer");
		resolver.addTemplateAlias("frontendSidebar", "frontend/fragments/sidebar");
		resolver.addTemplateAlias("accountMenu", "frontend/fragments/menu");
		resolver.addTemplateAlias("util", "util/navigation");
		return resolver;
	}
	
	 @Bean
	 public ClassLoaderTemplateResolver emailTemplateResolver(){
		 ClassLoaderTemplateResolver  emailTemplateResolver = new ClassLoaderTemplateResolver();
		 emailTemplateResolver.setPrefix(EMAILS);
		 emailTemplateResolver.setSuffix(".html");
		 emailTemplateResolver.setTemplateMode("HTML5");
		 emailTemplateResolver.setCharacterEncoding("UTF-8");
		 emailTemplateResolver.setOrder(2);
		 return emailTemplateResolver;
	 }
	@Bean 
	public TemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		//engine.setTemplateResolver(templateResolver());
		 final Set<ITemplateResolver> templateResolvers = new HashSet<ITemplateResolver>();
		    templateResolvers.add(templateResolver());
		    templateResolvers.add(emailTemplateResolver());
		    engine.setTemplateResolvers(templateResolvers);
		//engine.addTemplateResolver(emailTemplateResolver());
		//engine.addTemplateResolver(templateResolver());
		
		engine.addDialect(new LayoutDialect(new GroupingStrategy()));
		engine.addDialect(new SpringSecurityDialect());
		engine.addDialect(new SpringDataDialect());
		//engine.addDialect(new DandelionDialect());
		//engine.addDialect(new DataTablesDialect());
		return engine;
	}
	
	@Bean 
	public ThymeleafViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setOrder(0);
		resolver.setCharacterEncoding("UTF-8");
		resolver.setContentType("text/html; charset=UTF-8");
		return resolver;
	}
	
	 @Bean(name = "messageSource")
	    public MessageSource messageSource() {
	        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	        messageSource.setBasename(MESSAGE_SOURCE);
	        messageSource.setCacheSeconds(5);
	        messageSource.setUseCodeAsDefaultMessage(true);
	        messageSource.setDefaultEncoding("UTF-8");
	        return messageSource;
	    }
	 
	 @Bean
	 public MultipartResolver createMultipartResolver() {
	     /*CommonsMultipartResolver resolver=new CommonsMultipartResolver();
	     resolver.setDefaultEncoding("utf-8");*/
	     return new StandardServletMultipartResolver();
	 }
	 
	 @Bean
	    public JavaMailSenderImpl javaMailSenderImpl() {
	        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
	        mailSenderImpl.setHost(env.getProperty("mail.server.smtp.host"));
	        mailSenderImpl.setPort(env.getProperty("mail.server.smtp.port", Integer.class));
	        mailSenderImpl.setProtocol(env.getProperty("mail.server.smtp.protocol"));
	     //  mailSenderImpl.setUsername(env.getProperty("mail.server.username"));
	     //   mailSenderImpl.setPassword(env.getProperty("mail.server.password"));
	        mailSenderImpl.setUsername(System.getProperty("mail.server.username"));
	        mailSenderImpl.setPassword(System.getProperty("mail.server.password"));
	        Properties javaMailProps = new Properties();
	        javaMailProps.put("mail.smtp.auth", true);
	        javaMailProps.put("mail.smtp.starttls.enable", true);
	        mailSenderImpl.setJavaMailProperties(javaMailProps);
	        return mailSenderImpl;
	    }
	
}
