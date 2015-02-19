/**
 * 
 */
package com.buseni.ubukwebwiza.contactus.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.contactus.ContactusForm;
import com.buseni.ubukwebwiza.contactus.repository.ContactusRepo;

/**
 * @author fahabumu
 *
 */
@Service("contactusService")
@Transactional(readOnly=true)
public class ContactusServiceImpl implements ContactusService {
	
	@Autowired
	private ContactusRepo contactusRepo;
	
	
	/*public ContactusServiceImpl(ContactusRepo contactusRepo) {
		this.contactusRepo = contactusRepo;
	}*/

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.contactus.service.ContactusService#add(com.buseni.ubukwebwiza.contactus.ContactusForm)
	 */
	@Override
	@Transactional
	public void add(ContactusForm contactusForm) {
		contactusForm.setCreatedAt(new Date());
		contactusRepo.save(contactusForm);

	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.contactus.service.ContactusService#findAll()
	 */
	@Override
	public List<ContactusForm> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.contactus.service.ContactusService#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<ContactusForm> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
