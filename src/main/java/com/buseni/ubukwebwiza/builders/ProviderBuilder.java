package com.buseni.ubukwebwiza.builders;

import com.buseni.ubukwebwiza.core.domain.Vendor;



public class ProviderBuilder {
	private Vendor vendor;
	
	public ProviderBuilder(){
		vendor = new Vendor();
	}
	
	public ProviderBuilder id(Integer id){
		this.vendor.setId(id);
		return this;
	}
	public ProviderBuilder email(String email){
		this.vendor.setEmail(email);
		return this;
	}
	public ProviderBuilder password(String password){
		this.vendor.setPassword(password);
		return this;
	}
	
	public ProviderBuilder firstName(String firstName){
		this.vendor.setFirstName(firstName);
		return this;
	}
	
	public ProviderBuilder lastName(String lastName){
		this.vendor.setLastName(lastName);
		return this;
	}
	public ProviderBuilder phoneNumber(String vendorTel){
		this.vendor.setPhoneNumber(vendorTel);
		return this;
	}
	
	public ProviderBuilder aboutMe(String aboutMe){
		this.vendor.setAboutme(aboutMe);
		return this;
	}
	
	
	
	public ProviderBuilder actif(int actif){
		this.vendor.setActiveFlag(actif);
		return this;
	}
	public ProviderBuilder address(String address){
		this.vendor.setAddress(address);
		return this;
	}
	public Vendor build(){
		
		return vendor;
	}

}
