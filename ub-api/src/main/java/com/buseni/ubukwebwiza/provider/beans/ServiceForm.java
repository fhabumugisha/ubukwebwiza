package com.buseni.ubukwebwiza.provider.beans;

import java.io.Serializable;

public class ServiceForm implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer idProvider;
	private String description;
	private Integer idcService;
	private boolean enabled =  true;
	
	
	public ServiceForm() {
	
	}
	
	public ServiceForm(Integer idProvider) {
		this.idProvider = idProvider;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdProvider() {
		return idProvider;
	}
	public void setIdProvider(Integer idProvider) {
		this.idProvider = idProvider;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public Integer getIdcService() {
		return idcService;
	}
	public void setIdcService(Integer idcService) {
		this.idcService = idcService;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	

}
