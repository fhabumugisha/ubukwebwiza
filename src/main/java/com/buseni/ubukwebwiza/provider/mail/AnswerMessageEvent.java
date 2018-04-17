package com.buseni.ubukwebwiza.provider.mail;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.buseni.ubukwebwiza.provider.domain.MessageAnswer;

@SuppressWarnings("serial")
public class AnswerMessageEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final MessageAnswer messageAnswer;
   
    public AnswerMessageEvent(MessageAnswer messageAnswer, Locale locale, String appUrl) {
        super(messageAnswer);
        this.messageAnswer = messageAnswer;
        this.locale = locale;
        this.appUrl = appUrl;
       
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

	public MessageAnswer getMessageAnswer() {
		return messageAnswer;
	}

   
}