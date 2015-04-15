package com.buseni.ubukwebwiza.home;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.vendor.beans.VendorSearch;
import com.buseni.ubukwebwiza.vendor.domain.District;
import com.buseni.ubukwebwiza.vendor.domain.Vendor;
import com.buseni.ubukwebwiza.vendor.domain.WeddingService;
import com.buseni.ubukwebwiza.vendor.service.DistrictService;
import com.buseni.ubukwebwiza.vendor.service.PhotoService;
import com.buseni.ubukwebwiza.vendor.service.VendorService;
import com.buseni.ubukwebwiza.vendor.service.WeddingServiceManager;

@Controller
@SessionAttributes({"allWeddingServices","allDistricts"})
@Navigation(url="/", name="Home")
public class HomeController {

	
	@Autowired
	private WeddingServiceManager weddingServiceManager;
	
	@Autowired
	private DistrictService districtService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private PhotoService photoService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(VendorSearch vendorSearch){
		return "frontend/index";
	}

	
	@ModelAttribute("allWeddingServices")
	public List<WeddingService> populateWeddingServices(){
		return weddingServiceManager.findByEnabled(Boolean.TRUE);
	}
	
	@ModelAttribute("allDistricts")
	public List<District> populateDistricts(){
		return districtService.findByEnabled(Boolean.TRUE);
	}
	
	@ModelAttribute("featuredVendors")
	public List<Vendor> populateFeaturedVendors(){
		return vendorService.getFeaturedVendors();
	}
	@ModelAttribute("currentMenu")
	public String module(){
		return "home";
	}
}
