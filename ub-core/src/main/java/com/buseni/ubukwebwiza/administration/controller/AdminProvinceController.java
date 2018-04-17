package com.buseni.ubukwebwiza.administration.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.buseni.ubukwebwiza.provider.domain.Province;
import com.buseni.ubukwebwiza.provider.service.ProvinceService;
import com.buseni.ubukwebwiza.utils.PageWrapper;

@Controller
@RequestMapping(value="/admin")
@Navigation(url="/admin/provinces", name="Provinces", parent= AdminHomeController.class)
public class AdminProvinceController {

	public  static final Logger LOGGER = LoggerFactory.getLogger(AdminProvinceController.class);

	@Autowired
	private ProvinceService provinceService;

	@RequestMapping(value="/provinces", method=RequestMethod.GET)
	public String provinces(Model model, Pageable page){
		LOGGER.info("IN: Provinces/list-GET");

		Page<Province> pageProvince = provinceService.findAll(page);
		PageWrapper<Province> pageWrapper = new PageWrapper<Province>(pageProvince, "/admin/provinces");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("provinces", pageProvince.getContent());	
		if(!model.containsAttribute("province")){
			model.addAttribute("province", new Province());
		}
		return "adminpanel/province/listingProvince";
	}

	@RequestMapping(value="/provinces/save",method=RequestMethod.POST)
	public String save(@Valid @ModelAttribute Province province , BindingResult result, RedirectAttributes attributes) throws BusinessException{		
		LOGGER.info("IN: Provinces/save-POSST");
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("Province-edit error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.province", result);
			attributes.addFlashAttribute("province", province);
			return "adminpanel/province/editProvince";

		}else{

			try {
				provinceService.add(province);
				//Business errors	
			} catch (final BusinessException e) {
				ErrorsHelper.rejectErrors(result, e.getErrors());
				LOGGER.info("Province-edit error: " + result.toString());
				attributes.addFlashAttribute("org.springframework.validation.BindingResult.province", result);
				attributes.addFlashAttribute("province", province);
				return "adminpanel/province/editProvince";
			}


			
			String message = "Province " + province.getId() + " was successfully added";
			attributes.addFlashAttribute("message", message);
			return "redirect:/admin/provinces";
		}


	}



	@RequestMapping(value="/provinces/delete", method=RequestMethod.GET)
	public String delete( @RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes) {
		LOGGER.info("IN: Provinces/delete-GET");
		provinceService.delete(id);
		String message = "Province " + id + " was successfully deleted";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/admin/provinces";
	}

	@RequestMapping(value="/provinces/edit", method=RequestMethod.GET)
	public String edit(@RequestParam(value="id", required=true) Integer id, Model model, HttpServletRequest request) {
		LOGGER.info("IN: Provinces/edit-GET");
		if (isRememberMeAuthenticated()) {
			//send login for update
			setRememberMeTargetUrlToSession(request, "/provinces/edit?id="+id);
			model.addAttribute("loginUpdate", true);
			return "adminpanel/signin";
		}
		Province province =  provinceService.findOne(id);
		model.addAttribute("province", province);
		return "adminpanel/province/editProvince";
		
		
	}

	@RequestMapping(value="/provinces/new", method=RequestMethod.GET)
	public String newProvince( Model model) {		
		LOGGER.info("IN: Provinces/new-GET");
		model.addAttribute("province", new Province());
		return "adminpanel/province/editProvince";
	}

	@ModelAttribute("currentMenu")
	public String module(){
		return "provinces";
	}

	/**
	 * save targetURL in session
	 */
	private void setRememberMeTargetUrlToSession(HttpServletRequest request, String targetUrl){
		HttpSession session = request.getSession(false);
		if(session!=null){
			LOGGER.info("setting target url : " + targetUrl);
			session.setAttribute("targetUrl", targetUrl);
		}
	}
 
/*	*//**
	 * get targetURL from session
	 *//*
	private String getRememberMeTargetUrlFromSession(HttpServletRequest request){
		String targetUrl = "";
		HttpSession session = request.getSession(false);
		if(session!=null){
			targetUrl = session.getAttribute("targetUrl")==null?"":session.getAttribute("targetUrl").toString();
		}
		return targetUrl;
	}*/
	
	/**
	 * Check if user is login by remember me cookie, refer
	 * org.springframework.security.authentication.AuthenticationTrustResolverImpl
	 */
	private boolean isRememberMeAuthenticated() {
 
		Authentication authentication = 
                    SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return false;
		}
 
		return RememberMeAuthenticationToken.class.isAssignableFrom(authentication.getClass());
	}
}
