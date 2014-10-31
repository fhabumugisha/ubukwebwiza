package com.buseni.ubukwebwiza.core.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="code_wedding_service")
public class CodeWeddingService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idc_wedding_service")
	private Integer idc;	
	private String code;
	private String libelle;
	private String libelleFr;
	private String libelleEn;
	private String libelleKn;
	private int activeFlag;
	
	public CodeWeddingService(){
		
	}

	public Integer getId() {
		return idc;
	}

	public void setId(Integer id) {
		this.idc = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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
		return "CodePrestation [idc=" + idc + ", code=" + code + ", libelle=" + libelle
				+ ", libelleFr=" + libelleFr + ", libelleEn=" + libelleEn
				+ ", libelleKn=" + libelleKn + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((idc == null) ? 0 : idc.hashCode());
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CodeWeddingService other = (CodeWeddingService) obj;
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		if (idc == null) {
			if (other.idc != null) {
				return false;
			}
		} else if (!idc.equals(other.idc)) {
			return false;
		}
		if (libelle == null) {
			if (other.libelle != null) {
				return false;
			}
		} else if (!libelle.equals(other.libelle)) {
			return false;
		}
		if (libelleEn == null) {
			if (other.libelleEn != null) {
				return false;
			}
		} else if (!libelleEn.equals(other.libelleEn)) {
			return false;
		}
		if (libelleFr == null) {
			if (other.libelleFr != null) {
				return false;
			}
		} else if (!libelleFr.equals(other.libelleFr)) {
			return false;
		}
		if (libelleKn == null) {
			if (other.libelleKn != null) {
				return false;
			}
		} else if (!libelleKn.equals(other.libelleKn)) {
			return false;
		}
		return true;
	}

	public int getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	

}
