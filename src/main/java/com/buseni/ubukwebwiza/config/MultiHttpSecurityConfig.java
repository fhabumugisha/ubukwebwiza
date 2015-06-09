package com.buseni.ubukwebwiza.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.buseni.ubukwebwiza.account.service.UserAccountService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class MultiHttpSecurityConfig {
	
	
	@Autowired
	private UserAccountService userAccountService;
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
		auth.userDetailsService(userAccountService).passwordEncoder(passwordEncoder());
	}

	@Configuration
	@Order(1)                                                        
	public static class adminWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Autowired
		private  DataSource dataSource;
		protected void configure(HttpSecurity http) throws Exception {
			http
			.antMatcher("/admin/**")
			.authorizeRequests().anyRequest().hasRole("ADMIN")			
			.and()
				.formLogin().loginPage("/adminlogin").failureUrl("/adminlogin?error").usernameParameter("email")
					.passwordParameter("password").successHandler(savedRequestAwareAuthenticationSuccessHandler())
	        .and()
	        	.logout().logoutSuccessUrl("/adminlogin?logout").deleteCookies("JSESSIONID")
	        .and()
	        	.exceptionHandling().accessDeniedPage("/admin403")
	        .and()
	        	.rememberMe().tokenRepository(persistentTokenRepository()).tokenValiditySeconds(604800);
		}
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/**");
		}
		
		@Bean
		public  SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() { 
	               SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
			auth.setTargetUrlParameter("targetUrl");
			auth.setDefaultTargetUrl("/admin");
			auth.setUseReferer(true);
			return auth;
		}
		
		@Bean
		public  PersistentTokenRepository persistentTokenRepository() {
			JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
			db.setDataSource(dataSource);
			return db;
		}
	}

	@Configuration                                                   
	public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.antMatcher("/profile/**")
				.authorizeRequests().anyRequest().hasRole("PROVIDER")
				.and()
				.formLogin().loginPage("/login").usernameParameter("email")
				.passwordParameter("password").failureUrl("/login?error").successHandler(savedRequestAwareAuthenticationSuccessHandler())
				.and()
	        	.exceptionHandling().accessDeniedPage("/admin403")
	        	.and()
	        	.logout().logoutSuccessUrl("/").deleteCookies("JSESSIONID");
		        
		}
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/**");
		}
		@Bean
		public  SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() { 
	               SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
			auth.setTargetUrlParameter("targetUrl");
			auth.setDefaultTargetUrl("/");
			auth.setUseReferer(true);
			return auth;
		}
		
	}
	
	
	
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
}