package com.buseni.ubukwebwiza.contactus.controller;



import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.contactus.domain.ContactusForm;
import com.buseni.ubukwebwiza.contactus.service.ContactusService;
import com.buseni.ubukwebwiza.exceptions.ErrorsHelper;
import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.home.HomeController;

@Controller
@Navigation(url="/contactus", name="Contact us" , parent = HomeController.class)
public class ContactusController {

	public  static final Logger LOGGER = LoggerFactory.getLogger(ContactusController.class);
	
	@Autowired
	private ContactusService  contactusService;

	@RequestMapping(value="/contactus", method=RequestMethod.GET)
	public String goToContactus(Model model){
		model.addAttribute("contactusForm", new ContactusForm());
		return "frontend/contactus";
	}

	@RequestMapping(value="/contactus", method=RequestMethod.POST)
	public String contactus(@RequestParam(value="enterHere", required=false) String enterHere, @Valid @ModelAttribute ContactusForm contactusForm,  BindingResult result, RedirectAttributes attributes){
		//If enterHere is filled it is a spam
		if(StringUtils.isNotEmpty(enterHere)){
					return "redirect:/";
		}
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("Contactus error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.contactusForm", result);
			attributes.addFlashAttribute("contactusForm", contactusForm);
			return "frontend/contactus";

		}
		try {
			contactusService.add(contactusForm);
		} catch (BusinessException e) {
			ErrorsHelper.rejectErrors(result, e.getErrors());
			LOGGER.info("Contactus-edit error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.contactusForm", result);
			attributes.addFlashAttribute("contactusForm", contactusForm);
			return "frontend/contactus";
		}
		attributes.addFlashAttribute("message", "Your message have been sent! Thank you");
		return "redirect:/contactus";
	}



	@ModelAttribute("currentMenu")
	public String module(){
		return "contactus";
	}
	
	@ModelAttribute("showSidebar")
	public boolean showSidebar(){
		return true;
	}
}
