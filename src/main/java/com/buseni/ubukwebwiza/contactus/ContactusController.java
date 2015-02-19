package com.buseni.ubukwebwiza.contactus;



import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.contactus.service.ContactusService;
import com.buseni.ubukwebwiza.home.HomeController;

@Controller
//@SessionAttributes({"allDistricts", "allWeddingServices"})
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
	public String contactus(@Valid @ModelAttribute ContactusForm contactusForm,  BindingResult result, RedirectAttributes attributes){
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("Contactus error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.district", result);
			attributes.addFlashAttribute("contactusForm", contactusForm);
			return "frontend/contactus";

		}
		contactusService.add(contactusForm);
		attributes.addFlashAttribute("message", "Your message have been sent! Thank you");
		return "redirect:/contactus";
	}



	@ModelAttribute("currentMenu")
	public String module(){
		return "contactus";
	}
}
