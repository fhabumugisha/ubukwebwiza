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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ubukwebwiza.account.domain.PasswordResetToken;
import com.buseni.ubukwebwiza.account.domain.UserAccount;
import com.buseni.ubukwebwiza.account.service.UserAccountService;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.home.HomeController;

@Controller
@Navigation(url="/login", name="Login" , parent = HomeController.class)
public class LoginController {

	
	public  static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	/*@Autowired
	private ProviderService  providerService;*/
	
	@Autowired
	private UserAccountService userAccountService;
	
	 @Autowired
	 private MessageSource messages;

	 @Autowired
	 private JavaMailSender mailSender;
	 
	@Value("${support.email}")
	private String supportEmail;
	
	
	@RequestMapping(value="/profile/login", method=RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error, Model model){
		return "frontend/account/login";
	}
	
	@RequestMapping(value="/forgot-password", method=RequestMethod.GET)
	public String forgotPassword(Model model){
		return "frontend/account/forgotPassword";
	}
	
	
	
	@RequestMapping(value="/reset-password", method=RequestMethod.GET)
	public String resetPassword(HttpServletRequest request,Locale locale, @RequestParam("id") Integer id,  @RequestParam("token") String token, RedirectAttributes model){
		PasswordResetToken passToken = userAccountService.getPasswordResetToken(token);
	   
	    if (passToken == null || passToken.getUserAccount().getId() != id) {
	        String error = messages.getMessage("auth.message.invalidToken", null, locale);
	        LOGGER.error(error);
	        model.addFlashAttribute("error", error);
	        return "redirect:/login";
	    }
	 
	    Calendar cal = Calendar.getInstance();
	    if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	    	String error = messages.getMessage("auth.message.expired", null, locale);
	    	LOGGER.error(error);
	        model.addFlashAttribute("error", error);
	        return "redirect:/login";
	    }
	    UserAccount account = passToken.getUserAccount();
	    UserDetails userDetails  = userAccountService.loadUserByUsername(account.getEmail());
	    Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	    SecurityContextHolder.getContext().setAuthentication(auth);
		
		return "redirect:/change-password";
	}
	
	
	@RequestMapping(value="/change-password", method=RequestMethod.GET)
	public String changePassword(Model model){
		return "frontend/account/changePassword";
	}
	
	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_PROVIDER')")
	//@ResponseBody
	public String savePassword(HttpServletRequest request, @RequestParam("password" ) String password, @RequestParam("passwordConfirm") String passwordConfirm, RedirectAttributes attributes) {
	  if(!password.equals(passwordConfirm)){
		  String error = messages.getMessage("error.passwordMatches", null, request.getLocale());		
	    	LOGGER.error(error);
			attributes.addFlashAttribute("error", error);			
			return "redirect:/change-password";
	  }		
	  UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	  UserAccount account  =  userAccountService.findByUsername(user.getUsername());
		userAccountService.changeUserPassword(account, password);
	    String message = messages.getMessage("message.resetPasswordSuc", null, request.getLocale());			
		attributes.addFlashAttribute("message", message);
	    return "redirect:/login";
	}
	
	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
	public String sendResetPassword(HttpServletRequest request, @RequestParam("email") String userEmail, RedirectAttributes attributes) {
	     
	    UserAccount account = userAccountService.findByUsername(userEmail);
	    if (account == null) {	    	
	    	String error = messages.getMessage("message.resetPasswordInvalidEmail", null, request.getLocale());		
	    	LOGGER.error(error);
			attributes.addFlashAttribute("error", error);
			attributes.addFlashAttribute("email", userEmail);	
			return "redirect:/forgot-password";
	    }
	 
	    String token = UUID.randomUUID().toString();
	    userAccountService.createPasswordResetTokenForUser(account, token);
	    String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	    SimpleMailMessage email = constructResetTokenEmail(appUrl, request.getLocale(), token, account);
	   
		mailSender.send(email);	
	   
	    LOGGER.info("email sent");
	    String message = messages.getMessage("message.resetPasswordEmail", null, request.getLocale());			
		attributes.addFlashAttribute("message", message);		
		return "redirect:/forgot-password";	
	}
	
	private SimpleMailMessage constructResetTokenEmail(String contextPath,
			Locale locale, String token, UserAccount user) {
		String url = contextPath + "/reset-password?id="+ user.getId() + "&token=" + token;
		String message = messages.getMessage("message.resetPassword", null,	locale);
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("Reset Password");
		email.setText(message + "\r\n" + url);
		email.setFrom(supportEmail);
		return email;
	}
	
	@ModelAttribute("currentMenu")
	public String module(){
		return "login";
	}
	@ModelAttribute("showSidebar")
	public boolean showSidebar(){
		return true;
	}
}
