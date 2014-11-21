package com.buseni.ubukwebwiza.vendor.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="vendor_wedding_service")
public class VendorWeddingService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	@Column(name="id_vendor_wedding_service")
	private Integer id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_update")
	private Date lastUpdated;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;
	
	private BigDecimal price;
	
	private String description;
	@Column(name="active_flag")
	private int activeFlag;
	
	@OneToOne
	@JoinColumn(name="id_wedding_service")
	private WeddingService weddingService;
	
	@ManyToOne
	@JoinColumn(name="id_vendor")
	private Vendor vendor;
	
	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public WeddingService getWeddingService() {
		return weddingService;
	}

	public void setWeddingService(WeddingService weddingService) {
		this.weddingService = weddingService;
	}

	
	
	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Date getCreated_at() {
		return createdAt;
	}

	public void setCreated_at(Date created_at) {
		this.createdAt = created_at;
	}

	public int getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}

	public VendorWeddingService(){
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + activeFlag;
		result = prime
				* result
				+ ((weddingService == null) ? 0 : weddingService
						.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastUpdated == null) ? 0 : lastUpdated.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		VendorWeddingService other = (VendorWeddingService) obj;
		if (activeFlag != other.activeFlag)
			return false;
		if (weddingService == null) {
			if (other.weddingService != null)
				return false;
		} else if (!weddingService.equals(other.weddingService))
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
		return true;
	}

	@Override
	public String toString() {
		return "VendorWeddingService [id=" + id + ", lastUpdated="
				+ lastUpdated + ", created_at=" + createdAt + ", price="
				+ price + ", description=" + description + ", activeFlag="
				+ activeFlag + ", weddingService=" + weddingService
				+ ", vendor=" + vendor + "]";
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

	
	
	

}
