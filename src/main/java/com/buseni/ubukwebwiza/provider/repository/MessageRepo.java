package com.buseni.ubukwebwiza.provider.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.provider.domain.Message;

public interface MessageRepo extends JpaRepository<Message, Integer>{ 
	
	Page<Message>  findByProvider_id(Integer idProvider, Pageable pageable);

}
