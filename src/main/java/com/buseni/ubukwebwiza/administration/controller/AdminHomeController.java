package com.buseni.ubukwebwiza.administration.controller;



import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.buseni.ubukwebwiza.administrator.service.AdministratorService;
import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.contactus.domain.ContactusForm;
import com.buseni.ubukwebwiza.contactus.service.ContactusService;
@Controller
//@SessionAttributes({"allDistricts", "allWeddingServices"})
@Navigation(url="/admin" ,name = "Dashbord")
public class AdminHomeController {
	public  static final Logger LOGGER = LoggerFactory.getLogger(AdminHomeController.class);
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private ContactusService contactusService;
	
	
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public String home(Model model){
		model.addAttribute("currentMenu", "dashbord");
		return "adminpanel/dashbord";
	}

	@RequestMapping(value="/signin", method=RequestMethod.GET)
	public String sign(Model model){
		model.addAttribute("currentMenu", "dashbord");
		return "adminpanel/dashbord";
	}
	
	@RequestMapping(value="/adminlogin", method=RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
			  @RequestParam(value = "logout", required = false) String logout, 
	          HttpServletRequest request,Model model){
		//model.addAttribute("currentMenu", "dashbord");
		if (error != null) {
/*			model.addAttribute("error", "Invalid username and password!");
*/ 
			//login form for update page
                        //if login error, get the targetUrl from session again.
			String targetUrl = getRememberMeTargetUrlFromSession(request);
			LOGGER.info(targetUrl);
			if(StringUtils.isNotEmpty(targetUrl)){
				model.addAttribute("targetUrl", targetUrl);
				model.addAttribute("loginUpdate", true);
			}
 
		}
 
		/*if (logout != null) {
			model.addAttribute("msg", "You've been logged out successfully.");
		}*/
		return "adminpanel/signin";
	}

	//for 403 access denied page
		@RequestMapping(value = "/admin403", method = RequestMethod.GET)
		public String accesssDenied(Model model) {	 
		  //check if user is login
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();	
			model.addAttribute("username", userDetail.getUsername());
		  }
		  return "adminpanel/403";
		}
		  
		@ModelAttribute("unreadMessages")
		public int unreadMessages(){
			List<ContactusForm>  unreadMessages = contactusService.findUnread();
			if(!CollectionUtils.isEmpty(unreadMessages)){
				return unreadMessages.size();
				
			}
			return 0;
		}
		
		/**
		 * get targetURL from session
		 */
		private String getRememberMeTargetUrlFromSession(HttpServletRequest request){
			String targetUrl = "";
			HttpSession session = request.getSession(false);
			if(session!=null){
				targetUrl = session.getAttribute("targetUrl")==null?""
	                             :session.getAttribute("targetUrl").toString();
			}
			return targetUrl;
		}

}
