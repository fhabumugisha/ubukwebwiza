package com.buseni.ubukwebwiza.vendor.builders;

import com.buseni.ubukwebwiza.vendor.domain.CodeDistrict;
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
	
	public VendorBuilder district(CodeDistrict codeDistrict){
		this.vendor.setCodeDistrict(codeDistrict);
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
	
	
	
	public VendorBuilder actif(int actif){
		this.vendor.setActiveFlag(actif);
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
