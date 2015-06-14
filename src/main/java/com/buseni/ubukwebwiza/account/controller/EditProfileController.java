package com.buseni.ubukwebwiza.account.controller;

import java.io.File;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ubukwebwiza.account.domain.UserAccount;
import com.buseni.ubukwebwiza.account.service.UserAccountService;
import com.buseni.ubukwebwiza.administrator.enums.EnumPhotoCategory;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.exceptions.ErrorsHelper;
import com.buseni.ubukwebwiza.gallery.domain.Photo;
import com.buseni.ubukwebwiza.home.HomeController;
import com.buseni.ubukwebwiza.provider.beans.ServiceForm;
import com.buseni.ubukwebwiza.provider.domain.District;
import com.buseni.ubukwebwiza.provider.domain.Provider;
import com.buseni.ubukwebwiza.provider.domain.ProviderWeddingService;
import com.buseni.ubukwebwiza.provider.domain.WeddingService;
import com.buseni.ubukwebwiza.provider.service.DistrictService;
import com.buseni.ubukwebwiza.provider.service.ProviderService;
import com.buseni.ubukwebwiza.provider.service.ProviderWeddingServiceManager;
import com.buseni.ubukwebwiza.provider.service.WeddingServiceManager;
import com.buseni.ubukwebwiza.utils.AmazonS3Util;
import com.buseni.ubukwebwiza.utils.ImagesUtils;
import com.buseni.ubukwebwiza.utils.UbUtils;

@Controller
@Navigation(url="/profile", name="My profile" , parent = HomeController.class)
public class EditProfileController {
	
	public  static final Logger LOGGER = LoggerFactory.getLogger(EditProfileController.class);
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private WeddingServiceManager weddingServiceManager;
	
	@Autowired
	private ProviderWeddingServiceManager providerWeddingServiceManager;
	
	@Autowired
	private DistrictService districtService;
	
	 @Autowired
	 private MessageSource messages;
	@Autowired
	private AmazonS3Util amazonS3Util;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String myAccount(Principal principal, Model model){
		Provider provider = providerService.findProviderByUsername(principal.getName());
		model.addAttribute("provider", provider);
		model.addAttribute("currentTab", "personnalInfo");
		return "frontend/account/editProfile";
	}
	
	@RequestMapping(value="/profile/update", method=RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_PROVIDER')")
	public String update(HttpServletRequest request, @Valid @ModelAttribute Provider provider , 
			BindingResult result, RedirectAttributes attributes,  @RequestParam("file") MultipartFile file) throws BusinessException{		
		LOGGER.info("IN: profile/update-POST");
		attributes.addFlashAttribute("currentTab", "accountInfo");
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("Profile-updater: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.provider", result);
			attributes.addFlashAttribute("provider", provider);
			return "frontend/account/editProfile";

		}
		String filename = "no_person.jpg";
		if (!file.isEmpty()) {
			if(file.getSize() > ImagesUtils.MAXSIZE){
				LOGGER.error("File size should be less than " + ImagesUtils.MAXSIZE+ " byte.");
				result.reject(ImagesUtils.MAX_SIZE_EXCEEDED_ERROR, new String[] {String.valueOf(ImagesUtils.MAXSIZE)}, "File size should be less than " + ImagesUtils.MAXSIZE+ " byte.");
				//attributes.addAttribute("org.springframework.validation.BindingResult.provider",result);
				attributes.addFlashAttribute("provider", provider);	  
				attributes.addFlashAttribute("errors", "File size should be less than " + ImagesUtils.MAXSIZE+ " byte.");
				return "frontend/account/editProfile";
			}

			filename = UbUtils.normalizeFileName(file.getOriginalFilename());
			Photo profil = new Photo();
			profil.setFilename(filename);
			profil.setDescription(provider.getBusinessName());
			profil.setEnabled(true);
			profil.setCreatedAt(new Date());
			profil.setLastUpdate(new Date());
			profil.setContentType(file.getContentType());
			profil.setCategory(EnumPhotoCategory.PROFILE.getId());
			provider.setProfilPicture(profil);
		} 

		try {
			providerService.updateInfos(provider);				
			//Save profil pricture to amazon S3
			File fileToUpload =  ImagesUtils.prepareUploading(file, EnumPhotoCategory.PROFILE.getId());
			amazonS3Util.uploadFile(fileToUpload, filename);

			//Business errors	
		} catch (final BusinessException e) {
			ErrorsHelper.rejectErrors(result, e.getErrors());
			LOGGER.error("Profile-update error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.provider", result);
			attributes.addFlashAttribute("provider", provider);
			return "frontend/account/editProfile";
		}
		String message = messages.getMessage("message.profileUpdateSuccess", null, request.getLocale());			
		attributes.addFlashAttribute("message", message);
		return "redirect:/profile";
	}
	
	@RequestMapping(value = "/profile/updateAccount", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_PROVIDER')")
	public String updateAccount(HttpServletRequest request,Principal principal, @RequestParam("currentPassword" ) String currentPassword,  @RequestParam("password" ) String password, @RequestParam("passwordConfirm") String passwordConfirm, RedirectAttributes attributes) {
		
		Provider provider = providerService.findProviderByUsername(principal.getName());		
		if(!password.equals(passwordConfirm)){
		  String error = messages.getMessage("error.passwordMatches", null, request.getLocale());		
	    	LOGGER.error(error);
			attributes.addFlashAttribute("error", error);	
			//attributes.addFlashAttribute("provider", provider);
			return "redirect:/profile";
	  }		
		UserAccount account  = provider.getAccount();
		
		if(!account.getPassword().equals(encoder.encode(currentPassword))){
			 String error = messages.getMessage("error.currentPassword", null, request.getLocale());		
		    	LOGGER.error(error);
				attributes.addFlashAttribute("error", error);
				//attributes.addFlashAttribute("provider", provider);
				return "redirect:/profile";
		}
		userAccountService.changeUserPassword(account, password);
	    String message = messages.getMessage("message.passwordChangedSuccess", null, request.getLocale());			
		attributes.addFlashAttribute("message", message);		
	    return "redirect:/profile";
	}
	
	
	
	@RequestMapping(value="/profile/addService",method=RequestMethod.POST)
	public String addService( HttpServletRequest request, Principal principal, @ModelAttribute ServiceForm serviceForm, Model model) throws BusinessException{		
		LOGGER.info("IN: profile/addService-POST");
		Provider provider = providerService.findProviderByUsername(principal.getName());
			ProviderWeddingService vws  = new ProviderWeddingService();				
			try {
				vws.setDescription(serviceForm.getDescription());
				vws.setId(serviceForm.getId());
				vws.setEnabled(serviceForm.isEnabled());
				vws.setProvider(provider);
				WeddingService  ws = weddingServiceManager.findOne(serviceForm.getIdcService());
				vws.setWeddingService(ws);		
				String message  =  "";
				if(vws.getId() == null){
					 message = messages.getMessage("message.profileServiceAddSuccess", new String[]{ ws.getLibelle()}, request.getLocale());	
				}else{
					message = messages.getMessage("message.profileServiceUpdateSuccess", new String[]{ ws.getLibelle()}, request.getLocale());	
				}
				providerWeddingServiceManager.create(vws);				
				model.addAttribute("message", message);
				model.addAttribute("provider", provider);
				model.addAttribute("currentTab", "services");
				return "frontend/account/editProfile::services-bloc";
				
				//Business errors
			} catch (final BusinessException e) {
				//ErrorsHelper.rejectErrors(result, e.getErrors());
				LOGGER.error("profile/addService-POST error adding service");
				model.addAttribute("currentTab", "services");
				return "frontend/account/editProfile::services-bloc";
											}
	}
	
	@RequestMapping(value="/profile/editService", method=RequestMethod.GET)
	public String editService(HttpServletRequest request, Principal principal, @RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: profile/editService-GET");
		ProviderWeddingService vws =  providerWeddingServiceManager.findById(id);
		ServiceForm serviceForm = new ServiceForm();
		serviceForm.setDescription(vws.getDescription());
		serviceForm.setId(vws.getId());
		serviceForm.setIdcService(vws.getWeddingService().getId());
		serviceForm.setEnabled(vws.isEnabled());		
		model.addAttribute("serviceForm", serviceForm);	
		Provider provider = providerService.findProviderByUsername(principal.getName());		
		model.addAttribute("provider", provider );
		model.addAttribute("currentPane", "services");
		return "frontend/account/editProfile::services-bloc";
	}
	
	@RequestMapping(value="/profile/deleteService", method=RequestMethod.GET)
	public String deleteService( HttpServletRequest request, Principal principal, @RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: profile/deleteService-GET");
		ProviderWeddingService vws =  providerWeddingServiceManager.findById(id);
		providerWeddingServiceManager.delete(id);
		String message = messages.getMessage("message.profileServiceDeleteSuccess", new String[]{ vws.getWeddingService().getLibelle()}, request.getLocale());	
		model.addAttribute("message", message);		
		Provider provider = providerService.findProviderByUsername(principal.getName());
		model.addAttribute("provider", provider);
		return "frontend/account/editProfile::services-bloc";
	}
	
	@ModelAttribute("currentMenu")
	public String module(){
		return "profile";
	}
	
	@ModelAttribute("allWeddingServices")
	public List<WeddingService> populateWeddingServices(){
		return weddingServiceManager.findByEnabled(Boolean.TRUE);
	}
	
	@ModelAttribute("allDistricts")
	public List<District> populateDistricts(){
		return districtService.findByEnabled(Boolean.TRUE);
	}
	
	@ModelAttribute("serviceForm")
	public ServiceForm serviceForm(){
		return new ServiceForm();
	}
	
	@ModelAttribute("showSidebar")
	public boolean showSidebar(){
		return false;
	}

}
