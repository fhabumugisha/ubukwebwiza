package com.buseni.ubukwebwiza.contactus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.contactus.ContactusForm;

public interface ContactusRepo extends JpaRepository<ContactusForm, Integer>{

}
