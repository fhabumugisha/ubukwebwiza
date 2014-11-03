package com.buseni.ubukwebwiza.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.core.domain.CodeDistrict;
import com.buseni.ubukwebwiza.core.domain.CodeWeddingService;
import com.buseni.ubukwebwiza.core.service.CodeDistrictService;
import com.buseni.ubukwebwiza.core.service.CodeWeddingServiceManager;

@Controller
public class SiteController {

	
	@Autowired
	private CodeWeddingServiceManager codeWeddingServiceManager;
	
	@Autowired
	private CodeDistrictService codeDistrictService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(){
		return "frontend/index";
	}
	
	
	@ModelAttribute("allWeddingServices")
	public List<CodeWeddingService> populateWeddingServices(){
		return codeWeddingServiceManager.findByActiveFlag(1);
	}
	
	@ModelAttribute("allDistricts")
	public List<CodeDistrict> populateDistricts(){
		return codeDistrictService.findByActiveFlagGrouped(1);
	}
	
}
