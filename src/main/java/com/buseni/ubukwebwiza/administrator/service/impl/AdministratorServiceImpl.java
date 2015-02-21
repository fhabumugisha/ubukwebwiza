package com.buseni.ubukwebwiza.administrator.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
		if(administrator.getId() == null){
			administrator.setCreatedAt(new Date());
			administrator.setLastUpdate(new Date());
			
				for(String roleName : administrator.getListRoles()){
					AdminRole role = new AdminRole();
					role.setAdmin(administrator);
					role.setRole(roleName);
				//	role.setId(idRole);
					administrator.getRoles().add(role);
					
					
				}
			
			
				PasswordEncoder encoder = new BCryptPasswordEncoder();
				administrator.setPassword(encoder.encode(administrator.getPassword()));
			
			
			administratorRepo.save(administrator);
		
		}else{
			Administrator adminDb = administratorRepo.findOne(administrator.getId());
			adminDb.setLastUpdate(new Date());
			adminDb.setEmail(administrator.getEmail());
			adminDb.setEnabled(administrator.isEnabled());
			adminDb.setLastName(administrator.getLastName());
			adminDb.setFirstName(administrator.getFirstName());
			for(String roleName : administrator.getListRoles()){
				AdminRole role = new AdminRole();
				role.setAdmin(administrator);
				role.setRole(roleName);
			if(!containsRole(adminDb, roleName)){
				adminDb.getRoles().add(role);
			}
				
				
				
			}
			 administratorRepo.save(adminDb);
		}
		
		
		

	}
	
	private boolean containsRole(Administrator admin, String role){
		for(AdminRole adminRole : admin.getRoles()){
			if(adminRole.getRole().equals(role)){
				return true;
			}
		}
		return false;
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
		administrator.setLastUpdate(new Date());
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
		Administrator admin   = administratorRepo.findOne(id);
		if(admin  == null){
			return null;
		}
		List<String> listRoles =  new ArrayList<String>();
		for(AdminRole role : admin.getRoles()){
			listRoles.add(role.getRole());
		}
		
		admin.setListRoles(listRoles);
		return admin;
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
		PageRequest pr = new PageRequest(pageable.getPageNumber()-1, pageable.getPageSize());
		return administratorRepo.findAll(pr);
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
		Administrator admin = administratorRepo.findByEmail(username);
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
		return new User(admin.getEmail(), admin.getPassword(),
				admin.isEnabled(), true, true, true, authorities);
	}

}
