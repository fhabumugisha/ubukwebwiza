package com.buseni.ubukwebwiza.administration.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.buseni.ubukwebwiza.exceptions.ErrorsHelper;
import com.buseni.ubukwebwiza.exceptions.ServiceLayerException;
import com.buseni.ubukwebwiza.gallery.beans.PhotoForm;
import com.buseni.ubukwebwiza.utils.ImagesUtils;
import com.buseni.ubukwebwiza.utils.PageWrapper;
import com.buseni.ubukwebwiza.vendor.domain.Photo;
import com.buseni.ubukwebwiza.vendor.service.PhotoService;
@Controller
@Navigation(url="/admin/photos", name="Photos", parent= AdminHomeController.class)
@RequestMapping(value="/admin")
public class AdminPhotoController {
	
	public static final int HP_IMAGE_HEIGHT = 300;

	public static final int HP_IMAGE_WIDTH = 944;

	public  static final Logger LOGGER = LoggerFactory.getLogger(AdminVendorController.class);
	
	@Autowired
	private PhotoService photoService;
	
	@RequestMapping(value="/photos", method=RequestMethod.GET)
	public String photos(Model model, Pageable page){
		Page<Photo>  pagePhoto = photoService.findAll(page);
		model.addAttribute("photos", pagePhoto.getContent());		
		PageWrapper<Photo> pageWrapper = new PageWrapper<Photo>(pagePhoto, "/admin/photos");
		model.addAttribute("page", pageWrapper);	
		return "adminpanel/photo/listingPhoto";
	}

	@RequestMapping(value="/photos/new", method=RequestMethod.GET)
	public String add(Model model, Pageable page){
		model.addAttribute("photoForm", new PhotoForm());
		return "adminpanel/photo/editPhoto";
	}
	
	@RequestMapping(value="/photos/addPhoto",method=RequestMethod.POST)
	public String savePhoto( @ModelAttribute PhotoForm photoForm, BindingResult result, RedirectAttributes attributes) throws ServiceLayerException{		
		LOGGER.info("IN: photos/save-POST");
	
			MultipartFile file  = photoForm.getFile();
			Photo photo = new Photo();
			if (file != null && !file.isEmpty()) {
	            try {
	               
	            	//String workingDir = System.getProperty("user.dir");
	    /*            String saveDirectory =  env.getProperty("files.location");
	                file.transferTo(new File(saveDirectory+"/" + file.getOriginalFilename()));
	             
	                LOGGER.info("You successfully uploaded " + file.getOriginalFilename() + " into " + file.getOriginalFilename() + "-uploaded !");
	               
	                resizeImagScal(new File(saveDirectory+"/" + file.getOriginalFilename()), new File(saveDirectory+"/" + "thumbnail" + file.getOriginalFilename()));*/
	                
	                photo.setFilename(file.getOriginalFilename());
	                photo.setContent(ImagesUtils.resizeImage(file, HP_IMAGE_WIDTH, HP_IMAGE_HEIGHT));
	                photo.setContentType(file.getContentType());
	                
	            } catch (Exception e) {
	            	LOGGER.info("You failed to upload " + file.getName() + " => " + e.getMessage());
	            	result.reject(e.getMessage());
	            	attributes.addFlashAttribute("org.springframework.validation.BindingResult.photoForm", result);
	    			attributes.addFlashAttribute("photoForm", photoForm);
	    			return "adminpanel/photo/editPhoto";
	            	
	            }
	        } else {
	        	if(photoForm.getId() == null){
	        		LOGGER.info("You failed to upload  because the file was empty.");
	        		result.reject("error.file.empty");
	            	attributes.addFlashAttribute("org.springframework.validation.BindingResult.photoForm", result);
	    			attributes.addFlashAttribute("photoForm", photoForm);
	        		return "adminpanel/photo/editPhoto";
	        	}
	        	
	        }
			
			photo.setCategory(EnumPhotoCategory.HOME_PAGE.getId());
            photo.setDescription(photoForm.getDescription());          
			photo.setId(photoForm.getId());
			photo.setEnabled(photoForm.isEnabled());
			
			try {								
				photoService.create(photo);				
				String message = "Photo " + photo.getId() + " was successfully added";				
				attributes.addFlashAttribute("message", message);				
				return "redirect:/admin/photos";				
			
				//Business errors
			} catch (final ServiceLayerException e) {
				ErrorsHelper.rejectErrors(result, e.getErrors());
				LOGGER.info("Photo-edit error: " + result.toString());
				attributes.addFlashAttribute("org.springframework.validation.BindingResult.photoForm", result);
				attributes.addFlashAttribute("photoForm", photoForm);
								
				return "adminpanel/photo/editPhoto";
			}
	}
	
	
	@RequestMapping(value="/photos/edit", method=RequestMethod.GET)
	public String editPhoto( @RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: photos/edit-GET");
		Photo photo = photoService.findById(id);
		PhotoForm photoForm = new PhotoForm();
		photoForm.setDescription(photo.getDescription());
		photoForm.setId(id);
		photoForm.setName(photo.getFilename());		
		photoForm.setEnabled(photo.isEnabled());		
		model.addAttribute("photoForm", photoForm);	
		return "adminpanel/photo/editPhoto";
	}
	
	
	@RequestMapping(value="/photos/delete", method=RequestMethod.GET)
	public String deletePhoto(@RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes) {
		LOGGER.info("IN: photos/delete-GET");
		photoService.delete(id);
		String message = "Photo " + id + " was successfully deleted";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/admin/photos";
	}
	
	
	@ModelAttribute("currentMenu")
	public String module(){
		return "photos";
	}
}
