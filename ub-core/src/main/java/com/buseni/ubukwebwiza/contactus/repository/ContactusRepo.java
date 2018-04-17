package com.buseni.ubukwebwiza.contactus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.contactus.domain.ContactusForm;

public interface ContactusRepo extends JpaRepository<ContactusForm, Integer>{
	
	List<ContactusForm> findByReaded(boolean readed);

}
