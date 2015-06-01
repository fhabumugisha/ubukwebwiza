package com.buseni.ubukwebwiza.account.controller;

import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ubukwebwiza.administrator.domain.Administrator;
import com.buseni.ubukwebwiza.administrator.domain.PasswordResetToken;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.home.HomeController;
import com.buseni.ubukwebwiza.provider.service.ProviderService;

@Controller
@Navigation(url="/login", name="Login" , parent = HomeController.class)
public class LoginController {

	
	public  static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private ProviderService  providerService;
	
	 @Autowired
	 private MessageSource messages;

	 @Autowired
	 private JavaMailSender mailSender;
	 
	@Value("${support.email}")
	private String supportEmail;
	
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(Model model){
		return "frontend/account/login";
	}
	
	@RequestMapping(value="/forgotPassword", method=RequestMethod.GET)
	public String forgotPassword(Model model){
		return "frontend/account/forgotPassword";
	}
	
	
	
	@RequestMapping(value="/resetPassword", method=RequestMethod.GET)
	public String resetPassword(HttpServletRequest request,Locale locale, @RequestParam("id") Integer id,  @RequestParam("token") String token, RedirectAttributes model){
		/*PasswordResetToken passToken = providerService.getPasswordResetToken(token);
	   // Administrator user = passToken.getAdministrator();
	    if (passToken == null || passToken.getAdministrator().getId() != id) {
	        String error = messages.getMessage("auth.message.invalidToken", null, locale);
	        LOGGER.error(error);
	        model.addFlashAttribute("error", error);
	        return "redirect:/adminlogin";
	    }
	 
	    Calendar cal = Calendar.getInstance();
	    if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	    	String error = messages.getMessage("auth.message.expired", null, locale);
	    	LOGGER.error(error);
	        model.addFlashAttribute("error", error);
	        return "redirect:/adminlogin";
	    }
	    Administrator user = passToken.getAdministrator();
	    Authentication auth = new UsernamePasswordAuthenticationToken(user, null, providerService.loadUserByUsername(user.getEmail()).getAuthorities());
	    SecurityContextHolder.getContext().setAuthentication(auth);*/
		
		return "redirect:/changePassword";
	}
	
	
	@RequestMapping(value="/changePassword", method=RequestMethod.GET)
	public String changePassword(Model model){
		return "frontend/account/changePassword";
	}
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	//@ResponseBody
	public String savePassword(HttpServletRequest request, @RequestParam("password" ) String password, @RequestParam("passwordConfirm") String passwordConfirm, RedirectAttributes attributes) {
	  if(!password.equals(passwordConfirm)){
		  String error = messages.getMessage("PasswordMatches.user", null, request.getLocale());		
	    	LOGGER.error(error);
			attributes.addFlashAttribute("error", error);			
			return "redirect:/changePassword";
	  }		
		/*Administrator admin = (Administrator) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    providerService.changeAdministratorPassword(admin, password);
	    String message = messages.getMessage("message.resetPasswordSuc", null, request.getLocale());			
		attributes.addFlashAttribute("message", message);	*/	
	    return "redirect:/login";
	}
	
	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	//@ResponseBody
	public String sendResetPassword(HttpServletRequest request, @RequestParam("email") String userEmail, RedirectAttributes attributes) {
	     
	    /*Administrator admin = providerService.findByEmail(userEmail);
	    if (admin == null) {	    	
	    	String error = messages.getMessage("message.resetPasswordInvalidEmail", null, request.getLocale());		
	    	LOGGER.error(error);
			attributes.addFlashAttribute("error", error);
			attributes.addFlashAttribute("email", userEmail);	
			return "redirect:/adminForgotPassword";
	    }
	 
	    String token = UUID.randomUUID().toString();
	    providerService.createPasswordResetTokenForAdministrator(admin, token);
	    String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	    SimpleMailMessage email = constructResetTokenEmail(appUrl, request.getLocale(), token, admin);
	   
		mailSender.send(email);	
	   
	    LOGGER.info("email sent");
	    String message = messages.getMessage("message.resetPasswordEmail", null, request.getLocale());			
		attributes.addFlashAttribute("message", message);	*/			
		return "redirect:/adminForgotPassword";	
	}
	
	private SimpleMailMessage constructResetTokenEmail(String contextPath,
			Locale locale, String token, Administrator administrator) {
		String url = contextPath + "/adminResetPassword?id="+ administrator.getId() + "&token=" + token;
		String message = messages.getMessage("message.resetPassword", null,	locale);
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(administrator.getEmail());
		email.setSubject("Reset Password");
		email.setText(message + "\r\n" + url);
		email.setFrom(supportEmail);
		return email;
	}
	
	@ModelAttribute("currentMenu")
	public String module(){
		return "login";
	}
}
