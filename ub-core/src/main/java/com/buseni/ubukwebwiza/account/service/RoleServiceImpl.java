/**
 * 
 */
package com.buseni.ubukwebwiza.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.account.domain.Role;
import com.buseni.ubukwebwiza.account.repository.RoleRepository;

/**
 * @author fahabumu
 *
 */
@Service
@Transactional(readOnly=true)
public class RoleServiceImpl implements RoleService {
	
	private RoleRepository roleRepository;
	private static final String ROLE_PROVIDER = "ROLE_PROVIDER";
	
	
@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}



	/* (non-Javadoc)
	 * @see com.buseni.ubukwebwiza.account.service.RoleService#findAll()
	 */
	@Override
	public List<Role> findAll() {	
		return roleRepository.findAll();
	}



	@Override
	public List<Role> findAdminRoles() {
		List<Role> roles =  roleRepository.findAll();
		roles.remove(roleRepository.findByName(ROLE_PROVIDER));
		return roles;
	}

}
