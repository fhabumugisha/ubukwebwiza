/**
 * 
 */
package com.buseni.ubukwebwiza.provider.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.buseni.ubukwebwiza.gallery.domain.Photo;


/**
 * @author habumugisha
 * 
 */

public class ProviderDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	
	@NotEmpty(message="{error.provider.requiredfield.businessname}")
	@Length(min=1, max=50)
	private String businessName;

	
	@Length(min=1, max=50)
	private String phoneNumber;

	@Length(min=1, max=50)
	private String website;
	

	@Length(min=1, max=50)
	private String fbUsername;
	
	
	@Length(min=1, max=50)
	private String twitterUsername;
	
	
	
	private Photo profilPicture;
	
	
	private Photo coverPicture;
	
	
	@Length(min=0, max=500)
	private String aboutme;
	
	@Length(min=1, max=100)
	private String address;		
	


	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	//@ValidPassword
	private String password;
	
	@NotNull
	private Integer idDistrict;
	
	private Integer idcService;

	/**
	 * 
	 */
	public ProviderDTO() {

	}

	/**
	 * 
	 */
	public ProviderDTO(Integer id) {
		this.id = id;
	}



	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the businessName
	 */
	public String getBusinessName() {
		return businessName;
	}

	/**
	 * @param businessName
	 *            the businessName to set
	 */
	public void setBusinessName(String providerName) {
		this.businessName = providerName;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(String providerTel) {
		this.phoneNumber = providerTel;
	}

	

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	









	public String getFbUsername() {
		return fbUsername;
	}



	public void setFbUsername(String facebookUsername) {
		this.fbUsername = facebookUsername;
	}



	public String getAddress() {
		return address;
	}

	
	@Override
	public String toString() {
		return "ProviderDTO [id=" + id + ", businessName=" + businessName + ", phoneNumber=" + phoneNumber + ", website=" + website + ", fbUsername=" + fbUsername + ", twitterUsername="
				+ twitterUsername + ", profilPicture=" + profilPicture + ", coverPicture=" + coverPicture + ", aboutme=" + aboutme + ", address=" + address + ", email=" + email + ", password="
				+ password + ", idDistrict=" + idDistrict + ", idcService=" + idcService + "]";
	}



	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aboutme == null) ? 0 : aboutme.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((businessName == null) ? 0 : businessName.hashCode());
		result = prime * result + ((coverPicture == null) ? 0 : coverPicture.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((fbUsername == null) ? 0 : fbUsername.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idDistrict == null) ? 0 : idDistrict.hashCode());
		result = prime * result + ((idcService == null) ? 0 : idcService.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((profilPicture == null) ? 0 : profilPicture.hashCode());
		result = prime * result + ((twitterUsername == null) ? 0 : twitterUsername.hashCode());
		result = prime * result + ((website == null) ? 0 : website.hashCode());
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
		ProviderDTO other = (ProviderDTO) obj;
		if (aboutme == null) {
			if (other.aboutme != null)
				return false;
		} else if (!aboutme.equals(other.aboutme))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (businessName == null) {
			if (other.businessName != null)
				return false;
		} else if (!businessName.equals(other.businessName))
			return false;
		if (coverPicture == null) {
			if (other.coverPicture != null)
				return false;
		} else if (!coverPicture.equals(other.coverPicture))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fbUsername == null) {
			if (other.fbUsername != null)
				return false;
		} else if (!fbUsername.equals(other.fbUsername))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idDistrict == null) {
			if (other.idDistrict != null)
				return false;
		} else if (!idDistrict.equals(other.idDistrict))
			return false;
		if (idcService == null) {
			if (other.idcService != null)
				return false;
		} else if (!idcService.equals(other.idcService))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (profilPicture == null) {
			if (other.profilPicture != null)
				return false;
		} else if (!profilPicture.equals(other.profilPicture))
			return false;
		if (twitterUsername == null) {
			if (other.twitterUsername != null)
				return false;
		} else if (!twitterUsername.equals(other.twitterUsername))
			return false;
		if (website == null) {
			if (other.website != null)
				return false;
		} else if (!website.equals(other.website))
			return false;
		return true;
	}




	



	/**
	 * @param website the website to set
	 */
	public void setWebsite(String website) {
		this.website = website;
	}



	/**
	 * @return the website
	 */
	public String getWebsite() {
		return website;
	}











	/**
	 * @param twitterUsername the twitterUsername to set
	 */
	public void setTwitterUsername(String twitterUsername) {
		this.twitterUsername = twitterUsername;
	}



	/**
	 * @return the twitterUsername
	 */
	public String getTwitterUsername() {
		return twitterUsername;
	}



	/**
	 * @param aboutme the aboutme to set
	 */
	public void setAboutme(String aboutme) {
		this.aboutme = aboutme;
	}



	/**
	 * @return the aboutme
	 */
	public String getAboutme() {
		return aboutme;
	}








	public Integer getIdcService() {
		return idcService;
	}



	public void setIdcService(Integer idcService) {
		this.idcService = idcService;
	}

	public Photo getProfilPicture() {
		return profilPicture;
	}

	public void setProfilPicture(Photo profilPicture) {
		this.profilPicture = profilPicture;
	}

	public Photo getCoverPicture() {
		return coverPicture;
	}

	public void setCoverPicture(Photo coverPicture) {
		this.coverPicture = coverPicture;
	}





}
