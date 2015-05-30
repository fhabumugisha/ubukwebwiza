package com.buseni.ubukwebwiza.administrator.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.buseni.ubukwebwiza.administrator.domain.Administrator;
import com.buseni.ubukwebwiza.administrator.domain.PasswordResetToken;
import com.buseni.ubukwebwiza.exceptions.ServiceLayerException;

public interface AdministratorService extends UserDetailsService {

	/**
	 * 
	 * @param weddingService
	 */
	void create(Administrator administrator) throws ServiceLayerException;

	/**
	 * 
	 * @param weddingService
	 * @return
	 */
	Administrator update(Administrator administrator);
	/**
	 * 
	 * @param id
	 * @return
	 */
	Administrator findById(Integer id);


	/**
	 * 
	 * @param pageable
	 * @return
	 */
	Page<Administrator> findAll(Pageable pageable) ;

	void delete(Integer id);

	Administrator findByEmail(String email);

	void createPasswordResetTokenForAdministrator(Administrator uadministrator, String token);


	PasswordResetToken getPasswordResetToken(String token);

	Administrator getAdministratorByPasswordResetToken(String token);

	void changeAdministratorPassword(Administrator admin, String password);
	
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
