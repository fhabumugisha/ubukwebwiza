package com.buseni.ubukwebwiza.administrator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.vendor.domain.CodeDistrict;
import com.buseni.ubukwebwiza.vendor.service.CodeDistrictService;
import com.buseni.ubukwebwiza.vendor.utils.PageWrapper;

@Controller
@RequestMapping(value="/admin/districts", method=RequestMethod.GET)
public class AdminDistrictController {

	@Autowired
	private CodeDistrictService codeDistrictService;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String districts(Model model, Pageable page){
		Page<CodeDistrict> pageDistrict = codeDistrictService.findAll(page);
		PageWrapper<CodeDistrict> pageWrapper = new PageWrapper<CodeDistrict>(pageDistrict, "/districts");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("currentMenu", "districts");
		model.addAttribute("districts", pageDistrict.getContent());	
		return "adminpanel/district/listing";
	}
	
	@ModelAttribute("allDistricts")
	public List<CodeDistrict> populateDistricts(){
		return codeDistrictService.findByActiveFlag(1);
	}
}
