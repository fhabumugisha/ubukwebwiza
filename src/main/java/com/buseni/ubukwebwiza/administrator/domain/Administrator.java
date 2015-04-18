/**
 * 
 */
package com.buseni.ubukwebwiza.administrator.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * @author habumugisha
 * 
 */
@Entity
@Table(name = "administrator")
public class Administrator implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_administrator")
	private Integer id;

	@Column(unique = true)
	@Email
	@NotEmpty(message = "{error.administrator.requiredfield.email}")
	private String email;

	@NotEmpty(message = "{error.admnistrator.requiredfield.password}")
	private String password;


	@NotEmpty(message = "{error.administrator.requiredfield.firstname}")
	@Column(name="first_name")
	private String firstName;

	@NotEmpty(message = "{error.administrator.requiredfield.lastname}")
	@Column(name="last_name")
	private String lastName;
	
	
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="last_update")
	private Date lastUpdate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="created_at")
	private Date createdAt;
	
	private boolean enabled;

	@OneToMany(mappedBy="admin", cascade =CascadeType.ALL)
	private Set<AdminRole> roles =  new HashSet<AdminRole>(); 
	@Transient
	private List<String> listRoles = new ArrayList<String>();

	/**
	 * 
	 */
	public Administrator() {

	}

	/**
	 * 
	 * @param email
	 * @param password
	 * @param fName
	 * @param lName
	 */
	public Administrator(String email, String password, String fName,
			String lName) {
		this.email = email;
		this.firstName = fName;
		this.lastName = lName;
	}

	/**
	 * @return the administratorId
	 */
	public Integer getId() {
		return id;
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

	@Override
	public String toString() {
		return "Administrator [id=" + id + ", email=" + email
				+ ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", lastUpdate=" + lastUpdate
				+ ", createdAt=" + createdAt + ", enabled=" + enabled + "]";
	}

	
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((lastUpdate == null) ? 0 : lastUpdate.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((email == null) ? 0 : email.hashCode());
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
		Administrator other = (Administrator) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
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
		if (lastUpdate == null) {
			if (other.lastUpdate != null)
				return false;
		} else if (!lastUpdate.equals(other.lastUpdate))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	

	

	

	public void setId(Integer id) {
		this.id = id;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String username) {
		this.email = username;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<AdminRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<AdminRole> roles) {
		this.roles = roles;
	}

	public List<String> getListRoles() {
		return listRoles;
	}

	public void setListRoles(List<String> listRoles) {
		this.listRoles = listRoles;
	}



}
