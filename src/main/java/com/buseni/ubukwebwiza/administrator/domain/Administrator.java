/**
 * 
 */
package com.buseni.ubukwebwiza.administrator.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.buseni.ubukwebwiza.account.domain.UserAccount;
/**
 * @author habumugisha
 * 
 */
@Entity
@Table(name = "administrator")
public class Administrator implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;

	@NotEmpty(message = "{error.administrator.requiredfield.firstname}")
	@Column(name="first_name")
	private String firstName;

	@NotEmpty(message = "{error.administrator.requiredfield.lastname}")
	@Column(name="last_name")
	private String lastName;
	
	
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@JoinColumn(nullable = false, name = "id_account", referencedColumnName="id")
	private UserAccount account;
	
	

	/**
	 * 
	 */
	public Administrator() {

	}

	/**
	
	 * @param fName
	 * @param lName
	 */
	public Administrator( String fName, String lName) {		
		this.firstName = fName;
		this.lastName = lName;
	}

	/**
	
	 * @param fName
	 * @param lName
	 * @param account
	 */
	public Administrator( String fName, String lName, UserAccount account) {		
		this.firstName = fName;
		this.lastName = lName;
		this.account =  account;
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


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserAccount getAccount() {
		return account;
	}

	public void setAccount(UserAccount account) {
		this.account = account;
	}

	
}
