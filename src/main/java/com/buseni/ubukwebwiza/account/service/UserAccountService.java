package com.buseni.ubukwebwiza.account.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.buseni.ubukwebwiza.account.domain.PasswordResetToken;
import com.buseni.ubukwebwiza.account.domain.UserAccount;
import com.buseni.ubukwebwiza.account.domain.VerificationToken;
import com.buseni.ubukwebwiza.exceptions.BusinessException;


public interface UserAccountService extends UserDetailsService {


	UserAccount create(UserAccount account) throws BusinessException;


	UserAccount update(UserAccount account) ;


	void createPasswordResetTokenForUser(final UserAccount account, final String token) ;   


	PasswordResetToken getPasswordResetToken(final String token) ;


	UserAccount getUserByPasswordResetToken(final String token) ;


	void changeUserPassword(final UserAccount user, final String password) ;

	UserAccount getUserByVerificationToken(final String verificationToken) ;


	VerificationToken getVerificationToken(final String VerificationToken);


	void createVerificationTokenForUser(final UserAccount user, final String token);


	VerificationToken generateNewVerificationToken(final String existingVerificationToken) ;

	UserAccount findByEmail(String userEmail) ;
}
