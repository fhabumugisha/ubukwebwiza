/**
 * 
 */
package com.buseni.ubukwebwiza.core.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
	
	private String businessName;

	@NotEmpty(message = "The first Name must not be empty")
	private String firstName;
	
	@NotEmpty(message = "The last Name must not be empty")
	private String lastName;
	
	private String phoneNumber;

	private String website;
	
	private String facebookUsername;
	
	private String twitterUsername;
	
	private String profilPicture;
	
	private String coverPicture;
	@Lob
	@Column(length=500)
	private String aboutme;
	
	private String photo;
	
	private String address;
	
	@NotEmpty(message="Your address must be provided")
	private String district;
	
	
	private String city;
	
	private String province;
	
	private String sector;
	
	
	private String country;
	
	private int activeFlag;

	private String token;
	
	private String normalizedName;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_update")
	private Date lastUpdated;

	@OneToMany(mappedBy="vendor", cascade = CascadeType.REMOVE,fetch=FetchType.EAGER)
	private Set<WeddingService> weddingServices = new HashSet<WeddingService>();
	
	
	@OneToMany(mappedBy="vendor",cascade=CascadeType.REMOVE,fetch=FetchType.EAGER)
	private Set<Photo> photos = new HashSet<Photo>();
	
	private int nbViews;
	
	@Transient
	private String weddingService;





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

	public String getDistrict() {
		return district;
	}



	public void setDistrict(String district) {
		this.district = district;
	}



	public String getCity() {
		return city;
	}



	public void setCity(String city) {
		this.city = city;
	}



	public String getProvince() {
		return province;
	}



	public void setProvince(String province) {
		this.province = province;
	}



	public String getSector() {
		return sector;
	}



	public void setSector(String sector) {
		this.sector = sector;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}







	public String getFacebookUsername() {
		return facebookUsername;
	}



	public void setFacebookUsername(String facebookUsername) {
		this.facebookUsername = facebookUsername;
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

	/**
	 * @return the activeFlag
	 */
	public int getActiveFlag() {
		return activeFlag;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
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
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((facebookUsername == null) ? 0 : facebookUsername.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((lastUpdated == null) ? 0 : lastUpdated.hashCode());
		result = prime * result
				+ ((normalizedName == null) ? 0 : normalizedName.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
		
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result
				+ ((twitterUsername == null) ? 0 : twitterUsername.hashCode());
		result = prime * result + ((website == null) ? 0 : website.hashCode());
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
		Vendor other = (Vendor) obj;
		if (aboutme == null) {
			if (other.aboutme != null) {
				return false;
			}
		} else if (!aboutme.equals(other.aboutme)) {
			return false;
		}
		if (activeFlag != other.activeFlag) {
			return false;
		}
		if (address == null) {
			if (other.address != null) {
				return false;
			}
		} else if (!address.equals(other.address)) {
			return false;
		}
		if (businessName == null) {
			if (other.businessName != null) {
				return false;
			}
		} else if (!businessName.equals(other.businessName)) {
			return false;
		}
		if (confirmPassword == null) {
			if (other.confirmPassword != null) {
				return false;
			}
		} else if (!confirmPassword.equals(other.confirmPassword)) {
			return false;
		}
		if (email == null) {
			if (other.email != null) {
				return false;
			}
		} else if (!email.equals(other.email)) {
			return false;
		}
		if (facebookUsername == null) {
			if (other.facebookUsername != null) {
				return false;
			}
		} else if (!facebookUsername.equals(other.facebookUsername)) {
			return false;
		}
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (lastUpdated == null) {
			if (other.lastUpdated != null) {
				return false;
			}
		} else if (!lastUpdated.equals(other.lastUpdated)) {
			return false;
		}
		if (normalizedName == null) {
			if (other.normalizedName != null) {
				return false;
			}
		} else if (!normalizedName.equals(other.normalizedName)) {
			return false;
		}
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		if (phoneNumber == null) {
			if (other.phoneNumber != null) {
				return false;
			}
		} else if (!phoneNumber.equals(other.phoneNumber)) {
			return false;
		}
		if (photo == null) {
			if (other.photo != null) {
				return false;
			}
		} else if (!photo.equals(other.photo)) {
			return false;
		}
		
		if (token == null) {
			if (other.token != null) {
				return false;
			}
		} else if (!token.equals(other.token)) {
			return false;
		}
		if (twitterUsername == null) {
			if (other.twitterUsername != null) {
				return false;
			}
		} else if (!twitterUsername.equals(other.twitterUsername)) {
			return false;
		}
		if (website == null) {
			if (other.website != null) {
				return false;
			}
		} else if (!website.equals(other.website)) {
			return false;
		}
		return true;
	}



	@Override
	public String toString() {
		return "Professional [id=" + id + ", email=" + email + ", password="
				+ password + ", confirmPassword=" + confirmPassword
				+ ", businessName=" + businessName + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", phoneNumber=" + phoneNumber
				+ ", website=" + website + ", facebookUrl=" + facebookUsername
				+ ", twitterUsername=" + twitterUsername + ", aboutme="
				+ aboutme + ", photo=" + photo 
				+ ", address=" + address + ", activeFlag=" + activeFlag
				+ ", token=" + token + ", normalizedName=" + normalizedName
				+ ", lastUpdated=" + lastUpdated + "]";
	}




	public Set<WeddingService> getWeddingServices() {
		return weddingServices;
	}



	public void setWeddingServices(Set<WeddingService> weddingServices) {
		this.weddingServices = weddingServices;
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
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}



	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
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
		if(null !=  weddingServices  && !weddingServices.isEmpty()){
			WeddingService ws =  weddingServices.iterator().next();
			weddingService =  ws.getCodeWeddingService().getLibelle();
			
		}
		return weddingService;
	}



}
