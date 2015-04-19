/**
 * 
 */
package com.buseni.ubukwebwiza.provider.domain;

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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "provider")
public class Provider implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id_provider")
	private Integer id;

	@Column(unique = true)
	@Email
	@NotEmpty(message="{error.provider.requiredfield.email}")
	private String email;

	@NotEmpty(message="{error.provider.requiredfield.password}")
	private String password;

	
	@Column(name="business_name")
	@NotEmpty(message="{error.provider.requiredfield.businessname}")
	private String businessName;

	@Column(name="phone_number")
	private String phoneNumber;

	private String website;
	
	@Column(name="fb_username")
	private String fbUsername;
	
	@Column(name="twitter_username")
	private String twitterUsername;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_profil_picture", referencedColumnName="id_photo")
	private Photo profilPicture;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_cover_picture" , referencedColumnName="id_photo")
	private Photo coverPicture;
	
	@Lob
	@Column(length=500)
	private String aboutme;
		
	private String address;		
	
	private String country;
	
	private boolean enabled;
	
	private String token;
	
	
	@Column(name="normalized_name")
	private String normalizedName;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_update")
	private Date lastUpdate;



	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@OneToMany(mappedBy="provider", cascade = CascadeType.REMOVE)
	private Set<ProviderWeddingService> providerWeddingServices = new HashSet<ProviderWeddingService>();
	
	
	@OneToMany(cascade=CascadeType.ALL)
	 @JoinTable
	  (
	      name="provider_photo",
	      joinColumns={ @JoinColumn(name="id_provider", referencedColumnName="id_provider") },
	      inverseJoinColumns={ @JoinColumn(name="id_photo", referencedColumnName="id_photo", unique=true) }
	  )
	private Set<Photo> photos = new HashSet<Photo>();
	
	@Column(name="nb_views")
	private int nbViews;
	


	@ManyToOne
	@JoinColumn(name="id_district")
	private District district;

	

	@Transient
	private Integer idcService;

	/**
	 * 
	 */
	public Provider() {

	}

	/**
	 * 
	 */
	public Provider(Integer id) {
		this.id = id;
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


	
	@Override
	public String toString() {
		return "Provider [id=" + id + ", email=" + email + ", businessName="
				+ businessName + ", phoneNumber=" + phoneNumber + ", website="
				+ website + ", fbUsername=" + fbUsername + ", twitterUsername="
				+ twitterUsername + ", profilPicture=" + profilPicture
				+ ", coverPicture=" + coverPicture + ", aboutme=" + aboutme
				+ ", address=" + address + ", country=" + country
				+ ", enabled=" + enabled + ", token=" + token
				+ ", normalizedName=" + normalizedName + ", lastUpdate="
				+ lastUpdate + ", createdAt=" + createdAt + ", nbViews="
				+ nbViews + ", district=" + district + "]";
	}



	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aboutme == null) ? 0 : aboutme.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((businessName == null) ? 0 : businessName.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result
				+ ((coverPicture == null) ? 0 : coverPicture.hashCode());
		result = prime * result
				+ ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result
				+ ((district == null) ? 0 : district.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result
				+ ((fbUsername == null) ? 0 : fbUsername.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((idcService == null) ? 0 : idcService.hashCode());
		result = prime * result
				+ ((lastUpdate == null) ? 0 : lastUpdate.hashCode());
		result = prime * result + nbViews;
		result = prime * result
				+ ((normalizedName == null) ? 0 : normalizedName.hashCode());
		result = prime * result
				+ ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result
				+ ((profilPicture == null) ? 0 : profilPicture.hashCode());
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
		Provider other = (Provider) obj;
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
		if (district == null) {
			if (other.district != null)
				return false;
		} else if (!district.equals(other.district))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (enabled != other.enabled)
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
		if (idcService == null) {
			if (other.idcService != null)
				return false;
		} else if (!idcService.equals(other.idcService))
			return false;
		if (lastUpdate == null) {
			if (other.lastUpdate != null)
				return false;
		} else if (!lastUpdate.equals(other.lastUpdate))
			return false;
		if (nbViews != other.nbViews)
			return false;
		if (normalizedName == null) {
			if (other.normalizedName != null)
				return false;
		} else if (!normalizedName.equals(other.normalizedName))
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







	public Date getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}



	public Set<ProviderWeddingService> getProviderWeddingServices() {
		return providerWeddingServices;
	}



	public void setProviderWeddingServices(
			Set<ProviderWeddingService> providerWeddingServices) {
		this.providerWeddingServices = providerWeddingServices;
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
	 * @param lastUpdated the lastUpdated to set
	 */
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdate = lastUpdated;
	}



	/**
	 * @return the lastUpdated
	 */
	public Date getLastUpdated() {
		return lastUpdate;
	}



	public void setViews(int views) {
		this.nbViews = views;
	}



	public int getViews() {
		return nbViews;
	}





	public String getWeddingService() {		
		if( null !=  providerWeddingServices  && !providerWeddingServices.isEmpty()){
			ProviderWeddingService ws =  providerWeddingServices.iterator().next();
			return ws.getWeddingService().getLibelle();
			
		}
		return "";
	}



	public District getDistrict() {
		return district;
	}
	
	
	public Date getLastUpdate() {
		return lastUpdate;
	}



	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}




	public void setDistrict(District district) {
		this.district = district;
	}



	public boolean isEnabled() {
		return enabled;
	}



	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
