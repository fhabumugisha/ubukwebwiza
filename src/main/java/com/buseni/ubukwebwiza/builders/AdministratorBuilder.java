package com.buseni.ubukwebwiza.builders;

import com.buseni.ubukwebwiza.domain.Administrator;


public class AdministratorBuilder  {

	
	
	private Administrator administrator;
	public AdministratorBuilder(){
		administrator = new Administrator();
	}
	
	public AdministratorBuilder id(Integer id){
		administrator.setId(id);
		return this;
	}
	public AdministratorBuilder email(String email){
		administrator.setEmail(email);
		return this;
	}
	public AdministratorBuilder password(String password){
		administrator.setPassword(password);
		return this;
	}
	public AdministratorBuilder firstName(String firstName){
		administrator.setFirstName(firstName);
		return this;
	}
	public AdministratorBuilder lastName(String lastName){
		administrator.setLastName(lastName);
		return this;
	}
	
	public Administrator build(){
		return administrator;
	}

}
