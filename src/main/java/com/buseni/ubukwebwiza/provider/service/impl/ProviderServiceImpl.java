/**
 * 
 */
package com.buseni.ubukwebwiza.provider.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.buseni.ubukwebwiza.exceptions.ResourceNotFoundException;
import com.buseni.ubukwebwiza.gallery.domain.Photo;
import com.buseni.ubukwebwiza.gallery.repository.PhotoRepo;
import com.buseni.ubukwebwiza.provider.beans.ProviderSearch;
import com.buseni.ubukwebwiza.provider.domain.Provider;
import com.buseni.ubukwebwiza.provider.domain.ProviderWeddingService;
import com.buseni.ubukwebwiza.provider.domain.WeddingService;
import com.buseni.ubukwebwiza.provider.predicates.ProviderPredicates;
import com.buseni.ubukwebwiza.provider.repository.ProviderRepo;
import com.buseni.ubukwebwiza.provider.repository.ProviderWeddingServiceRepo;
import com.buseni.ubukwebwiza.provider.repository.WeddingServiceRepo;
import com.buseni.ubukwebwiza.provider.service.ProviderService;

/**
 * @author habumugisha
 *
 */
@Service
@Transactional(readOnly=true)
public class ProviderServiceImpl implements ProviderService {
	
	private ProviderRepo providerRepo;
	private WeddingServiceRepo weddingServiceRepo;
	private ProviderWeddingServiceRepo providerWeddingServiceRepo;
	private PhotoRepo photoRepo;
	@Autowired
	public ProviderServiceImpl(ProviderRepo providerRepo, WeddingServiceRepo weddingServiceRepo, ProviderWeddingServiceRepo providerWeddingServiceRepo, PhotoRepo photoRepo){
		this.providerRepo = providerRepo;
		this.weddingServiceRepo = weddingServiceRepo;
		this.providerWeddingServiceRepo = providerWeddingServiceRepo;
		this.photoRepo = photoRepo;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.ProviderService#create(com.buseni.ubukwebwiza.administrator.domain.Provider)
	 */
	@Override
	@Transactional
	public void add(Provider provider) {
		// TODO control before save
		if(null == provider){
			throw new NullPointerException();
		}
		//Creation
		if(provider.getId() == null){
			provider.setCreatedAt(new Date());			
			if(provider.getIdcService() != null){
				WeddingService weddingService = weddingServiceRepo.findOne(provider.getIdcService());
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
			Provider bdd =  providerRepo.findOne(provider.getId());
			if(provider.getProfilPicture() == null){				
				provider.setProfilPicture(bdd.getProfilPicture());
			}
			provider.setCreatedAt(bdd.getCreatedAt());
			provider.setNbViews(bdd.getNbViews());
		}
	
		provider.setLastUpdate(new Date());
		providerRepo.save(provider);

	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.ProviderService#update(com.buseni.ubukwebwiza.administrator.domain.Provider)
	 */
	@Override
	@Transactional
	public Provider update(Provider provider) {
		// TODO COntrol before save
		provider.setLastUpdate(new Date());
		return providerRepo.save(provider);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.ProviderService#findById(java.lang.Integer)
	 */
	@Override
	public Provider getProvider(Integer id) {
		if(null == id){
			throw new NullPointerException();
		}
		
		Provider provider  = providerRepo.findOne(id);
		if(provider ==  null){
			throw new ResourceNotFoundException();
		}
		provider.setNbViews(provider.getNbViews() + 1);
		provider.setLastUpdated(new Date());
		providerRepo.save(provider);
		return provider;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.ProviderService#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Provider> findAll(Pageable pageable) {
		PageRequest pr = new PageRequest(pageable.getPageNumber()-1, pageable.getPageSize());
		return providerRepo.findAll(pr);
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.ProviderService#findAll(com.buseni.ubukwebwiza.utils.ProviderSearch, org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Provider> search(ProviderSearch providerSearch,
			Pageable pageable) {

		if(null != providerSearch){
			PageRequest pr = new PageRequest(pageable.getPageNumber()-1, pageable.getPageSize());
			return	providerRepo.findAll(ProviderPredicates.search(providerSearch), pr);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.administrator.service.ProviderService#delete(java.lang.Integer)
	 */
	@Override
	@Transactional
	public void delete(Integer id) {
		if(null != id){
			providerRepo.delete(id);
		}
		
	}

	@Override
	public List<Provider> getFeaturedProviders() {	
		Page<Provider> page = providerRepo.findByEnabled(Boolean.TRUE, new PageRequest(0, 3, Sort.Direction.DESC, "nbViews"));
		return page.getContent();
	}

	@Override
	public Page<Provider> findByEnabled(boolean enabled, Pageable pageable) {
		PageRequest pr = new PageRequest(pageable.getPageNumber()-1, pageable.getPageSize());
		return providerRepo.findByEnabled(enabled, pr);
	}

	@Override
	public Provider findOne(Integer id) {
		if(null == id){
			throw new NullPointerException();
		}		
		Provider provider  = providerRepo.findOne(id);
		if(provider ==  null){
			throw new ResourceNotFoundException();
		}			
		return provider;
	}

	@Transactional
	@Override
	public Provider deletePhoto(Integer idProvider, Integer  idPhoto) {
		Provider provider = findOne(idProvider);
		Photo photo =  photoRepo.findOne(idPhoto);
		if(photo != null && !CollectionUtils.isEmpty(provider.getPhotos())){
			provider.getPhotos().remove(photo);
			providerRepo.save(provider);
			photoRepo.delete(photo);
		}
		return provider;
	}

	@Transactional
	@Override
	public Provider deletePhoto(Integer idProvider, Photo photo) {
		Provider provider = findOne(idProvider);
		if(photo != null && !CollectionUtils.isEmpty(provider.getPhotos())){
			provider.getPhotos().remove(photo);
			providerRepo.save(provider);
			photoRepo.delete(photo);
		}
		return provider;
	}



}
