package com.buseni.ubukwebwiza.vendor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.vendor.domain.Vendor;
import com.buseni.ubukwebwiza.vendor.service.VendorService;

@Controller
@Navigation(url="/vendors/details", name="Provider", parent= ListVendorController.class)
@SessionAttributes({"allWeddingServices","allDistricts"})
public class DetailVendorController {

	public static final Logger LOGGER = LoggerFactory.getLogger( DetailVendorController.class );

	/*@Autowired
	private WeddingServiceManager weddingServiceManager;

	@Autowired
	private DistrictService districtService;*/

	@Autowired
	private VendorService vendorService;



	@RequestMapping(value="/vendors/details{id}",method=RequestMethod.GET)
	public String getVendor(@RequestParam Integer id, Model model){
		Vendor vendor = vendorService.getVendor(id);
		model.addAttribute("vendor", vendor);
		return "frontend/vendor/detailVendor";
	}

	
/*	@ModelAttribute("allWeddingServices")
	public List<WeddingService> populateWeddingServices(){
		return weddingServiceManager.findByEnabled(Boolean.TRUE);
	}

	@ModelAttribute("allDistricts")
	public List<District> populateDistricts(){
		return districtService.findByEnabled(Boolean.TRUE);
	}*/
	@ModelAttribute("currentMenu")
	public String module(){
		return "vendors";
	}
}
