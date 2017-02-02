package com.buseni.ubukwebwiza.provider.domain;

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

import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="wedding_service")
@SQLDelete(sql = "UPDATE wedding_service set deleted = true  WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted = 'false'")
public class WeddingService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;	
	
	@NotEmpty(message="{error.weddingservice.requiredfield.libelle}")
	@Length(min=1, max=100)
	private String libelle;
	@Column(name="libelle_fr")
	private String libelleFr;
	@Column(name="libelle_en")
	private String libelleEn;
	@Column(name="libelle_kn")
	private String libelleKn;
	

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_update")
	private Date lastUpdate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;
	
	private boolean enabled;
	
	private boolean deleted;
	

	public Integer getId() {
		return id;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelleFr() {
		return libelleFr;
	}

	public void setLibelleFr(String libelleFr) {
		this.libelleFr = libelleFr;
	}

	public String getLibelleEn() {
		return libelleEn;
	}

	public void setLibelleEn(String libelleEn) {
		this.libelleEn = libelleEn;
	}

	public String getLibelleKn() {
		return libelleKn;
	}

	public void setLibelleKn(String libelleKn) {
		this.libelleKn = libelleKn;
	}

	

	@Override
	public String toString() {
		return "WeddingService [id=" + id + ", libelle=" + libelle
				+ ", libelleFr=" + libelleFr + ", libelleEn=" + libelleEn
				+ ", libelleKn=" + libelleKn + ", enabled=" + enabled + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
		result = prime * result
				+ ((libelleEn == null) ? 0 : libelleEn.hashCode());
		result = prime * result
				+ ((libelleFr == null) ? 0 : libelleFr.hashCode());
		result = prime * result
				+ ((libelleKn == null) ? 0 : libelleKn.hashCode());
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
		if (enabled != other.enabled)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (libelle == null) {
			if (other.libelle != null)
				return false;
		} else if (!libelle.equals(other.libelle))
			return false;
		if (libelleEn == null) {
			if (other.libelleEn != null)
				return false;
		} else if (!libelleEn.equals(other.libelleEn))
			return false;
		if (libelleFr == null) {
			if (other.libelleFr != null)
				return false;
		} else if (!libelleFr.equals(other.libelleFr))
			return false;
		if (libelleKn == null) {
			if (other.libelleKn != null)
				return false;
		} else if (!libelleKn.equals(other.libelleKn))
			return false;
		return true;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	
	
	

}
