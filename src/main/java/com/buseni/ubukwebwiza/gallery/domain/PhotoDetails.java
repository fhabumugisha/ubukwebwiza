package com.buseni.ubukwebwiza.gallery.domain;

import java.io.Serializable;

import javax.annotation.concurrent.Immutable;



@Immutable
public class PhotoDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6389020019313251168L;
	
	private Integer id;
	private String description;
	public PhotoDetails(Integer id, String description) {
		super();
		this.id = id;
		this.description = description;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
