package com.buseni.ubukwebwiza.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.account.domain.UserAccount;
import com.buseni.ubukwebwiza.account.domain.VerificationToken;

public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Integer>{

	VerificationToken findByToken(String verificationToken);

	VerificationToken findByUserAccount(UserAccount userAccount);

}
