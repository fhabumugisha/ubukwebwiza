package com.buseni.ubukwebwiza.provider.beans;

public class ProviderSearch {
	
	private String name;
	private Integer service;
	private Integer district;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getService() {
		return service;
	}
	public void setService(Integer idcWeddingService) {
		this.service = idcWeddingService;
	}
	public Integer getDistrict() {
		return district;
	}
	public void setDistrict(Integer idDistrict) {
		this.district = idDistrict;
	}
	@Override
	public String toString() {
		return "ProviderSearch [name=" + name + ", service="
				+ service + ", district=" + district + "]";
	}
	
	

}