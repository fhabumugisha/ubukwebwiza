package com.buseni.ubukwebwiza.gallery.beans;

public class PhotoDetails {
	private Integer idPhoto;
	private String description;	
	private String filename;
	private Integer idProvider;
	
	
	private String providerUrl;
	private String providerBusinessName;
	
	public PhotoDetails() {
		
	}
	
	public PhotoDetails(Integer idPhoto, String description, String filename, Integer idProvider, String providerBusinessName,  String providerUrl) {
		this.idPhoto = idPhoto;
		this.description =  description;
		this.filename = filename;
		this.idProvider = idProvider;
		this.providerBusinessName =  providerBusinessName;
		this.providerUrl = providerUrl;
	}
	
	
	public PhotoDetails(Integer idPhoto, String description, String filename) {
		this.idPhoto = idPhoto;
		this.description =  description;
		this.filename = filename;
		
	}
	
	public Integer getIdPhoto() {
		return idPhoto;
	}
	public void setIdPhoto(Integer idPhoto) {
		this.idPhoto = idPhoto;
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
	public String getProviderUrl() {
		return providerUrl;
	}
	public void setProviderUrl(String providerUrl) {
		this.providerUrl = providerUrl;
	}
	public String getProviderBusinessName() {
		return providerBusinessName;
	}
	public void setProviderBusinessName(String providerBusinessName) {
		this.providerBusinessName = providerBusinessName;
	}
	@Override
	public String toString() {
		return "PhotoDetails [idPhoto=" + idPhoto + ", idProvider=" + idProvider + ", description=" + description
				+ ", providerUrl=" + providerUrl + ", providerBusinessName=" + providerBusinessName + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((idPhoto == null) ? 0 : idPhoto.hashCode());
		result = prime * result + ((idProvider == null) ? 0 : idProvider.hashCode());
		result = prime * result + ((providerBusinessName == null) ? 0 : providerBusinessName.hashCode());
		result = prime * result + ((providerUrl == null) ? 0 : providerUrl.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhotoDetails other = (PhotoDetails) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (idPhoto == null) {
			if (other.idPhoto != null)
				return false;
		} else if (!idPhoto.equals(other.idPhoto))
			return false;
		if (idProvider == null) {
			if (other.idProvider != null)
				return false;
		} else if (!idProvider.equals(other.idProvider))
			return false;
		if (providerBusinessName == null) {
			if (other.providerBusinessName != null)
				return false;
		} else if (!providerBusinessName.equals(other.providerBusinessName))
			return false;
		if (providerUrl == null) {
			if (other.providerUrl != null)
				return false;
		} else if (!providerUrl.equals(other.providerUrl))
			return false;
		return true;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	

}
