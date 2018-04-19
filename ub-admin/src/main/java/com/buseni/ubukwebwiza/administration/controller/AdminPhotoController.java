package com.buseni.ubukwebwiza.administration.controller;

import java.io.File;

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
import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.gallery.beans.PhotoForm;
import com.buseni.ubukwebwiza.gallery.domain.Photo;
import com.buseni.ubukwebwiza.gallery.service.PhotoService;
import com.buseni.ubukwebwiza.utils.AmazonS3Util;
import com.buseni.ubukwebwiza.utils.ImagesUtils;
import com.buseni.ubukwebwiza.utils.PageWrapper;
import com.buseni.ubukwebwiza.utils.UbUtils;
@Controller
@Navigation(url="/admin/photos", name="Photos", parent= AdminHomeController.class)
@RequestMapping(value="/admin")
public class AdminPhotoController {
	
	

	public  static final Logger LOGGER = LoggerFactory.getLogger(AdminPhotoController.class);
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private AmazonS3Util amazonS3Util;
	
	@RequestMapping(value="/photos", method=RequestMethod.GET)
	public String photos(Model model, Pageable page){
		Page<Photo>  pagePhoto = photoService.findByCategory(EnumPhotoCategory.HOME_PAGE.getId(), page);
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
	public String savePhoto( @ModelAttribute PhotoForm photoForm, BindingResult result, RedirectAttributes attributes) throws BusinessException{		
		LOGGER.info("IN: photos/save-POST");
	
			MultipartFile file  = photoForm.getFile();
			Photo photo = new Photo();
			String filename = "no_person.jpg";
			if (file != null && !file.isEmpty()) {
				if(file.getSize() > ImagesUtils.MAXSIZE){
					LOGGER.error("File size should be less than " + ImagesUtils.MAXSIZE+ " byte.");
					result.reject(ImagesUtils.MAX_SIZE_EXCEEDED_ERROR, new String[] {String.valueOf(ImagesUtils.MAXSIZE)}, "File size should be less than " + ImagesUtils.MAXSIZE+ " byte.");
					//attributes.addAttribute("org.springframework.validation.BindingResult.photoForm",result);
					attributes.addFlashAttribute("photoForm", photoForm);     
					attributes.addFlashAttribute("errors", "File size should be less than " + ImagesUtils.MAXSIZE+ " byte.");
					return "adminpanel/photo/editPhoto";
				}
				filename = UbUtils.normalizeFileName(file.getOriginalFilename());
				photo.setFilename(filename);
				photo.setContentType(file.getContentType());
			} else if(photoForm.getId() == null){
	        		LOGGER.error("You failed to upload  because the file was empty.");
	        		result.reject("error.file.empty");
	            	//attributes.addFlashAttribute("org.springframework.validation.BindingResult.photoForm", result);
	    			attributes.addFlashAttribute("photoForm", photoForm);
	    			attributes.addFlashAttribute("errors", "You failed to upload  because the file was empty.");
	        		return "adminpanel/photo/editPhoto";   	
	        }
			
			photo.setCategory(EnumPhotoCategory.HOME_PAGE.getId());
            photo.setDescription(photoForm.getDescription());          
			photo.setId(photoForm.getId());
			photo.setEnabled(photoForm.isEnabled());
			
		//	try {								
				photoService.addOrUpdate(photo);			
				
				//Save profil pricture to amazon S3
				File fileToUpload =  ImagesUtils.prepareUploading(file,filename, EnumPhotoCategory.HOME_PAGE.getId());
				amazonS3Util.uploadFile(fileToUpload, filename);				
				
				String message = "Photo " + photo.getId() + " was successfully added";				
				attributes.addFlashAttribute("message", message);				
				return "redirect:/admin/photos";				
			
				//Business errors
			/*} catch (final BusinessException e) {
				ErrorsHelper.rejectErrors(result, e.getErrors());
				LOGGER.info("Photo-edit error: " + result.toString());
				attributes.addFlashAttribute("org.springframework.validation.BindingResult.photoForm", result);
				attributes.addFlashAttribute("photoForm", photoForm);
								
				return "adminpanel/photo/editPhoto";
			}*/
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
		Photo photo = photoService.findById(id);
		photoService.delete(photo);
		amazonS3Util.deleteFile(photo.getFilename());
		String message = "Photo " + id + " was successfully deleted";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/admin/photos";
	}
	
	
	@ModelAttribute("currentMenu")
	public String module(){
		return "photos";
	}
}
