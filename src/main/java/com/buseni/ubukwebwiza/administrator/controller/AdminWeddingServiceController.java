package com.buseni.ubukwebwiza.administrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.vendor.domain.WeddingService;
import com.buseni.ubukwebwiza.vendor.service.WeddingServiceManager;
import com.buseni.ubukwebwiza.vendor.utils.PageWrapper;
@Controller

@RequestMapping(value="/admin/weddingServices")
public class AdminWeddingServiceController {
	
	@Autowired
	private WeddingServiceManager weddingServiceManager;
	
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String vendors(Model model, Pageable page){				
		Page<WeddingService> weddingServicePage  =  weddingServiceManager.findAll(page);		
		PageWrapper<WeddingService> pageWrapper = new PageWrapper<WeddingService>(weddingServicePage, "/weddingServices");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("currentMenu", "weddingServices");
		model.addAttribute("weddingServices", weddingServicePage.getContent());		
		return "adminpanel/weddingservice/listing";
	}

}
