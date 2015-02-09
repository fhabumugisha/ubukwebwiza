package com.buseni.ubukwebwiza.administrator.forms;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class PhotoForm implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer idVendor;
	private String description;
	private  MultipartFile file;
	private String name;
	
	
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
	
	

}
