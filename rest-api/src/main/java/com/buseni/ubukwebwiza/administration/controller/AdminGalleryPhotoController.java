package com.buseni.ubukwebwiza.administration.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ubukwebwiza.administrator.enums.EnumPhotoCategory;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.gallery.domain.Photo;
import com.buseni.ubukwebwiza.gallery.service.PhotoService;
import com.buseni.ubukwebwiza.utils.PageWrapper;
@Controller
@Navigation(url="/admin/gallery-photos", name="Gallery Photos", parent= AdminHomeController.class)
@RequestMapping(value="/admin")
public class AdminGalleryPhotoController {
	
	

	public  static final Logger LOGGER = LoggerFactory.getLogger(AdminGalleryPhotoController.class);
	
	@Autowired
	private PhotoService photoService;
	

	
	@RequestMapping(value="/gallery-photos", method=RequestMethod.GET)
	public String photos(Model model, Pageable page){
		Page<Photo>  pagePhoto = photoService.findByCategory(EnumPhotoCategory.PROVIDER.getId(), page);
		model.addAttribute("galleryPhotos", pagePhoto.getContent());		
		PageWrapper<Photo> pageWrapper = new PageWrapper<Photo>(pagePhoto, "/admin/gallery-photos");
		model.addAttribute("page", pageWrapper);	
		return "adminpanel/photo/listingGalleryPhoto";
	}

	
	
	
	
	
	@RequestMapping(value="/gallery-photos/addToGallery", method=RequestMethod.GET)
	public String addToGallery(@RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes) {
		LOGGER.info("IN: photos/addToGallery-GET");
		Photo photo = photoService.findById(id);
		photo.setIsGalleryPhoto(Boolean.TRUE);
		photoService.update(photo);
		
		String message = "Photo " + id + " was successfully added to gallery";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/admin/gallery-photos";
	}
	
	@RequestMapping(value="/gallery-photos/removeFromGallery", method=RequestMethod.GET)
	public String removeFromGallery(@RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes) {
		LOGGER.info("IN: photos/delete-GET");
		Photo photo = photoService.findById(id);
		photo.setIsGalleryPhoto(Boolean.FALSE);
		photoService.update(photo);
		
		String message = "Photo " + id + " was successfully removed from gallery";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/admin/gallery-photos";
	}
	
	
	@ModelAttribute("currentMenu")
	public String module(){
		return "gallery-photos";
	}
}
