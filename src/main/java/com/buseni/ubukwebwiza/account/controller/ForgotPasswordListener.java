package com.buseni.ubukwebwiza.account.controller;

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

import com.buseni.ubukwebwiza.provider.domain.Provider;

@Component
public class ForgotPasswordListener implements ApplicationListener<ResetPasswordEvent> {
	
	public  static final Logger LOGGER = LoggerFactory.getLogger(ForgotPasswordListener.class);
    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
	 private TemplateEngine templateEngine;

    @Value("${support.email}")
	private String supportEmail;

  
    @Override
    public void onApplicationEvent(ResetPasswordEvent event) {
        this.sendResetPasswordMail(event.getAppUrl(),  event.getToken(), event.getProvider(), event.getLocale());
    }

      
    public void sendResetPasswordMail(String contextPath, String token, Provider provider, final Locale locale) {
		String url = contextPath + "/reset-password?id="+ provider.getAccount().getId() + "&token=" + token;
		String resetPasswordText = messages.getMessage("message.resetPasswordText", null,	locale);
		String resetPasswordText2 = messages.getMessage("message.resetPasswordText2", null,	locale);
		String resetPasswordLinkText = messages.getMessage("message.resetPasswordLinkText", null,	locale);

		// Prepare the evaluation context
		final Context ctx = new Context(locale);
		ctx.setVariable("name", provider.getBusinessName());
		ctx.setVariable("emailText", resetPasswordText);
		ctx.setVariable("resetLink", url);
		ctx.setVariable("clickHereText", resetPasswordLinkText);
		ctx.setVariable("afterLinkText", resetPasswordText2);
	//	ctx.setVariable("imageResourceName", "logo.jpg"); // so that we can reference it from HTML
		try { 
			// Prepare message using a Spring helper
			final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			final MimeMessageHelper message =
					new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
			message.setSubject(messages.getMessage("message.resetPasswordSubject", null,	locale));
			message.setFrom(supportEmail);
			message.setTo(provider.getAccount().getEmail());

			// Create the HTML body using Thymeleaf
			final String htmlContent = this.templateEngine.process("resetPasswordMail", ctx);
			message.setText(htmlContent, true);			// true = isHtml
			/*Resource resource = null;
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
			// TODO Auto-generated catch block
			LOGGER.error(e1.getLocalizedMessage());
		} 
	}

}
