package com.buseni.ubukwebwiza.administration.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ubukwebwiza.administrator.enums.EnumPhotoCategory;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.exceptions.ErrorsHelper;
import com.buseni.ubukwebwiza.exceptions.ServiceLayerException;
import com.buseni.ubukwebwiza.gallery.beans.PhotoForm;
import com.buseni.ubukwebwiza.utils.ImagesUtils;
import com.buseni.ubukwebwiza.utils.PageWrapper;
import com.buseni.ubukwebwiza.vendor.beans.ServiceForm;
import com.buseni.ubukwebwiza.vendor.domain.District;
import com.buseni.ubukwebwiza.vendor.domain.Photo;
import com.buseni.ubukwebwiza.vendor.domain.Vendor;
import com.buseni.ubukwebwiza.vendor.domain.VendorWeddingService;
import com.buseni.ubukwebwiza.vendor.domain.WeddingService;
import com.buseni.ubukwebwiza.vendor.service.DistrictService;
import com.buseni.ubukwebwiza.vendor.service.PhotoService;
import com.buseni.ubukwebwiza.vendor.service.VendorService;
import com.buseni.ubukwebwiza.vendor.service.VendorWeddingServiceManager;
import com.buseni.ubukwebwiza.vendor.service.WeddingServiceManager;

@Controller
@RequestMapping(value="/admin")
@Navigation(url="/admin/vendors", name="Vendors", parent= AdminHomeController.class)
public class AdminVendorController {
	public static final int PROFILE_IMAGE_HEIGHT = 150;

	public static final int PROFILE_IMAGE_WIDTH = 213;

	public  static final Logger LOGGER = LoggerFactory.getLogger(AdminVendorController.class);

	@Autowired
	private VendorService vendorService;
	
	@Autowired
	private DistrictService  districtService;
	
	@Autowired
	private WeddingServiceManager weddingServiceManager;
	
	@Autowired
	private VendorWeddingServiceManager vendorWeddingServiceManager;
	
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
	               
	            	//String workingDir = System.getProperty("user.dir");
	               /* String saveDirectory =  env.getProperty("files.location");
	                file.transferTo(new File(saveDirectory+"/profil/" + file.getOriginalFilename()));*/
	               /*
	                 byte[] bytes = file.getBytes();
	                   BufferedOutputStream stream =
	                        new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename() + "-uploaded")));
	                stream.write(bytes);
	                stream.close(); */
	                /*LOGGER.info("You successfully uploaded " + file.getOriginalFilename() + " into " + file.getOriginalFilename() + "-uploaded !");
	              
	                resizeImagScal(new File(saveDirectory+"/profil/" + file.getOriginalFilename()), new File(saveDirectory+"/profil/" + "thumbnail" + file.getOriginalFilename()));*/
	              
	               Photo profil = new Photo();
	               profil.setFilename(file.getOriginalFilename());
	               profil.setDescription(vendor.getBusinessName());
	               profil.setContent(ImagesUtils.resizeImage(file, PROFILE_IMAGE_WIDTH, PROFILE_IMAGE_HEIGHT));
	               profil.setEnabled(false);
	               profil.setCreatedAt(new Date());
	               profil.setLastUpdate(new Date());
	               profil.setContentType(file.getContentType());
	               profil.setCategory(EnumPhotoCategory.PROFILE.getId());
	               vendor.setProfilPicture(profil);
	               
	                
	                /*vendor.setProfilPicture(file.getOriginalFilename());
	                vendor.setThumbnail("thumbnail"+file.getOriginalFilename());*/
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
		
		
		//String workingDir = System.getProperty("user.dir");
       /* String saveDirectory =  env.getProperty("files.location");
        File filePhoto = new File(saveDirectory+"/profil/" + vendor.getProfilPicture());
        DiskFileItem diskFile =  new DiskFileItem("file", "multipart/form-data", false, filePhoto.getName(), (int)filePhoto.length(), filePhoto.getParentFile());
        try {
			diskFile.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        MultipartFile file =  new CommonsMultipartFile(diskFile);
        
		model.addAttribute(file);*/
		return "adminpanel/vendor/editVendor";
	}
	

	
	@RequestMapping(value="/vendors/new", method=RequestMethod.GET)
	public String newVendor( Model model) {		
		LOGGER.info("IN: vendors/new-GET");
		model.addAttribute("vendor", new Vendor());
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

	
	
	@RequestMapping(value="/vendors/{idVendor:[\\d]+}/photos/addPhoto",method=RequestMethod.POST)
	public String savePhoto(@PathVariable Integer idVendor, @ModelAttribute PhotoForm photoForm, Model model) throws ServiceLayerException{		
		LOGGER.info("IN: vendors/save-POSST");
	
			MultipartFile file  = photoForm.getFile();
			Photo photo = new Photo();
			if (file != null && !file.isEmpty()) {
	            try {
	               
	            	//String workingDir = System.getProperty("user.dir");
	    /*            String saveDirectory =  env.getProperty("files.location");
	                file.transferTo(new File(saveDirectory+"/" + file.getOriginalFilename()));
	             
	                LOGGER.info("You successfully uploaded " + file.getOriginalFilename() + " into " + file.getOriginalFilename() + "-uploaded !");
	               
	                resizeImagScal(new File(saveDirectory+"/" + file.getOriginalFilename()), new File(saveDirectory+"/" + "thumbnail" + file.getOriginalFilename()));*/
	                
	                photo.setFilename(file.getOriginalFilename());
	                photo.setContent(file.getBytes());
	                photo.setContentType(file.getContentType());
	                photo.setCategory(EnumPhotoCategory.PROVIDER.getId());
	            } catch (Exception e) {
	            	LOGGER.info("You failed to upload " + file.getName() + " => " + e.getMessage());
	            	//result.reject(e.getMessage());
	            	
	    			
	    			return "adminpanel/vendor/photos::error";
	            }
	        } else {
	        	if(photoForm.getId() == null){
	        		LOGGER.info("You failed to upload  because the file was empty.");
		        	//result.reject("error.file.empty");	
	    			
	    			return "adminpanel/vendor/photos::error";
	        	}
	        	
	        }
			
			try {
				photo.setDescription(photoForm.getDescription());
				photo.setId(photoForm.getId());
				photo.setEnabled(photoForm.isEnabled());				
				photoService.create(photo);
				Vendor vendor = vendorService.findOne(idVendor);
				vendor.getPhotos().add(photo);
				vendorService.update(vendor);
				String message = "Photo " + photo.getId() + " was successfully added";
				model.addAttribute("message", message);
				model.addAttribute("vendor", vendor);
				 photoForm = new PhotoForm();
				//photoForm.setIdVendor(vendor.getId());
				model.addAttribute("photoForm", photoForm);
				return "adminpanel/vendor/photos::listPhotos";					
			
				//Business errors
			} catch (final ServiceLayerException e) {
				//ErrorsHelper.rejectErrors(result, e.getErrors());
				//LOGGER.info("Photo save error: " + result.toString());
								
				return "adminpanel/vendor/photos::error";
			}
	}
	

	@RequestMapping(value="/vendors/{idVendor:[\\d]+}/photos/deletePhoto", method=RequestMethod.GET)
	public String deletePhoto(@PathVariable Integer idVendor, @RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: vendors/deletePhoto-GETT");
		photoService.delete(id);
		String message = "Photo " + id + " was successfully deleted";
		model.addAttribute("message", message);		
		model.addAttribute("vendor", vendorService.findOne(idVendor));
		PhotoForm photoForm = new PhotoForm();
		//	photoForm.setIdVendor(idVendor);
		model.addAttribute("photoForm", photoForm);	
		return "adminpanel/vendor/photos::listPhotos";
	}
	
	@RequestMapping(value="/vendors/{idVendor:[\\d]+}/photos/editPhoto", method=RequestMethod.GET)
	public String editPhoto(@PathVariable Integer idVendor, @RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: vendors/editPhoto-GET");
		Photo photo = photoService.findById(id);
		PhotoForm photoForm = new PhotoForm();
		photoForm.setDescription(photo.getDescription());
		photoForm.setId(id);
		photoForm.setName(photo.getFilename());
		//photoForm.setIdVendor(idVendor);
		photoForm.setEnabled(photo.isEnabled());
		/*String workingDir = System.getProperty("user.dir");
        String saveDirectory =  env.getProperty("files.location");
        File filePhoto = new File(workingDir+saveDirectory+"/" + photo.getName());
        DiskFileItem diskFile =  new DiskFileItem("file", "multipart/form-data", false, filePhoto.getName(), (int)filePhoto.length(), filePhoto.getParentFile());
        try {
			diskFile.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        MultipartFile file =  new CommonsMultipartFile(diskFile);
        
		photoForm.setFile(file);*/
		model.addAttribute("photoForm", photoForm);	
		Vendor vendor = vendorService.findOne(idVendor);
		if(!CollectionUtils.isEmpty(vendor.getPhotos()) ){
			vendor.getPhotos().remove(photo);
		}
		
		model.addAttribute("vendor", vendor );
		return "adminpanel/vendor/photos::listPhotos";
	}
	
	
	@RequestMapping(value="/vendors/{idVendor:[\\d]+}/services", method=RequestMethod.GET)
	public String services( @PathVariable Integer idVendor, Model model) {		
		LOGGER.info("IN: vendors/services");		
		Vendor vendor =  vendorService.findOne(idVendor);
		model.addAttribute("vendor", vendor);
		ServiceForm serviceForm = new ServiceForm();
		model.addAttribute("serviceForm", serviceForm);
		return "adminpanel/vendor/services";
	}
	
	
	@RequestMapping(value="/vendors/{idVendor:[\\d]+}/services/addService",method=RequestMethod.POST)
	public String addService( @PathVariable Integer idVendor, @ModelAttribute ServiceForm serviceForm, Model model) throws ServiceLayerException{		
		LOGGER.info("IN: vendors/addService-POST");
			VendorWeddingService vws  = new VendorWeddingService();	
			
			try {
				vws.setDescription(serviceForm.getDescription());
				vws.setId(serviceForm.getId());
				vws.setEnabled(serviceForm.isEnabled());
				Vendor vendor = vendorService.findOne(idVendor);
				vws.setVendor(vendor);
				WeddingService  ws = weddingServiceManager.findOne(serviceForm.getIdcService());
				vws.setWeddingService(ws);
				/*if(vendor.getPhotos().contains(photo)){
					vendor.getPhotos().remove(photo);
				}*/
				vendorWeddingServiceManager.create(vws);
				
				String message = "Service " + vws.getId() + " was successfully added";
				model.addAttribute("message", message);
				model.addAttribute("vendor", vendor);
				 serviceForm = new ServiceForm();
				model.addAttribute("serviceForm", serviceForm);
				return "adminpanel/vendor/services::listServices";
				
					
			
				//Business errors
			} catch (final ServiceLayerException e) {
				//ErrorsHelper.rejectErrors(result, e.getErrors());
				//LOGGER.info("Photo save error: " + result.toString());
								
				return "adminpanel/vendor/services::error";
			}
	}
	
	@RequestMapping(value="/vendors/{idVendor:[\\d]+}/services/editService", method=RequestMethod.GET)
	public String editService(@PathVariable Integer idVendor, @RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: vendors/editService-GET");
		VendorWeddingService vws =  vendorWeddingServiceManager.findById(id);
		ServiceForm serviceForm = new ServiceForm();
		serviceForm.setDescription(vws.getDescription());
		serviceForm.setId(vws.getId());
	//	serviceForm.setIdVendor(vws.getVendor().getId());
		serviceForm.setIdcService(vws.getWeddingService().getId());
		serviceForm.setEnabled(vws.isEnabled());
		
		model.addAttribute("serviceForm", serviceForm);	
		Vendor vendor = vws.getVendor();
		if(!CollectionUtils.isEmpty(vendor.getVendorWeddingServices()) ){
			vendor.getVendorWeddingServices().remove(vws);
		}
		
		model.addAttribute("vendor", vendor );
		return "adminpanel/vendor/services::listServices";
	}
	
	@RequestMapping(value="/vendors/{idVendor:[\\d]+}/services/deleteService", method=RequestMethod.GET)
	public String deleteService(@PathVariable Integer idVendor, @RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: vendors/deleteService-GET");
		vendorWeddingServiceManager.delete(id);
		String message = "Service " + id + " was successfully deleted";
		model.addAttribute("message", message);		
		model.addAttribute("vendor", vendorService.findOne(idVendor));
		ServiceForm serviceForm = new ServiceForm();
		model.addAttribute("serviceForm", serviceForm);	
		return "adminpanel/vendor/services::listServices";
	}
	
	/*@RequestMapping(value = "/image/{image_id}", method = RequestMethod.GET, produces = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	@ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable("image_id") Integer imageId) throws IOException {
    	Photo photo = photoService.findById(imageId);
        byte[] imageContent = photo.getContent();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
      List<MediaType> mediaTypes = Arrays.asList(MediaType.IMAGE_JPEG, MediaType.IMAGE_PNG);
      headers.setAccept(mediaTypes);
        InputStream in = ServletContext.class.getResourceAsStream("/userimages/new_look.jpg");
        return new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
    }*/
    
	@RequestMapping(value = "/image/{imageId}", method = RequestMethod.GET)
	public void showImage(@PathVariable("imageId") Integer imageId,
			HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		Photo photo = photoService.findById(imageId);
		byte[] imageContent = photo.getContent();
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(imageContent);
		response.getOutputStream().close();
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
