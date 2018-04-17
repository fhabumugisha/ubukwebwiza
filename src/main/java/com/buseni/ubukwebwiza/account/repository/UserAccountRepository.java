package com.buseni.ubukwebwiza.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.account.domain.UserAccount;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

  // All methods in this repository will be available in the UserRepository,
  // in the PersonRepository and in the CompanyRepository. 
	

  UserAccount findByEmail(String email);
  
 
 
}