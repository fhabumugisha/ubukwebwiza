package com.buseni.ubukwebwiza.builders;

import com.buseni.ubukwebwiza.domain.Provider;



public class ProviderBuilder {
	private Provider provider;
	
	public ProviderBuilder(){
		provider = new Provider();
	}
	
	public ProviderBuilder id(Integer id){
		this.provider.setId(id);
		return this;
	}
	public ProviderBuilder email(String email){
		this.provider.setEmail(email);
		return this;
	}
	public ProviderBuilder password(String password){
		this.provider.setPassword(password);
		return this;
	}
	
	public ProviderBuilder firstName(String firstName){
		this.provider.setFirstName(firstName);
		return this;
	}
	
	public ProviderBuilder lastName(String lastName){
		this.provider.setLastName(lastName);
		return this;
	}
	public ProviderBuilder phoneNumber(String vendorTel){
		this.provider.setPhoneNumber(vendorTel);
		return this;
	}
	
	public ProviderBuilder aboutMe(String aboutMe){
		this.provider.setAboutme(aboutMe);
		return this;
	}
	
	
	
	public ProviderBuilder actif(int actif){
		this.provider.setActiveFlag(actif);
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
