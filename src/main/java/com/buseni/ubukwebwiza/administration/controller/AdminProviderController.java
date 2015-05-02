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
import com.buseni.ubukwebwiza.gallery.domain.Photo;
import com.buseni.ubukwebwiza.gallery.service.PhotoService;
import com.buseni.ubukwebwiza.utils.ImagesUtils;
import com.buseni.ubukwebwiza.utils.PageWrapper;
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
@RequestMapping(value="/admin")
@Navigation(url="/admin/providers", name="Providers", parent= AdminHomeController.class)
public class AdminProviderController {
	public static final int PROFILE_IMAGE_HEIGHT = 150;

	public static final int PROFILE_IMAGE_WIDTH = 290;

	public  static final Logger LOGGER = LoggerFactory.getLogger(AdminProviderController.class);

	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private DistrictService  districtService;
	
	@Autowired
	private WeddingServiceManager weddingServiceManager;
	
	@Autowired
	private ProviderWeddingServiceManager providerWeddingServiceManager;
	
	@Autowired
	private PhotoService photoService;
	
	@Autowired
	private Environment env;
	
	@RequestMapping(value="/providers",method=RequestMethod.GET)
	public String providers(Model model, Pageable page){				
		Page<Provider> providerPage  =  providerService.findAll(page);		
		PageWrapper<Provider> pageWrapper = new PageWrapper<Provider>(providerPage, "/admin/providers");
		model.addAttribute("page", pageWrapper);
		//model.addAttribute("currentMenu", "providers");
		model.addAttribute("providers", providerPage.getContent());		
		if(!model.containsAttribute("provider")){
			model.addAttribute("provider", new Provider());
		}
		return "adminpanel/provider/listingProvider";
	}
	
	@RequestMapping(value="/providers/save",method=RequestMethod.POST)
	public String save(@Valid @ModelAttribute Provider provider , BindingResult result, RedirectAttributes attributes,  @RequestParam("file") MultipartFile file) throws ServiceLayerException{		
		LOGGER.info("IN: providers/save-POSST");
		//Validation erros	
		if (result.hasErrors()) {
			LOGGER.info("Provider-edit error: " + result.toString());
			attributes.addFlashAttribute("org.springframework.validation.BindingResult.provider", result);
			attributes.addFlashAttribute("provider", provider);
			return "adminpanel/provider/editProvider";

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
	               profil.setDescription(provider.getBusinessName());
	               profil.setContent(ImagesUtils.resizeImage(file, PROFILE_IMAGE_WIDTH, PROFILE_IMAGE_HEIGHT));
	               profil.setEnabled(true);
	               profil.setCreatedAt(new Date());
	               profil.setLastUpdate(new Date());
	               profil.setContentType(file.getContentType());
	               profil.setCategory(EnumPhotoCategory.PROFILE.getId());
	               provider.setProfilPicture(profil);
	               
	                
	                /*provider.setProfilPicture(file.getOriginalFilename());
	                provider.setThumbnail("thumbnail"+file.getOriginalFilename());*/
	            } catch (Exception e) {
	            	LOGGER.info("You failed to upload " + file.getName() + " => " + e.getMessage());
	            	result.reject(e.getMessage());
	            	attributes.addFlashAttribute("org.springframework.validation.BindingResult.provider", result);
	    			attributes.addFlashAttribute("provider", provider);
	    			return "adminpanel/provider/editProvider";
	            }
	        } else {
	        	if(provider.getId() == null){
	        		LOGGER.info("You failed to upload  because the file was empty.");
		        	result.reject("error.file.empty");
	            	attributes.addFlashAttribute("org.springframework.validation.BindingResult.provider", result);
	    			attributes.addFlashAttribute("provider", provider);
	    			return "adminpanel/provider/editProvider";
	        	}
	        	
	        }
			
			try {
				providerService.add(provider);
				//Business errors	
			} catch (final ServiceLayerException e) {
				ErrorsHelper.rejectErrors(result, e.getErrors());
				LOGGER.info("Provider-edit error: " + result.toString());
				attributes.addFlashAttribute("org.springframework.validation.BindingResult.provider", result);
				attributes.addFlashAttribute("provider", provider);
				return "adminpanel/provider/editProvider";
			}
			
			String message = "Provider " + provider.getId() + " was successfully added";
			attributes.addFlashAttribute("message", message);
			return "redirect:/admin/providers";
		}


	}



	@RequestMapping(value="/providers/delete", method=RequestMethod.GET)
	public String delete( @RequestParam(value="id", required=true) Integer id, RedirectAttributes attributes) {
		LOGGER.info("IN: providers/delete-GET");
		providerService.delete(id);
		String message = "Provider " + id + " was successfully deleted";
		attributes.addFlashAttribute("message", message);		
		return "redirect:/admin/providers";
	}

	@RequestMapping(value="/providers/edit", method=RequestMethod.GET)
	public String edit(@RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: providers/edit-GET");
		Provider provider =  providerService.findOne(id);
		model.addAttribute("provider", provider);
		
		
		//String workingDir = System.getProperty("user.dir");
       /* String saveDirectory =  env.getProperty("files.location");
        File filePhoto = new File(saveDirectory+"/profil/" + provider.getProfilPicture());
        DiskFileItem diskFile =  new DiskFileItem("file", "multipart/form-data", false, filePhoto.getName(), (int)filePhoto.length(), filePhoto.getParentFile());
        try {
			diskFile.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        MultipartFile file =  new CommonsMultipartFile(diskFile);
        
		model.addAttribute(file);*/
		return "adminpanel/provider/editProvider";
	}
	

	
	@RequestMapping(value="/providers/new", method=RequestMethod.GET)
	public String newProvider( Model model) {		
		LOGGER.info("IN: providers/new-GET");
		model.addAttribute("provider", new Provider());
		return "adminpanel/provider/editProvider";
	}
	
	
	@RequestMapping(value="/providers/{idProvider:[\\d]+}/photos", method=RequestMethod.GET)
	public String photos(@PathVariable Integer idProvider, Model model) {
		LOGGER.info("IN: providers/photos-GET");
		Provider provider =  providerService.findOne(idProvider);
		model.addAttribute("provider", provider);
		PhotoForm photoForm = new PhotoForm();
		photoForm.setIdProvider(provider.getId());
		model.addAttribute("photoForm", photoForm);
		return "adminpanel/provider/photos";
	}

	
	
	@RequestMapping(value="/providers/{idProvider:[\\d]+}/photos/addPhoto",method=RequestMethod.POST)
	public String savePhoto(@PathVariable Integer idProvider, @ModelAttribute PhotoForm photoForm, Model model) throws ServiceLayerException{		
		LOGGER.info("IN: providers/save-POSST");
	
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
	            	
	    			
	    			return "adminpanel/provider/photos::error";
	            }
	        } else {
	        	if(photoForm.getId() == null){
	        		LOGGER.info("You failed to upload  because the file was empty.");
		        	//result.reject("error.file.empty");	
	    			
	    			return "adminpanel/provider/photos::error";
	        	}
	        	
	        }
			
			try {
				photo.setDescription(photoForm.getDescription());
				photo.setId(photoForm.getId());
				photo.setEnabled(photoForm.isEnabled());				
				photoService.create(photo);
				Provider provider = providerService.findOne(idProvider);
				provider.getPhotos().add(photo);
				providerService.update(provider);
				String message = "Photo " + photo.getId() + " was successfully added";
				model.addAttribute("message", message);
				model.addAttribute("provider", provider);
				 photoForm = new PhotoForm();
				//photoForm.setIdProvider(provider.getId());
				model.addAttribute("photoForm", photoForm);
				return "adminpanel/provider/photos::listPhotos";					
			
				//Business errors
			} catch (final ServiceLayerException e) {
				//ErrorsHelper.rejectErrors(result, e.getErrors());
				//LOGGER.info("Photo save error: " + result.toString());
								
				return "adminpanel/provider/photos::error";
			}
	}
	

	@RequestMapping(value="/providers/{idProvider:[\\d]+}/photos/deletePhoto", method=RequestMethod.GET)
	public String deletePhoto(@PathVariable Integer idProvider, @RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: providers/deletePhoto-GETT");
		photoService.delete(id);
		String message = "Photo " + id + " was successfully deleted";
		model.addAttribute("message", message);		
		model.addAttribute("provider", providerService.findOne(idProvider));
		PhotoForm photoForm = new PhotoForm();
		//	photoForm.setIdProvider(idProvider);
		model.addAttribute("photoForm", photoForm);	
		return "adminpanel/provider/photos::listPhotos";
	}
	
	@RequestMapping(value="/providers/{idProvider:[\\d]+}/photos/editPhoto", method=RequestMethod.GET)
	public String editPhoto(@PathVariable Integer idProvider, @RequestParam(value="id", required=true) Integer id, Model model) {
		LOGGER.info("IN: providers/editPhoto-GET");
		Photo photo = photoService.findById(id);
		PhotoForm photoForm = new PhotoForm();
		photoForm.setDescription(photo.getDescription());
		photoForm.setId(id);
		photoForm.setName(photo.getFilename());
		//photoForm.setIdProvider(idProvider);
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
		Provider provider = providerService.findOne(idProvider);
		if(!CollectionUtils.isEmpty(provider.getPhotos()) ){
			provider.getPhotos().remove(photo);
		}
		
		model.addAttribute("provider", provider );
		return "adminpanel/provider/photos::listPhotos";
	}
	
	
	@RequestMapping(value="/providers/{idProvider:[\\d]+}/services", method=RequestMethod.GET)
	public String services( @PathVariable Integer idProvider, Model model) {		
		LOGGER.info("IN: providers/services");		
		Provider provider =  providerService.findOne(idProvider);
		model.addAttribute("provider", provider);
		ServiceForm serviceForm = new ServiceForm();
		model.addAttribute("serviceForm", serviceForm);
		return "adminpanel/provider/services";
	}
	
	
	@RequestMapping(value="/providers/{idProvider:[\\d]+}/services/addService",method=RequestMethod.POST)
	public String addService( @PathVariable Integer idProvider, @ModelAttribute ServiceForm serviceForm, Model model) throws ServiceLayerException{		
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
				/*if(provider.getPhotos().contains(photo)){
					provider.getPhotos().remove(photo);
				}*/
				providerWeddingServiceManager.create(vws);
				
				String message = "Service " + vws.getId() + " was successfully added";
				model.addAttribute("message", message);
				model.addAttribute("provider", provider);
				 serviceForm = new ServiceForm();
				model.addAttribute("serviceForm", serviceForm);
				return "adminpanel/provider/services::listServices";
				
					
			
				//Business errors
			} catch (final ServiceLayerException e) {
				//ErrorsHelper.rejectErrors(result, e.getErrors());
				//LOGGER.info("Photo save error: " + result.toString());
								
				return "adminpanel/provider/services::error";
			}
	}
	
	@RequestMapping(value="/providers/{idProvider:[\\d]+}/services/editService", method=RequestMethod.GET)
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
		return "adminpanel/provider/services::listServices";
	}
	
	@RequestMapping(value="/providers/{idProvider:[\\d]+}/services/deleteService", method=RequestMethod.GET)
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
