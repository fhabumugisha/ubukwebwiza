package com.buseni.ubukwebwiza.vendor.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="photo")
public class Photo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_photo")
	private Integer id;
	
	@NotEmpty(message="You must choose a photo")
	@Column(name="photo_name")
	private String photoName;
	
	@NotEmpty(message="You must write a description")
	private String description;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_update")
	private Date lastUpdate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date created_at;
	
	@ManyToOne
	@JoinColumn(name="id_vendor")
	private Vendor vendor;
	
	@Column(name="active_flag")
	private int activeFlag;
	
	public Photo(){
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((photoName == null) ? 0 : photoName.hashCode());
		result = prime * result + ((lastUpdate == null) ? 0 : lastUpdate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Photo other = (Photo) obj;
		if (photoName == null) {
			if (other.photoName != null) {
				return false;
			}
		} else if (!photoName.equals(other.photoName)) {
			return false;
		}
		if (lastUpdate == null) {
			if (other.lastUpdate != null) {
				return false;
			}
		} else if (!lastUpdate.equals(other.lastUpdate)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Photo [id=" + id + ", photoName=" + photoName + ", description="
				+ description + ", lastUpdate=" + lastUpdate + ", vendor=" + vendor
				+ "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAvatar() {
		return photoName;
	}

	public void setAvatar(String avatar) {
		this.photoName = avatar;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setDate(Date date) {
		this.lastUpdate = date;
	}

	/**
	 * @return the lastUpdate
	 */
	public Date getDate() {
		return lastUpdate;
	}

	

	/**
	 * @return the professional
	 */
	public Vendor getProvider() {
		return vendor;
	}

	public void setState(int state) {
		this.activeFlag = state;
	}

	public int getState() {
		return activeFlag;
	}

	

	public void setProvider(Vendor vendor) {
		this.vendor = vendor;
	}

	public String getPhotoName() {
		return photoName;
	}

	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public int getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

}
