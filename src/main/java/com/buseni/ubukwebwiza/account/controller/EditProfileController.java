package com.buseni.ubukwebwiza.account.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.account.service.UserAccountService;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.home.HomeController;
import com.buseni.ubukwebwiza.provider.domain.Provider;
import com.buseni.ubukwebwiza.provider.service.ProviderService;

@Controller
@Navigation(url="/profile", name="My profile" , parent = HomeController.class)
public class EditProfileController {
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private ProviderService providerService;
	
	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String myAccount(Principal principal, Model model){
		Provider provider = providerService.findByUsername(principal.getName());
		model.addAttribute("provider", provider);
		return "frontend/account/editProfile";
	}
	
	@ModelAttribute("currentMenu")
	public String module(){
		return "profile";
	}

}
