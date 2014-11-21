package com.buseni.ubukwebwiza.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends  WebSecurityConfigurerAdapter {

	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	  auth.inMemoryAuthentication().withUser("superadmin@ubukwebwiza.com").password("123456").roles("ADMIN","VENDOR","SETTINGS","USER");
	  auth.inMemoryAuthentication().withUser("admin@ubukwebwiza.com").password("123456").roles("ADMIN","VENDOR");
	
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
 
	  http.authorizeRequests()
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.and().formLogin().loginPage("/login")
        .permitAll()
        .and()
        .logout()
        .permitAll();
 
	}
}
