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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.vendor.domain.Province;
import com.buseni.ubukwebwiza.vendor.service.ProvinceService;
import com.buseni.ubukwebwiza.vendor.utils.PageWrapper;

@Controller
@RequestMapping(value="/admin")
@Navigation(url="/admin/provinces", name="Provinces", parent= AdminHomeController.class)
public class AdminProvinceController {

	@Autowired
	private ProvinceService provinceService;
	
	@RequestMapping(value="/provinces", method=RequestMethod.GET)
	public String provinces(Model model, Pageable page){
		Page<Province> pageProvince = provinceService.findAll(page);
		PageWrapper<Province> pageWrapper = new PageWrapper<Province>(pageProvince, "/admin/provinces");
		model.addAttribute("page", pageWrapper);
		if(!model.containsAttribute("province")){
			model.addAttribute("province", new Province());
		}
		model.addAttribute("provinces", pageProvince.getContent());	
		
		return "adminpanel/province/listingProvince";
	}
	
	@RequestMapping(value="/provinces/add",method=RequestMethod.POST)
	public String save(Province province , RedirectAttributes attributes){		
			provinceService.add(province);
		String message = "Province " + province.getId() + " was successfully added";
		attributes.addFlashAttribute("message", message);
		
		return "redirect:/admin/provinces";
	}
	
	@RequestMapping(value="/provinces/delete", method=RequestMethod.GET)
	public String delete( @RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes) {
		provinceService.delete(id);
		String message = "Province " + id + " was successfully deleted";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/admin/provinces";
	}
	
	@RequestMapping(value="/provinces/edit", method=RequestMethod.GET)
	public String edit(@RequestParam(value="id", required=true) Integer id, Model model) {
		Province province =  provinceService.findOne(id);
		model.addAttribute("province", province);
		return "adminpanel/province/editProvince";
	}
	
	@RequestMapping(value="/provinces/new", method=RequestMethod.GET)
	public String newProvince( Model model) {		
		model.addAttribute("province", new Province());
		return "adminpanel/province/editProvince";
	}
	
	@ModelAttribute("currentMenu")
	public String module(){
		return "provinces";
	}
	
}
