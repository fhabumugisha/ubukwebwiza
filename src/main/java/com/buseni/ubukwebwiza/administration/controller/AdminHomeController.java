package com.buseni.ubukwebwiza.administration.controller;



import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ubukwebwiza.administrator.domain.Administrator;
import com.buseni.ubukwebwiza.administrator.domain.PasswordResetToken;
import com.buseni.ubukwebwiza.administrator.service.AdministratorService;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.contactus.domain.ContactusForm;
import com.buseni.ubukwebwiza.contactus.service.ContactusService;
@Controller
//@SessionAttributes({"allDistricts", "allWeddingServices"})
@Navigation(url="/admin" ,name = "Dashbord")
public class AdminHomeController {
	public  static final Logger LOGGER = LoggerFactory.getLogger(AdminHomeController.class);
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private ContactusService contactusService;
	
	 @Autowired
	 private MessageSource messages;

	 @Autowired
	 private JavaMailSender mailSender;
	 
	@Value("${support.email}")
	private String supportEmail;
	 
	
	
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public String home(Model model){
		model.addAttribute("currentMenu", "dashbord");
		return "adminpanel/dashbord";
	}

	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public String sign(Model model){
		model.addAttribute("currentMenu", "dashbord");
		return "adminpanel/dashbord";
	}
	
	@RequestMapping(value="/adminlogin", method=RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
			  @RequestParam(value = "logout", required = false) String logout, 
	          HttpServletRequest request,Model model){
		//model.addAttribute("currentMenu", "dashbord");
		if (error != null) {
/*			model.addAttribute("error", "Invalid username and password!");
*/ 
			//login form for update page
                        //if login error, get the targetUrl from session again.
			String targetUrl = getRememberMeTargetUrlFromSession(request);
			LOGGER.info(targetUrl);
			if(StringUtils.isNotEmpty(targetUrl)){
				model.addAttribute("targetUrl", targetUrl);
				model.addAttribute("loginUpdate", true);
			}
 
		}
 
		/*if (logout != null) {
			model.addAttribute("msg", "You've been logged out successfully.");
		}*/
		return "adminpanel/signin";
	}
	
	@RequestMapping(value="/adminForgotPassword", method=RequestMethod.GET)
	public String forgotPassword(Model model){
		return "adminpanel/forgotPassword";
	}
	
	
	
	@RequestMapping(value="/adminResetPassword", method=RequestMethod.GET)
	public String adminResetPassword(HttpServletRequest request,Locale locale, @RequestParam("id") Integer id,  @RequestParam("token") String token, RedirectAttributes model){
		PasswordResetToken passToken = administratorService.getPasswordResetToken(token);
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
	    Authentication auth = new UsernamePasswordAuthenticationToken(user, null, administratorService.loadUserByUsername(user.getEmail()).getAuthorities());
	    SecurityContextHolder.getContext().setAuthentication(auth);
		
		return "redirect:/adminChangePassword";
	}
	
	
	@RequestMapping(value="/adminChangePassword", method=RequestMethod.GET)
	public String adminChangePassword(Model model){
		return "adminpanel/changePassword";
	}
	@RequestMapping(value = "/adminChangePassword", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	//@ResponseBody
	public String adminSavePassword(HttpServletRequest request, @RequestParam("password" ) String password, @RequestParam("passwordConfirm") String passwordConfirm, RedirectAttributes attributes) {
	  if(!password.equals(passwordConfirm)){
		  String error = messages.getMessage("PasswordMatches.user", null, request.getLocale());		
	    	LOGGER.error(error);
			attributes.addFlashAttribute("error", error);			
			return "redirect:/adminChangePassword";
	  }		
		Administrator admin = (Administrator) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    administratorService.changeAdministratorPassword(admin, password);
	    String message = messages.getMessage("message.resetPasswordSuc", null, request.getLocale());			
		attributes.addFlashAttribute("message", message);		
	    return "redirect:/adminlogin";
	}
	
	@RequestMapping(value = "/adminForgotPassword", method = RequestMethod.POST)
	//@ResponseBody
	public String adminForgotPassword(HttpServletRequest request, @RequestParam("email") String userEmail, RedirectAttributes attributes) {
	     
	    Administrator admin = administratorService.findByEmail(userEmail);
	    if (admin == null) {	    	
	    	String error = messages.getMessage("message.resetPasswordInvalidEmail", null, request.getLocale());		
	    	LOGGER.error(error);
			attributes.addFlashAttribute("error", error);
			attributes.addFlashAttribute("email", userEmail);	
			return "redirect:/adminForgotPassword";
	    }
	 
	    String token = UUID.randomUUID().toString();
	    administratorService.createPasswordResetTokenForAdministrator(admin, token);
	    String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	    SimpleMailMessage email = constructResetTokenEmail(appUrl, request.getLocale(), token, admin);
	   
		mailSender.send(email);	
	   
	    LOGGER.info("email sent");
	    String message = messages.getMessage("message.resetPasswordEmail", null, request.getLocale());			
		attributes.addFlashAttribute("message", message);				
		return "redirect:/adminForgotPassword";	
	}
	
	

	//for 403 access denied page
		@RequestMapping(value = "/admin403", method = RequestMethod.GET)
		public String accesssDenied(Model model) {	 
		  //check if user is login
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();	
			model.addAttribute("username", userDetail.getUsername());
		  }
		  return "adminpanel/403";
		}
		  
		@ModelAttribute("unreadMessages")
		public int unreadMessages(){
			List<ContactusForm>  unreadMessages = contactusService.findUnread();
			if(!CollectionUtils.isEmpty(unreadMessages)){
				return unreadMessages.size();
				
			}
			return 0;
		}
		
		/**
		 * get targetURL from session
		 */
		private String getRememberMeTargetUrlFromSession(HttpServletRequest request){
			String targetUrl = "";
			HttpSession session = request.getSession(false);
			if(session!=null){
				targetUrl = session.getAttribute("targetUrl")==null?""
	                             :session.getAttribute("targetUrl").toString();
			}
			return targetUrl;
		}
		
		@ModelAttribute("currentMenu")
		public String getCurrentMenu(){
			return "dashbord";
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

}
