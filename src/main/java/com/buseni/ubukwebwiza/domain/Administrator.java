/**
 * 
 */
package com.buseni.ubukwebwiza.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

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
	@NotEmpty(message = "The email must not be empty")
	private String email;

	@NotEmpty(message = "The password must not be empty")
	private String password;


	@NotEmpty(message = "The administrator first name must not be empty")
	@Column(name="first_name")
	private String firstName;

	@NotEmpty(message = "The administrator last name must not be empty")
	@Column(name="last_name")
	private String lastName;
	
	
	@Column(name="providers_access",columnDefinition="Boolean default false")
	private Boolean providersAccess;
	
	@Column(name="administration_access",columnDefinition="Boolean default false")
	private Boolean administrationAccess;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_update")
	private Date lastUpdate;
	
	@NotNull
	@Column(name="active_flag")
	private int activeFlag;




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
		return "Administrator [id=" + id + ", email=" + email + ", password="
				+ password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", articlesAccess="
				+ providersAccess + ", administrationAccess="
				+ administrationAccess + ", lastUpdate=" + lastUpdate
				+ ", enabled=" + activeFlag + "]";
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
		result = prime
				* result
				+ ((administrationAccess == null) ? 0 : administrationAccess
						.hashCode());
		
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + activeFlag;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		
		
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime
				* result
				+ ((providersAccess == null) ? 0 : providersAccess
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Administrator other = (Administrator) obj;
		if (administrationAccess == null) {
			if (other.administrationAccess != null) {
				return false;
			}
		} else if (!administrationAccess.equals(other.administrationAccess)) {
			return false;
		}
			if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (activeFlag != other.activeFlag) {
			return false;
		}
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (lastUpdate == null) {
			if (other.lastUpdate != null) {
				return false;
			}
		} else if (!lastUpdate.equals(other.lastUpdate)) {
			return false;
		}
		
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (providersAccess == null) {
			if (other.providersAccess != null) {
				return false;
			}
		} else if (!providersAccess.equals(other.providersAccess)) {
			return false;
		}
		return true;
	}

	

	

	public void setEnabled(int enabled) {
		this.activeFlag = enabled;
	}

	public int isEnabled() {
		return activeFlag;
	}



	public int getEnabled() {
		return activeFlag;
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

	

	public Boolean getProfessionalsAccess() {
		return providersAccess;
	}

	public void setProfessionalsAccess(Boolean professionalsAccess) {
		this.providersAccess = professionalsAccess;
	}

	public Boolean getAdministrationAccess() {
		return administrationAccess;
	}

	public void setAdministrationAccess(Boolean administrationAccess) {
		this.administrationAccess = administrationAccess;
	}

}
