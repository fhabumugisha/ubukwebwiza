package com.buseni.ubukwebwiza.administration.controller;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ubukwebwiza.administrator.enums.EnumPhotoCategory;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.exceptions.ErrorsHelper;
import com.buseni.ubukwebwiza.exceptions.ServiceLayerException;
import com.buseni.ubukwebwiza.gallery.beans.PhotoForm;
import com.buseni.ubukwebwiza.gallery.domain.Photo;
import com.buseni.ubukwebwiza.gallery.service.PhotoService;
import com.buseni.ubukwebwiza.provider.domain.Provider;
import com.buseni.ubukwebwiza.provider.service.ProviderService;
import com.buseni.ubukwebwiza.utils.AmazonS3Util;
import com.buseni.ubukwebwiza.utils.ImagesUtils;
import com.buseni.ubukwebwiza.utils.UbUtils;

@Controller
@Navigation(url="/admin/providers/{idProvider:[\\d]+}/photos", name="Provider's photos", parent= {AdminProviderController.class, AdminHomeController.class})
public class AdminProviderPhotosController{
	
	
	public  static final Logger LOGGER = LoggerFactory.getLogger(AdminProviderPhotosController.class);

	@Autowired
	private ProviderService providerService;
	
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private AmazonS3Util amazonS3Util;
	

	
	@RequestMapping(value="/admin/providers/{idProvider:[\\d]+}/photos", method=RequestMethod.GET)
	public String photos(@PathVariable Integer idProvider, Model model) {
		LOGGER.info("IN: providers/photos-GET");
		Provider provider =  providerService.findOne(idProvider);
		model.addAttribute("provider", provider);
		return "adminpanel/provider/photos";
	}

	@RequestMapping(value="/admin/providers/{idProvider:[\\d]+}/photos/addPhoto", method=RequestMethod.GET)
	public String addPhoto(@PathVariable Integer idProvider, Model model) {
		LOGGER.info("IN: providers/addPhoto-GET");			
		Provider provider = providerService.findOne(idProvider);
		model.addAttribute("photoForm", new PhotoForm());	
		model.addAttribute("provider", provider );
		return "adminpanel/provider/editPhoto";
	}
	
	@RequestMapping(value="/admin/providers/{idProvider:[\\d]+}/photos/addPhoto", method=RequestMethod.POST)
	public String savePhoto(@PathVariable Integer idProvider, @ModelAttribute PhotoForm photoForm,
			BindingResult result, RedirectAttributes attributes) throws ServiceLayerException{		
		LOGGER.info("IN: providers/addPhoto-POST");
		Provider provider = providerService.findOne(idProvider);
		LOGGER.info("IN: providers/addPhoto to provider : " + provider);

		MultipartFile file  = photoForm.getFile();
		Photo photo = new Photo();
		String filename = "no_person.jpg";
		if (file != null && !file.isEmpty()) {
			if(file.getSize() > ImagesUtils.MAXSIZE){
				LOGGER.error("File size should be less than " + ImagesUtils.MAXSIZE+ " byte.");
				result.reject(ImagesUtils.MAX_SIZE_EXCEEDED_ERROR);
				attributes.addAttribute("org.springframework.validation.BindingResult.photoForm",result);
				attributes.addAttribute("photoForm", photoForm);
				attributes.addAttribute("provider", provider);	     
				
				return "adminpanel/provider/editPhoto";
			}

			filename = UbUtils.normalizeFileName(file.getOriginalFilename());
			photo.setFilename(filename);
			photo.setContentType(file.getContentType());
			photo.setCategory(EnumPhotoCategory.PROVIDER.getId());

		} else if (photoForm.getId() == null) {
			LOGGER.error("You failed to upload  because the file was empty.");
			result.reject("error.file.empty");
			attributes.addAttribute("org.springframework.validation.BindingResult.photoForm",result);
			attributes.addAttribute("photoForm", photoForm);
			attributes.addAttribute("provider", provider);
			return "adminpanel/provider/editPhoto";

		}

		try {
			photo.setDescription(photoForm.getDescription());
			photo.setId(photoForm.getId());
			photo.setEnabled(photoForm.isEnabled());
			photoService.create(photo);

			provider.getPhotos().add(photo);
			providerService.update(provider);

			// Save profil pricture to amazon S3
			File fileToUpload = ImagesUtils.prepareUploading(file,	EnumPhotoCategory.PROVIDER.getId());
			amazonS3Util.uploadFile(fileToUpload, filename);

			String message = "Photo " + photo.getId() + " was successfully added";
			attributes.addFlashAttribute("message", message);
			attributes.addFlashAttribute("provider", provider);
			photoForm = new PhotoForm();
			// photoForm.setIdProvider(provider.getId());
			attributes.addFlashAttribute("photoForm", photoForm);
			return "redirect:/admin/providers/" + provider.getId() + "/photos";

			// Business errors
		} catch (final ServiceLayerException e) {
			ErrorsHelper.rejectErrors(result, e.getErrors());
			LOGGER.error("Photo-save error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.photoForm", result);
			attributes.addFlashAttribute("provider", provider);
			return "adminpanel/provider/editPhoto";
		} 
	}
	
	
	

	@RequestMapping(value="/admin/providers/{idProvider:[\\d]+}/photos/deletePhoto", method=RequestMethod.GET)
	public String deletePhoto(@PathVariable Integer idProvider, @RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: providers/deletePhoto-GETT");
		Provider provider = providerService.deletePhoto(idProvider, id);	
		String message = "Photo " + id + " was successfully deleted";
		model.addAttribute("message", message);		
		model.addAttribute("provider", provider);
		PhotoForm photoForm = new PhotoForm();
		
		model.addAttribute("photoForm", photoForm);	
		return "adminpanel/provider/photos::listPhotos";
	}
	
	@RequestMapping(value="/admin/providers/{idProvider:[\\d]+}/photos/editPhoto", method=RequestMethod.GET)
	public String editPhoto(@PathVariable Integer idProvider, @RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: providers/editPhoto-GET");
		Photo photo = photoService.findById(id);
		PhotoForm photoForm = new PhotoForm();
		photoForm.setDescription(photo.getDescription());
		photoForm.setId(id);
		photoForm.setName(photo.getFilename());
		//photoForm.setIdProvider(idProvider);
		photoForm.setEnabled(photo.isEnabled());
		
		model.addAttribute("photoForm", photoForm);	
		Provider provider = providerService.findOne(idProvider);
		if(!CollectionUtils.isEmpty(provider.getPhotos()) ){
			provider.getPhotos().remove(photo);
		}
		
		model.addAttribute("provider", provider );
		return "adminpanel/provider/editPhoto";
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
	

	/*@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception) {
		//Map<Object, Object> model = new HashMap<Object, Object>();
		 ModelAndView mav = new ModelAndView();
		LOGGER.error(exception.getMessage());
		System.out.println(exception.getMessage());
		if (exception instanceof MaxUploadSizeExceededException) {
			mav.addObject("errors", "File size should be less then " + ((MaxUploadSizeExceededException) exception).getMaxUploadSize()+ " byte.");

		} else {
			mav.addObject("errors", "Unexpected error: " + exception.getMessage());
		}

		//model.put("photoForm", new PhotoForm());

		
	       // mav.addObject("exception", e);
	      //  mav.addObject("url", req.getRequestURL());
	     //   mav.setViewName(DEFAULT_ERROR_VIEW);
	        mav.setViewName("adminpanel/provider/editPhoto");
	        
		return mav;

	}*/
	
	
	
}
