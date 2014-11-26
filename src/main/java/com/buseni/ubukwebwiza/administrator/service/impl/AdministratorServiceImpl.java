package com.buseni.ubukwebwiza.administrator.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.administrator.domain.AdminRole;
import com.buseni.ubukwebwiza.administrator.domain.Administrator;
import com.buseni.ubukwebwiza.administrator.repository.AdministratorRepo;
import com.buseni.ubukwebwiza.administrator.service.AdministratorService;

@Service
@Transactional(readOnly = true)
public class AdministratorServiceImpl implements AdministratorService
		{

	private AdministratorRepo administratorRepo;

	@Autowired
	public AdministratorServiceImpl(AdministratorRepo administratorRepo) {
		this.administratorRepo = administratorRepo;
	}

	public AdministratorServiceImpl() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.buseni.ubukwebwiza.administrator.service.AdministratorService#create
	 * (com.buseni.ubukwebwiza.administrator.domain.Administrator)
	 */
	@Override
	@Transactional
	public void create(Administrator administrator) {
		// Control before saving
		administratorRepo.save(administrator);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.buseni.ubukwebwiza.administrator.service.AdministratorService#update
	 * (com.buseni.ubukwebwiza.administrator.domain.Administrator)
	 */
	@Override
	@Transactional
	public Administrator update(Administrator administrator) {
		// TODO Control
		return administratorRepo.save(administrator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.buseni.ubukwebwiza.administrator.service.AdministratorService#findById
	 * (java.lang.Integer)
	 */
	@Override
	public Administrator findById(Integer id) {
		if (null == id) {
			return null;
		}
		return administratorRepo.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.buseni.ubukwebwiza.administrator.service.AdministratorService#findAll
	 * (org.springframework.data.domain.Pageable)
	 */
	@Override
	public Page<Administrator> findAll(Pageable pageable) {
		return administratorRepo.findAll(pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.buseni.ubukwebwiza.administrator.service.AdministratorService#delete
	 * (java.lang.Integer)
	 */
	@Override
	@Transactional
	public void delete(Integer id) {
		if (null != id) {
			administratorRepo.delete(id);
		}

	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Administrator admin = administratorRepo.findByUsername(username);
		List<GrantedAuthority> authorities = buildAdminAuthority(admin.getRoles());

		return buildAdminForAuthentication(admin, authorities);
	}

	private List<GrantedAuthority> buildAdminAuthority(Set<AdminRole> roles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build admin's authorities
		for (AdminRole adminRole : roles) {
			setAuths.add(new SimpleGrantedAuthority(adminRole.getRole()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(
				setAuths);

		return Result;
	}

	// Converts admin to org.springframework.security.core.userdetails.User
	private User buildAdminForAuthentication(Administrator admin,
			List<GrantedAuthority> authorities) {
		return new User(admin.getUsername(), admin.getPassword(),
				admin.isEnabled(), true, true, true, authorities);
	}

}
