package com.buseni.ubukwebwiza.administration.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.contactus.domain.ContactusForm;
import com.buseni.ubukwebwiza.contactus.service.ContactusService;
import com.buseni.ubukwebwiza.provider.domain.Message;
import com.buseni.ubukwebwiza.provider.service.MessageService;
import com.buseni.ubukwebwiza.utils.PageWrapper;

@Controller
@RequestMapping(value="/admin")
@Navigation(url="/admin/messages", name="Messages", parent= AdminHomeController.class)
public class AdminMessageController {

	public  static final Logger LOGGER = LoggerFactory.getLogger(AdminMessageController.class);

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ContactusService contactusService;

	@RequestMapping(value="/messages", method=RequestMethod.GET)
	public String Message(Model model, Pageable page){
		LOGGER.info("IN: Message/list-GET");

		Page<Message> pageMessage = messageService.findAll(page);
		PageWrapper<Message> pageWrapper = new PageWrapper<Message>(pageMessage, "/admin/messages");
		model.addAttribute("page", pageWrapper);
		model.addAttribute("messages", pageMessage.getContent());	
		
		return "adminpanel/messages/listingMessage";
	}

	



	



	@ModelAttribute("currentMenu")
	public String module(){
		return "messages";
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
