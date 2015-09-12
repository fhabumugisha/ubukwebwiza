package com.buseni.ubukwebwiza.administration.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.buseni.ubukwebwiza.administrator.enums.EnumPhotoCategory;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.exceptions.ErrorsHelper;
import com.buseni.ubukwebwiza.gallery.domain.Photo;
import com.buseni.ubukwebwiza.provider.domain.District;
import com.buseni.ubukwebwiza.provider.domain.Provider;
import com.buseni.ubukwebwiza.provider.domain.WeddingService;
import com.buseni.ubukwebwiza.provider.service.DistrictService;
import com.buseni.ubukwebwiza.provider.service.ProviderService;
import com.buseni.ubukwebwiza.provider.service.WeddingServiceManager;
import com.buseni.ubukwebwiza.utils.AmazonS3Util;
import com.buseni.ubukwebwiza.utils.ImagesUtils;
import com.buseni.ubukwebwiza.utils.PageWrapper;
import com.buseni.ubukwebwiza.utils.UbUtils;

@Controller
@RequestMapping(value="/admin")
@Navigation(url="/admin/providers", name="Providers", parent= AdminHomeController.class)
public class AdminProviderController {
	

	public  static final Logger LOGGER = LoggerFactory.getLogger(AdminProviderController.class);

	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private DistrictService  districtService;
	
	@Autowired
	private WeddingServiceManager weddingServiceManager;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private AmazonS3Util amazonS3Util;
	
	@RequestMapping(value="/providers",method=RequestMethod.GET)
	public String providers(Model model, Pageable page){				
		Page<Provider> providerPage  =  providerService.findAll(page);		
		PageWrapper<Provider> pageWrapper = new PageWrapper<Provider>(providerPage, "/admin/providers");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("providers", providerPage.getContent());		
		if(!model.containsAttribute("provider")){
			model.addAttribute("provider", new Provider());
		}
		return "adminpanel/provider/listingProvider";
	}
	
	@RequestMapping(value="/providers/save",method=RequestMethod.POST)
	public String save(HttpServletRequest request, @Valid @ModelAttribute Provider provider , BindingResult result, RedirectAttributes attributes,  @RequestParam("file") MultipartFile file) throws BusinessException{		
		LOGGER.info("IN: providers/save-POSST");
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("Provider-edit error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.provider", result);
			attributes.addFlashAttribute("provider", provider);
			return "adminpanel/provider/editProvider";

		}
			String filename = "no_person.jpg";
			if (!file.isEmpty()) {
				if(file.getSize() > ImagesUtils.MAXSIZE){
					LOGGER.error("File size should be less than " + ImagesUtils.MAXSIZE+ " byte.");
					result.reject(ImagesUtils.MAX_SIZE_EXCEEDED_ERROR, new String[] {String.valueOf(ImagesUtils.MAXSIZE)}, "File size should be less than " + ImagesUtils.MAXSIZE+ " byte.");
					//attributes.addAttribute("org.springframework.validation.BindingResult.provider",result);
					attributes.addFlashAttribute("provider", provider);	  
					attributes.addFlashAttribute("errors", "File size should be less than " + ImagesUtils.MAXSIZE+ " byte.");
					
					return "adminpanel/provider/editProvider";
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
	               
	           
	        } else  if(provider.getId() == null){
	        		LOGGER.error("You failed to upload  because the file was empty.");
		        	result.reject("error.file.empty");
	            	//attributes.addFlashAttribute("org.springframework.validation.BindingResult.provider", result);
	    			attributes.addFlashAttribute("provider", provider);
	    			attributes.addFlashAttribute("errors", "You failed to upload  because the file was empty.");
	    			return "adminpanel/provider/editProvider";
	        	
	        }
			String message = "";
			try {
				String password = null;
				//creation mode, generate a random passowrd
				if(provider.getId() == null){
					password = UbUtils.generatePassword(6);
					PasswordEncoder encoder = new BCryptPasswordEncoder();
					provider.getAccount().setPassword(encoder.encode(password));
					 message = "Provider " + provider.getBusinessName() + " was successfully added";
				}else{
					 message = "Provider " + provider.getBusinessName() + " was successfully updated";
				}
				providerService.addOrUpdate(provider);				
				//Save profil pricture to amazon S3
				File fileToUpload =  ImagesUtils.prepareUploading(file, EnumPhotoCategory.PROFILE.getId());
				amazonS3Util.uploadFile(fileToUpload, filename);
				
				//in creation mode send a "provider added email
				LOGGER.debug("in creation mode send a 'provider added email'");
				if(StringUtils.isNotEmpty(password)){
					String appUrl =  "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
				    eventPublisher.publishEvent(new OnProviderRegistrationCompleteEvent(provider.getBusinessName(), provider.getUrlName()
				    		, provider.getAccount().getEmail(), password, request.getLocale(),  appUrl));
				}
				
				
				
				//Business errors	
			} catch (final BusinessException e) {
				ErrorsHelper.rejectErrors(result, e.getErrors());
				LOGGER.error("Provider-save error: " + result.toString());
				attributes.addFlashAttribute("org.springframework.validation.BindingResult.provider", result);
				attributes.addFlashAttribute("provider", provider);
				return "adminpanel/provider/editProvider";
			}
			
			
			attributes.addFlashAttribute("message", message);
			return "redirect:/admin/providers";
		


	}



	@RequestMapping(value="/providers/delete", method=RequestMethod.GET)
	public String delete( @RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes) {
		LOGGER.info("IN: providers/delete-GET");
		providerService.delete(id);
		String message = "Provider " + id + " was successfully deleted";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/admin/providers";
	}

	@RequestMapping(value="/providers/edit", method=RequestMethod.GET)
	public String edit(@RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: providers/edit-GET");
		Provider provider =  providerService.findOne(id);
		model.addAttribute("provider", provider);
		
		return "adminpanel/provider/editProvider";
	}
	

	
	@RequestMapping(value="/providers/new", method=RequestMethod.GET)
	public String newProvider( Model model) {		
		LOGGER.info("IN: providers/new-GET");
		model.addAttribute("provider", new Provider());
		return "adminpanel/provider/editProvider";
	}
	
    
	/*@RequestMapping(value = "/image/{imageId}", method = RequestMethod.GET)
	public void showImage(@PathVariable("imageId") Integer imageId,
			HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		Photo photo = photoService.findById(imageId);
		byte[] imageContent = photo.getContent();
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(imageContent);
		response.getOutputStream().close();
	}*/
	
	@ModelAttribute("currentMenu")
	public String module(){
		return "providers";
	}
	
	@ModelAttribute("allWeddingServices")
	public List<WeddingService> populateWeddingServices(){
		return weddingServiceManager.findByEnabled(Boolean.TRUE);
	}
	@ModelAttribute("districts")
	public List<District> populateDistricts(){
		return districtService.findByEnabled(Boolean.TRUE);
	}

	
	
	
	
}
