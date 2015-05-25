package com.buseni.ubukwebwiza.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.buseni.ubukwebwiza.administrator.service.AdministratorService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends  WebSecurityConfigurerAdapter {

	
	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	  /*auth.inMemoryAuthentication().withUser("superadmin@ubukwebwiza.com").password("123456").roles("ADMIN","VENDOR","SETTINGS","USER");
	  auth.inMemoryAuthentication().withUser("admin@ubukwebwiza.com").password("123456").roles("ADMIN","VENDOR");*/
		auth.userDetailsService(administratorService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
 
	  http.authorizeRequests()
		.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
		.and().formLogin().loginPage("/adminlogin").usernameParameter("username")
		.passwordParameter("password").successHandler(mySimpleUrlAuthenticationSuccessHandler())
        .and()
        .logout().logoutSuccessUrl("/admin").and().exceptionHandling().accessDeniedPage("/admin403");
        
 
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}
	
	@Bean 
	public AuthenticationSuccessHandler  mySimpleUrlAuthenticationSuccessHandler(){
		AuthenticationSuccessHandler mySimpleUrlAuthenticationSuccessHandler =  new MySimpleUrlAuthenticationSuccessHandler();
		return mySimpleUrlAuthenticationSuccessHandler;
	}
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}
