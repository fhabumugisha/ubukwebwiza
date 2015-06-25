package com.buseni.ubukwebwiza.account.beans;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

public class ChangePasswordForm implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6329353944175544782L;
	
	
	@NotEmpty(message = "{error.user.requiredfield.password}")
	//@ValidPassword
	private String password;
	
	@NotEmpty(message = "{error.user.requiredfield.passwordConfirm}")
	//@ValidPassword
	private String passwordConfirm;
	
	private String currentPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	@Override
	public String toString() {
		return "ChangePasswordForm [password=" + password
				+ ", passwordConfirm=" + passwordConfirm + ", currentPassword="
				+ currentPassword + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currentPassword == null) ? 0 : currentPassword.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((passwordConfirm == null) ? 0 : passwordConfirm.hashCode());
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
		ChangePasswordForm other = (ChangePasswordForm) obj;
		if (currentPassword == null) {
			if (other.currentPassword != null)
				return false;
		} else if (!currentPassword.equals(other.currentPassword))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (passwordConfirm == null) {
			if (other.passwordConfirm != null)
				return false;
		} else if (!passwordConfirm.equals(other.passwordConfirm))
			return false;
		return true;
	}

	
	
	

}
