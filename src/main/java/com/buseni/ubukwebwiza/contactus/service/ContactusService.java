package com.buseni.ubukwebwiza.contactus.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.buseni.ubukwebwiza.contactus.ContactusForm;

public interface ContactusService {
	
	void add(ContactusForm contactusForm);
	
	List<ContactusForm> findAll();
	
	Page<ContactusForm> findAll(Pageable pageable) ;

}
