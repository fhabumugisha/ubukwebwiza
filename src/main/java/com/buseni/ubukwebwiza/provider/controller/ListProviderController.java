package com.buseni.ubukwebwiza.provider.controller;

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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.home.HomeController;
import com.buseni.ubukwebwiza.utils.PageWrapper;
import com.buseni.ubukwebwiza.provider.beans.ProviderSearch;
import com.buseni.ubukwebwiza.provider.domain.District;
import com.buseni.ubukwebwiza.provider.domain.Provider;
import com.buseni.ubukwebwiza.provider.domain.WeddingService;
import com.buseni.ubukwebwiza.provider.service.DistrictService;
import com.buseni.ubukwebwiza.provider.service.ProviderService;
import com.buseni.ubukwebwiza.provider.service.WeddingServiceManager;

@Controller
@Navigation(url="/providers", name="Service providers", parent= HomeController.class)
@SessionAttributes({"allWeddingServices","allDistricts"})
public class ListProviderController {

	public static final Logger LOGGER = LoggerFactory.getLogger( ListProviderController.class );

	@Autowired
	private WeddingServiceManager weddingServiceManager;

	@Autowired
	private DistrictService districtService;

	@Autowired
	private ProviderService providerService;


	@RequestMapping(value="/providers",method=RequestMethod.GET)
	public String listing(Model model, Pageable page){

		Page<Provider> providerPage  =  providerService.findByEnabled(Boolean.TRUE, page);

		PageWrapper<Provider> pageWrapper = new PageWrapper<Provider>(providerPage, "/providers");
		model.addAttribute("page", pageWrapper);
		//model.addAttribute("currentMenu", "providers");
		model.addAttribute("providers", providerPage.getContent());
		model.addAttribute("providerSearch", new ProviderSearch());
		return "frontend/provider/listingProvider";
	}


	@RequestMapping(value="/search",method=RequestMethod.GET)
	public String search(ProviderSearch providerSearch, Model model, Pageable page, HttpServletRequest request){
		List<Provider> providers = new ArrayList<Provider>();
		String qs =  request.getQueryString();
		if(StringUtils.isNoneEmpty(qs) && qs.contains("page")){
			qs =	qs.substring(0 , qs.indexOf("page")-1);
		}
		Page<Provider> providerPage =  providerService.search(providerSearch, page);
		if(providerPage != null){
			providers = providerPage.getContent();
		}

		PageWrapper<Provider> pageWrapper = new PageWrapper<Provider>(providerPage, "/search?"+qs);
		model.addAttribute("page", pageWrapper);
		model.addAttribute("providers", providers);
		model.addAttribute("providerSearch", providerSearch);
		return "frontend/provider/listingProvider";
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
		return "providers";
	}
	@ModelAttribute("showSidebar")
	public boolean showSidebar(){
		return true;
	}
}
