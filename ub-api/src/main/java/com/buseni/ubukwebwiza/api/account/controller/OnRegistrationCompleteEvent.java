package com.buseni.ubukwebwiza.api.account.controller;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.buseni.ubukwebwiza.account.domain.UserAccount;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final UserAccount user;

    public OnRegistrationCompleteEvent(UserAccount user, Locale locale, String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public UserAccount getUser() {
        return user;
    }
}