package com.buseni.ubukwebwiza.administrator.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminSettingsController {

	/*@Autowired
	private DistrictService codeDistrictService;*/
	
	@RequestMapping(value="/admin/settings",method=RequestMethod.GET)
	public String settings(Model model, Pageable page){
		/*Page<CodeDistrict> pageDistrict = codeDistrictService.findAll(page);
		PageWrapper<CodeDistrict> pageWrapper = new PageWrapper<CodeDistrict>(pageDistrict, "/districts");
		model.addAttribute("page", pageWrapper);
		
		model.addAttribute("districts", pageDistrict.getContent());	*/
	//	model.addAttribute("currentMenu", "settings");
		return "adminpanel/settings/listing";
	}
	
	@ModelAttribute("currentMenu")
	public String module(){
		return "settings";
	}
}
