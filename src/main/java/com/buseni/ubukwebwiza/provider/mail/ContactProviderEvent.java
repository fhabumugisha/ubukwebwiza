package com.buseni.ubukwebwiza.provider.mail;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.buseni.ubukwebwiza.provider.beans.MessageDto;

@SuppressWarnings("serial")
public class ContactProviderEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final MessageDto messageDto;
   
    public ContactProviderEvent(MessageDto messageDto, Locale locale, String appUrl) {
        super(messageDto);
        this.messageDto = messageDto;
        this.locale = locale;
        this.appUrl = appUrl;
       
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

	public MessageDto getMessageDto() {
		return messageDto;
	}

   
}