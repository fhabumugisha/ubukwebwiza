package com.buseni.ubukwebwiza.provider.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.account.beans.VerificationToken;
import com.buseni.ubukwebwiza.provider.domain.Provider;

public interface VerificationTokenRepo extends JpaRepository<VerificationToken, Integer>{

	VerificationToken findByToken(String verificationToken);

	VerificationToken findByProvider(Provider provider);

}
