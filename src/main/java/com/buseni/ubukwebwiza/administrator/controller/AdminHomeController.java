package com.buseni.ubukwebwiza.administrator.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.administrator.domain.Administrator;
import com.buseni.ubukwebwiza.administrator.service.AdministratorService;
import com.buseni.ubukwebwiza.vendor.utils.PageWrapper;

@Controller
//@SessionAttributes({"allDistricts", "allWeddingServices"})
public class AdminHomeController {
	
	@Autowired
	private AdministratorService administratorService;
	
	
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public String home(Model model){
		model.addAttribute("currentMenu", "dashbord");
		return "adminpanel/dashbord";
	}

	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public String sign(Model model){
		model.addAttribute("currentMenu", "dashbord");
		return "adminpanel/dashbord";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(Model model){
		//model.addAttribute("currentMenu", "dashbord");
		return "adminpanel/signin";
	}

	@RequestMapping(value="/admin/admins",method=RequestMethod.GET)
	public String admins(Model model, Pageable page){
		Page<Administrator> adminPage  =  administratorService.findAll(page);	
		PageWrapper<Administrator> pageWrapper = new PageWrapper<Administrator>(adminPage, "/admins");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("currentMenu", "admins");
		model.addAttribute("admins", adminPage.getContent());		
		return "adminpanel/admin/listing";
	}
	

}
