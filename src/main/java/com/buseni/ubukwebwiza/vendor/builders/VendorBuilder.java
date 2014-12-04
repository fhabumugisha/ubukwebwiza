package com.buseni.ubukwebwiza.vendor.builders;

import com.buseni.ubukwebwiza.vendor.domain.District;
import com.buseni.ubukwebwiza.vendor.domain.Vendor;



public class VendorBuilder {
	private Vendor vendor;
	
	public VendorBuilder(){
		vendor = new Vendor();
	}
	
	public VendorBuilder id(Integer id){
		this.vendor.setId(id);
		return this;
	}
	public VendorBuilder email(String email){
		this.vendor.setEmail(email);
		return this;
	}
	public VendorBuilder password(String password){
		this.vendor.setPassword(password);
		return this;
	}
	
	public VendorBuilder district(District district){
		this.vendor.setDistrict(district);
		return this;
	}
	

	public VendorBuilder phoneNumber(String vendorTel){
		this.vendor.setPhoneNumber(vendorTel);
		return this;
	}
	
	public VendorBuilder aboutMe(String aboutMe){
		this.vendor.setAboutme(aboutMe);
		return this;
	}
	
	
	
	public VendorBuilder enabled(boolean enabled){
		this.vendor.setEnabled(enabled);
		return this;
	}
	public VendorBuilder address(String address){
		this.vendor.setAddress(address);
		return this;
	}
	public Vendor build(){
		
		return vendor;
	}

}
