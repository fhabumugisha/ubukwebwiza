package com.buseni.ubukwebwiza.provider.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.account.beans.SignupForm;
import com.buseni.ubukwebwiza.account.domain.UserAccount;
import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.gallery.domain.Photo;
import com.buseni.ubukwebwiza.provider.beans.ProviderSearch;
import com.buseni.ubukwebwiza.provider.domain.Provider;

public interface ProviderService {
	
	/**
	 * 
	 * @param weddingService
	 */
	void addOrUpdate(Provider provider) throws BusinessException;
	
	/**
	 * Update provider related info
	 * @param provider
	 * @throws BusinessException
	 */
	void updateInfos(Provider provider) throws BusinessException;
	
	/**
	 * Update provider related info
	 * @param provider
	 * @throws BusinessException
	 */
	void updateSocialMedia(Provider provider) throws BusinessException;
	
	/**
	 * 
	 * @param weddingService
	 * @return
	 */
	Provider update(Provider provider);
	/**
	 * 
	 * @param id
	 * @return
	 */
	Provider findOne(Integer id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Provider getProvider(Integer id);
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Provider> findAll(Pageable pageable) ;
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Provider> findByEnabled(boolean enabled, Pageable pageable) ;
	
	
	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Provider> search(ProviderSearch providerSearch, Pageable pageable) ;
	
	void delete(Integer id);

	/**
	 * 
	 * @return
	 */
	List<Provider> getFeaturedProviders();
	
	/**
	 * 
	 * @param idProvider
	 * @param idPhoto
	 * @return 
	 */
	Provider deletePhoto(Integer idProvider, Integer  idPhoto);
	
	/**
	 * 
	 * @param idProvider
	 * @param idPhoto
	 * @return 
	 */
	Provider deletePhoto(Integer idProvider, Photo  photo);
	
	/**
	 * Create an provider account
	 * @param signupForm
	 * @return
	 * @throws BusinessException 
	 */
	UserAccount createAccount(SignupForm signupForm) throws BusinessException;

	/**
	 * Return a provider by his username
	 * @param name
	 * @return
	 */
	Provider findProviderByUsername(String name);

	


}
