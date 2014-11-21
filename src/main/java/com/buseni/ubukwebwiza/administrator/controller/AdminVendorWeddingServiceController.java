package com.buseni.ubukwebwiza.administrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.vendor.domain.VendorWeddingService;
import com.buseni.ubukwebwiza.vendor.service.VendorWeddingServiceManager;
import com.buseni.ubukwebwiza.vendor.utils.PageWrapper;
@Controller
public class AdminVendorWeddingServiceController {
	
	@Autowired
	private VendorWeddingServiceManager vendorWeddingServiceManager;
	
	@RequestMapping(value="/admin/vendorweddingservices",method=RequestMethod.GET)
	public String vendors(Model model, Pageable page){				
		Page<VendorWeddingService> vendorWeddingServicePage  =  vendorWeddingServiceManager.findAll(page);		
		PageWrapper<VendorWeddingService> pageWrapper = new PageWrapper<VendorWeddingService>(vendorWeddingServicePage, "/vendorweddingservices");
		model.addAttribute("page", pageWrapper);
	//	model.addAttribute("currentMenu", "vendorweddingservices");
		model.addAttribute("vendorWeddingServices", vendorWeddingServicePage.getContent());		
		return "adminpanel/vendorweddingservice/listing";
	}

	
	@ModelAttribute("currentMenu")
	public String module(){
		return "vendorweddingservices";
	}
}
