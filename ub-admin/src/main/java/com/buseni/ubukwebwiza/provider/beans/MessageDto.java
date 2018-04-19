package com.buseni.ubukwebwiza.provider.beans;

import java.io.Serializable;

import javax.persistence.Column;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class MessageDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@Column(name="sender_name")
	@NotEmpty(message="{error.message.requiredfield.sendername}")
	@Length(min=1, max=50)
	private String senderName;

	@Email
	@NotEmpty(message = "{error.message.requiredfield.senderemail}")
	private String senderEmail;
	
	@Column(name="sender_phone_number")
	@NotEmpty(message = "{error.message.requiredfield.senderphonenumber}")
	@Length(min=0, max=50)
	private String senderPhonenumber;
	
	@NotEmpty(message = "{error.message.requiredfield.comment}")
	private String comment;
	private String subject;
	
	private String providerName;
	private String providerEmail;
	private Integer idProvider;
	private String providerUrlName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSenderEmail() {
		return senderEmail;
	}
	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	public String getSenderPhonenumber() {
		return senderPhonenumber;
	}
	public void setSenderPhonenumber(String senderPhonenumber) {
		this.senderPhonenumber = senderPhonenumber;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public Integer getIdProvider() {
		return idProvider;
	}
	public void setIdProvider(Integer idProvider) {
		this.idProvider = idProvider;
	}
	public String getProviderEmail() {
		return providerEmail;
	}
	public void setProviderEmail(String providerEmail) {
		this.providerEmail = providerEmail;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getProviderUrlName() {
		return providerUrlName;
	}
	public void setProviderUrlName(String providerUrlName) {
		this.providerUrlName = providerUrlName;
	}
	
	

}
