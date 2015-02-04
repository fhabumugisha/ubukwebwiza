package com.buseni.ubukwebwiza.administrator.controller;



import java.util.Arrays;
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

import com.buseni.ubukwebwiza.administrator.domain.Administrator;
import com.buseni.ubukwebwiza.administrator.enums.EnumRole;
import com.buseni.ubukwebwiza.administrator.service.AdministratorService;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.exceptions.ErrorsHelper;
import com.buseni.ubukwebwiza.exceptions.ServiceLayerException;
import com.buseni.ubukwebwiza.vendor.domain.District;
import com.buseni.ubukwebwiza.vendor.utils.PageWrapper;
@Controller
//@SessionAttributes({"allDistricts", "allWeddingServices"})

@RequestMapping(value="/admin")
@Navigation(url="/admin/administrators", name="Administrators", parent= AdminHomeController.class)
public class AdminAdministratorController {
	public  static final Logger LOGGER = LoggerFactory.getLogger(AdminAdministratorController.class);
	@Autowired
	private AdministratorService administratorService;
	
	private String roles;
		  
	@RequestMapping(value="/administrators",method=RequestMethod.GET)
	public String admins(Model model, Pageable page){
		Page<Administrator> adminPage  =  administratorService.findAll(page);	
		PageWrapper<Administrator> pageWrapper = new PageWrapper<Administrator>(adminPage, "/admin/administrators");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("currentMenu", "administrators");
		model.addAttribute("administrators", adminPage.getContent());		
		return "adminpanel/admin/listingAdministrator";
	}
	
	@RequestMapping(value="/administrators/delete", method=RequestMethod.GET)
	public String delete( @RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes) {
		LOGGER.info("IN: administrators/delete-GET");
		administratorService.delete(id);
		String message = "Administrator " + id + " was successfully deleted";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/admin/administrators";
	}

	@RequestMapping(value="/administrators/edit", method=RequestMethod.GET)
	public String edit(@RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: administrators/edit-GET");
		Administrator administrator =  administratorService.findById(id);
		model.addAttribute("administrator", administrator);
		return "adminpanel/admin/editAdministrator";
	}

	@RequestMapping(value="/administrators/new", method=RequestMethod.GET)
	public String newAdmin( Model model) {		
		LOGGER.info("IN: administrators/new-GET");
		model.addAttribute("administrator", new Administrator());
		return "adminpanel/admin/editAdministrator";
	}

	
	@RequestMapping(value="/administrators/save",method=RequestMethod.POST)
	public String save(@Valid @ModelAttribute Administrator administrator , BindingResult result, RedirectAttributes attributes) throws ServiceLayerException{		
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("Strategy-edit error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.district", result);
			attributes.addFlashAttribute("administrator", administrator);
			return "adminpanel/admin/editAdministrator";

		}else{

			try {
				administratorService.create(administrator);
				//Business errors	
			} catch (final ServiceLayerException e) {
				ErrorsHelper.rejectErrors(result, e.getErrors());
				LOGGER.info("Administrator-edit error: " + result.toString());
				attributes.addFlashAttribute("org.springframework.validation.BindingResult.district", result);
				attributes.addFlashAttribute("administrator", administrator);
				return "adminpanel/admin/editAdministrator";
			}


			LOGGER.info("IN: Administrators/save-POSST");
			String message = "Administrator " + administrator.getId() + " was successfully added";
			attributes.addFlashAttribute("message", message);
			return "redirect:/admin/administrators";
		}


	}

	/**
	 * Retourne la liste des roles
	 * @return
	 */
	@ModelAttribute("allRoles")
	public List<EnumRole> getAllRoles(){		
		return  Arrays.asList( EnumRole.values() );
		
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
}
