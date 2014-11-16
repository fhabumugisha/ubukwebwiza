package com.buseni.ubukwebwiza.gallery;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.vendor.domain.Photo;
import com.buseni.ubukwebwiza.vendor.service.PhotoService;

@Controller
//@SessionAttributes({"allDistricts", "allWeddingServices"})
public class GalleryController {

	
	
	@Autowired
	private PhotoService photoService;
	
	

	@RequestMapping(value="/gallery", method=RequestMethod.GET)
	public String photos(Model model){
		Page<Photo>  photos = photoService.findByActiveFlag(1, new PageRequest(0, 100));
		model.addAttribute("photos", photos.getContent());
		return "frontend/gallery/photoGallery";
	}
	

	
	@ModelAttribute("page")
	public String module(){
		return "gallery";
	}
}
