package com.buseni.ubukwebwiza.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.buseni.ubukwebwiza.core.domain.CodeDistrict;
import com.buseni.ubukwebwiza.core.domain.CodeWeddingService;
import com.buseni.ubukwebwiza.core.domain.Vendor;
import com.buseni.ubukwebwiza.core.service.CodeDistrictService;
import com.buseni.ubukwebwiza.core.service.CodeWeddingServiceManager;
import com.buseni.ubukwebwiza.core.service.VendorService;
import com.buseni.ubukwebwiza.utils.VendorSearch;

@Controller
//@SessionAttributes({"allDistricts", "allWeddingServices"})
public class SiteController {

	
	@Autowired
	private CodeWeddingServiceManager codeWeddingServiceManager;
	
	@Autowired
	private CodeDistrictService codeDistrictService;
	
	@Autowired
	private VendorService vendorService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(VendorSearch vendorSearch){
		return "frontend/index";
	}
	
	
	@ModelAttribute("allWeddingServices")
	public List<CodeWeddingService> populateWeddingServices(){
		return codeWeddingServiceManager.findByActiveFlag(1);
	}
	
	@ModelAttribute("allDistricts")
	public List<CodeDistrict> populateDistricts(){
		return codeDistrictService.findByActiveFlag(1);
	}
	
	@ModelAttribute("featuredVendors")
	public List<Vendor> populateFeaturedVendors(){
		return vendorService.getFeaturedVendors();
	}
}
