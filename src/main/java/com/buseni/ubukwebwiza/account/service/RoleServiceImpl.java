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

}
