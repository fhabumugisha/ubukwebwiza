/**
 * 
 */
package com.buseni.ubukwebwiza.administrator.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * @author habumugisha
 * 
 */

public class AdministratorDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Integer id;
	
	@NotEmpty(message = "{error.administrator.requiredfield.firstname}")
	private String firstName;

	@NotEmpty(message = "{error.administrator.requiredfield.lastname}")
	private String lastName;	
	
	@NotEmpty(message = "{error.user.requiredfield.email}")
	@Email
	private String email;
	
	private String password;
	
	private boolean enabled;
	

	private List<String> listRoles = new ArrayList<String>();

	/**
	 * 
	 */
	public AdministratorDTO() {

	}

	/**
	 * 
	 * @param email
	 * @param password
	 * @param fName
	 * @param lName
	 */
	public AdministratorDTO( String fName,
			String lName) {
		
		
		this.firstName = fName;
		this.lastName = lName;
	}



	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String administratorFirstName) {
		this.firstName = administratorFirstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String administratorLastName) {
		this.lastName = administratorLastName;
	}


	public List<String> getListRoles() {
		return listRoles;
	}

	public void setListRoles(List<String> listRoles) {
		this.listRoles = listRoles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public boolean getEnabled(){
		return enabled;
	}
	

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	@Override
	public String toString() {
		return "AdministratorDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", password=" + password + ", enabled=" + enabled + ", listRoles="
				+ listRoles + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((listRoles == null) ? 0 : listRoles.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		AdministratorDTO other = (AdministratorDTO) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (enabled != other.enabled)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (listRoles == null) {
			if (other.listRoles != null)
				return false;
		} else if (!listRoles.equals(other.listRoles))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}



	



}
