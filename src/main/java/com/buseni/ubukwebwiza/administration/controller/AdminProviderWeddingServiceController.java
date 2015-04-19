package com.buseni.ubukwebwiza.administration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.utils.PageWrapper;
import com.buseni.ubukwebwiza.provider.domain.ProviderWeddingService;
import com.buseni.ubukwebwiza.provider.service.ProviderWeddingServiceManager;
@Controller
@Navigation(url="/admin/providerweddingservices", name="Provider wedding services", parent= AdminHomeController.class)
public class AdminProviderWeddingServiceController {
	
	@Autowired
	private ProviderWeddingServiceManager providerWeddingServiceManager;
	
	@RequestMapping(value="/admin/providerweddingservices",method=RequestMethod.GET)
	public String providers(Model model, Pageable page){				
		Page<ProviderWeddingService> providerWeddingServicePage  =  providerWeddingServiceManager.findAll(page);		
		PageWrapper<ProviderWeddingService> pageWrapper = new PageWrapper<ProviderWeddingService>(providerWeddingServicePage, "/admin/providerweddingservices");
		model.addAttribute("page", pageWrapper);
	//	model.addAttribute("currentMenu", "providerweddingservices");
		model.addAttribute("providerWeddingServices", providerWeddingServicePage.getContent());		
		return "adminpanel/providerweddingservice/listingProviderWeddingService";
	}

	
	@ModelAttribute("currentMenu")
	public String module(){
		return "providerweddingservices";
	}
}
