package com.buseni.ubukwebwiza.sandbox;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Sandbox {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println("123456 encode is " + encoder.encode("123456"));
		
	}

}
