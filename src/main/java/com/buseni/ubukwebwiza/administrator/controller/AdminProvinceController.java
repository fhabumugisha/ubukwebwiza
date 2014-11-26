package com.buseni.ubukwebwiza.administrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.buseni.ubukwebwiza.vendor.domain.Province;
import com.buseni.ubukwebwiza.vendor.service.ProvinceService;
import com.buseni.ubukwebwiza.vendor.utils.PageWrapper;

@Controller
@RequestMapping(value="/admin")
public class AdminProvinceController {

	@Autowired
	private ProvinceService provinceService;
	
	@RequestMapping(value="/provinces")
	public String provinces(Model model, Pageable page){
		Page<Province> pageProvince = provinceService.findAll(page);
		PageWrapper<Province> pageWrapper = new PageWrapper<Province>(pageProvince, "/admin/provinces");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("provinces", pageProvince.getContent());	
		model.addAttribute("province", new Province());
		return "adminpanel/province/listingProvince";
	}
	
	@RequestMapping(value="/provinces/add",method=RequestMethod.POST)
	public String save(Model model, Province province){
		if(province != null ){
			if(province.getId() != null){
				provinceService.update(province);
			}else{
				provinceService.add(province);
			}
		}
		
		String message = "Province " + province.getId() + " was successfully added";
		model.addAttribute("message", message);
		return "redirect:/admin/provinces";
	}
	
	@RequestMapping(value="/provinces/delete", method=RequestMethod.GET)
	public String delete(Model model, @RequestParam(value="id", required=true) Integer id) {
		provinceService.delete(id);
		String message = "Province " + id + " was successfully deleted";
		model.addAttribute("message", message);
		return "redirect:/admin/provinces";
	}
	
	@RequestMapping(value="/provinces/edit", method=RequestMethod.GET)
	public String edit(Model model, @RequestParam(value="id", required=true) Integer id) {
		Province province =  provinceService.findOne(id);
		model.addAttribute("province", province);
		return "redirect:/admin/provinces";
	}
	
	@ModelAttribute("currentMenu")
	public String module(){
		return "provinces";
	}
	
}
