package com.buseni.ubukwebwiza.home;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.gallery.domain.Photo;
import com.buseni.ubukwebwiza.gallery.service.PhotoService;
import com.buseni.ubukwebwiza.provider.beans.ProviderSearch;
import com.buseni.ubukwebwiza.provider.domain.District;
import com.buseni.ubukwebwiza.provider.domain.Provider;
import com.buseni.ubukwebwiza.provider.domain.WeddingService;
import com.buseni.ubukwebwiza.provider.service.DistrictService;
import com.buseni.ubukwebwiza.provider.service.ProviderService;
import com.buseni.ubukwebwiza.provider.service.WeddingServiceManager;

@Controller
@SessionAttributes({"allWeddingServices","allDistricts"})
@Navigation(url="/", name="Home")
public class HomeController {

	
	@Autowired
	private WeddingServiceManager weddingServiceManager;
	
	@Autowired
	private DistrictService districtService;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private PhotoService photoService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(ProviderSearch providerSearch){
		return "frontend/landingPage";
	}

	@RequestMapping(value="/landing-page", method=RequestMethod.GET)
	public String landingPage(ProviderSearch providerSearch){
		return "frontend/landingPage";
	}

	
	@RequestMapping(value="/privacy", method=RequestMethod.GET)
	public String privacy(){
		return "frontend/privacy";
	}
	@RequestMapping(value="/terms", method=RequestMethod.GET)
	public String termsOfService(){
		return "frontend/terms";
	}
	@ModelAttribute("allWeddingServices")
	public List<WeddingService> populateWeddingServices(){
		return weddingServiceManager.findByEnabled(Boolean.TRUE);
	}
	
	@ModelAttribute("allDistricts")
	public List<District> populateDistricts(){
		return districtService.findByEnabled(Boolean.TRUE);
	}
	
	@ModelAttribute("featuredProviders")
	public List<Provider> populateFeaturedProviders(){
		return providerService.getFeaturedProviders();
	}
	@ModelAttribute("currentMenu")
	public String module(){
		return "home";
	}
	
	@ModelAttribute("sliderPhotos")
	public List<Photo> sliderPhotos(){
		return photoService.homePagePhotos();
	
	}
	
	@ModelAttribute("showSidebar")
	public boolean showSidebar(){
		return true;
	}
	
}
