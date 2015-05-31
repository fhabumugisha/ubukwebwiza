package com.buseni.ubukwebwiza.account.controller;



import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ubukwebwiza.account.beans.SignupForm;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.exceptions.ErrorsHelper;
import com.buseni.ubukwebwiza.exceptions.BusinessException;
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
	private WeddingServiceManager weddingServiceManager;
	
	@Autowired
	private DistrictService districtService;

	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public String goToSignup(Model model){
		model.addAttribute("signupForm", new SignupForm());
		return "frontend/account/signup";
	}

	@RequestMapping(value="/signup", method=RequestMethod.POST)
	public String signup(@RequestParam(value="enterHere", required=false) String enterHere, @Valid @ModelAttribute SignupForm signupForm,  BindingResult result, RedirectAttributes attributes){
	
		//If enterHere is filled it is a spam
		if(StringUtils.isNotEmpty(enterHere)){
			return "redirect:/signup";
		}
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.error("signup error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.signupForm", result);
			attributes.addFlashAttribute("signupForm", signupForm);
			return "frontend/account/signup";

		}
		try {
			providerService.createAccount(signupForm);
		} catch (BusinessException e) {
			ErrorsHelper.rejectErrors(result, e.getErrors());
			LOGGER.error("signup error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.signupForm", result);
			attributes.addFlashAttribute("signupForm", signupForm);
			return "frontend/account/signup";
		}
		attributes.addFlashAttribute("message", "Your account have been created! Thank you for joing us! Now can can complete your profile");
		return "redirect:/signup";
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
