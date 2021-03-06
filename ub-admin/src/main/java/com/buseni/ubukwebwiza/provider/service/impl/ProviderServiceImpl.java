/**
 * 
 */
package com.buseni.ubukwebwiza.provider.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.buseni.ubukwebwiza.account.beans.SignupForm;
import com.buseni.ubukwebwiza.account.domain.Role;
import com.buseni.ubukwebwiza.account.domain.UserAccount;
import com.buseni.ubukwebwiza.account.repository.RoleRepository;
import com.buseni.ubukwebwiza.account.repository.UserAccountRepository;
import com.buseni.ubukwebwiza.administrator.enums.EnumAccountType;
import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.exceptions.CustomError;
import com.buseni.ubukwebwiza.exceptions.CustomErrorBuilder;
import com.buseni.ubukwebwiza.exceptions.ResourceNotFoundException;
import com.buseni.ubukwebwiza.gallery.domain.Photo;
import com.buseni.ubukwebwiza.gallery.repository.PhotoRepo;
import com.buseni.ubukwebwiza.provider.beans.ProviderSearch;
import com.buseni.ubukwebwiza.provider.domain.District;
import com.buseni.ubukwebwiza.provider.domain.Provider;
import com.buseni.ubukwebwiza.provider.domain.ProviderWeddingService;
import com.buseni.ubukwebwiza.provider.domain.WeddingService;
import com.buseni.ubukwebwiza.provider.predicates.ProviderPredicates;
import com.buseni.ubukwebwiza.provider.repository.DistrictRepo;
import com.buseni.ubukwebwiza.provider.repository.ProviderRepo;
import com.buseni.ubukwebwiza.provider.repository.ProviderWeddingServiceRepo;
import com.buseni.ubukwebwiza.provider.repository.WeddingServiceRepo;
import com.buseni.ubukwebwiza.provider.service.ProviderService;
import com.buseni.ubukwebwiza.utils.AmazonS3Util;
import com.buseni.ubukwebwiza.utils.UbUtils;
import com.querydsl.core.types.Predicate;



/**
 * @author habumugisha
 *
 */
@Service
@Transactional(readOnly=true)
public class ProviderServiceImpl implements ProviderService {
	
	private static final String ROLE_PROVIDER = "ROLE_PROVIDER";
	public static final Logger LOGGER = LoggerFactory.getLogger( ProviderServiceImpl.class );
	private ProviderRepo providerRepo;
	private WeddingServiceRepo weddingServiceRepo;
	private ProviderWeddingServiceRepo providerWeddingServiceRepo;
	private PhotoRepo photoRepo;   	
	private DistrictRepo districtRepo;
	private RoleRepository roleRepository;
	private UserAccountRepository userAccountRepository;
	
	@Autowired
	private AmazonS3Util amazonS3Util;
	@Autowired
	public ProviderServiceImpl(ProviderRepo providerRepo, WeddingServiceRepo weddingServiceRepo, ProviderWeddingServiceRepo providerWeddingServiceRepo,
			PhotoRepo photoRepo,  DistrictRepo districtRepo, RoleRepository roleRepository, UserAccountRepository userAccountRepository){
		this.providerRepo = providerRepo;
		this.weddingServiceRepo = weddingServiceRepo;
		this.providerWeddingServiceRepo = providerWeddingServiceRepo;
		this.photoRepo = photoRepo;
		this.districtRepo = districtRepo;
		this.roleRepository = roleRepository;
		this.userAccountRepository =  userAccountRepository;
	
		
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.ProviderService#create(com.buseni.ubukwebwiza.administrator.domain.Provider)
	 */
	@Override
	@Transactional
	public void addOrUpdate(Provider provider) throws BusinessException {
		//control before save
		if(null == provider){
			throw new NullPointerException("Provider shouldn't be null");
		}
		//Creation
		if(provider.getId() == null){			
			if (emailExist(provider.getAccount().getEmail())) {  
				CustomErrorBuilder ceb =  new CustomErrorBuilder("error.user.emailexists");			
				CustomError  ce = ceb.field("account.email").errorArgs(new String[]{provider.getAccount().getEmail()}).buid();
				throw new BusinessException(ce);
	           
	        }
			provider.getAccount().setCreatedAt(new Date());	
			Role roleProvider =  roleRepository.findByName(ROLE_PROVIDER);
			provider.getAccount().getRoles().add(roleProvider);
			provider.getAccount().setLastUpdate(new Date());
			provider.getAccount().setType(EnumAccountType.PROVIDER.name());
			String urlName =  UbUtils.createUrlName(provider.getBusinessName(), false);
			if(Objects.nonNull(providerRepo.findByUrlName(urlName))){
				urlName = UbUtils.createUrlName(provider.getBusinessName(), true);
			}
			provider.setUrlName(urlName);
			providerRepo.save(provider);
			if(provider.getIdcService() != null){
				WeddingService weddingService = weddingServiceRepo.findById(provider.getIdcService()).orElseThrow(() -> new NullPointerException(" shouldn't be null"));
				ProviderWeddingService vws = new ProviderWeddingService();
				vws.setWeddingService(weddingService);
				vws.setProvider(provider);
				vws.setCreatedAt(new Date());
				vws.setLastUpdate(new Date());
				vws.setEnabled(true);
				providerWeddingServiceRepo.save(vws);
				provider.getProviderWeddingServices().add(vws);
				
			}
		
		//Update
		}else{
			Provider bdd =  providerRepo.findById(provider.getId()).orElseThrow(()-> new NullPointerException(" shouldn't be null"));
			if(!bdd.getAccount().getEmail().equals(provider.getAccount().getEmail())){
				if (emailExist(provider.getAccount().getEmail())) {  
					CustomErrorBuilder ceb =  new CustomErrorBuilder("error.user.emailexists");			
					CustomError  ce = ceb.field("account.email").errorArgs(new String[]{provider.getAccount().getEmail()}).buid();
					throw new BusinessException(ce);
		           
		        }
				bdd.getAccount().setEmail(provider.getAccount().getEmail());
			}
			if(provider.getProfilPicture() != null){	
				//profile picture changed, delete the old one
				if(bdd.getProfilPicture() != null){
					photoRepo.delete(bdd.getProfilPicture());
					amazonS3Util.deleteFile(bdd.getProfilPicture().getFilename());				
				}
				bdd.setProfilPicture(provider.getProfilPicture());
			}
			if(!bdd.getBusinessName().equals(provider.getBusinessName())){
				bdd.setUrlName(UbUtils.createUrlName(provider.getBusinessName(), true));
			}
				
			bdd.setBusinessName(provider.getBusinessName());
			bdd.setAboutme(provider.getAboutme());
			bdd.setAddress(provider.getAddress());
			bdd.setPhoneNumber(provider.getPhoneNumber());
			bdd.setDistrict(provider.getDistrict());
			bdd.setWebsite(provider.getWebsite());
			bdd.setFbUsername(provider.getFbUsername());
			bdd.setTwitterUsername(provider.getTwitterUsername());
			
			bdd.getAccount().setEnabled(provider.getAccount().isEnabled());
			bdd.getAccount().setLastUpdate(new Date());
						
			providerRepo.save(bdd);
		}
	

	}
	
	/*
	 * 
	 */
	@Override
	@Transactional
	public void removeProfilePhoto(Integer idProvider){
		if(null == idProvider){
			throw new NullPointerException("idProvider shouldn't be null");
		}
		Optional<Provider> provider =  providerRepo.findById(idProvider);
		if(! provider.isPresent()){
			throw new NullPointerException("provider shouldn't be null");
		}
		if(provider.get().getProfilPicture() != null){
			
			photoRepo.delete(provider.get().getProfilPicture());
			amazonS3Util.deleteFile(provider.get().getProfilPicture().getFilename());		
			provider.get().setProfilPicture(null);
		}
		provider.get().getAccount().setLastUpdate(new Date());
		providerRepo.save(provider.get());
		
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.ProviderService#update(com.buseni.ubukwebwiza.administrator.domain.Provider)
	 */
	@Override
	@Transactional
	public Provider update(Provider provider) {
		// TODO COntrol before save
		provider.getAccount().setLastUpdate(new Date());
		Provider updated = providerRepo.save(provider);		 
		 return updated;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.ProviderService#findById(java.lang.Integer)
	 */
	@Override
	public Provider getProvider(Integer id) {
		if(null == id){
			throw new NullPointerException();
		}
		
		Optional<Provider> provider  = providerRepo.findById(id);
		if(!provider.isPresent()){
			throw new ResourceNotFoundException();
		}
		LOGGER.info("Increase the number of views of the provider " + provider.get().getBusinessName());
		provider.get().setNbViews(provider.get().getNbViews() + 1);
		provider.get().getAccount().setLastUpdate(new Date());
		providerRepo.save(provider.get());
		return provider.get();
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.ProviderService#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	//@Transactional
	public Page<Provider> findAll(Pageable pageable) {
		if(pageable == null){
			return new PageImpl<>(providerRepo.findAll());
		}
		PageRequest pr = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
		
		Page<Provider> allProviders = providerRepo.findAll(pr);
//		allProviders.getContent().forEach(d-> {
//			String urlName  = UbUtils.createUrlName(d.getBusinessName(), false);
//			d.setUrlName(urlName);
//			providerRepo.save(d);
//		});
		
		return allProviders;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.ProviderService#findAll(com.buseni.ubukwebwiza.utils.ProviderSearch, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Provider> search(ProviderSearch providerSearch,	Pageable pageable) {
		if(null == providerSearch){
			throw new NullPointerException("providerSearch should be null");
		}	
		if(pageable == null){
			Predicate predicate = ProviderPredicates.searchByUrlName(providerSearch);
			List<Provider> providers = IteratorUtils.toList(providerRepo.findAll(predicate).iterator());
			return new PageImpl<>(providers);
		}
		PageRequest pr = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
		return	providerRepo.findAll(ProviderPredicates.searchByUrlName(providerSearch), pr);


	}

	/*
	 * (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.ProviderService#delete(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void delete(Integer id) {
		if(null != id){
			Provider provider = providerRepo.findById(id).orElse(null);
			if(provider != null){			
				providerRepo.delete(provider);
			}
			
		}
		
	}

	@Override
	public List<Provider> getFeaturedProviders() {	
		Page<Provider> page = providerRepo.findByAccount_Enabled(Boolean.TRUE, PageRequest.of(0, 3, Sort.Direction.DESC, "nbViews"));
		return page.getContent();
	}

	@Override
	public Page<Provider> findByEnabled(boolean enabled, Pageable pageable) {
		if(pageable == null){
			return new PageImpl<>(providerRepo.findByAccount_Enabled(enabled));
		}
		PageRequest pr = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
		return providerRepo.findByAccount_Enabled(enabled, pr);
	}

	@Override
	public Provider findOne(Integer id) {
		if(null == id){
			throw new NullPointerException("Id should be null");
		}		
		Optional<Provider> provider  = providerRepo.findById(id);
		if(!provider.isPresent() ){
			throw new ResourceNotFoundException();
		}			
		return provider.get();
	}

	@Transactional
	@Override
	public Provider deletePhoto(Integer idProvider, Integer  idPhoto) {
		if(null == idProvider || idPhoto == null){
			throw new NullPointerException("idProvider and idPhoto shouldn't be null");
		}	
		Provider provider = findOne(idProvider);
		
		Optional<Photo> photo =  photoRepo.findById(idPhoto);
		if(photo.isPresent() && !CollectionUtils.isEmpty(provider.getPhotos())){
			provider.getPhotos().remove(photo.get());
			providerRepo.save(provider);
			photoRepo.delete(photo.get());
		}
		return provider;
	}

	@Transactional
	@Override
	public Provider deletePhoto(Integer idProvider, Photo photo) {
		Provider provider = findOne(idProvider);
		if(photo != null && !CollectionUtils.isEmpty(provider.getPhotos())){
			provider.getPhotos().remove(photo);
			provider.getAccount().setLastUpdate(new Date());
			providerRepo.save(provider);
			photoRepo.delete(photo);
		}
		return provider;
	}

	@Transactional
	@Override
	public Provider deleteService(Integer idProvider, ProviderWeddingService providerWeddingService) {
		Provider provider = findOne(idProvider);
		if(providerWeddingService != null && !CollectionUtils.isEmpty(provider.getProviderWeddingServices())){
			provider.getProviderWeddingServices().remove(providerWeddingService);
			provider.getAccount().setLastUpdate(new Date());
			providerRepo.save(provider);
			providerWeddingServiceRepo.delete(providerWeddingService);
		}
		return provider;
	}
	 private boolean emailExist(String email) {
		 	UserAccount user = userAccountRepository.findByEmail(email);
	        if (user != null) {
	            return true;
	        }
	        return false;
	    }

	@Override
	@Transactional
	public UserAccount createAccount(SignupForm signupForm) throws BusinessException {
		if(null == signupForm){
			throw new NullPointerException();
		}	

		if (emailExist(signupForm.getEmail())) {  
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.account.emailexists");			
			CustomError  ce = ceb.field("email").errorArgs(new String[]{signupForm.getEmail()}).buid();
			throw new BusinessException(ce);

		}
		Provider provider = new Provider();
		provider.setBusinessName(signupForm.getBusinessName());
		UserAccount  account  = new UserAccount();
		
		account.setEmail(signupForm.getEmail());
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		account.setPassword(encoder.encode(signupForm.getPassword()));
		account.setCreatedAt(new Date());			
		account.setLastUpdate(new Date());
		String urlName =  UbUtils.createUrlName(provider.getBusinessName(), false);
		if(Objects.nonNull(providerRepo.findByUrlName(urlName))){
			urlName = UbUtils.createUrlName(provider.getBusinessName(), true);
		}
		provider.setUrlName(urlName);
		//add roles
		Role roleProvider =  roleRepository.findByName(ROLE_PROVIDER);
		account.getRoles().add(roleProvider);
		account.setType(EnumAccountType.PROVIDER.name());
		provider.setAccount(account);
		District district  = districtRepo.findById(signupForm.getIdDistrict()).orElseThrow(()-> new NullPointerException(" shouldn't be null"));
		provider.setDistrict(district);
		WeddingService weddingService = weddingServiceRepo.findById(signupForm.getIdService()).orElseThrow(()-> new NullPointerException(" shouldn't be null"));
		ProviderWeddingService vws = new ProviderWeddingService();
		vws.setWeddingService(weddingService);
		vws.setProvider(provider);
		vws.setCreatedAt(new Date());
		vws.setLastUpdate(new Date());
		vws.setEnabled(true);
		providerWeddingServiceRepo.save(vws);
		provider.getProviderWeddingServices().add(vws);		
		providerRepo.save(provider);

		return account;
	}

	@Override
	//@Cacheable(value="findProviderByUsername")
	public Provider findProviderByUsername(String username) {
		if(null == username){
			throw new NullPointerException();
		}
		
		Provider provider  = providerRepo.findByAccount_Email(username);
		if(provider ==  null){
			throw new ResourceNotFoundException();
		}
		
		return provider;
	}

	@Override
	@Transactional
	public void updateInfos(Provider provider) throws BusinessException {
		if(null == provider){
			throw new NullPointerException("Provider shouldn't be null");
		}
		
		Provider bdd =  providerRepo.findById(provider.getId()).orElseThrow(()-> new NullPointerException(" shouldn't be null"));
		
		if(provider.getProfilPicture() != null){
			//profile picture changed, delete the old one
			if(bdd.getProfilPicture() != null){
				photoRepo.delete(bdd.getProfilPicture());
				amazonS3Util.deleteFile(bdd.getProfilPicture().getFilename());				
			}
			bdd.setProfilPicture(provider.getProfilPicture());
		}
		if(!bdd.getBusinessName().equals(provider.getBusinessName())){
			String urlName =  UbUtils.createUrlName(provider.getBusinessName(), false);
			if(Objects.nonNull(providerRepo.findByUrlName(urlName)) ){
				urlName = UbUtils.createUrlName(provider.getBusinessName(), true);
			}
			bdd.setUrlName(urlName);
		}
		bdd.setBusinessName(provider.getBusinessName());
		bdd.setAboutme(provider.getAboutme());
		bdd.setAddress(provider.getAddress());
		bdd.setPhoneNumber(provider.getPhoneNumber());
		bdd.setDistrict(provider.getDistrict());
		bdd.getAccount().setLastUpdate(new Date());
					
		providerRepo.save(bdd);
		
	}

	@Override
	@Transactional
	public void updateSocialMedia(Provider provider) throws BusinessException {
		if(null == provider){
			throw new NullPointerException("Provider shouldn't be null");
		}
		
		Provider bdd =  providerRepo.findById(provider.getId()).orElseThrow(()-> new NullPointerException(" shouldn't be null"));
		bdd.setWebsite(provider.getWebsite());
		bdd.setFbUsername(provider.getFbUsername());
		bdd.setTwitterUsername(provider.getTwitterUsername());
		bdd.getAccount().setLastUpdate(new Date());
					
		providerRepo.save(bdd);
	}

	@Override
	@Transactional
	public Provider getProvider(String urlName) {
		if(StringUtils.isEmpty(urlName)){
			throw new NullPointerException();
		}
		
		Provider provider  = providerRepo.findByUrlName(urlName);
		if(provider ==  null){
			throw new ResourceNotFoundException();
		}
		provider.setNbViews(provider.getNbViews() + 1);
		provider.getAccount().setLastUpdate(new Date());
		providerRepo.save(provider);
		return provider;
	}

	


	@Override
	public Provider getProviderByUrlName(String urlName) {
		if(StringUtils.isEmpty(urlName)){
			throw new NullPointerException();
		}
		
		Provider provider  = providerRepo.findByUrlName(urlName);
		if(provider ==  null){
			throw new ResourceNotFoundException();
		}
		return provider;
	}


	
	
}
