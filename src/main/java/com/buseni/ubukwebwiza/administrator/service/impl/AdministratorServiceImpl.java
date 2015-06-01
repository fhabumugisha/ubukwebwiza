package com.buseni.ubukwebwiza.administrator.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.util.CollectionUtils;

import com.buseni.ubukwebwiza.administrator.domain.AdminRole;
import com.buseni.ubukwebwiza.administrator.domain.Administrator;
import com.buseni.ubukwebwiza.administrator.domain.PasswordResetToken;
import com.buseni.ubukwebwiza.administrator.repository.AdminRoleRepo;
import com.buseni.ubukwebwiza.administrator.repository.AdministratorRepo;
import com.buseni.ubukwebwiza.administrator.repository.PasswordResetTokenRepo;
import com.buseni.ubukwebwiza.administrator.service.AdministratorService;
import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.exceptions.CustomError;
import com.buseni.ubukwebwiza.exceptions.CustomErrorBuilder;

@Service
@Transactional(readOnly = true)
public class AdministratorServiceImpl implements AdministratorService
		{

	private AdministratorRepo administratorRepo;
	private AdminRoleRepo adminRoleRepo;
	
	private PasswordResetTokenRepo passwordTokenRepo;
	
	@Autowired
    private LoginAttemptService loginAttemptService;
 
    @Autowired
    private HttpServletRequest request;

	@Autowired
	public AdministratorServiceImpl(AdministratorRepo administratorRepo, AdminRoleRepo adminRoleRepo,
			PasswordResetTokenRepo passwordTokenRepository) {
		this.administratorRepo = administratorRepo;
		this.adminRoleRepo = adminRoleRepo;
		this.passwordTokenRepo = passwordTokenRepository;
	
		
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
		if(CollectionUtils.isEmpty(administrator.getListRoles())){
			CustomErrorBuilder ceb =  new CustomErrorBuilder("error.administrator.missingroles");			
			CustomError  ce = ceb.field("listRoles").buid();
			throw new BusinessException(ce);
		}
		//TODO edit roles
		if (administrator.getId() == null) {
			administrator.setCreatedAt(new Date());
			administrator.setLastUpdate(new Date());
			for (String roleName : administrator.getListRoles()) {
				AdminRole role = new AdminRole();
				role.setAdmin(administrator);
				role.setRole(roleName);
				// role.setId(idRole);
				administrator.getRoles().add(role);

			}
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			administrator.setPassword(encoder.encode(administrator.getPassword()));

			administratorRepo.save(administrator);

		} else {
			Administrator adminDb = administratorRepo.findOne(administrator
					.getId());
			adminDb.setLastUpdate(new Date());
			adminDb.setEmail(administrator.getEmail());
			adminDb.setEnabled(administrator.isEnabled());
			adminDb.setLastName(administrator.getLastName());
			adminDb.setFirstName(administrator.getFirstName());
			//Changes in roles
			if (adminDb.getRoles().size() != administrator.getListRoles()
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
				
			}
			administratorRepo.save(adminDb);
		}

	}
	
	private AdminRole containsRole(Administrator admin, String role){
		for(AdminRole adminRole : admin.getRoles()){
			if(adminRole.getRole().equals(role)){
				return adminRole;
			}
		}
		return null;
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
			throw new NullPointerException();
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
	//@PreAuthorize(value = "hasRole('SUPER_ADMIN')")
	public void delete(Integer id) {
		if (null != id) {
			administratorRepo.delete(id);
		}

	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		String ip = request.getRemoteAddr();
        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("blocked");
        }
		Administrator admin = administratorRepo.findByEmail(username);
		if (admin == null) {
            throw new UsernameNotFoundException("No user found with username: "+ username);
        }
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

	@Override
	public Administrator findByEmail(String email) {
		if(email == null){
			throw new NullPointerException("Null email");
		}
		return  administratorRepo.findByEmail(email);
	}
	
	
	@Override
	@Transactional
    public void createPasswordResetTokenForAdministrator(final Administrator administrator, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, administrator);
        passwordTokenRepo.save(myToken);
    }

   

    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordTokenRepo.findByToken(token);
    }

    @Override
    public Administrator getAdministratorByPasswordResetToken(final String token) {
        return passwordTokenRepo.findByToken(token).getAdministrator();
    }
    
    @Override
    @Transactional
    public void changeAdministratorPassword(final Administrator admin, final String password) {
    	if(password== null || admin == null){
    		throw new NullPointerException("Null password");
    	}
    	PasswordEncoder encoder = new BCryptPasswordEncoder();
        admin.setPassword(encoder.encode(password));
        administratorRepo.save(admin);
    }

}
