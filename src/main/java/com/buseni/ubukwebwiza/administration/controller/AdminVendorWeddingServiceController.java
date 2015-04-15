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
import com.buseni.ubukwebwiza.vendor.domain.VendorWeddingService;
import com.buseni.ubukwebwiza.vendor.service.VendorWeddingServiceManager;
import com.buseni.ubukwebwiza.vendor.utils.PageWrapper;
@Controller
@Navigation(url="/admin/vendorweddingservices", name="Vendor wedding services", parent= AdminHomeController.class)
public class AdminVendorWeddingServiceController {
	
	@Autowired
	private VendorWeddingServiceManager vendorWeddingServiceManager;
	
	@RequestMapping(value="/admin/vendorweddingservices",method=RequestMethod.GET)
	public String vendors(Model model, Pageable page){				
		Page<VendorWeddingService> vendorWeddingServicePage  =  vendorWeddingServiceManager.findAll(page);		
		PageWrapper<VendorWeddingService> pageWrapper = new PageWrapper<VendorWeddingService>(vendorWeddingServicePage, "/admin/vendorweddingservices");
		model.addAttribute("page", pageWrapper);
	//	model.addAttribute("currentMenu", "vendorweddingservices");
		model.addAttribute("vendorWeddingServices", vendorWeddingServicePage.getContent());		
		return "adminpanel/vendorweddingservice/listingVendorWeddingService";
	}

	
	@ModelAttribute("currentMenu")
	public String module(){
		return "vendorweddingservices";
	}
}
