package com.buseni.ubukwebwiza.administrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.vendor.domain.Vendor;
import com.buseni.ubukwebwiza.vendor.service.VendorService;
import com.buseni.ubukwebwiza.vendor.utils.PageWrapper;

@Controller
public class AdminVendorController {

	@Autowired
	private VendorService vendorService;
	
	@RequestMapping(value="/admin/vendors",method=RequestMethod.GET)
	public String vendors(Model model, Pageable page){				
		Page<Vendor> vendorPage  =  vendorService.findAll(page);		
		PageWrapper<Vendor> pageWrapper = new PageWrapper<Vendor>(vendorPage, "/vendors");
		model.addAttribute("page", pageWrapper);
		//model.addAttribute("currentMenu", "vendors");
		model.addAttribute("vendors", vendorPage.getContent());		
		return "adminpanel/vendor/listing";
	}
	
	@ModelAttribute("currentMenu")
	public String module(){
		return "vendors";
	}
}
