package com.buseni.ubukwebwiza.provider.mail;

import java.util.Locale;

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

import com.buseni.ubukwebwiza.provider.domain.MessageAnswer;
import com.buseni.ubukwebwiza.provider.domain.Provider;

@Component
public class AnswerMessageListener implements ApplicationListener<AnswerMessageEvent> {
	
	public  static final Logger LOGGER = LoggerFactory.getLogger(AnswerMessageListener.class);
   
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
	 private TemplateEngine templateEngine;
    
    @Autowired
    private MessageSource messages;

    @Value("${support.email}")
	private String supportEmail;

  
    @Override
    public void onApplicationEvent(AnswerMessageEvent event) {
        this.sendMail(event.getAppUrl(),  event.getMessageAnswer(), event.getLocale());
    }

      
    public void sendMail(String contextPath,  MessageAnswer  messageAnswer, final Locale locale) {
    	Provider provider = messageAnswer.getMessage().getProvider();
		String url = contextPath + "/read-provider-message?id="+ messageAnswer.getId();
		
		String readMessageTextText = messages.getMessage("message.readMessageText", null,	locale);
		// Prepare the evaluation context
		final Context ctx = new Context(locale);
		ctx.setVariable("username", messageAnswer.getMessage().getSenderName());
		ctx.setVariable("emailText", messageAnswer.getMessage().getComment());
		ctx.setVariable("senderName", provider.getBusinessName());
		
	   
		ctx.setVariable("readMessageLink", url);
		ctx.setVariable("readMessageText", readMessageTextText);
		
		try { 
			// Prepare message using a Spring helper
			final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			final MimeMessageHelper message =
					new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
			String subject = messages.getMessage("message.answerMessageSubject", new String[]{ provider.getBusinessName()},	locale);
			message.setSubject(subject);
			message.setFrom(supportEmail);
			message.setTo(messageAnswer.getMessage().getSenderEmail());

			// Create the HTML body using Thymeleaf
			final String htmlContent = this.templateEngine.process("answerMessageMail", ctx);
			message.setText(htmlContent, true);			// true = isHtml
			

			// Send mail
			this.mailSender.send(mimeMessage);
		} catch (MessagingException e1) {
			// TODO Auto-generated catch block
			LOGGER.error(e1.getLocalizedMessage());
		} 
	}

}
