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

import com.buseni.ubukwebwiza.provider.beans.MessageDto;

@Component
public class ContactProviderListener implements ApplicationListener<ContactProviderEvent> {
	
	public  static final Logger LOGGER = LoggerFactory.getLogger(ContactProviderListener.class);
   
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
	 private TemplateEngine templateEngine;
    
    @Autowired
    private MessageSource messages;

    @Value("${support.email}")
	private String supportEmail;

  
    @Override
    public void onApplicationEvent(ContactProviderEvent event) {
        this.sendMail(event.getAppUrl(),  event.getMessageDto(), event.getLocale());
    }

      
    public void sendMail(String contextPath,  MessageDto  messageDto, final Locale locale) {
		String url = contextPath + "/profile/messages/read?id="+ messageDto.getId();
		
		String readMessageText = messages.getMessage("message.readMessageText", null,	locale);
		// Prepare the evaluation context
		final Context ctx = new Context(locale);
		ctx.setVariable("name", messageDto.getProviderName());
		//ctx.setVariable("emailText", messageDto.getComment());
		ctx.setVariable("senderName", messageDto.getSenderName());
		/*ctx.setVariable("senderEmail", messageDto.getSenderEmail());
		ctx.setVariable("senderPhonenumber", messageDto.getSenderPhonenumber());*/
	   
		ctx.setVariable("readMessageLink", url);
		ctx.setVariable("readMessageText", readMessageText);
		//ctx.setVariable("clickHereText", resetPasswordLinkText);
		//ctx.setVariable("afterLinkText", resetPasswordText2);
	//	ctx.setVariable("imageResourceName", "logo.jpg"); // so that we can reference it from HTML
		try { 
			// Prepare message using a Spring helper
			final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			final MimeMessageHelper message =
					new MimeMessageHelper(mimeMessage, true, "UTF-8"); // true = multipart
			message.setSubject(messageDto.getSubject());
			message.setFrom(supportEmail);
			message.setTo(messageDto.getProviderEmail());

			// Create the HTML body using Thymeleaf
			final String htmlContent = this.templateEngine.process("contactProviderMail", ctx);
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
