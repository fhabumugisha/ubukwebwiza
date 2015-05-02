package com.buseni.ubukwebwiza.gallery.controller;



import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.gallery.domain.Photo;
import com.buseni.ubukwebwiza.gallery.domain.PhotoDetails;
import com.buseni.ubukwebwiza.gallery.service.PhotoService;
import com.buseni.ubukwebwiza.home.HomeController;
import com.buseni.ubukwebwiza.utils.PageWrapper;

@Controller
//@SessionAttributes({"allDistricts", "allWeddingServices"})
@Navigation(url="/gallery", name="Photo gallery", parent= HomeController.class)
public class GalleryController {
	
	public  static final Logger LOGGER = LoggerFactory.getLogger(GalleryController.class);
	@Autowired
	private PhotoService photoService;	

	@RequestMapping(value="/gallery", method=RequestMethod.GET)
	public String photos(Model model, Pageable page){
		//Page<Photo>  photosPage = photoService.findByEnabledAndCategory(Boolean.TRUE, EnumPhotoCategory.PROVIDER.getId(), page);
		
		Page<PhotoDetails>  photosPage = photoService.findPhotoDetails(page);
		model.addAttribute("photos", photosPage.getContent());
		PageWrapper<PhotoDetails> pageWrapper = new PageWrapper<PhotoDetails>(photosPage, "/gallery");
		model.addAttribute("page", pageWrapper);
		return "frontend/gallery/photoGallery";
	}
	
	@RequestMapping(value = "/image/{imageId:[\\d]+}", method = RequestMethod.GET)
	public void showImage(@PathVariable("imageId") Integer imageId,
			HttpServletResponse response, HttpServletRequest request)
			throws IOException {
				
		Photo photo = photoService.findById(imageId);
		byte[] imageContent = photo.getContent();
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.setContentLength(imageContent.length);
		response.setStatus(HttpStatus.OK.value());
		response.getOutputStream().write(imageContent);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	@ModelAttribute("currentMenu")
	public String module(){
		return "gallery";
	}
}
