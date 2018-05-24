package com.buseni.ubukwebwiza;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
import com.buseni.ubukwebwiza.provider.domain.ProviderDTO;
import com.buseni.ubukwebwiza.provider.domain.ProviderWeddingService;
import com.buseni.ubukwebwiza.provider.domain.WeddingService;
import com.buseni.ubukwebwiza.provider.service.DistrictService;
import com.buseni.ubukwebwiza.provider.service.ProviderService;
import com.buseni.ubukwebwiza.provider.service.WeddingServiceManager;
import com.buseni.ubukwebwiza.utils.PageWrapper;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:8100","http://localhost:8080", "http://ecoledudimanche.jelastic.lunacloud.com"})
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
	
	@GetMapping("/all-wedding-services")
	@CrossOrigin(origins = {"http://localhost:8100","http://localhost:8080", "http://ecoledudimanche.jelastic.lunacloud.com"})
	public List<WeddingService> populateWeddingServices(){
		return weddingServiceManager.findByEnabled(Boolean.TRUE);
	}
	
	@GetMapping("/all-districts")
	@CrossOrigin(origins = {"http://localhost:8100","http://localhost:8080", "http://ecoledudimanche.jelastic.lunacloud.com"})
	public List<District> populateDistricts(){
		return districtService.findByEnabled(Boolean.TRUE);
	}
	
	@GetMapping("/featured-providers")
	@CrossOrigin(origins = {"http://localhost:8100","http://localhost:8080", "http://ecoledudimanche.jelastic.lunacloud.com"})
	public List<ProviderDTO> populateFeaturedProviders(){
		List<ProviderDTO>  lfp = new ArrayList<>();
		List<Provider> featuredProviders =  providerService.getFeaturedProviders();
		featuredProviders.forEach(fp -> {
			ProviderDTO pdto = new ProviderDTO();
			pdto.setId(fp.getId());
			pdto.setBusinessName(fp.getBusinessName());
			pdto.setAddress(fp.getAddress());
			lfp.add(pdto);
		});
		return lfp;
	}
	
	
	@GetMapping(value="/providers")
	@CrossOrigin(origins = {"http://localhost:8100","http://localhost:8080", "http://ecoledudimanche.jelastic.lunacloud.com"})
	public List<ProviderDTO> listing(Model model, Pageable page){

		Page<Provider> providerPage  =  providerService.findByEnabled(Boolean.TRUE, page);

		PageWrapper<Provider> pageWrapper = new PageWrapper<Provider>(providerPage, "/wedding-service-providers");
		model.addAttribute("page", pageWrapper);
		//model.addAttribute("currentMenu", "providers");
		model.addAttribute("providers", providerPage.getContent());
		model.addAttribute("providerSearch", new ProviderSearch());
		List<Provider> lp = providerPage.getContent();
		List<ProviderDTO> lpdto = new ArrayList<>();
		lp.forEach(fp -> {
			ProviderDTO pdto = new ProviderDTO();
			pdto.setId(fp.getId());
			pdto.setBusinessName(fp.getBusinessName());
			pdto.setAddress(fp.getAddress());
			pdto.setUrlName(fp.getUrlName());
			pdto.setDistrict(fp.getDistrict().getLibelle());
			pdto.setServices(fp.getProviderWeddingServices().stream().map(ProviderWeddingService::getWeddingService).map(WeddingService::getLibelle).collect(Collectors.joining(",")));
			if(fp.getProfilPicture() != null) {
				pdto.setProfilePicture(fp.getProfilPicture().getFilename());
			}
			if(CollectionUtils.isNotEmpty(fp.getPhotos())) {
				fp.getPhotos().forEach(p-> {
					PhotoDetails pd = new PhotoDetails(p.getId(), p.getDescription(), p.getFilename());
					pdto.getPhotos().add(pd);
				});
			}
			pdto.setPhoneNumber(fp.getPhoneNumber());
			
			lpdto.add(pdto);
		});
		return lpdto;
	}


	@GetMapping(value="/search")
	public List<ProviderDTO>  search(ProviderSearch providerSearch, Model model, Pageable page, HttpServletRequest request){
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
		List<Provider> lp = providerPage.getContent();
		List<ProviderDTO> lpdto = new ArrayList<>();
		return lpdto;
	}
	@CrossOrigin(origins = {"http://localhost:8100","http://localhost:8080", "http://ecoledudimanche.jelastic.lunacloud.com"})
	@GetMapping(value="/photos")
	public List<PhotoDetails> photos(Model model, Pageable page){
		Page<PhotoDetails>  photosPage = photoService.findPhotoGallery(page);
		model.addAttribute("photos", photosPage.getContent());
		PageWrapper<PhotoDetails> pageWrapper = new PageWrapper<PhotoDetails>(photosPage, "/photos");
		model.addAttribute("page", pageWrapper);
		return photosPage.getContent();
	}
	
	@GetMapping("/slider-photos")
	@CrossOrigin(origins = {"http://localhost:8100","http://localhost:8080", "http://ecoledudimanche.jelastic.lunacloud.com"})
	public List<PhotoDetails> sliderPhotos(){
		List<Photo>  sliderPhotos  = photoService.homePagePhotos();
		List<PhotoDetails>   listSp =  new ArrayList<>();
		sliderPhotos.forEach(sp ->{
			PhotoDetails pd = new PhotoDetails(sp.getId(), sp.getDescription(), sp.getFilename());
			listSp.add(pd);
					
		});
		return listSp;
	
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
