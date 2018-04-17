package com.buseni.ubukwebwiza.account.beans;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class SignupForm implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6329353944175544782L;
	@NotEmpty
	private String businessName;
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	//@ValidPassword
	private String password;
	@NotNull
	private Integer idDistrict;
	@NotNull
	private Integer idService;
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getIdDistrict() {
		return idDistrict;
	}
	public void setIdDistrict(Integer idDistrict) {
		this.idDistrict = idDistrict;
	}
	public Integer getIdService() {
		return idService;
	}
	public void setIdService(Integer idService) {
		this.idService = idService;
	}
	@Override
	public String toString() {
		return "SignupForm [businessName=" + businessName + ", email=" + email
				+ ", password=" + password + ", idDistrict=" + idDistrict
				+ ", idService=" + idService + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((businessName == null) ? 0 : businessName.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((idDistrict == null) ? 0 : idDistrict.hashCode());
		result = prime * result
				+ ((idService == null) ? 0 : idService.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
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
		SignupForm other = (SignupForm) obj;
		if (businessName == null) {
			if (other.businessName != null)
				return false;
		} else if (!businessName.equals(other.businessName))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (idDistrict == null) {
			if (other.idDistrict != null)
				return false;
		} else if (!idDistrict.equals(other.idDistrict))
			return false;
		if (idService == null) {
			if (other.idService != null)
				return false;
		} else if (!idService.equals(other.idService))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
	
	

}
