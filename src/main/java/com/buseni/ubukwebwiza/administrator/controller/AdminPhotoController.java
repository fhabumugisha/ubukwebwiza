package com.buseni.ubukwebwiza.administrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.vendor.domain.Photo;
import com.buseni.ubukwebwiza.vendor.service.PhotoService;
@Controller
@RequestMapping(value="/admin/photos")
public class AdminPhotoController {
	
	@Autowired
	private PhotoService photoService;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String photos(Model model, Pageable page){
		Page<Photo>  photos = photoService.findAll(page);
		model.addAttribute("photos", photos.getContent());
		return "adminpanel/photo/listing";
	}

}
