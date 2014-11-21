package com.buseni.ubukwebwiza.administrator.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.vendor.domain.CodeTypeWeddingService;
import com.buseni.ubukwebwiza.vendor.service.CodeWeddingServiceManager;
import com.buseni.ubukwebwiza.vendor.utils.PageWrapper;

@Controller
@RequestMapping(value="/admin/typesWeddingServices", method=RequestMethod.GET)
public class AdminCodeWeddingServiceController {


	@Autowired
	private CodeWeddingServiceManager codeWeddingServiceManager;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String typesWeddingService(Model model, Pageable page){
		Page<CodeTypeWeddingService> pageTypeWeddingService = codeWeddingServiceManager.findAll(page);
		PageWrapper<CodeTypeWeddingService> pageWrapper = new PageWrapper<CodeTypeWeddingService>(pageTypeWeddingService, "/typesWeddingServices");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("currentMenu", "typesWeddingServices");
		model.addAttribute("typesWeddingServices", pageTypeWeddingService.getContent());	
		
		return "adminpanel/typeweddingservice/listingTypeWeddingService";
	}
	
	@ModelAttribute("allWeddingServices")
	public List<CodeTypeWeddingService> populateWeddingServices(){
		return codeWeddingServiceManager.findByActiveFlag(1);
	}
}
