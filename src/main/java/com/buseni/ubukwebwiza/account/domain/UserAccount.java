package com.buseni.ubukwebwiza.account.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Table(name = "user_account")
//@MappedSuperclass
public  class UserAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 840767790155118289L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;

	@Column(unique = true)
	@Email
	@NotEmpty(message = "{error.user.requiredfield.email}")
	private String email;

	@NotEmpty(message = "{error.user.requiredfield.password}")
	private String password;

	@Temporal(TemporalType.DATE)
	@Column(name="last_update")
	private Date lastUpdate;

	@Temporal(TemporalType.DATE)
	@Column(name="created_at")
	private Date createdAt;

	private boolean enabled;

	@Column(name ="token_expired")
	private boolean tokenExpired;

	@ManyToMany
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> roles = new ArrayList<>();


	public UserAccount() {

	}
	public UserAccount(Integer id, String email, String password) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
	}

	public UserAccount(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isTokenExpired() {
		return tokenExpired;
	}

	public void setTokenExpired(boolean tokenExpired) {
		this.tokenExpired = tokenExpired;
	}

	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", email=" + email + ", password=" + password
				+ ", lastUpdate=" + lastUpdate + ", createdAt=" + createdAt
				+ ", enabled=" + enabled + ", tokenExpired=" + tokenExpired
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastUpdate == null) ? 0 : lastUpdate.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + (tokenExpired ? 1231 : 1237);
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
		UserAccount other = (UserAccount) obj;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (enabled != other.enabled)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		if (tokenExpired != other.tokenExpired)
			return false;
		return true;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}



}
