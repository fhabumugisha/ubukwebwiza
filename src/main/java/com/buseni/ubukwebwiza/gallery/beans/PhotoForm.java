package com.buseni.ubukwebwiza.gallery.beans;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class PhotoForm implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer idProvider;
	private String description;
	private  MultipartFile file;
	private String name;
	private boolean enabled;
	
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
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	

}
