package com.buseni.ubukwebwiza.administration.controller;

import java.util.Locale;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.buseni.ubukwebwiza.account.controller.OnRegistrationCompleteEvent;
import com.buseni.ubukwebwiza.account.domain.UserAccount;
import com.buseni.ubukwebwiza.account.service.UserAccountService;

@Component
public class ProviderRegistrationListener implements ApplicationListener<OnProviderRegistrationCompleteEvent> {
	
	public  static final Logger LOGGER = LoggerFactory.getLogger(ProviderRegistrationListener.class);
   
    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
	 private SpringTemplateEngine templateEngine;

    @Value("${support.email}")
	private String supportEmail;

    // API

    @Override
    public void onApplicationEvent(OnProviderRegistrationCompleteEvent event) {
    	
        this.sendMailWithInline(event.getAppUrl(),  event.getBusinessName(), event.getUrlName(), event.getEmail(), event.getPassword(),  event.getLocale());
    }
 
	public void sendMailWithInline(String contextPath, String businessName, String urlName, String email, String password, final Locale locale) {
		String url = contextPath+"/wedding-service-providers/"+urlName ;
		String viewLinkText = messages.getMessage("message.admin.provider.registration.viewLinkText", null,	locale);
		 String emailText = messages.getMessage("message.admin.provider.registration.successEmail", null, locale);
		// Prepare the evaluation context
		final Context ctx = new Context(locale);
		ctx.setVariable("emailText", emailText);
		ctx.setVariable("businessName", businessName);
		ctx.setVariable("email", email);
		ctx.setVariable("password", password);
		ctx.setVariable("viewProfileLink", url);
		ctx.setVariable("clickHereText", viewLinkText);
		
		//ctx.setVariable("imageResourceName", "logo.jpg"); // so that we can reference it from HTML
		try { 
			// Prepare message using a Spring helper
			final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			final MimeMessageHelper message =
					new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
		
			message.setSubject(messages.getMessage("message.admin.provider.registration.emailSubject", null,	locale));
			message.setFrom(supportEmail);
			message.setTo(supportEmail);

			// Create the HTML body using Thymeleaf
			final String htmlContent = this.templateEngine.process("providerAddedMail", ctx);
			message.setText(htmlContent, true);	// true = isHtml
		

			// Send mail
			this.mailSender.send(mimeMessage);
		} catch (MessagingException e1) {
			LOGGER.error("signup error sending email: " + e1.getMessage());
		} 
	}

}
