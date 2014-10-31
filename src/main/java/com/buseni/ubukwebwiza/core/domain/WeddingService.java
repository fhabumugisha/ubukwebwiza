package com.buseni.ubukwebwiza.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="wedding_service")
public class WeddingService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	@Column(name="id_wedding_service")
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;
		
	private BigDecimal price;
	
	private String description;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_provider",nullable=false)
	private Provider provider;
	
	@OneToOne
	private CodeWeddingService codeWeddingService;
	
	private int activeFlag;
	
	public WeddingService(){
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + activeFlag;
		result = prime
				* result
				+ ((codeWeddingService == null) ? 0 : codeWeddingService
						.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastUpdated == null) ? 0 : lastUpdated.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result
				+ ((provider == null) ? 0 : provider.hashCode());
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
		WeddingService other = (WeddingService) obj;
		if (activeFlag != other.activeFlag)
			return false;
		if (codeWeddingService == null) {
			if (other.codeWeddingService != null)
				return false;
		} else if (!codeWeddingService.equals(other.codeWeddingService))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastUpdated == null) {
			if (other.lastUpdated != null)
				return false;
		} else if (!lastUpdated.equals(other.lastUpdated))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WeddingService [id=" + id + ", lastUpdated=" + lastUpdated
				+ ", price=" + price + ", description=" + description
				+ ", provider=" + provider + ", codeWeddingService="
				+ codeWeddingService + ", activeFlag=" + activeFlag + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.lastUpdated = date;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return lastUpdated;
	}

	public void setState(int state) {
		this.activeFlag = state;
	}

	public int getState() {
		return activeFlag;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	
	

}
