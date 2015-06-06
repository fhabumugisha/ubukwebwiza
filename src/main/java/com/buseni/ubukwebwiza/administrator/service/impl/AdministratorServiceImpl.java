package com.buseni.ubukwebwiza.administrator.service.impl;


import java.util.Date;

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
		/*if(CollectionUtils.isEmpty(administrator.getListRoles())){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.administrator.missingroles");			
			CustomError  ce = ceb.field("listRoles").buid();
			throw new BusinessException(ce);
		}*/
		//TODO edit roles
		if (administrator.getId() == null) {
			//administrator.setCreatedAt(new Date());
			//administrator.setLastUpdate(new Date());
			/*for (String roleName : administrator.getListRoles()) {
				AdminRole role = new AdminRole();
				role.setAdmin(administrator);
				role.setRole(roleName);
				// role.setId(idRole);
				administrator.getRoles().add(role);

			}*/
			//PasswordEncoder encoder = new BCryptPasswordEncoder();
			//administrator.setPassword(encoder.encode(administrator.getPassword()));

			administratorRepo.save(administrator);

		} else {
			Administrator adminDb = administratorRepo.findOne(administrator
					.getId());
			/*adminDb.setLastUpdate(new Date());
			adminDb.setEmail(administrator.getEmail());
			adminDb.setEnabled(administrator.isEnabled());*/
			adminDb.setLastName(administrator.getLastName());
			adminDb.setFirstName(administrator.getFirstName());
			//Changes in roles
		/*	if (adminDb.getRoles().size() != administrator.getListRoles()
					.size()) {
				//Add new roles
				if(administrator.getListRoles().size() > adminDb.getRoles().size()){
					for (String roleName : administrator.getListRoles()) {
						AdminRole role = new AdminRole();
						role.setAdmin(administrator);
						role.setRole(roleName);
						// add new role
						AdminRole adminRole = containsRole(adminDb, roleName);
						if (adminRole == null) {
							adminDb.getRoles().add(role);
						}

					}
				//remove of roles
				}else if(administrator.getListRoles().size() < adminDb.getRoles().size()){
					for (AdminRole adminRole : adminDb.getRoles()) {
						for (String roleName : administrator.getListRoles()) {
							if(!adminRole.getRole().equals(roleName)){
								adminDb.getRoles().remove(adminRole);
								adminRoleRepo.delete(adminRole);
							}
						}
						
					}
				}
				
			}*/
			administratorRepo.save(adminDb);
		}

	}
/*	
	private AdminRole containsRole(Administrator admin, String role){
		for(AdminRole adminRole : admin.getRoles()){
			if(adminRole.getRole().equals(role)){
				return adminRole;
			}
		}
		return null;
	}*/

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
		Administrator admin   = administratorRepo.findOne(id);
		if(admin  == null){
			throw new NullPointerException();
		}
		/*List<String> listRoles =  new ArrayList<String>();
		for(AdminRole role : admin.getRoles()){
			listRoles.add(role.getRole());
		}
		
		admin.setListRoles(listRoles);*/
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
	//@PreAuthorize(value = "hasRole('SUPER_ADMIN')")
	public void delete(Integer id) {
		if (null != id) {
			administratorRepo.delete(id);
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
	

}
