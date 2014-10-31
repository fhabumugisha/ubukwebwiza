package com.buseni.ubukwebwiza.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.core.domain.Administrator;
import com.buseni.ubukwebwiza.core.repository.AdministratorRepo;
import com.buseni.ubukwebwiza.core.service.AdministratorService;

@Service
@Transactional(readOnly=true)
public class AdministratorServiceImpl implements AdministratorService {
	
	private AdministratorRepo  administratorRepo;
	
	
	@Autowired
	public AdministratorServiceImpl(AdministratorRepo administratorRepo){
		this.administratorRepo = administratorRepo;
	}

	public AdministratorServiceImpl(){
		
	}
	/*
	 * (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.core.service.AdministratorService#create(com.buseni.ubukwebwiza.core.domain.Administrator)
	 */
	@Transactional
	public void create(Administrator administrator) {
		//Control before saving
		administratorRepo.save(administrator);

	}

	/*
	 * (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.core.service.AdministratorService#update(com.buseni.ubukwebwiza.core.domain.Administrator)
	 */
	@Transactional
	public Administrator update(Administrator administrator) {
		// TODO Control
		return administratorRepo.save(administrator);
	}

	/*
	 * (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.core.service.AdministratorService#findById(java.lang.Integer)
	 */
	public Administrator findById(Integer id) {
		if( null == id){
			return null;
		}
		return administratorRepo.findOne(id);
	}
/*
 * (non-Javadoc)
 * @see com.buseni.ubukwebwiza.core.service.AdministratorService#findAll(org.springframework.data.domain.Pageable)
 */
	public Page<Administrator> findAll(Pageable pageable) {
		return administratorRepo.findAll(pageable);
	}

	/*
	 * (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.core.service.AdministratorService#delete(java.lang.Integer)
	 */
	@Transactional
	public void delete(Integer id) {
		if(null != id){
			administratorRepo.delete(id);
		}
		
	}

}
