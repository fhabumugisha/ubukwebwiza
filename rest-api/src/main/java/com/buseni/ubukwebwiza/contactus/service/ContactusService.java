package com.buseni.ubukwebwiza.contactus.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.contactus.domain.ContactusForm;
import com.buseni.ubukwebwiza.exceptions.BusinessException;

public interface ContactusService {
	/**
	 * add a new message
	 * @param contactusForm
	 */
	void add(ContactusForm contactusForm) throws BusinessException;
	
	/**
	 * List all message
	 * @return
	 */
	List<ContactusForm> findAll();
	
	/**
	 * List all unread messages
	 * @return
	 */
	List<ContactusForm> findUnread();
	
	/**
	 * List all messages paged
	 * @param pageable
	 * @return
	 */
	Page<ContactusForm> findAll(Pageable pageable) ;
	
	/**
	 * delete a message
	 * @param id
	 */
	void delete(Integer id);
	
	/**
	 * Update the message
	 * @param contactForm
	 */
	void update(ContactusForm contactForm) throws BusinessException;
	
	/**
	 * Return a message
	 * @param id
	 * @return
	 */
	public ContactusForm findOne(Integer id) ;

}
