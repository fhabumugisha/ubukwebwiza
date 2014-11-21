package com.buseni.ubukwebwiza.gallery;



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
//@SessionAttributes({"allDistricts", "allWeddingServices"})
public class GalleryController {
	
	
	@Autowired
	private PhotoService photoService;	

	@RequestMapping(value="/gallery", method=RequestMethod.GET)
	public String photos(Model model, Pageable page){
		Page<Photo>  photosPage = photoService.findByActiveFlag(1, page);
		model.addAttribute("photos", photosPage.getContent());
		PageWrapper<Photo> pageWrapper = new PageWrapper<Photo>(photosPage, "/gallery");
		model.addAttribute("page", pageWrapper);
		return "frontend/gallery/photoGallery";
	}
		
	@ModelAttribute("currentMenu")
	public String module(){
		return "gallery";
	}
}
