package com.buseni.ubukwebwiza.administrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.vendor.domain.CodeProvince;
import com.buseni.ubukwebwiza.vendor.service.CodeProvinceService;
import com.buseni.ubukwebwiza.vendor.utils.PageWrapper;

@Controller
@RequestMapping(value="/admin/provinces", method=RequestMethod.GET)
public class AdminProvinceController {

	@Autowired
	private CodeProvinceService codeProvinceService;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String districts(Model model, Pageable page){
		Page<CodeProvince> pageProvince = codeProvinceService.findAll(page);
		PageWrapper<CodeProvince> pageWrapper = new PageWrapper<CodeProvince>(pageProvince, "/provinces");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("currentMenu", "provinces");
		model.addAttribute("provinces", pageProvince.getContent());	
		return "adminpanel/province/listing";
	}
	
	
}
