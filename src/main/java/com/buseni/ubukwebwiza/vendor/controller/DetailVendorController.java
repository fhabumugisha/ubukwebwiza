package com.buseni.ubukwebwiza.vendor.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.home.HomeController;
import com.buseni.ubukwebwiza.vendor.domain.Vendor;
import com.buseni.ubukwebwiza.vendor.service.VendorService;

@Controller
@Navigation(url="/providers/{id:[\\d]+}", name="Provider", parent={ ListVendorController.class, HomeController.class})
@SessionAttributes({"allWeddingServices","allDistricts"})
public class DetailVendorController {

	public static final Logger LOGGER = LoggerFactory.getLogger( DetailVendorController.class );

	/*@Autowired
	private WeddingServiceManager weddingServiceManager;

	@Autowired
	private DistrictService districtService;*/

	@Autowired
	private VendorService vendorService;



	@RequestMapping(value="/providers/{id:[\\d]+}",method=RequestMethod.GET)
	public String getVendor(@PathVariable Integer id, Model model){
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
