package com.buseni.ubukwebwiza.administrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.vendor.domain.Photo;
import com.buseni.ubukwebwiza.vendor.service.PhotoService;
import com.buseni.ubukwebwiza.vendor.utils.PageWrapper;
@Controller
public class AdminPhotoController {
	
	@Autowired
	private PhotoService photoService;
	
	@RequestMapping(value="/admin/photos", method=RequestMethod.GET)
	public String photos(Model model, Pageable page){
		Page<Photo>  pagePhoto = photoService.findAll(page);
		model.addAttribute("photos", pagePhoto.getContent());		
		PageWrapper<Photo> pageWrapper = new PageWrapper<Photo>(pagePhoto, "/admin/photos");
		model.addAttribute("page", pageWrapper);
		//model.addAttribute("currentMenu", "photos");		
		return "adminpanel/photo/listingPhoto";
	}

	
	@ModelAttribute("currentMenu")
	public String module(){
		return "photos";
	}
}
