package com.buseni.ubukwebwiza.administrator.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.buseni.ubukwebwiza.account.domain.Role;
import com.buseni.ubukwebwiza.account.domain.UserAccount;
import com.buseni.ubukwebwiza.account.repository.RoleRepository;
import com.buseni.ubukwebwiza.account.repository.UserAccountRepository;
import com.buseni.ubukwebwiza.administrator.domain.Administrator;
import com.buseni.ubukwebwiza.administrator.domain.AdministratorDTO;
import com.buseni.ubukwebwiza.administrator.enums.EnumAccountType;
import com.buseni.ubukwebwiza.administrator.repository.AdministratorRepo;
import com.buseni.ubukwebwiza.administrator.service.AdministratorService;
import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.exceptions.CustomError;
import com.buseni.ubukwebwiza.exceptions.CustomErrorBuilder;

@Service
@Transactional(readOnly = true)
public class AdministratorServiceImpl implements AdministratorService		{

	private AdministratorRepo administratorRepo;	
	private RoleRepository roleRepository;
	private UserAccountRepository userAccountRepository;

	@Autowired
	public AdministratorServiceImpl(AdministratorRepo administratorRepo, RoleRepository roleRepository, UserAccountRepository userAccountRepository) {
		this.administratorRepo = administratorRepo;	
		this.roleRepository = roleRepository;
		this.userAccountRepository = userAccountRepository;
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
	public void create(Administrator administrator) throws BusinessException {
		// Control before saving
		if(administrator == null){
			throw new NullPointerException();
		}

		if (administrator.getId() == null) {
			administratorRepo.save(administrator);
		} else {
			Administrator adminDb = administratorRepo.findById(administrator.getId()).orElseThrow(()-> new NullPointerException(" shouldn't be null"));			
			adminDb.setLastName(administrator.getLastName());
			adminDb.setFirstName(administrator.getFirstName());		
			administratorRepo.save(adminDb);
		}

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
		if(administrator  == null){
			throw new NullPointerException();
		}
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
		Administrator admin   = administratorRepo.findById(id).orElse(null);
		if(admin  == null){
			throw new NullPointerException();
		}
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
		PageRequest pr = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
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
	//@PreAuthorize(value = "hasRole('SUPER_ADMIN')")
	public void delete(Integer id) {
		if (null != id) {
			administratorRepo.deleteById(id);
		}

	}



	@Override
	public Administrator findByEmail(String email) {
		if(email == null){
			throw new NullPointerException("Null email");
		}
		return  administratorRepo.findByAccount_Email(email);
	}

	@Override
	@Transactional
	public Administrator  create(AdministratorDTO administratorDTO) throws BusinessException {
		// Control before saving
		if(administratorDTO == null){
			throw new NullPointerException();
		}
		if(StringUtils.isEmpty(administratorDTO.getPassword())){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.user.requiredfield.password");			
			CustomError  ce = ceb.field("password").buid();
			throw new BusinessException(ce);			
		}
		if (emailExist(administratorDTO.getEmail())) {  
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.account.emailexists");			
			CustomError  ce = ceb.field("email").errorArgs(new String[]{administratorDTO.getEmail()}).buid();
			throw new BusinessException(ce);

		}
		if(CollectionUtils.isEmpty(administratorDTO.getListRoles())){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.administrator.missingroles");			
			CustomError  ce = ceb.field("listRoles").buid();
			throw new BusinessException(ce);
		}
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		UserAccount account  = new UserAccount();
		account.setPassword(encoder.encode(administratorDTO.getPassword()));
		account.setEmail(administratorDTO.getEmail());
		account.setEnabled(administratorDTO.getEnabled());		
		account.setCreatedAt(new Date());
		account.setLastUpdate(new Date());
		account.setType(EnumAccountType.ADMIN.name());
		for (String roleName : administratorDTO.getListRoles()) {
			Role role  =  roleRepository.findByName(roleName);
			if(role != null){
				account.getRoles().add(role);
			}
		}
		Administrator administrator = new Administrator(administratorDTO.getFirstName(), administratorDTO.getLastName(), account);
		return	administratorRepo.save(administrator);

	}

	private boolean emailExist(String email) {
		UserAccount user = userAccountRepository.findByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public Administrator update(AdministratorDTO administratorDTO) throws BusinessException {
		if(administratorDTO == null){
			throw new NullPointerException();
		}
		if(CollectionUtils.isEmpty(administratorDTO.getListRoles())){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.administrator.missingroles");			
			CustomError  ce = ceb.field("listRoles").buid();
			throw new BusinessException(ce);
		}
		Administrator adminDb = administratorRepo.findById(administratorDTO.getId()).orElseThrow(()-> new NullPointerException(" shouldn't be null"));
		//The email is changed
		if(!adminDb.getAccount().getEmail().equals(administratorDTO.getEmail())){
			if (emailExist(administratorDTO.getEmail())) {  
				CustomErrorBuilder ceb =  new CustomErrorBuilder("error.account.emailexists");			
				CustomError  ce = ceb.field("email").errorArgs(new String[]{administratorDTO.getEmail()}).buid();
				throw new BusinessException(ce);
			}
			adminDb.getAccount().setEmail(administratorDTO.getEmail());
		}
		adminDb.getAccount().setLastUpdate(new Date());
		adminDb.getAccount().setEnabled(administratorDTO.getEnabled());
		adminDb.setLastName(administratorDTO.getLastName());
		adminDb.setFirstName(administratorDTO.getFirstName());
		//Changes in roles
		List<Role> adminRoles = adminDb.getAccount().getRoles();
		if (adminRoles.size() != administratorDTO.getListRoles().size()) {
			//Add new roles
			if(administratorDTO.getListRoles().size() > adminRoles.size()){
				for (String roleName : administratorDTO.getListRoles()) {					
					if (!containsRole(adminRoles, roleName)) {
						Role role = roleRepository.findByName(roleName);
						adminDb.getAccount().getRoles().add(role);
					}
				}
				//remove of roles
			}else if(administratorDTO.getListRoles().size() < adminRoles.size()){
				List<Role> rolesToRemove = new ArrayList<Role>();
				for (Role adminRole : adminRoles) {
					for (String roleName : administratorDTO.getListRoles()) {
						if(!adminRole.getName().equals(roleName)){
							rolesToRemove.add(adminRole);							
						}
					}
				}
				adminDb.getAccount().getRoles().removeAll(rolesToRemove);
			}
		}
		return administratorRepo.save(adminDb);
	}

	/*
	 * 
	 */
	private boolean containsRole(List<Role> roles, String roleName){
		for(Role role : roles){
			if(role.getName().equals(roleName)){
				return true;
			}
		}
		return false;
	}
}
