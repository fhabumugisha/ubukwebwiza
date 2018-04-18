package com.buseni.ubukwebwiza.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.account.domain.PasswordResetToken;

public interface PasswordResetTokenRepo  extends JpaRepository<PasswordResetToken, Integer>{

	PasswordResetToken findByToken(String token);

}
