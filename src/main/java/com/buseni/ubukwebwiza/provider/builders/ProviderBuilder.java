package com.buseni.ubukwebwiza.provider.builders;

import com.buseni.ubukwebwiza.provider.domain.District;
import com.buseni.ubukwebwiza.provider.domain.Provider;



public class ProviderBuilder {
	private Provider provider;
	
	public ProviderBuilder(){
		provider = new Provider();
	}
	
	public ProviderBuilder id(Integer id){
		this.provider.setId(id);
		return this;
	}
	
	
	public ProviderBuilder district(District district){
		this.provider.setDistrict(district);
		return this;
	}
	

	public ProviderBuilder phoneNumber(String providerTel){
		this.provider.setPhoneNumber(providerTel);
		return this;
	}
	
	public ProviderBuilder aboutMe(String aboutMe){
		this.provider.setAboutme(aboutMe);
		return this;
	}
	
	
	public ProviderBuilder address(String address){
		this.provider.setAddress(address);
		return this;
	}
	public Provider build(){
		
		return provider;
	}

}
