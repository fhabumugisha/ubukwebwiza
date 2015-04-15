package com.buseni.ubukwebwiza.vendor.beans;

import java.io.Serializable;

public class ServiceForm implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer idVendor;
	private String description;
	private Integer idcService;
	private boolean enabled;
	
	
	public ServiceForm() {
	
	}
	
	public ServiceForm(Integer idVendor) {
		this.idVendor = idVendor;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdVendor() {
		return idVendor;
	}
	public void setIdVendor(Integer idVendor) {
		this.idVendor = idVendor;
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
