package com.buseni.ubukwebwiza.provider.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "message")
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
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
	
	@Lob
	@Column(length=500)
	@NotEmpty(message = "{error.message.requiredfield.comment}")
	private String comment;
	private String subject;
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;
	@ManyToOne
	@JoinColumn(name="id_provider", referencedColumnName="id")
	private Provider provider;
	
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
	
	public Provider getProvider() {
		return provider;
	}
	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	

}
