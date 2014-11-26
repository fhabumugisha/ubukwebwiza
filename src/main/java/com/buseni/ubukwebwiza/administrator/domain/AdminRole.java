package com.buseni.ubukwebwiza.administrator.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
@Entity
@Table(name="admin_roles" ,uniqueConstraints = @UniqueConstraint(
		columnNames = { "role", "username" }))
public class AdminRole {
	private Integer id;
	private Administrator admin;
	private String role;
 
	public AdminRole() {
	}
 
	public AdminRole(Administrator admin, String role) {
		this.admin = admin;
		this.role = role;
	}
 
	@Id
	@GeneratedValue
	@Column(name = "id_admin_role", 
		unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}
 
	public void setId(Integer id) {
		this.id = id;
	}
 
	@ManyToOne
	@JoinColumn(name = "username", nullable = false)
	public Administrator getAdmin() {
		return this.admin;
	}
 
	public void setAdmin(Administrator admin) {
		this.admin = admin;
	}
 
	@Column(name = "role", nullable = false, length = 45)
	public String getRole() {
		return this.role;
	}
 
	public void setRole(String role) {
		this.role = role;
	}
 
}
