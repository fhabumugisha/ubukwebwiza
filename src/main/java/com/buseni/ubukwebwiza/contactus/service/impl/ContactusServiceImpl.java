/**
 * 
 */
package com.buseni.ubukwebwiza.contactus.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.contactus.domain.ContactusForm;
import com.buseni.ubukwebwiza.contactus.repository.ContactusRepo;
import com.buseni.ubukwebwiza.contactus.service.ContactusService;

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
		contactusForm.setReaded(false);
		contactusForm.setCreatedAt(new Date());
		contactusRepo.save(contactusForm);

	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.contactus.service.ContactusService#findAll()
	 */
	@Override
	public List<ContactusForm> findAll() {
		return contactusRepo.findAll();
	}

	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.contactus.service.ContactusService#findAll(org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<ContactusForm> findAll(Pageable pageable) {
		PageRequest pr = new PageRequest(pageable.getPageNumber()-1, pageable.getPageSize());
		return contactusRepo.findAll(pr);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		if(null != id){
			ContactusForm contactusForm =  contactusRepo.findOne(id);
			if(contactusForm != null){
				contactusRepo.delete(contactusForm);
			}
			
		}
		
	}

	@Override
	@Transactional
	public void update(ContactusForm contactForm) {
		if(contactForm != null){
			
			ContactusForm contactFormToUpdate  = contactusRepo.findOne(contactForm.getId());
			contactFormToUpdate.setLastUpdate(new Date());
			contactFormToUpdate.setReaded(Boolean.TRUE);
			contactusRepo.save(contactFormToUpdate);
		}
		
	}
	
	@Override
	public ContactusForm findOne(Integer id) {
		if(null != id){
			return contactusRepo.findOne(id);
		}
		return null;
	}

	@Override
	public List<ContactusForm> findUnread() {
		return contactusRepo.findByReaded(Boolean.FALSE);
	}

}
