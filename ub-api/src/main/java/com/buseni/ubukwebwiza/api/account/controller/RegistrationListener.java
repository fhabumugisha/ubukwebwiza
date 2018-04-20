package com.buseni.ubukwebwiza.api.account.controller;

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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.buseni.ubukwebwiza.account.controller.OnRegistrationCompleteEvent;
import com.buseni.ubukwebwiza.account.domain.UserAccount;
import com.buseni.ubukwebwiza.account.service.UserAccountService;


@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
	
	public  static final Logger LOGGER = LoggerFactory.getLogger(RegistrationListener.class);
    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
	 private TemplateEngine templateEngine;

    @Value("${support.email}")
	private String supportEmail;

    // API

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
    	UserAccount userAccount = event.getUser();
    	String token = UUID.randomUUID().toString();
    	userAccountService.createVerificationTokenForUser(userAccount, token);
    	sendMailWithInline(event.getAppUrl(), token, userAccount, event.getLocale());
    	
    }

  
	public void sendMailWithInline(String contextPath, String token, UserAccount userAccount, final Locale locale) {
		String url = contextPath +  "/regitration-confirm?token=" + token;
		String activateAccountLinkText = messages.getMessage("message.registration.activateAccountLinkText", null,	locale);
		 String emailText = messages.getMessage("message.registration.successEmail", null, locale);
		// Prepare the evaluation context
		final Context ctx = new Context(locale);
		ctx.setVariable("emailText", emailText);
		ctx.setVariable("activateAccountLink", url);
		ctx.setVariable("clickHereText", activateAccountLinkText);
		
		//ctx.setVariable("imageResourceName", "logo.jpg"); // so that we can reference it from HTML
		try { 
			// Prepare message using a Spring helper
			final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			final MimeMessageHelper message =
					new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
		
			message.setSubject(messages.getMessage("message.registration.accountActivationTokenSubject", null,	locale));
			message.setFrom(supportEmail);
			message.setTo(userAccount.getEmail());

			// Create the HTML body using Thymeleaf
			final String htmlContent = this.templateEngine.process("accountActivationMail", ctx);
			message.setText(htmlContent, true);	// true = isHtml
		/*	Resource resource = null;
			try {
				resource = new UrlResource("https://s3.amazonaws.com/ubfiles/logo.jpg");
			} catch (MalformedURLException e) {
				LOGGER.error(e.getLocalizedMessage());
				e.printStackTrace();
			}
			message.addInline("logo.jpg", resource);*/

			// Send mail
			this.mailSender.send(mimeMessage);
		} catch (MessagingException e1) {
			LOGGER.error("signup error sending email: " + e1.getMessage());
		} 
	}

}
