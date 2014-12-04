package com.buseni.ubukwebwiza.vendor.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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

import com.buseni.ubukwebwiza.vendor.domain.District;
import com.buseni.ubukwebwiza.vendor.domain.Vendor;
import com.buseni.ubukwebwiza.vendor.domain.WeddingService;
import com.buseni.ubukwebwiza.vendor.service.DistrictService;
import com.buseni.ubukwebwiza.vendor.service.VendorService;
import com.buseni.ubukwebwiza.vendor.service.WeddingServiceManager;
import com.buseni.ubukwebwiza.vendor.utils.PageWrapper;
import com.buseni.ubukwebwiza.vendor.utils.VendorSearch;

@Controller
public class VendorController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger( VendorController.class );
	
	@Autowired
	private WeddingServiceManager weddingServiceManager;
	
	@Autowired
	private DistrictService districtService;
	
	@Autowired
	private VendorService vendorService;
	
	
	@RequestMapping(value="/vendors",method=RequestMethod.GET)
	public String listing(Model model, Pageable page){
			
		Page<Vendor> vendorPage  =  vendorService.findByEnabled(Boolean.TRUE, page);
		
		PageWrapper<Vendor> pageWrapper = new PageWrapper<Vendor>(vendorPage, "/vendors");
		model.addAttribute("page", pageWrapper);
		//model.addAttribute("currentMenu", "vendors");
		model.addAttribute("vendors", vendorPage.getContent());
		model.addAttribute("vendorSearch", new VendorSearch());
		return "frontend/vendor/listingVendor";
	}
	
	@RequestMapping(value="/vendors/details{id}",method=RequestMethod.GET)
	public String getVendor(@RequestParam Integer id, Model model){
		Vendor vendor = vendorService.getVendor(id);
		model.addAttribute("vendor", vendor);
		return "frontend/vendor/detailVendor";
	}
	
	@RequestMapping(value="/search",method=RequestMethod.GET)
	public String search(VendorSearch vendorSearch, Model model, Pageable page, HttpServletRequest request){
		List<Vendor> vendors = new ArrayList<Vendor>();
		String qs =  request.getQueryString();
		if(StringUtils.isNoneEmpty(qs) && qs.contains("page")){
			qs =	qs.substring(0 , qs.indexOf("page")-1);
		}
		Page<Vendor> vendorPage =  vendorService.search(vendorSearch, page);
		if(vendorPage != null){
			vendors = vendorPage.getContent();
		}

		PageWrapper<Vendor> pageWrapper = new PageWrapper<Vendor>(vendorPage, "/search?"+qs);
		model.addAttribute("page", pageWrapper);
		model.addAttribute("vendors", vendors);
		model.addAttribute("vendorSearch", vendorSearch);
		return "frontend/vendor/listingVendor";
	}
	@ModelAttribute("allWeddingServices")
	public List<WeddingService> populateWeddingServices(){
		return weddingServiceManager.findByEnabled(Boolean.TRUE);
	}
	
	@ModelAttribute("allDistricts")
	public List<District> populateDistricts(){
		return districtService.findByEnabled(Boolean.TRUE);
	}
	@ModelAttribute("currentMenu")
	public String module(){
		return "vendors";
	}
}
