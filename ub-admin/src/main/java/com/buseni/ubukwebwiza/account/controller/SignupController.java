package com.buseni.ubukwebwiza.account.controller;



import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.buseni.ubukwebwiza.account.domain.UserAccount;
import com.buseni.ubukwebwiza.account.domain.VerificationToken;
import com.buseni.ubukwebwiza.account.service.UserAccountService;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.exceptions.ErrorsHelper;
import com.buseni.ubukwebwiza.home.HomeController;
import com.buseni.ubukwebwiza.provider.domain.District;
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
	private UserAccountService userAccountService;
	
	@Autowired
	private WeddingServiceManager weddingServiceManager;
	
	@Autowired
	private DistrictService districtService;
	

	@Autowired
	private MessageSource messages;
	 

	
	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String goToSignup( @RequestParam(value = "timeout", required = false) String timeout,
			Model model,HttpServletRequest request){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(!(auth instanceof AnonymousAuthenticationToken)){
			model.asMap().clear();
			return "redirect:/";
		}
		if (timeout != null) {
			String errorMessage  =  messages.getMessage("message.sessiontimeout", null, request.getLocale());
			model.addAttribute("error", errorMessage);
		}
		model.addAttribute("signupForm", new SignupForm());
		return "frontend/account/signup";
	}
	
	@RequestMapping(value="/signup-error", method=RequestMethod.GET)
	public String registrationError(Model model){
		return "frontend/account/signupError";
	}
	

	@RequestMapping(value="/signup-success", method=RequestMethod.GET)
	public String signupSuccess(Model model){
		return "frontend/account/signupSuccess";
	}
	
	@RequestMapping(value="/new-token-sent", method=RequestMethod.GET)
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
			UserAccount provider = providerService.createAccount(signupForm);
			String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(provider, request.getLocale(), appUrl));
		} catch (BusinessException e) {
			ErrorsHelper.rejectErrors(result, e.getErrors());
			LOGGER.error("signup error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.signupForm", result);
			attributes.addFlashAttribute("signupForm", signupForm);
			return "frontend/account/signup";
		}
		
		String message  =  messages.getMessage("message.regSucc", null, request.getLocale());		
		attributes.addFlashAttribute("message", message);
		return "redirect:/signup-success";
	}

	
	@RequestMapping(value = "/regitration-confirm", method = RequestMethod.GET)
	public String confirmRegistration (WebRequest request, @RequestParam("token") String token, RedirectAttributes model) {	   
	     
	    VerificationToken verificationToken = userAccountService.getVerificationToken(token);
	    if (verificationToken == null) {
	        LOGGER.error("Invalid account confirmation token.");
	        model.addFlashAttribute("invalidToken", true);
	        return "redirect:/signup-error";
	    }
	     
	    UserAccount user = verificationToken.getUserAccount();
	    Calendar cal = Calendar.getInstance();
	    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	        LOGGER.error("Your registration token has expired.");
	       // model.addFlashAttribute("error", error);
	        model.addFlashAttribute("expired", true);
	        model.addFlashAttribute("token", token);
	        return "redirect:/signup-error";
	    } 
	     
	    user.setEnabled(true); 
	    userAccountService.update(user);
	    String message = messages.getMessage("message.accountVerified", null, request.getLocale());
	    model.addFlashAttribute("message", message);
	    return "redirect:/profile/signin?verified"; 
	}
	
	@RequestMapping(value = "/resend-registration-token", method = RequestMethod.GET)
	public String resendRegistrationToken(
	  HttpServletRequest request, @RequestParam("token") String existingToken, RedirectAttributes redirectAttributes) {
	    VerificationToken newToken = userAccountService.generateNewVerificationToken(existingToken);
	     
	    UserAccount userAccount = userAccountService.getUserByVerificationToken(newToken.getToken());
	    String appUrl =  "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	    eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userAccount, request.getLocale(), appUrl));
	 
	 
	    String message = messages.getMessage("message.resendToken", null, request.getLocale());
	    redirectAttributes.addFlashAttribute("message", message);
	    return "redirect:/new-token-sent";
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
