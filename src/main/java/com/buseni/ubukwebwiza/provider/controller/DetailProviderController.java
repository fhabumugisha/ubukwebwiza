package com.buseni.ubukwebwiza.provider.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.home.HomeController;
import com.buseni.ubukwebwiza.provider.domain.Provider;
import com.buseni.ubukwebwiza.provider.service.ProviderService;

@Controller
@Navigation(url="/wedding-service-providers{id:[\\d]+}", name="provider", parent={ ListProviderController.class, HomeController.class})
public class DetailProviderController {

	public static final Logger LOGGER = LoggerFactory.getLogger( DetailProviderController.class );


	@Autowired
	private ProviderService providerService;
	
	/*@RequestMapping(value="/wedding-service-providers/{id:[\\d]+}",method=RequestMethod.GET)
	public String getProvider(@PathVariable Integer id, Model model){		
		Provider provider = providerService.getProvider(id);
		
		model.addAttribute("provider", provider);		
		return "frontend/provider/detailProvider";
	}*/
	

	@RequestMapping(value="/wedding-service-providers/{urlName}",method=RequestMethod.GET)
	public String getProvider(@PathVariable String urlName, Model model){		
		Provider provider = providerService.getProvider(urlName);
		
		model.addAttribute("provider", provider);		
		return "frontend/provider/detailProvider";
	}
	
	

	@ModelAttribute("currentMenu")
	public String module(){
		return "providers";
	}
	
	@ModelAttribute("showSidebar")
	public boolean showSidebar(){
		return true;
	}
}
