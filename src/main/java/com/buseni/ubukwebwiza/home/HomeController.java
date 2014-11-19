package com.buseni.ubukwebwiza.home;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.vendor.domain.CodeDistrict;
import com.buseni.ubukwebwiza.vendor.domain.CodeTypeWeddingService;
import com.buseni.ubukwebwiza.vendor.domain.Photo;
import com.buseni.ubukwebwiza.vendor.domain.Vendor;
import com.buseni.ubukwebwiza.vendor.service.CodeDistrictService;
import com.buseni.ubukwebwiza.vendor.service.CodeWeddingServiceManager;
import com.buseni.ubukwebwiza.vendor.service.PhotoService;
import com.buseni.ubukwebwiza.vendor.service.VendorService;
import com.buseni.ubukwebwiza.vendor.utils.VendorSearch;

@Controller
//@SessionAttributes({"allDistricts", "allWeddingServices"})
public class HomeController {

	
	@Autowired
	private CodeWeddingServiceManager codeWeddingServiceManager;
	
	@Autowired
	private CodeDistrictService codeDistrictService;
	
	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private PhotoService photoService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(VendorSearch vendorSearch){
		return "frontend/index";
	}

	@RequestMapping(value="/photos", method=RequestMethod.GET)
	public String photos(Model model){
		Page<Photo>  photos = photoService.findByActiveFlag(1, new PageRequest(0, 100));
		model.addAttribute("photos", photos.getContent());
		return "frontend/gallery/photoGallery";
	}
	
	
	@ModelAttribute("allWeddingServices")
	public List<CodeTypeWeddingService> populateWeddingServices(){
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
	@ModelAttribute("currentMenu")
	public String module(){
		return "home";
	}
}
