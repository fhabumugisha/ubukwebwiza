package com.buseni.ubukwebwiza.provider.beans;

public class ProviderSearch {
	
	private String name;
	private String service;
	private String district;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	@Override
	public String toString() {
		return "ProviderSearch [name=" + name + ", service="
				+ service + ", district=" + district + "]";
	}
	
	

}
