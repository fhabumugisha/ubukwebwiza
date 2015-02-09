package com.buseni.ubukwebwiza.administrator.controller;

import java.io.File;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ubukwebwiza.administrator.forms.PhotoForm;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.exceptions.ErrorsHelper;
import com.buseni.ubukwebwiza.exceptions.ServiceLayerException;
import com.buseni.ubukwebwiza.vendor.domain.District;
import com.buseni.ubukwebwiza.vendor.domain.Photo;
import com.buseni.ubukwebwiza.vendor.domain.Vendor;
import com.buseni.ubukwebwiza.vendor.domain.VendorWeddingService;
import com.buseni.ubukwebwiza.vendor.domain.WeddingService;
import com.buseni.ubukwebwiza.vendor.service.DistrictService;
import com.buseni.ubukwebwiza.vendor.service.PhotoService;
import com.buseni.ubukwebwiza.vendor.service.VendorService;
import com.buseni.ubukwebwiza.vendor.service.WeddingServiceManager;
import com.buseni.ubukwebwiza.vendor.utils.PageWrapper;

@Controller
@RequestMapping(value="/admin")
@Navigation(url="/admin/vendors", name="Vendors", parent= AdminHomeController.class)
public class AdminVendorController {
	public  static final Logger LOGGER = LoggerFactory.getLogger(AdminVendorController.class);

	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private DistrictService  districtService;
	
	@Autowired
	private WeddingServiceManager weddingServiceManager;
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private Environment env;
	
	@RequestMapping(value="/vendors",method=RequestMethod.GET)
	public String vendors(Model model, Pageable page){				
		Page<Vendor> vendorPage  =  vendorService.findAll(page);		
		PageWrapper<Vendor> pageWrapper = new PageWrapper<Vendor>(vendorPage, "/admin/vendors");
		model.addAttribute("page", pageWrapper);
		//model.addAttribute("currentMenu", "vendors");
		model.addAttribute("vendors", vendorPage.getContent());		
		if(!model.containsAttribute("vendor")){
			model.addAttribute("vendor", new Vendor());
		}
		return "adminpanel/vendor/listingVendor";
	}
	
	@RequestMapping(value="/vendors/save",method=RequestMethod.POST)
	public String save(@Valid @ModelAttribute Vendor vendor , BindingResult result, RedirectAttributes attributes,  @RequestParam("file") MultipartFile file) throws ServiceLayerException{		
		LOGGER.info("IN: vendors/save-POSST");
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("Vendor-edit error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.vendor", result);
			attributes.addFlashAttribute("vendor", vendor);
			return "adminpanel/vendor/editVendor";

		}else{

			if (!file.isEmpty()) {
	            try {
	               
	            	String workingDir = System.getProperty("user.dir");
	                String saveDirectory =  env.getProperty("files.location");
	                file.transferTo(new File(workingDir+saveDirectory+"/profil/" + file.getOriginalFilename()));
	               /*
	                 byte[] bytes = file.getBytes();
	                   BufferedOutputStream stream =
	                        new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename() + "-uploaded")));
	                stream.write(bytes);
	                stream.close(); */
	                LOGGER.info("You successfully uploaded " + file.getOriginalFilename() + " into " + file.getOriginalFilename() + "-uploaded !");
	                vendor.setProfilPicture(file.getOriginalFilename());
	            } catch (Exception e) {
	            	LOGGER.info("You failed to upload " + file.getName() + " => " + e.getMessage());
	            	result.reject(e.getMessage());
	            	attributes.addFlashAttribute("org.springframework.validation.BindingResult.vendor", result);
	    			attributes.addFlashAttribute("vendor", vendor);
	    			return "adminpanel/vendor/editVendor";
	            }
	        } else {
	        	LOGGER.info("You failed to upload  because the file was empty.");
	        	result.reject("error.file.empty");
            	attributes.addFlashAttribute("org.springframework.validation.BindingResult.vendor", result);
    			attributes.addFlashAttribute("vendor", vendor);
    			return "adminpanel/vendor/editVendor";
	        }
			
			try {
				vendorService.add(vendor);
				//Business errors	
			} catch (final ServiceLayerException e) {
				ErrorsHelper.rejectErrors(result, e.getErrors());
				LOGGER.info("Vendor-edit error: " + result.toString());
				attributes.addFlashAttribute("org.springframework.validation.BindingResult.vendor", result);
				attributes.addFlashAttribute("vendor", vendor);
				return "adminpanel/vendor/editVendor";
			}
			
			String message = "Vendor " + vendor.getId() + " was successfully added";
			attributes.addFlashAttribute("message", message);
			return "redirect:/admin/vendors";
		}


	}



	@RequestMapping(value="/vendors/delete", method=RequestMethod.GET)
	public String delete( @RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes) {
		LOGGER.info("IN: vendors/delete-GET");
		vendorService.delete(id);
		String message = "Vendor " + id + " was successfully deleted";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/admin/vendors";
	}

	@RequestMapping(value="/vendors/edit", method=RequestMethod.GET)
	public String edit(@RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: vendors/edit-GET");
		Vendor vendor =  vendorService.findOne(id);
		model.addAttribute("vendor", vendor);
		return "adminpanel/vendor/editVendor";
	}
	
	@RequestMapping(value="/vendors/{idVendor:[\\d]+}/photos", method=RequestMethod.GET)
	public String photos(@PathVariable Integer idVendor, Model model) {
		LOGGER.info("IN: vendors/photos-GET");
		Vendor vendor =  vendorService.findOne(idVendor);
		model.addAttribute("vendor", vendor);
		PhotoForm photoForm = new PhotoForm();
		photoForm.setIdVendor(vendor.getId());
		model.addAttribute("photoForm", photoForm);
		return "adminpanel/vendor/photos";
	}

	@RequestMapping(value="/vendors/{idVendor:[\\d]+}/services", method=RequestMethod.GET)
	public String services( @PathVariable Integer idVendor, Model model) {		
		LOGGER.info("IN: vendors/services");		
		Vendor vendor =  vendorService.findOne(idVendor);
		model.addAttribute("vendor", vendor);
		model.addAttribute("vendorService", new VendorWeddingService());
		return "adminpanel/vendor/services";
	}
	
	@RequestMapping(value="/vendors/new", method=RequestMethod.GET)
	public String newVendor( Model model) {		
		LOGGER.info("IN: vendors/new-GET");
		model.addAttribute("vendor", new Vendor());
		return "adminpanel/vendor/editVendor";
	}
	
	@RequestMapping(value="/vendors/savePhoto",method=RequestMethod.POST)
	public String savePhoto( @ModelAttribute PhotoForm photoForm, Model model) throws ServiceLayerException{		
		LOGGER.info("IN: vendors/save-POSST");
	
			MultipartFile file  = photoForm.getFile();
			Photo photo = new Photo();
			if (!file.isEmpty()) {
	            try {
	               
	            	String workingDir = System.getProperty("user.dir");
	                String saveDirectory =  env.getProperty("files.location");
	                file.transferTo(new File(workingDir+saveDirectory+"/" + file.getOriginalFilename()));
	             
	                LOGGER.info("You successfully uploaded " + file.getOriginalFilename() + " into " + file.getOriginalFilename() + "-uploaded !");
	               
	                photo.setName(file.getOriginalFilename());
	            } catch (Exception e) {
	            	LOGGER.info("You failed to upload " + file.getName() + " => " + e.getMessage());
	            	//result.reject(e.getMessage());
	            	
	    			
	    			return "adminpanel/vendor/photos::error";
	            }
	        } else {
	        	LOGGER.info("You failed to upload  because the file was empty.");
	        	//result.reject("error.file.empty");	
    			
    			return "adminpanel/vendor/photos::error";
	        }
			
			try {
				photo.setDescription(photoForm.getDescription());
				photo.setId(photoForm.getId());
				Vendor vendor = vendorService.findOne(photoForm.getIdVendor());
				photo.setVendor(vendor);
				vendor.getPhotos().add(photo);
				photoService.create(photo);

				String message = "Photo " + photo.getId() + " was successfully added";
				//attributes.addFlashAttribute("message", message);
				model.addAttribute("vendor", vendor);
				return "adminpanel/vendor/photos::listPhotos";
				
					
			
				//Business errors
			} catch (final ServiceLayerException e) {
				//ErrorsHelper.rejectErrors(result, e.getErrors());
				//LOGGER.info("Photo save error: " + result.toString());
								
				return "adminpanel/vendor/photos::error";
			}
			
		


	}
	

	@RequestMapping(value="/vendors/deletePhoto", method=RequestMethod.POST)
	public String deletePhoto( @RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes) {
		LOGGER.info("IN: vendors/deletePhoto-GET");
		vendorService.delete(id);
		String message = "Vendor " + id + " was successfully deleted";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/admin/vendors";
	}
	@ModelAttribute("currentMenu")
	public String module(){
		return "vendors";
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
