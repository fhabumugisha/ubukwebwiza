package com.buseni.ubukwebwiza.administrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.vendor.domain.District;
import com.buseni.ubukwebwiza.vendor.service.DistrictService;
import com.buseni.ubukwebwiza.vendor.utils.PageWrapper;

@Controller
public class AdminDistrictController {

	@Autowired
	private DistrictService districtService;
	
	@RequestMapping(value="/admin/districts",method=RequestMethod.GET)
	public String districts(Model model, Pageable page){
		Page<District> pageDistrict = districtService.findAll(page);
		PageWrapper<District> pageWrapper = new PageWrapper<District>(pageDistrict, "/districts");
		model.addAttribute("page", pageWrapper);
		//model.addAttribute("currentMenu", "districts");
		model.addAttribute("districts", pageDistrict.getContent());	
		return "adminpanel/district/listing";
	}
	
	@ModelAttribute("currentMenu")
	public String module(){
		return "districts";
	}
}
