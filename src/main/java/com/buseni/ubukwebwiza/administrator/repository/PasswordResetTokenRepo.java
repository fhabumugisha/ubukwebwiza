package com.buseni.ubukwebwiza.administrator.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buseni.ubukwebwiza.administrator.domain.PasswordResetToken;

public interface PasswordResetTokenRepo  extends JpaRepository<PasswordResetToken, Integer>{

	PasswordResetToken findByToken(String token);

}
