package com.buseni.ubukwebwiza.administrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.vendor.domain.Province;
import com.buseni.ubukwebwiza.vendor.service.ProvinceService;
import com.buseni.ubukwebwiza.vendor.utils.PageWrapper;

@Controller
public class AdminProvinceController {

	@Autowired
	private ProvinceService codeProvinceService;
	
	@RequestMapping(value="/admin/provinces",method=RequestMethod.GET)
	public String districts(Model model, Pageable page){
		Page<Province> pageProvince = codeProvinceService.findAll(page);
		PageWrapper<Province> pageWrapper = new PageWrapper<Province>(pageProvince, "/provinces");
		model.addAttribute("page", pageWrapper);
		//model.addAttribute("currentMenu", "provinces");
		model.addAttribute("provinces", pageProvince.getContent());	
		return "adminpanel/province/listing";
	}
	
	@ModelAttribute("currentMenu")
	public String module(){
		return "provinces";
	}
	
}
