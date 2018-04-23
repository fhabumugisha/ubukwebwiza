package com.buseni.ubukwebwiza;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ubukwebwiza.contactus.domain.ContactusForm;
import com.buseni.ubukwebwiza.contactus.service.ContactusService;
import com.buseni.ubukwebwiza.gallery.beans.PhotoDetails;
import com.buseni.ubukwebwiza.gallery.domain.Photo;
import com.buseni.ubukwebwiza.gallery.service.PhotoService;
import com.buseni.ubukwebwiza.provider.beans.ProviderSearch;
import com.buseni.ubukwebwiza.provider.domain.District;
import com.buseni.ubukwebwiza.provider.domain.Provider;
import com.buseni.ubukwebwiza.provider.domain.WeddingService;
import com.buseni.ubukwebwiza.provider.service.DistrictService;
import com.buseni.ubukwebwiza.provider.service.ProviderService;
import com.buseni.ubukwebwiza.provider.service.WeddingServiceManager;
import com.buseni.ubukwebwiza.utils.PageWrapper;

@RestController
@RequestMapping("/api")
public class UbPublicApiController {
	public  static final Logger LOGGER = LoggerFactory.getLogger(UbPublicApiController.class);
	
	@Autowired
	private WeddingServiceManager weddingServiceManager;
	
	@Autowired
	private DistrictService districtService;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private PhotoService photoService;
	
	@GetMapping("/hello")
	public String test() {
		return " Hello API!";
	}
	
	@GetMapping("allWeddingServices")
	public List<WeddingService> populateWeddingServices(){
		return weddingServiceManager.findByEnabled(Boolean.TRUE);
	}
	
	@GetMapping("allDistricts")
	public List<District> populateDistricts(){
		return districtService.findByEnabled(Boolean.TRUE);
	}
	
	@GetMapping("featuredProviders")
	public List<Provider> populateFeaturedProviders(){
		return providerService.getFeaturedProviders();
	}
	
	
	@GetMapping(value="/wedding-service-providers")
	public List<Provider> listing(Model model, Pageable page){

		Page<Provider> providerPage  =  providerService.findByEnabled(Boolean.TRUE, page);

		PageWrapper<Provider> pageWrapper = new PageWrapper<Provider>(providerPage, "/wedding-service-providers");
		model.addAttribute("page", pageWrapper);
		//model.addAttribute("currentMenu", "providers");
		model.addAttribute("providers", providerPage.getContent());
		model.addAttribute("providerSearch", new ProviderSearch());
		//return "frontend/provider/listingProvider";
		return providerPage.getContent();
	}


	@GetMapping(value="/search")
	public List<Provider>  search(ProviderSearch providerSearch, Model model, Pageable page, HttpServletRequest request){
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
		return providerPage.getContent();
	}
	
	@GetMapping(value="/photos")
	public List<PhotoDetails> photos(Model model, Pageable page){
		Page<PhotoDetails>  photosPage = photoService.findPhotoGallery(page);
		model.addAttribute("photos", photosPage.getContent());
		PageWrapper<PhotoDetails> pageWrapper = new PageWrapper<PhotoDetails>(photosPage, "/photos");
		model.addAttribute("page", pageWrapper);
		return photosPage.getContent();
	}
	
	@GetMapping("sliderPhotos")
	public List<Photo> sliderPhotos(){
		return photoService.homePagePhotos();
	
	}
	
	@Autowired
	private ContactusService  contactusService;	

	@Autowired
	private MessageSource messages;

//	@GetMapping(value="/contactus")
//	public String goToContactus( @RequestParam(value = "timeout", required = false) String timeout, 
//			Model model,HttpServletRequest request){
//		model.addAttribute("contactusForm", new ContactusForm());
//		
//		if (timeout != null) {
//			String errorMessage  =  messages.getMessage("message.sessiontimeout", null, request.getLocale());
//			model.addAttribute("error", errorMessage);
//		}
//		return "frontend/contactus";
//	}

	@PostMapping(value="/contactus")
	public String contactus(@RequestParam(value="enterHere", required=false) String enterHere, @Valid @ModelAttribute ContactusForm contactusForm,  BindingResult result, RedirectAttributes attributes){
		//If enterHere is filled it is a spam
		if(StringUtils.isNotEmpty(enterHere)){
					return "kok";
		}
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("Contactus error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.contactusForm", result);
			attributes.addFlashAttribute("contactusForm", contactusForm);
			return "frontend/contactus";

		}
		try {
			contactusService.add(contactusForm);
		} catch (com.buseni.ubukwebwiza.exceptions.BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		attributes.addFlashAttribute("message", "Your message have been sent! Thank you");
		return "ok";
	}

}
