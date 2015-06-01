package com.buseni.ubukwebwiza.account.controller;



import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ubukwebwiza.account.beans.SignupForm;
import com.buseni.ubukwebwiza.account.beans.VerificationToken;
import com.buseni.ubukwebwiza.administration.controller.GenericResponse;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.exceptions.ErrorsHelper;
import com.buseni.ubukwebwiza.home.HomeController;
import com.buseni.ubukwebwiza.provider.domain.District;
import com.buseni.ubukwebwiza.provider.domain.Provider;
import com.buseni.ubukwebwiza.provider.domain.WeddingService;
import com.buseni.ubukwebwiza.provider.service.DistrictService;
import com.buseni.ubukwebwiza.provider.service.ProviderService;
import com.buseni.ubukwebwiza.provider.service.WeddingServiceManager;

@Controller
@Navigation(url="/signup", name="Sign up" , parent = HomeController.class)
public class SignupController {

	public  static final Logger LOGGER = LoggerFactory.getLogger(SignupController.class);
	
	@Autowired
	private ProviderService  providerService;
	@Autowired
	private WeddingServiceManager weddingServiceManager;
	
	@Autowired
	private DistrictService districtService;
	
	 @Autowired
	 private MessageSource messages;
	 

	 @Autowired
	 private JavaMailSender mailSender;
	 
	@Value("${support.email}")
	private String supportEmail;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String goToSignup(Model model){
		model.addAttribute("signupForm", new SignupForm());
		return "frontend/account/signup";
	}
	
	@RequestMapping(value="/signupError", method=RequestMethod.GET)
	public String registrationError(Model model){
		return "frontend/account/signupError";
	}
	

	@RequestMapping(value="/signupSuccess", method=RequestMethod.GET)
	public String signupSuccess(Model model){
		return "frontend/account/signupSuccess";
	}
	
	@RequestMapping(value="/newTokenSent", method=RequestMethod.GET)
	public String newtokensent(Model model){
		return "frontend/account/newTokenSent";
	}
	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signup(@RequestParam(value="enterHere", required=false) String enterHere, @Valid @ModelAttribute SignupForm signupForm,  
			BindingResult result, RedirectAttributes attributes,  HttpServletRequest request){
	
		//If enterHere is filled it is a spam
		if(StringUtils.isNotEmpty(enterHere)){
			return "redirect:/";
		}
		//Validation errors	
		if (result.hasErrors()) {
			LOGGER.error("signup error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.signupForm", result);
			attributes.addFlashAttribute("signupForm", signupForm);
			return "frontend/account/signup";

		}
		try {
			Provider provider = providerService.createAccount(signupForm);
			String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(provider, request.getLocale(), appUrl));
		} catch (BusinessException e) {
			ErrorsHelper.rejectErrors(result, e.getErrors());
			LOGGER.error("signup error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.signupForm", result);
			attributes.addFlashAttribute("signupForm", signupForm);
			return "frontend/account/signup";
		}catch (Exception me) {
			LOGGER.error("signup error sending email: " + me.getMessage());
			result.reject("error.sendingverificationEmail");
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.signupForm", result);
			attributes.addFlashAttribute("signupForm", signupForm);
			return "frontend/account/signup";
	   
	    }
		
		String message  =  messages.getMessage("message.regSucc", null, request.getLocale());		
		attributes.addFlashAttribute("message", message);
		return "redirect:/signupSuccess";
	}

	
	@RequestMapping(value = "/regitrationConfirm", method = RequestMethod.GET)
	public String confirmRegistration (WebRequest request, @RequestParam("token") String token) {
	    Locale locale = request.getLocale();
	     
	    VerificationToken verificationToken = providerService.getVerificationToken(token);
	    if (verificationToken == null) {
	        String error = messages.getMessage("auth.message.invalidToken", null, locale);
	        LOGGER.error(error);
	        return "redirect:/signupError?error";
	    }
	     
	    Provider user = verificationToken.getProvider();
	    Calendar cal = Calendar.getInstance();
	    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	    	String error = messages.getMessage("auth.message.expired", null, locale);
	        LOGGER.error(error);
	        return "redirect:/signupError?expired=true&token="+token;
	    } 
	     
	    user.setEnabled(true); 
	    providerService.update(user);
	   // String message = messages.getMessage("message.accountVerified", null, locale);
	    return "redirect:/login?verified"; 
	}
	
	@RequestMapping(value = "/resendRegistrationToken", method = RequestMethod.GET)
	public String resendRegistrationToken(
	  HttpServletRequest request, @RequestParam("token") String existingToken, RedirectAttributes redirectAttributes) {
	    VerificationToken newToken = providerService.generateNewVerificationToken(existingToken);
	     
	    Provider user = providerService.getProviderByVerificationToken(newToken.getToken());
	    String appUrl =  "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	    SimpleMailMessage email =   constructResendVerificationTokenEmail(appUrl, request.getLocale(), newToken, user);
	    mailSender.send(email);
	 
	    String message = messages.getMessage("message.resendToken", null, request.getLocale());
	    redirectAttributes.addFlashAttribute("message", message);
	    return "redirect:/newTokenSent";
	}
	
	
	private SimpleMailMessage constructResendVerificationTokenEmail (String appUrl, Locale locale, VerificationToken newToken, Provider user) {
	    String confirmationUrl =       appUrl + "/regitrationConfirm?token=" + newToken.getToken();
	    String message = messages.getMessage("message.regSuccEmail", null, locale);
	    SimpleMailMessage email = new SimpleMailMessage();
	    email.setSubject("Resend Registration Token");
	    email.setText(message + " \r\n" + confirmationUrl);
	    email.setFrom(supportEmail);
	    email.setTo(user.getEmail());
	    return email;
	}
	
	
	@ModelAttribute("allWeddingServices")
	public List<WeddingService> populateWeddingServices(){
		return weddingServiceManager.findByEnabled(Boolean.TRUE);
	}
	
	@ModelAttribute("allDistricts")
	public List<District> populateDistricts(){
		return districtService.findByEnabled(Boolean.TRUE);
	}

	@ModelAttribute("currentMenu")
	public String module(){
		return "signup";
	}
}
