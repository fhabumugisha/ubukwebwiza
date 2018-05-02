/**
 * 
 */
package com.buseni.ubukwebwiza.provider.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.buseni.ubukwebwiza.gallery.beans.PhotoDetails;
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
	
	private String urlName;
	
	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

//	private Photo profilPicture;
	
	
	private Photo coverPicture;
	
	
	@Length(min=0, max=500)
	private String aboutme;
	
	@Length(min=1, max=100)
	private String address;		
	
private String profilePicture;

	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	//@ValidPassword
	private String password;
	
	@NotNull
	private Integer idDistrict;
	
	private Integer idcService;
	
	private String district;
	
	private String services;
	
	private List<PhotoDetails> photos = new ArrayList<>();

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public List<PhotoDetails> getPhotos() {
		return photos;
	}

	public void setPhotos(List<PhotoDetails> photos) {
		this.photos = photos;
	}

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



	public Photo getCoverPicture() {
		return coverPicture;
	}

	public void setCoverPicture(Photo coverPicture) {
		this.coverPicture = coverPicture;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePictureFilename) {
		this.profilePicture = profilePictureFilename;
	}





}
