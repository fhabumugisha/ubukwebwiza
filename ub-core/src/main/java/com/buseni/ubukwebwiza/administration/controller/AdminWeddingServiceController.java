package com.buseni.ubukwebwiza.administration.controller;

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
import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.exceptions.ErrorsHelper;
import com.buseni.ubukwebwiza.provider.domain.WeddingService;
import com.buseni.ubukwebwiza.provider.service.WeddingServiceManager;
import com.buseni.ubukwebwiza.utils.PageWrapper;

@Controller
@RequestMapping(value="/admin")
@Navigation(url="/admin/weddingservices", name="Wedding Services", parent= AdminHomeController.class)
public class AdminWeddingServiceController {

	public  static final Logger LOGGER = LoggerFactory.getLogger(AdminWeddingServiceController.class);

	@Autowired
	private WeddingServiceManager weddingServiceManager;

	@RequestMapping(value="/weddingservices", method=RequestMethod.GET)
	public String weddingServices(Model model, Pageable page){
		LOGGER.info("IN: Wedding Services/list-GET");

		Page<WeddingService> pageWeddingService = weddingServiceManager.findAll(page);
		PageWrapper<WeddingService> pageWrapper = new PageWrapper<WeddingService>(pageWeddingService, "/admin/weddingservices");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("weddingServices", pageWeddingService.getContent());	
		if(!model.containsAttribute("weddingService")){
			model.addAttribute("weddingService", new WeddingService());
		}
		

		return "adminpanel/weddingservice/listingWeddingService";
	}

	@RequestMapping(value="/weddingservices/save",method=RequestMethod.POST)
	public String save(@Valid @ModelAttribute WeddingService weddingService , BindingResult result, RedirectAttributes attributes) throws BusinessException{		
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("Strategy-edit error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.weddingService", result);
			attributes.addFlashAttribute("weddingService", weddingService);
			return "adminpanel/weddingservice/editWeddingService";

		}else{

			try {
				weddingServiceManager.add(weddingService);
				//Business errors	
			} catch (final BusinessException e) {
				ErrorsHelper.rejectErrors(result, e.getErrors());
				LOGGER.info("WeddingService-edit error: " + result.toString());
				attributes.addFlashAttribute("org.springframework.validation.BindingResult.weddingService", result);
				attributes.addFlashAttribute("weddingService", weddingService);
				return "adminpanel/weddingservice/editWeddingService";
			}


			LOGGER.info("IN: Wedding Services/save-POSST");
			String message = "WeddingService " + weddingService.getId() + " was successfully added";
			attributes.addFlashAttribute("message", message);
			return "redirect:/admin/weddingservices";
		}


	}



	@RequestMapping(value="/weddingservices/delete", method=RequestMethod.GET)
	public String delete( @RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes) {
		LOGGER.info("IN: Wedding Services/delete-GET");
		weddingServiceManager.delete(id);
		String message = "WeddingService " + id + " was successfully deleted";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/admin/weddingservices";
	}

	@RequestMapping(value="/weddingservices/edit", method=RequestMethod.GET)
	public String edit(@RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: Wedding Services/edit-GET");
		WeddingService weddingService =  weddingServiceManager.findOne(id);
		model.addAttribute("weddingService", weddingService);
		return "adminpanel/weddingservice/editWeddingService";
	}

	@RequestMapping(value="/weddingservices/new", method=RequestMethod.GET)
	public String newWeddingService( Model model) {		
		LOGGER.info("IN: Wedding Services/new-GET");
		model.addAttribute("weddingService", new WeddingService());
		return "adminpanel/weddingservice/editWeddingService";
	}

	@ModelAttribute("currentMenu")
	public String module(){
		return "weddingservices";
	}

}
