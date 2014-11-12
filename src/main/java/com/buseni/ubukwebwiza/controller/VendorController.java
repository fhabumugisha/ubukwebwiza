package com.buseni.ubukwebwiza.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.buseni.ubukwebwiza.core.domain.CodeDistrict;
import com.buseni.ubukwebwiza.core.domain.CodeWeddingService;
import com.buseni.ubukwebwiza.core.domain.Vendor;
import com.buseni.ubukwebwiza.core.service.CodeDistrictService;
import com.buseni.ubukwebwiza.core.service.CodeWeddingServiceManager;
import com.buseni.ubukwebwiza.core.service.VendorService;
import com.buseni.ubukwebwiza.utils.VendorSearch;

@Controller
@RequestMapping()
public class VendorController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger( VendorController.class );
	
	@Autowired
	private CodeWeddingServiceManager codeWeddingServiceManager;
	
	@Autowired
	private CodeDistrictService codeDistrictService;
	
	@Autowired
	private VendorService vendorService;
	
	@RequestMapping(value="/vendors",method=RequestMethod.GET)
	public String listing(Model model){
		List<Vendor> vendors = new ArrayList<Vendor>();
		//PageRequest page = new PageRequest(0, 10, Direction.ASC, "");		
		vendors =  vendorService.getFeaturedVendors();
		model.addAttribute("vendors", vendors);
		model.addAttribute("vendorSearch", new VendorSearch());
		return "frontend/listingVendor";
	}
	
	@RequestMapping(value="/vendors/details{id}",method=RequestMethod.GET)
	public String getVendor(@RequestParam Integer id, Model model){
		Vendor vendor = vendorService.findById(id);
		model.addAttribute("vendor", vendor);
		return "frontend/detailVendor";
	}
	
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public String search(VendorSearch vendorSearch, Model model){
		List<Vendor> vendors = new ArrayList<Vendor>();
		LOGGER.debug(vendorSearch.toString());
		//PageRequest page = new PageRequest(0, 10, Direction.ASC, "");		
		vendors =  vendorService.getFeaturedVendors();
		model.addAttribute("vendors", vendors);
		model.addAttribute("vendorSearch", vendorSearch);
		return "frontend/listingVendor";
	}
	@ModelAttribute("allWeddingServices")
	public List<CodeWeddingService> populateWeddingServices(){
		return codeWeddingServiceManager.findByActiveFlag(1);
	}
	
	@ModelAttribute("allDistricts")
	public List<CodeDistrict> populateDistricts(){
		return codeDistrictService.findByActiveFlag(1);
	}
}
