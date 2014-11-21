package com.buseni.ubukwebwiza.vendor.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.buseni.ubukwebwiza.vendor.domain.CodeDistrict;
import com.buseni.ubukwebwiza.vendor.domain.CodeTypeWeddingService;
import com.buseni.ubukwebwiza.vendor.domain.Vendor;
import com.buseni.ubukwebwiza.vendor.service.CodeDistrictService;
import com.buseni.ubukwebwiza.vendor.service.CodeWeddingServiceManager;
import com.buseni.ubukwebwiza.vendor.service.VendorService;
import com.buseni.ubukwebwiza.vendor.utils.PageWrapper;
import com.buseni.ubukwebwiza.vendor.utils.VendorSearch;

@Controller
public class VendorController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger( VendorController.class );
	
	@Autowired
	private CodeWeddingServiceManager codeWeddingServiceManager;
	
	@Autowired
	private CodeDistrictService codeDistrictService;
	
	@Autowired
	private VendorService vendorService;
	
	public static final Integer  ACTIVE = 1;
	
	@RequestMapping(value="/vendors",method=RequestMethod.GET)
	public String listing(Model model, Pageable page){
			
		Page<Vendor> vendorPage  =  vendorService.findByActiveFlag(ACTIVE, page);
		
		PageWrapper<Vendor> pageWrapper = new PageWrapper<Vendor>(vendorPage, "/vendors");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("currentMenu", "vendors");
		model.addAttribute("vendors", vendorPage.getContent());
		model.addAttribute("vendorSearch", new VendorSearch());
		return "frontend/vendor/listingVendor";
	}
	
	@RequestMapping(value="/vendors/details{id}",method=RequestMethod.GET)
	public String getVendor(@RequestParam Integer id, Model model){
		Vendor vendor = vendorService.findById(id);
		model.addAttribute("vendor", vendor);
		return "frontend/vendor/detailVendor";
	}
	
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public String search(VendorSearch vendorSearch, Model model, Pageable page){
		List<Vendor> vendors = new ArrayList<Vendor>();
		
		
		Page<Vendor> vendorPage =  vendorService.search(vendorSearch, page);
		if(vendorPage != null){
			vendors = vendorPage.getContent();
		}
		PageWrapper<Vendor> pageWrapper = new PageWrapper<Vendor>(vendorPage, "/search");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("currentMenu", "vendors");
		model.addAttribute("vendors", vendors);
		model.addAttribute("vendorSearch", vendorSearch);
		return "frontend/vendor/listingVendor";
	}
	@ModelAttribute("allWeddingServices")
	public List<CodeTypeWeddingService> populateWeddingServices(){
		return codeWeddingServiceManager.findByActiveFlag(1);
	}
	
	@ModelAttribute("allDistricts")
	public List<CodeDistrict> populateDistricts(){
		return codeDistrictService.findByActiveFlag(1);
	}
	@ModelAttribute("page")
	public String module(){
		return "vendors";
	}
}
