package com.buseni.ubukwebwiza.provider.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "message_answer")
@Audited
@EntityListeners(AuditingEntityListener.class)
public class MessageAnswer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	
	@Lob
	@Column(length=500)
	@NotEmpty(message = "{error.message.requiredfield.comment}")
	private String comment;
	
		

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;
	
	@Column(name="from_provider")
	boolean fromProvider;
	
	@Column(name="from_user")
	boolean fromUser;
	
	@ManyToOne
	@JoinColumn(name="id_message", referencedColumnName="id")
	private Message message;
	
	@Column(name = "created_date", nullable = false, updatable = false)
	@CreatedDate
	private Long createdDate;

	@Column(name = "modified_date")
	@LastModifiedDate
	private Long modifiedDate;

	@Column(name = "created_by")
	@CreatedBy
	private String createdBy;

	@Column(name = "modified_by")
	@LastModifiedBy
	private String modifiedBy;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public boolean isFromProvider() {
		return fromProvider;
	}
	public boolean getFromProvider() {
		return fromProvider;
	}
	public void setFromProvider(boolean fromProvider) {
		this.fromProvider = fromProvider;
	}
	public boolean isFromUser() {
		return fromUser;
	}
	public boolean getFromUser() {
		return fromUser;
	}
	public void setFromUser(boolean fromUser) {
		this.fromUser = fromUser;
	}
	

}
