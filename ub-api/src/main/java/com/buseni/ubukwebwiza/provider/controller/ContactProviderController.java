package com.buseni.ubukwebwiza.provider.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.buseni.ubukwebwiza.breadcrumbs.navigation.Navigation;
import com.buseni.ubukwebwiza.home.HomeController;
import com.buseni.ubukwebwiza.provider.beans.MessageDto;
import com.buseni.ubukwebwiza.provider.domain.MessageAnswer;
import com.buseni.ubukwebwiza.provider.domain.Provider;
import com.buseni.ubukwebwiza.provider.mail.ContactProviderEvent;
import com.buseni.ubukwebwiza.provider.service.MessageService;
import com.buseni.ubukwebwiza.provider.service.ProviderService;

@Controller
@Navigation(url="/contact-provider", name="Contact provider", parent={ ListProviderController.class, HomeController.class})
public class ContactProviderController {

	public static final Logger LOGGER = LoggerFactory.getLogger( ContactProviderController.class );


	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private MessageService messageService;
	
	 @Autowired
	 private MessageSource messages;

	
	 @Autowired
	private ApplicationEventPublisher eventPublisher;
	
	
	
	@RequestMapping(value="/contact-provider",method=RequestMethod.GET)
	public String contactProvider(@RequestParam(value="name") String urlName, Model model){
		Provider provider = providerService.getProviderByUrlName(urlName);
		MessageDto messageDto = new MessageDto();
		messageDto.setIdProvider(provider.getId());
		messageDto.setProviderEmail(provider.getAccount().getEmail());
		messageDto.setProviderName(provider.getBusinessName());
		messageDto.setProviderUrlName(provider.getUrlName());
		model.addAttribute("messageDto", messageDto);
		return "frontend/provider/contactProvider";
	}
	@RequestMapping(value="/contact-provider",method=RequestMethod.POST)
	public String sendMail(@RequestParam(value="enterHere", required=false) String enterHere, @Valid @ModelAttribute("messageDto") MessageDto messageDto, BindingResult result, RedirectAttributes attributes,HttpServletRequest request){
		//If enterHere is filled it is a spam
		if(StringUtils.isNotEmpty(enterHere)){
			return "redirect:/";
		}
		String subject = messages.getMessage("message.contactProviderSubject", null,	request.getLocale());
		messageDto.setSubject(subject);
		MessageDto updatedMessageDto = 	messageService.contactProvider(messageDto);
		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		eventPublisher.publishEvent(new ContactProviderEvent(updatedMessageDto, request.getLocale(), appUrl));
		
		 String message = messages.getMessage("message.contactProviderSuccess", new String[]{messageDto.getProviderName()}, request.getLocale());			
		attributes.addFlashAttribute("message", message);
		return "redirect:/wedding-service-providers/"+messageDto.getProviderUrlName();
	}

	
	@RequestMapping(value="/read-provider-message",method=RequestMethod.GET)
	public String replyProvider(@RequestParam(value="id") Integer idMessage, Model model){
		MessageAnswer oldMessageAnswer  = messageService.findMessageAnswerById(idMessage);
		Provider provider = oldMessageAnswer.getMessage().getProvider();
		MessageAnswer messageAnswer = new MessageAnswer();		
		messageAnswer.setMessage(oldMessageAnswer.getMessage());
		model.addAttribute("provider", provider);
		
		model.addAttribute("messageAnswer", messageAnswer);
		model.addAttribute("oldMessageAnswer", oldMessageAnswer);
		return "frontend/provider/replyProvider";
	}
	@RequestMapping(value="/reply-provider",method=RequestMethod.POST)
	public String replyProvider(@RequestParam(value="enterHere", required=false) String enterHere, @Valid @ModelAttribute("messageAnswer") MessageAnswer messageAnswer, BindingResult result, RedirectAttributes attributes,HttpServletRequest request){
		//If enterHere is filled it is a spam
		if(StringUtils.isNotEmpty(enterHere)){
			return "redirect:/";		}
		messageAnswer.setFromUser(true);
		MessageAnswer updatedMessageAnswer = 	messageService.answerMessage(messageAnswer);
		
		
		Provider provider = updatedMessageAnswer.getMessage().getProvider();
		
		 String message = messages.getMessage("message.contactProviderSuccess", new String[]{provider.getBusinessName()}, request.getLocale());			
		attributes.addFlashAttribute("message", message);
		return "redirect:/wedding-service-providers/"+provider.getUrlName();
	}
	
	@ModelAttribute("currentMenu")
	public String module(){
		return "providers";
	}
	
	@ModelAttribute("showSidebar")
	public boolean showSidebar(){
		return true;
	}
}
