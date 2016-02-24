package com.buseni.ubukwebwiza.provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.provider.domain.Message;

public interface MessageRepo extends JpaRepository<Message, Integer>{ 

}
