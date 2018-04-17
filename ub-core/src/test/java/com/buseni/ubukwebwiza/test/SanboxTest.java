package com.buseni.ubukwebwiza.test;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.buseni.ubukwebwiza.account.domain.UserAccount;
import com.buseni.ubukwebwiza.account.repository.UserAccountRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
@WebAppConfiguration
//@Transactional

public class SanboxTest {
	

	
	
	
	@Autowired
	private JavaMailSenderImpl mailSender;
	@Test
	@Ignore
	public void sendMail(){
		

		
		try { 
			// Prepare message using a Spring helper
			final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
			final MimeMessageHelper message =
					new MimeMessageHelper(mimeMessage, false, "UTF-8"); // true = multipart
		
			message.setSubject("Test");
			message.setFrom("test@ubukwebwiza.com");
			message.setTo("me@ubukwebwiza.com");

			message.setText("Hello JavaMailSender");	
			// Send mail
			this.mailSender.send(mimeMessage);
		} catch (MessagingException e1) {
			System.out.println("signup error sending email: " + e1.getMessage());
			
		} 
		
	     
		
	}

}
