/**
 * 
 */
package com.buseni.ubukwebwiza.vendor.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


/**
 * @author habumugisha
 * 
 */
@Entity
@Table(name = "vendor")
public class Vendor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_vendor")
	private Integer id;

	@Column(unique = true)
	@Email
	@NotEmpty(message = "The login must not be empty")
	private String email;

	@NotEmpty(message = "The password must not be empty")
	private String password;

	@Transient
	private String confirmPassword;
	
	@Column(name="business_name")
	private String businessName;

	@Column(name="phone_number")
	private String phoneNumber;

	private String website;
	
	@Column(name="fb_username")
	private String fbUsername;
	
	@Column(name="twitter_username")
	private String twitterUsername;
	
	@Column(name="profil_picture")
	private String profilPicture;
	
	@Column(name="cover_picture")
	private String coverPicture;
	@Lob
	@Column(length=500)
	private String aboutme;
		
	private String address;
		
	
	private String country;
	
	@Column(name="active_flag")
	private int activeFlag;

	private String token;
	
	@Column(name="normalized_name")
	private String normalizedName;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_update")
	private Date lastUpdated;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@OneToMany(mappedBy="vendor", cascade = CascadeType.REMOVE)
	private Set<VendorWeddingService> vendorWeddingServices = new HashSet<VendorWeddingService>();
	
	
	@OneToMany(mappedBy="vendor",cascade=CascadeType.REMOVE)
	private Set<Photo> photos = new HashSet<Photo>();
	
	@Column(name="nb_views")
	private int nbViews;
	


	@ManyToOne
	@JoinColumn(name="id_district")
	private District district;



	/**
	 * 
	 */
	public Vendor() {

	}



	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	public void setBusinessName(String vendorName) {
		this.businessName = vendorName;
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
	public void setPhoneNumber(String vendorTel) {
		this.phoneNumber = vendorTel;
	}

	

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	

	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}







	public String getFbUsername() {
		return fbUsername;
	}



	public void setFbUsername(String facebookUsername) {
		this.fbUsername = facebookUsername;
	}



	public int getNbViews() {
		return nbViews;
	}



	public void setNbViews(int nbViews) {
		this.nbViews = nbViews;
	}



	public String getAddress() {
		return address;
	}



	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}


	

	/**
	 * @param activeFlag
	 *            the activeFlag to set
	 */
	public void setActiveFlag(int activeFlag) {
		this.activeFlag = activeFlag;
	}

	@Override
	public String toString() {
		return "Vendor [id=" + id + ", email=" + email + ", password="
				+ password + ", confirmPassword=" + confirmPassword
				+ ", businessName=" + businessName + ", phoneNumber="
				+ phoneNumber + ", website=" + website + ", fbUsername="
				+ fbUsername + ", twitterUsername=" + twitterUsername
				+ ", profilPicture=" + profilPicture + ", coverPicture="
				+ coverPicture + ", aboutme=" + aboutme + ", address="
				+ address + ", country=" + country + ", activeFlag="
				+ activeFlag + ", token=" + token + ", normalizedName="
				+ normalizedName + ", lastUpdated=" + lastUpdated
				+ ", createdAt=" + createdAt + ", nbViews=" + nbViews + "]";
	}



	/**
	 * @return the activeFlag
	 */
	public int getActiveFlag() {
		return activeFlag;
	}


	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aboutme == null) ? 0 : aboutme.hashCode());
		result = prime * result + activeFlag;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((businessName == null) ? 0 : businessName.hashCode());
		result = prime * result
				+ ((confirmPassword == null) ? 0 : confirmPassword.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result
				+ ((coverPicture == null) ? 0 : coverPicture.hashCode());
		result = prime * result
				+ ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((fbUsername == null) ? 0 : fbUsername.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastUpdated == null) ? 0 : lastUpdated.hashCode());
		result = prime * result + nbViews;
		result = prime * result
				+ ((normalizedName == null) ? 0 : normalizedName.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result
				+ ((profilPicture == null) ? 0 : profilPicture.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result
				+ ((twitterUsername == null) ? 0 : twitterUsername.hashCode());
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
		Vendor other = (Vendor) obj;
		if (aboutme == null) {
			if (other.aboutme != null)
				return false;
		} else if (!aboutme.equals(other.aboutme))
			return false;
		if (activeFlag != other.activeFlag)
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
		if (confirmPassword == null) {
			if (other.confirmPassword != null)
				return false;
		} else if (!confirmPassword.equals(other.confirmPassword))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (coverPicture == null) {
			if (other.coverPicture != null)
				return false;
		} else if (!coverPicture.equals(other.coverPicture))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
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
		if (lastUpdated == null) {
			if (other.lastUpdated != null)
				return false;
		} else if (!lastUpdated.equals(other.lastUpdated))
			return false;
		if (nbViews != other.nbViews)
			return false;
		if (normalizedName == null) {
			if (other.normalizedName != null)
				return false;
		} else if (!normalizedName.equals(other.normalizedName))
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
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
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







	public Date getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}



	public Set<VendorWeddingService> getVendorWeddingServices() {
		return vendorWeddingServices;
	}



	public void setVendorWeddingServices(
			Set<VendorWeddingService> vendorWeddingServices) {
		this.vendorWeddingServices = vendorWeddingServices;
	}



	public Set<Photo> getPhotos() {
		return photos;
	}



	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
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



	/**
	 * @param photos the photos to set
	 */
	public void setFashions(Set<Photo> photos) {
		this.photos = photos;
	}



	/**
	 * @return the photos
	 */
	public Set<Photo> getFashions() {
		return photos;
	}



	/**
	 * @param normalizedName the normalizedName to set
	 */
	public void setNormalizedName(String normalizedName) {
		this.normalizedName = normalizedName;
	}



	/**
	 * @return the normalizedName
	 */
	public String getNormalizedName() {
		return normalizedName;
	}



	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}



	/**
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}



	/**
	 * @param lastUpdated the lastUpdated to set
	 */
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}



	/**
	 * @return the lastUpdated
	 */
	public Date getLastUpdated() {
		return lastUpdated;
	}



	public void setViews(int views) {
		this.nbViews = views;
	}



	public int getViews() {
		return nbViews;
	}



	public String getProfilPicture() {
		return profilPicture;
	}



	public void setProfilPicture(String profilPicture) {
		this.profilPicture = profilPicture;
	}



	public String getCoverPicture() {
		return coverPicture;
	}



	public void setCoverPicture(String coverPicture) {
		this.coverPicture = coverPicture;
	}



	public String getWeddingService() {		
		if( null !=  vendorWeddingServices  && !vendorWeddingServices.isEmpty()){
			VendorWeddingService ws =  vendorWeddingServices.iterator().next();
			return ws.getWeddingService().getLibelle();
			
		}
		return "";
	}



	public District getDistrict() {
		return district;
	}



	public void setDistrict(District district) {
		this.district = district;
	}



}
