package com.buseni.ubukwebwiza.breadcrumbs.navigation;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * UserAccount: yfliu
 * Date: 12/18/12
 * Time: 8:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class NavigationEntry implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -244861090443980077L;
	private String url;
    private String name;
    private Class<? extends Object> navigationClass;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<? extends Object> getNavigationClass() {
        return navigationClass;
    }

    public void setNavigationClass(Class<? extends Object> navigationClass) {
        this.navigationClass = navigationClass;
    }
}
