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
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.contactus.domain.ContactusForm;
import com.buseni.ubukwebwiza.contactus.service.ContactusService;
import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.exceptions.ErrorsHelper;
import com.buseni.ubukwebwiza.utils.PageWrapper;

@Controller
@RequestMapping(value="/admin")
@Navigation(url="/admin/contactuss", name="Contactus", parent= AdminHomeController.class)
public class AdminContactusController {

	public  static final Logger LOGGER = LoggerFactory.getLogger(AdminContactusController.class);

	@Autowired
	private ContactusService contactusService;
	
	

	@RequestMapping(value="/contactus", method=RequestMethod.GET)
	public String contactus(Model model, Pageable page){
		LOGGER.info("IN: Contactus/list-GET");

		Page<ContactusForm> pageContactus = contactusService.findAll(page);
		PageWrapper<ContactusForm> pageWrapper = new PageWrapper<ContactusForm>(pageContactus, "/admin/contactus");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("messages", pageContactus.getContent());	
		if(!model.containsAttribute("contactusForm")){
			model.addAttribute("contactusForm", new ContactusForm());
		}
		return "adminpanel/contactus/listingContactus";
	}

	

	@RequestMapping(value="/contactus/save",method=RequestMethod.POST)
	public String save(@Valid @ModelAttribute ContactusForm contactusForm , BindingResult result, RedirectAttributes attributes) throws BusinessException{		
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("contactusForm-edit error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.district", result);
			attributes.addFlashAttribute("contactusForm", contactusForm);
			return "adminpanel/contactus/editContactus";

		}

		try {
			contactusService.update(contactusForm);
			//Business errors	
		} catch (final BusinessException e) {
			ErrorsHelper.rejectErrors(result, e.getErrors());
			LOGGER.info("Contactus-edit error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.contactusForm", result);
			attributes.addFlashAttribute("contactusForm", contactusForm);
			return "adminpanel/contactus/editContactus";
		}


		LOGGER.info("IN: Contactus/save-POSST");
		String message = "Contactus " + contactusForm.getId() + " was successfully added";
		attributes.addFlashAttribute("message", message);
		return "redirect:/admin/contactus";



	}


	@RequestMapping(value="/contactus/delete", method=RequestMethod.GET)
	public String delete( @RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes) {
		LOGGER.info("IN: Contactus/delete-GET");
		contactusService.delete(id);
		String message = "Conctactus " + id + " was successfully deleted";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/admin/contactus";
	}

	@RequestMapping(value="/contactus/edit", method=RequestMethod.GET)
	public String edit(@RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: COntactus/edit-GET");
		ContactusForm contactusForm =  contactusService.findOne(id);
		model.addAttribute("contactusForm", contactusForm);
		return "adminpanel/contactus/editContactus";
	}


	@ModelAttribute("currentMenu")
	public String module(){
		return "contactus";
	}
	
	@ModelAttribute("unreadMessages")
	public int unreadMessages(){
		List<ContactusForm>  unreadMessages = contactusService.findUnread();
		if(!CollectionUtils.isEmpty(unreadMessages)){
			return unreadMessages.size();
			
		}
		return 0;
	}


	
}
