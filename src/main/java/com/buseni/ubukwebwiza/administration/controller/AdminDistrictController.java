package com.buseni.ubukwebwiza.administration.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.exceptions.ErrorsHelper;
import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.utils.PageWrapper;
import com.buseni.ubukwebwiza.provider.domain.District;
import com.buseni.ubukwebwiza.provider.domain.Province;
import com.buseni.ubukwebwiza.provider.service.DistrictService;
import com.buseni.ubukwebwiza.provider.service.ProvinceService;

@Controller
@RequestMapping(value="/admin")
@Navigation(url="/admin/districts", name="Districts", parent= AdminHomeController.class)
public class AdminDistrictController {

	public  static final Logger LOGGER = LoggerFactory.getLogger(AdminDistrictController.class);

	@Autowired
	private DistrictService districtService;
	
	@Autowired
	private ProvinceService provinceService;

	@RequestMapping(value="/districts", method=RequestMethod.GET)
	public String districts(Model model, Pageable page){
		LOGGER.info("IN: Districts/list-GET");

		Page<District> pageDistrict = districtService.findAll(page);
		PageWrapper<District> pageWrapper = new PageWrapper<District>(pageDistrict, "/admin/districts");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("districts", pageDistrict.getContent());	
		if(!model.containsAttribute("district")){
			model.addAttribute("district", new District());
		}

		return "adminpanel/district/listingDistrict";
	}

	@RequestMapping(value="/districts/save",method=RequestMethod.POST)
	public String save(@Valid @ModelAttribute District district , BindingResult result, RedirectAttributes attributes) throws BusinessException{		
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("Strategy-edit error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.district", result);
			attributes.addFlashAttribute("district", district);
			return "adminpanel/district/editDistrict";

		}

		try {
			districtService.add(district);
			//Business errors	
		} catch (final BusinessException e) {
			ErrorsHelper.rejectErrors(result, e.getErrors());
			LOGGER.info("District-edit error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.district", result);
			attributes.addFlashAttribute("district", district);
			return "adminpanel/district/editDistrict";
		}


		LOGGER.info("IN: Districts/save-POSST");
		String message = "District " + district.getId() + " was successfully added";
		attributes.addFlashAttribute("message", message);
		return "redirect:/admin/districts";



	}



	@RequestMapping(value="/districts/delete", method=RequestMethod.GET)
	public String delete( @RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes) {
		LOGGER.info("IN: Districts/delete-GET");
		districtService.delete(id);
		String message = "District " + id + " was successfully deleted";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/admin/districts";
	}

	@RequestMapping(value="/districts/edit", method=RequestMethod.GET)
	public String edit(@RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: Districts/edit-GET");
		District district =  districtService.findOne(id);
		model.addAttribute("district", district);
		return "adminpanel/district/editDistrict";
	}

	@RequestMapping(value="/districts/new", method=RequestMethod.GET)
	public String newDistrict( Model model) {		
		LOGGER.info("IN: Districts/new-GET");
		model.addAttribute("district", new District());
		return "adminpanel/district/editDistrict";
	}

	@ModelAttribute("currentMenu")
	public String module(){
		return "districts";
	}

	@ModelAttribute("provinces")
	public List<Province> populateProvinces(){
		return provinceService.findByEnabled(Boolean.TRUE);
	}
	
}
