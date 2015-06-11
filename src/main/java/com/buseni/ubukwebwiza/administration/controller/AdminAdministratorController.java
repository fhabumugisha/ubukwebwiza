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

import com.buseni.ubukwebwiza.account.domain.Role;
import com.buseni.ubukwebwiza.account.service.RoleService;
import com.buseni.ubukwebwiza.administrator.domain.Administrator;
import com.buseni.ubukwebwiza.administrator.domain.AdministratorDTO;
import com.buseni.ubukwebwiza.administrator.service.AdministratorService;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.exceptions.ErrorsHelper;
import com.buseni.ubukwebwiza.utils.PageWrapper;
@Controller
//@SessionAttributes({"allDistricts", "allWeddingServices"})

@RequestMapping(value="/admin")
@Navigation(url="/admin/administrators", name="Administrators", parent= AdminHomeController.class)
public class AdminAdministratorController {
	public  static final Logger LOGGER = LoggerFactory.getLogger(AdminAdministratorController.class);
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private RoleService roleService;
	
		  
	@RequestMapping(value="/administrators",method=RequestMethod.GET)
	public String admins(Model model, Pageable page){
		Page<Administrator> adminPage  =  administratorService.findAll(page);	
		PageWrapper<Administrator> pageWrapper = new PageWrapper<Administrator>(adminPage, "/admin/administrators");
		model.addAttribute("page", pageWrapper);
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
		model.addAttribute("administrator", createDTO( administrator));
		return "adminpanel/admin/editAdministrator";
	}



	@RequestMapping(value="/administrators/new", method=RequestMethod.GET)
	public String newAdmin( Model model) {		
		LOGGER.info("IN: administrators/new-GET");
		if(!model.containsAttribute("administrator")){
			model.addAttribute("administrator", new AdministratorDTO());
		}
		return "adminpanel/admin/editAdministrator";
	}

	
	@RequestMapping(value="/administrators/new",method=RequestMethod.POST)
	public String save(@Valid @ModelAttribute AdministratorDTO administrator , 
 BindingResult result, RedirectAttributes attributes) throws BusinessException {
		LOGGER.info("IN: administrators/new-POST");
		// Validation erros
		if (result.hasErrors()) {
			LOGGER.info("IN: administrators/new-POST error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.administrator", result);
			attributes.addFlashAttribute("administrator", administrator);
			return "redirect:/admin/administrators/new";
		}

		try {
			if(administrator.getId() == null){
				Administrator admin = administratorService.create(administrator);
				String message = "Administrator " + admin.getId() + " was successfully added";
				attributes.addFlashAttribute("message", message);
			}else{
				Administrator admin = administratorService.update(administrator);
				String message = "Administrator " + admin.getId() + " was successfully updated";
				attributes.addFlashAttribute("message", message);
			}
			
			
			return "redirect:/admin/administrators";
			// Business errors
		} catch (final BusinessException e) {
			ErrorsHelper.rejectErrors(result, e.getErrors());
			LOGGER.info("IN: administrators/new-POST error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.administrator", result);
			attributes.addFlashAttribute("administrator", administrator);
			return "redirect:/admin/administrators/new";
		}

	}

	
	/*
	 * Create adminitrator dto
	 */
	private AdministratorDTO createDTO(Administrator administrator) {
		AdministratorDTO dto = new AdministratorDTO();
		if(administrator.getAccount() != null){
			dto.setEmail(administrator.getAccount().getEmail());
			dto.setPassword(administrator.getAccount().getPassword());
			dto.setEnabled(administrator.getAccount().isEnabled());
			for(Role role : administrator.getAccount().getRoles()){
				dto.getListRoles().add(role.getName());
			}
		}
		dto.setId(administrator.getId());
		dto.setFirstName(administrator.getFirstName());
		dto.setLastName(administrator.getLastName());
		return dto;
	}
	
	/**
	 * Retourne la liste des roles
	 * @return
	 */
	@ModelAttribute("allRoles")
	public List<Role> getAllRoles(){	
		return  roleService.findAdminRoles();
		
	}


	@ModelAttribute("currentMenu")
	public String module(){
		return "administrators";
	}
}
