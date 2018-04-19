package com.buseni.ubukwebwiza.administration.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.provider.beans.ServiceForm;
import com.buseni.ubukwebwiza.provider.domain.District;
import com.buseni.ubukwebwiza.provider.domain.Provider;
import com.buseni.ubukwebwiza.provider.domain.ProviderWeddingService;
import com.buseni.ubukwebwiza.provider.domain.WeddingService;
import com.buseni.ubukwebwiza.provider.service.DistrictService;
import com.buseni.ubukwebwiza.provider.service.ProviderService;
import com.buseni.ubukwebwiza.provider.service.ProviderWeddingServiceManager;
import com.buseni.ubukwebwiza.provider.service.WeddingServiceManager;

@Controller
@Navigation(url="/admin/providers/{idProvider:[\\d]+}/services", name="Provider's services", parent={AdminProviderController.class, AdminHomeController.class})
public class AdminProviderServicesController {
	

	public  static final Logger LOGGER = LoggerFactory.getLogger(AdminProviderServicesController.class);

	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private DistrictService  districtService;
	
	@Autowired
	private WeddingServiceManager weddingServiceManager;
	
	@Autowired
	private ProviderWeddingServiceManager providerWeddingServiceManager;
	
	
	
	@RequestMapping(value="/admin/providers/{idProvider:[\\d]+}/services", method=RequestMethod.GET)
	public String services( @PathVariable Integer idProvider, Model model) {		
		LOGGER.info("IN: providers/services");		
		Provider provider =  providerService.findOne(idProvider);
		model.addAttribute("provider", provider);
		ServiceForm serviceForm = new ServiceForm();
		model.addAttribute("serviceForm", serviceForm);
		return "adminpanel/provider/services";
	}
	

	@RequestMapping(value="/admin/providers/{idProvider:[\\d]+}/services/addService", method=RequestMethod.GET)
	public String showAddServiceForm(@PathVariable Integer idProvider, Model model) {
		LOGGER.info("IN: providers/addService-GET");
		model.addAttribute("serviceForm", new ServiceForm());	
		model.addAttribute("provider", providerService.findOne(idProvider) );
		return "adminpanel/provider/editService";
	}
	
	@RequestMapping(value="/admin/providers/{idProvider:[\\d]+}/services/addService",method=RequestMethod.POST)
	public String addService( @PathVariable Integer idProvider, @ModelAttribute ServiceForm serviceForm, RedirectAttributes attributes) throws BusinessException{		
		LOGGER.info("IN: providers/addService-POST");
			ProviderWeddingService vws  = new ProviderWeddingService();	
			
			try {
				vws.setDescription(serviceForm.getDescription());
				vws.setId(serviceForm.getId());
				vws.setEnabled(serviceForm.isEnabled());
				Provider provider = providerService.findOne(idProvider);
				vws.setProvider(provider);
				WeddingService  ws = weddingServiceManager.findOne(serviceForm.getIdcService());
				vws.setWeddingService(ws);				
				providerWeddingServiceManager.create(vws);				
				String message = "Service " + vws.getId() + " was successfully added";
				attributes.addFlashAttribute("message", message);
				attributes.addFlashAttribute("provider", provider);
				 serviceForm = new ServiceForm();
				 attributes.addFlashAttribute("serviceForm", serviceForm);
				return "redirect:/admin/providers/"+provider.getId() +"/services";
				
					
			
				//Business errors
			} catch (final BusinessException e) {
				//ErrorsHelper.rejectErrors(result, e.getErrors());
				//LOGGER.info("Photo save error: " + result.toString());
								
				return "adminpanel/provider/editService";
			}
	}
	
	@RequestMapping(value="/admin/providers/{idProvider:[\\d]+}/services/editService", method=RequestMethod.GET)
	public String editService(@PathVariable Integer idProvider, @RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: providers/editService-GET");
		ProviderWeddingService vws =  providerWeddingServiceManager.findById(id);
		ServiceForm serviceForm = new ServiceForm();
		serviceForm.setDescription(vws.getDescription());
		serviceForm.setId(vws.getId());
	//	serviceForm.setIdProvider(vws.getProvider().getId());
		serviceForm.setIdcService(vws.getWeddingService().getId());
		serviceForm.setEnabled(vws.isEnabled());
		
		model.addAttribute("serviceForm", serviceForm);	
		Provider provider = vws.getProvider();
		if(!CollectionUtils.isEmpty(provider.getProviderWeddingServices()) ){
			provider.getProviderWeddingServices().remove(vws);
		}
		
		model.addAttribute("provider", provider );
		return "adminpanel/provider/editService";
	}
	
	@RequestMapping(value="/admin/providers/{idProvider:[\\d]+}/services/deleteService", method=RequestMethod.GET)
	public String deleteService(@PathVariable Integer idProvider, @RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: providers/deleteService-GET");
		providerWeddingServiceManager.delete(id);
		String message = "Service " + id + " was successfully deleted";
		model.addAttribute("message", message);		
		model.addAttribute("provider", providerService.findOne(idProvider));
		ServiceForm serviceForm = new ServiceForm();
		model.addAttribute("serviceForm", serviceForm);	
		return "adminpanel/provider/services::listServices";
	}
	
	
   
	
	@ModelAttribute("currentMenu")
	public String module(){
		return "providers";
	}
	
	@ModelAttribute("allWeddingServices")
	public List<WeddingService> populateWeddingServices(){
		return weddingServiceManager.findByEnabled(Boolean.TRUE);
	}
	@ModelAttribute("districts")
	public List<District> populateDistricts(){
		return districtService.findByEnabled(Boolean.TRUE);
	}

	
	
	
	
}
