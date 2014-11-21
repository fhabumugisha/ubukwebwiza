package com.buseni.ubukwebwiza.administrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.vendor.domain.WeddingService;
import com.buseni.ubukwebwiza.vendor.service.WeddingServiceManager;
import com.buseni.ubukwebwiza.vendor.utils.PageWrapper;

@Controller
public class AdminWeddingServiceController {


	@Autowired
	private WeddingServiceManager weddingServiceManager;
	
	@RequestMapping(value="/admin/weddingservices", method=RequestMethod.GET)
	public String typesWeddingService(Model model, Pageable page){
		Page<WeddingService> weddingServicePage = weddingServiceManager.findAll(page);
		PageWrapper<WeddingService> pageWrapper = new PageWrapper<WeddingService>(weddingServicePage, "/weddingservices");
		model.addAttribute("page", pageWrapper);
		//model.addAttribute("currentMenu", "weddingservices");
		model.addAttribute("weddingServices", weddingServicePage.getContent());	
		
		return "adminpanel/weddingservice/listing";
	}
	
	
	@ModelAttribute("currentMenu")
	public String module(){
		return "weddingservices";
	}
}
