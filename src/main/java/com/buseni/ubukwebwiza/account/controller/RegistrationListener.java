package com.buseni.ubukwebwiza.account.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

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

    @Value("${support.email}")
	private String supportEmail;

    // API

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
    	UserAccount user = event.getUser();
    	String token = UUID.randomUUID().toString();
    	userAccountService.createVerificationTokenForUser(user, token);

    	final SimpleMailMessage email = constructEmailMessage(event, user, token);
    	try{
    		mailSender.send(email);
    	}catch(Exception me){
    		LOGGER.error("signup error sending email: " + me.getMessage());
    		throw me;
    	}
    }

    //

    private final SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final UserAccount user, final String token) {
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = event.getAppUrl() + "/regitration-confirm?token=" + token;
        final String message = messages.getMessage("message.regSuccEmail", null, event.getLocale());
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(supportEmail);
        return email;
    }

}
