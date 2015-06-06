package com.buseni.ubukwebwiza.account.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buseni.ubukwebwiza.account.domain.PasswordResetToken;
import com.buseni.ubukwebwiza.account.domain.Privilege;
import com.buseni.ubukwebwiza.account.domain.Role;
import com.buseni.ubukwebwiza.account.domain.UserAccount;
import com.buseni.ubukwebwiza.account.domain.VerificationToken;
import com.buseni.ubukwebwiza.account.repository.PasswordResetTokenRepo;
import com.buseni.ubukwebwiza.account.repository.UserAccountRepository;
import com.buseni.ubukwebwiza.account.repository.VerificationTokenRepo;
import com.buseni.ubukwebwiza.administrator.service.impl.LoginAttemptService;
import com.buseni.ubukwebwiza.exceptions.BusinessException;
import com.buseni.ubukwebwiza.exceptions.CustomError;
import com.buseni.ubukwebwiza.exceptions.CustomErrorBuilder;

@Service
@Transactional(readOnly=true)
public class UserAccountServiceImpl implements UserAccountService {

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private PasswordResetTokenRepo passwordTokenRepo;

	@Autowired
	private VerificationTokenRepo verificationTokenRepo;

	@Autowired
	private LoginAttemptService loginAttemptService;

	@Autowired
	private HttpServletRequest request;

	@Override
	@Transactional
	public UserAccount create(UserAccount account) throws BusinessException {
		if (account == null) {
			throw new NullPointerException("Account must not be null");
		}
		if (emailExist(account.getEmail())) {
			CustomErrorBuilder ceb = new CustomErrorBuilder(
					"error.account.emailexists");
			CustomError ce = ceb.field("email")
					.errorArgs(new String[] { account.getEmail() }).buid();
			throw new BusinessException(ce);

		}
		account.setCreatedAt(new Date());
		account.setLastUpdate(new Date());
		return userAccountRepository.save(account);

	}

	@Override
	@Transactional
	public UserAccount update(UserAccount account) {
		if (account == null) {
			throw new NullPointerException("Account must not be null");
		}
		account.setLastUpdate(new Date());
		return userAccountRepository.save(account);

	}

	private boolean emailExist(String email) {
		UserAccount user = userAccountRepository.findByEmail(email);
		if (user != null) {
			return true;
		}
		return false;
	}

	// API

	@Override
	public UserDetails loadUserByUsername(final String email)
			throws UsernameNotFoundException {
		/*String ip = request.getRemoteAddr();
		if (loginAttemptService.isBlocked(ip)) {
			throw new RuntimeException("blocked");
		}*/

		try {
			final UserAccount user = userAccountRepository.findByEmail(email);
			if (user == null) {
				throw new UsernameNotFoundException(
						"No user found with username: " + email);
			}

			return new User(user.getEmail(), user.getPassword(),
					user.isEnabled(), true, true, true,
					getAuthorities(user.getRoles()));
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	// UTIL

	public final Collection<? extends GrantedAuthority> getAuthorities(
			final Collection<Role> roles) {
		return getGrantedAuthorities(getPrivileges(roles));
	}

	private final List<String> getPrivileges(final Collection<Role> roles) {
		final List<String> privileges = new ArrayList<String>();
		//final List<Privilege> collection = new ArrayList<Privilege>();
		for (Role role : roles) {
			privileges.add(role.getName());
			for ( Privilege item : role.getPrivileges()) {
				privileges.add(item.getName());
			}
			//collection.addAll(role.getPrivileges());
		}
		/*for (final Privilege item : collection) {
			privileges.add(item.getName());
		}*/
		return privileges;
	}

	private final List<GrantedAuthority> getGrantedAuthorities(
			final List<String> privileges) {
		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (final String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}
	


	@Override
	@Transactional
	public void createPasswordResetTokenForUser(final UserAccount account,
			final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token,
				account);
		passwordTokenRepo.save(myToken);
	}

	@Override
	public PasswordResetToken getPasswordResetToken(final String token) {
		return passwordTokenRepo.findByToken(token);
	}

	@Override
	public UserAccount getUserByPasswordResetToken(final String token) {
		return passwordTokenRepo.findByToken(token).getAccount();
	}

	@Override
	@Transactional
	public void changeUserPassword(final UserAccount user, final String password) {
		if (password == null || user == null) {
			throw new NullPointerException("Null password");
		}
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(password));
		user.setLastUpdate(new Date());
		userAccountRepository.save(user);
	}

	/**
	 * 
	 * @param verificationToken
	 * @return
	 */
	@Override
	public UserAccount getUserByVerificationToken(final String verificationToken) {
		if (null == verificationToken) {
			throw new NullPointerException("verification token is null");
		}
		return verificationTokenRepo.findByToken(verificationToken)
				.getAccount();

	}

	@Override
	public VerificationToken getVerificationToken(final String VerificationToken) {
		if (null == VerificationToken) {
			throw new NullPointerException("verificationtoken is null");
		}
		return verificationTokenRepo.findByToken(VerificationToken);
	}

	@Override
	@Transactional
	public void createVerificationTokenForUser(final UserAccount user,
			final String token) {
		if (null == user || token == null) {
			throw new NullPointerException("UserAccount or toke are null");
		}
		final VerificationToken myToken = new VerificationToken(token, user);
		verificationTokenRepo.save(myToken);
	}

	@Override
	@Transactional
	public VerificationToken generateNewVerificationToken(	final String existingVerificationToken) {
		if (null == existingVerificationToken) {
			throw new NullPointerException("existingverificationtoken is null");
		}
		VerificationToken vToken = verificationTokenRepo
				.findByToken(existingVerificationToken);
		vToken.updateToken(UUID.randomUUID().toString());
		vToken = verificationTokenRepo.save(vToken);
		return vToken;
	}

	/**
	 * 
	 * @param userEmail
	 * @return
	 */
	@Override
	public UserAccount findByEmail(String userEmail) {
		if (userEmail == null) {
			throw new NullPointerException("Null password");
		}
		return userAccountRepository.findByEmail(userEmail);
	}
}
