package com.buseni.ubukwebwiza.account.controller;

import java.io.File;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ubukwebwiza.account.beans.ChangePasswordForm;
import com.buseni.ubukwebwiza.account.domain.UserAccount;
import com.buseni.ubukwebwiza.account.service.UserAccountService;
import com.buseni.ubukwebwiza.administrator.enums.EnumPhotoCategory;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.exceptions.ErrorsHelper;
import com.buseni.ubukwebwiza.gallery.beans.PhotoForm;
import com.buseni.ubukwebwiza.gallery.domain.Photo;
import com.buseni.ubukwebwiza.gallery.service.PhotoService;
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
@SessionAttributes(value="provider")
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
	private PhotoService photoService;

	@Autowired
	private MessageSource messages;

	@Autowired
	private AmazonS3Util amazonS3Util;



	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String myAccount( Model model, Principal principal){
		Provider provider = providerService.findProviderByUsername(principal.getName());
		model.addAttribute("provider", provider);		
		if(!model.containsAttribute("currentTab")){
			model.addAttribute("currentTab", "personnalInfo");
		}
		//model.addAttribute("canAddMorePhoto", false);
		//Free account can add only 8 photos 
		if(CollectionUtils.isEmpty(provider.getPhotos()) || provider.getPhotos().size() < UbUtils.MAX_PHOTO){
			model.addAttribute("canAddMorePhoto", true);
		}
		return "frontend/account/editProfile";
	}

	@RequestMapping(value="/profile/update", method=RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_PROVIDER')")
	public String update(HttpServletRequest request, @Valid @ModelAttribute Provider provider , 
			BindingResult result, RedirectAttributes attributes,  @RequestParam("file") MultipartFile file) throws BusinessException{		
		LOGGER.info("IN: profile/update-POST");
		attributes.addFlashAttribute("currentTab", "personnalInfo");
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("Profile-update: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.provider", result);
			attributes.addFlashAttribute("provider", provider);
			return "redirect:/profile";

		}
		String filename = "no_person.jpg";
		if (!file.isEmpty()) {
			if(file.getSize() > ImagesUtils.MAXSIZE){
				LOGGER.error("File size should be less than " + ImagesUtils.MAXSIZE+ " byte.");
				result.reject(ImagesUtils.MAX_SIZE_EXCEEDED_ERROR, new String[] {String.valueOf(ImagesUtils.MAXSIZE)}, "File size should be less than " + ImagesUtils.MAXSIZE+ " byte.");
				//attributes.addAttribute("org.springframework.validation.BindingResult.provider",result);
				attributes.addFlashAttribute("provider", provider);	  
				String  error = messages.getMessage("error.file.maxsizeexceeded", new String[]{ImagesUtils.MAXSIZE+ " byte."}, request.getLocale());	
				attributes.addFlashAttribute("errorPersonnalInfo", error);
				attributes.addFlashAttribute("provider", provider);
				return "redirect:/profile";
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
			//Save profil picture to amazon S3
			File fileToUpload =  ImagesUtils.prepareUploading(file, EnumPhotoCategory.PROFILE.getId());
			amazonS3Util.uploadFile(fileToUpload, filename);

			//Business errors	
		} catch (final BusinessException e) {
			ErrorsHelper.rejectErrors(result, e.getErrors());
			LOGGER.error("Profile-update error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.provider", result);
			attributes.addFlashAttribute("provider", provider);
			return "redirect:/profile";
		}
		String message = messages.getMessage("message.profileUpdateSuccess", null, request.getLocale());			
		attributes.addFlashAttribute("messagePersonnalInfo", message);
		return "redirect:/profile";
	}

	@RequestMapping(value="/profile/updateSocialMedia", method=RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_PROVIDER')")
	public String updateSocialMedia(HttpServletRequest request, @ModelAttribute Provider provider , 
			BindingResult result, RedirectAttributes attributes) throws BusinessException{		
		LOGGER.info("IN: profile/updateSocialMedia-POST");
		attributes.addFlashAttribute("currentTab", "socialMedia");
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("Profile-updateSocialMedia errors: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.provider", result);
			attributes.addFlashAttribute("provider", provider);
			return "redirect:/profile";

		}

		try {
			providerService.updateSocialMedia(provider);			

			//Business errors	
		} catch (final BusinessException e) {
			ErrorsHelper.rejectErrors(result, e.getErrors());
			LOGGER.error("Profile-updateSocialMedia errors: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.provider", result);
			attributes.addFlashAttribute("provider", provider);
			return "redirect:/profile";
		}
		String message = messages.getMessage("message.profileUpdateSuccess", null, request.getLocale());			
		attributes.addFlashAttribute("messageSocialMedia", message);
		return "redirect:/profile";
	}

	@RequestMapping(value = "/profile/updateAccount", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_PROVIDER')")
	public String updateAccount(HttpServletRequest request, Principal principal, @RequestParam(value="currentPassword", required=false ) String currentPassword,  @RequestParam("password" ) String password, @RequestParam("passwordConfirm") String passwordConfirm, RedirectAttributes attributes) {
		//Provider provider = providerService.findProviderByUsername(principal.getName());
		attributes.addFlashAttribute("currentTab", "accountInfo");

		if(!password.equals(passwordConfirm)){
			String error = messages.getMessage("error.passwordMatches", null, request.getLocale());		
			LOGGER.error("Password does not match!");
			attributes.addFlashAttribute("errorAccountInfo", error);	
			//attributes.addAttribute("provider", provider);
			return "redirect:/profile";
		}		
		//Provider provider = providerService.findProviderByUsername(principal.getName());
		UserAccount account  = userAccountService.findByUsername(principal.getName());
		//TODO is it necessary to ask current password?
		/*if(!account.getPassword().equals(encoder.encode(currentPassword))){
			 String error = messages.getMessage("error.currentPassword", null, request.getLocale());		
		    	LOGGER.error(error);
				attributes.addFlashAttribute("error", error);
				//attributes.addFlashAttribute("provider", provider);
				return "redirect:/profile";
		}*/
		userAccountService.changeUserPassword(account, password);
		String message = messages.getMessage("message.passwordChangedSuccess", null, request.getLocale());			
		attributes.addFlashAttribute("messageAccountInfo", message);		
		return "redirect:/profile";
	}



	@RequestMapping(value="/profile/addService",method=RequestMethod.POST)
	public String addService( HttpServletRequest request, @ModelAttribute ServiceForm serviceForm, Model model) throws BusinessException{		
		LOGGER.info("IN: profile/addService-POST");
		Provider provider = (Provider) model.asMap().get("provider");
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
			model.addAttribute("messageService", message);
			Provider updated =  providerService.update(provider);
			model.addAttribute("provider", updated);
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
	public String editService(  @RequestParam(value="id") Integer id, Model model) {
		LOGGER.info("IN: profile/editService-GET");
		ProviderWeddingService vws =  providerWeddingServiceManager.findById(id);
		ServiceForm serviceForm = new ServiceForm();
		serviceForm.setDescription(vws.getDescription());
		serviceForm.setId(vws.getId());
		serviceForm.setIdcService(vws.getWeddingService().getId());
		serviceForm.setEnabled(vws.isEnabled());		
		model.addAttribute("serviceForm", serviceForm);	

		model.addAttribute("currentTab", "services");
		return "frontend/account/editProfile::services-bloc";
	}

	@RequestMapping(value="/profile/deleteService", method=RequestMethod.GET)
	public String deleteService( HttpServletRequest request,  @RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: profile/deleteService-GET");
		ProviderWeddingService vws =  providerWeddingServiceManager.findById(id);
		providerWeddingServiceManager.delete(id);
		Provider provider = (Provider) model.asMap().get("provider");
		provider.getProviderWeddingServices().remove(vws);
		model.addAttribute("provider", provider);
		String message = messages.getMessage("message.profileServiceDeleteSuccess", new String[]{ vws.getWeddingService().getLibelle()}, request.getLocale());	
		model.addAttribute("messageService", message);	
		return "frontend/account/editProfile::services-bloc";
	}

	@RequestMapping(value="/profile/addPhoto", method=RequestMethod.POST)
	public String savePhoto(HttpServletRequest request, @ModelAttribute PhotoForm photoForm,  Model model) throws BusinessException{		
		LOGGER.info("IN: profile/addPhoto-POST");
		model.addAttribute("currentTab", "photos");
		Provider provider = (Provider) model.asMap().get("provider");
		
		MultipartFile file  = photoForm.getFile();
		Photo photo = new Photo();
		String filename = "no_person.jpg";
		if (file != null && !file.isEmpty()) {
			if(file.getSize() > ImagesUtils.MAXSIZE){
				LOGGER.error("File size should be less than " + ImagesUtils.MAXSIZE+ " byte.");				
				String  error = messages.getMessage("error.file.maxsizeexceeded", new String[]{ImagesUtils.MAXSIZE+ " byte."}, request.getLocale());	
				model.addAttribute("errorPhoto", error);
				model.addAttribute("photoForm", photoForm);

				return "frontend/account/editProfile::photos-bloc";
			}

			filename = UbUtils.normalizeFileName(file.getOriginalFilename());
			photo.setFilename(filename);
			photo.setContentType(file.getContentType());
			photo.setCategory(EnumPhotoCategory.PROVIDER.getId());

		} else if (photoForm.getId() == null) {
			LOGGER.error("You failed to upload  because the file was empty.");
			String  error = messages.getMessage("error.file.empty", null, request.getLocale());
			model.addAttribute("errorPhoto", error);
			model.addAttribute("photoForm", photoForm);
			return "frontend/account/editProfile::photos-bloc";

		}

		photo.setDescription(photoForm.getDescription());
		photo.setId(photoForm.getId());
		photo.setEnabled(photoForm.isEnabled());

		String message  =  "";
		if(photo.getId() == null){
			message = messages.getMessage("message.editprofile.photoAddSuccess", null, request.getLocale());	
		}else{
			message = messages.getMessage("message.editprofile.photoUpdateSuccess", null, request.getLocale());	
		}



		Photo updatedPhoto = photoService.addOrUpdate(photo);

		//TODO improve this

		Photo providerPhoto =  getPhoto(provider.getPhotos(), updatedPhoto.getId());
		if(providerPhoto != null){
			provider.getPhotos().remove(providerPhoto);
		}
		provider.getPhotos().add(updatedPhoto);
		Provider updated = providerService.update(provider);
		model.addAttribute("provider", updated);
		//Free account can add only 8 photos 
		if(CollectionUtils.isEmpty(updated.getPhotos()) || updated.getPhotos().size() < UbUtils.MAX_PHOTO){
			model.addAttribute("canAddMorePhoto", true);
		}else{
			model.addAttribute("canAddMorePhoto", false);
		}
		// Save profil pricture to amazon S3
		File fileToUpload = ImagesUtils.prepareUploading(file,	EnumPhotoCategory.PROVIDER.getId());
		amazonS3Util.uploadFile(fileToUpload, filename);


		model.addAttribute("messagePhoto", message);

		return "frontend/account/editProfile::photos-bloc";

		// Business errors
		/*} catch (final BusinessException e) {
			ErrorsHelper.rejectErrors(result, e.getErrors());
			LOGGER.error("Photo-save error: " + result.toString());
			model.addAttribute("org.springframework.validation.BindingResult.photoForm", result);
			model.addAttribute("provider", provider);
			return "frontend/account/editProfile::photos-bloc";
		} */
	}



	@RequestMapping(value="/profile/deletePhoto", method=RequestMethod.GET)
	public String deletePhoto(HttpServletRequest request, @RequestParam(value="id") Integer id, Model model) {
		LOGGER.info("IN: providers/deletePhoto-GETT");
		Provider provider = (Provider) model.asMap().get("provider");
		Photo photo = photoService.findById(id);
		Provider updatedProvider = providerService.deletePhoto(provider.getId(), photo);	
		//	provider.getPhotos().remove(photo);
		amazonS3Util.deleteFile(photo.getFilename());
		String message = messages.getMessage("message.editprofile.photoDeleteSuccess", null, request.getLocale());	
		model.addAttribute("messagePhoto", message);		
		model.addAttribute("provider", updatedProvider);	
		//Free account can add only 8 photos 
		if(CollectionUtils.isEmpty(updatedProvider.getPhotos()) || updatedProvider.getPhotos().size() < UbUtils.MAX_PHOTO){
			model.addAttribute("canAddMorePhoto", true);
		}
		model.addAttribute("currentTab", "photos");		
		return "frontend/account/editProfile::photos-bloc";
	}

	@RequestMapping(value="/profile/editPhoto", method=RequestMethod.GET)
	public String editPhoto( @RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: profile/editPhoto-GET");

		Photo photo = photoService.findById(id);
		PhotoForm photoForm = new PhotoForm();
		photoForm.setDescription(photo.getDescription());
		photoForm.setId(id);
		photoForm.setName(photo.getFilename());
		photoForm.setEnabled(photo.isEnabled());
		model.addAttribute("photoForm", photoForm);	
		model.addAttribute("currentTab", "photos");

		return "frontend/account/editProfile::photos-bloc";
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

	@ModelAttribute("photoForm")
	public PhotoForm photoForm(){
		return new PhotoForm();
	}

	@ModelAttribute("changePasswordForm")
	public ChangePasswordForm changePasswordForm(){
		return new ChangePasswordForm();
	}
	/*
	@ModelAttribute("provider")
	public Provider provider( Principal principal){
		return providerService.findProviderByUsername(principal.getName());
	}*/

	private Photo getPhoto(Set<Photo> photos, Integer idPhoto){
		for(Photo photo : photos){
			if(photo.getId().equals(idPhoto)){
				return photo;
			}
		}
		return null;
	}

	@ModelAttribute("showSidebar")
	public boolean showSidebar(){
		return false;
	}
	
	@ModelAttribute("canAddMorePhoto")
	public boolean canAddMorePhoto(Model model){
		Provider provider = (Provider) model.asMap().get("provider");
		//Free account can add only 8 photos 
		if(provider != null){
			if( CollectionUtils.isEmpty(provider.getPhotos()) || provider.getPhotos().size() < UbUtils.MAX_PHOTO){
				return true;
			}
		}
		
		return false;
	}

}
