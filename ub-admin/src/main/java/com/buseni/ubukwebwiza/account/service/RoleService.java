package com.buseni.ubukwebwiza.account.service;

import java.util.List;

import com.buseni.ubukwebwiza.account.domain.Role;

public interface RoleService {

	List<Role> findAll();
	
	List<Role> findAdminRoles();
}
