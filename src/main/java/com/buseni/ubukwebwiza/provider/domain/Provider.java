/**
 * 
 */
package com.buseni.ubukwebwiza.provider.domain;

import java.io.Serializable;
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
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.buseni.ubukwebwiza.account.domain.UserAccount;
import com.buseni.ubukwebwiza.gallery.domain.Photo;


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
	@Column(name="id")
	private Integer id;
	
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@JoinColumn(nullable = false, name = "id_account", referencedColumnName="id")
	private UserAccount account;
	

	
	@Column(name="business_name")
	@NotEmpty(message="{error.provider.requiredfield.businessname}")
	@Length(min=1, max=50)
	private String businessName;

	@Column(name="phone_number")
	@Length(min=0, max=50)
	private String phoneNumber;

	@Length(min=0, max=50)
	private String website;
	
	@Column(name="fb_username")
	@Length(min=0, max=50)
	private String fbUsername;
	
	@Column(name="twitter_username")
	@Length(min=0, max=50)
	private String twitterUsername;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_profil_picture", referencedColumnName="id")
	private Photo profilPicture;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_cover_picture" , referencedColumnName="id")
	private Photo coverPicture;
	
	@Lob
	@Column(length=500)
	private String aboutme;
	
	@Length(min=0, max=100)
	private String address;		
	
	private String country;
	
	
	
	@Column(name="url_name")
	private String urlName;
	



	@OneToMany(mappedBy="provider", cascade = CascadeType.REMOVE)
	private Set<ProviderWeddingService> providerWeddingServices = new HashSet<ProviderWeddingService>();
	
	
	@OneToMany(cascade=CascadeType.ALL)
	 @JoinTable
	  (
	      name="provider_photo",
	      joinColumns={ @JoinColumn(name="id_provider", referencedColumnName="id") },
	      inverseJoinColumns={ @JoinColumn(name="id_photo", referencedColumnName="id", unique=true) }
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

	
	@Override
	public String toString() {
		return "Provider [id=" + id + ", account=" + account + ", businessName=" + businessName + ", phoneNumber=" + phoneNumber + ", website=" + website + ", fbUsername=" + fbUsername
				+ ", twitterUsername=" + twitterUsername + ", profilPicture=" + profilPicture + ", coverPicture=" + coverPicture + ", aboutme=" + aboutme + ", address=" + address + ", country="
				+ country + ", urlName=" + urlName + ", nbViews=" + nbViews + ", district=" + district + "]";
	}



	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aboutme == null) ? 0 : aboutme.hashCode());
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((businessName == null) ? 0 : businessName.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((coverPicture == null) ? 0 : coverPicture.hashCode());
		result = prime * result + ((district == null) ? 0 : district.hashCode());
		result = prime * result + ((fbUsername == null) ? 0 : fbUsername.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + nbViews;
		result = prime * result + ((urlName == null) ? 0 : urlName.hashCode());
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
		Provider other = (Provider) obj;
		if (aboutme == null) {
			if (other.aboutme != null)
				return false;
		} else if (!aboutme.equals(other.aboutme))
			return false;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
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
		if (district == null) {
			if (other.district != null)
				return false;
		} else if (!district.equals(other.district))
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
		if (nbViews != other.nbViews)
			return false;
		if (urlName == null) {
			if (other.urlName != null)
				return false;
		} else if (!urlName.equals(other.urlName))
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



	public String getUrlName() {
		return urlName;
	}

	public void setUrlName(String urlName) {
		this.urlName = urlName;
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

	public UserAccount getAccount() {
		return account;
	}

	public void setAccount(UserAccount account) {
		this.account = account;
	}

	public void setDistrict(District district) {
		this.district = district;
	}



}
