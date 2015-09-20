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
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.buseni.ubukwebwiza.account.service.UserAccountService;
import com.buseni.ubukwebwiza.filters.CustomAccessDeniedHandlerImpl;
import com.buseni.ubukwebwiza.filters.CustomSavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class MyMultiHttpSecurityConfig {
	

@Autowired
	private UserAccountService userAccountService;
	
	
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
		auth.userDetailsService(userAccountService).passwordEncoder(passwordEncoder());
	}

	@Configuration
	@Order (2)                                                   
	public static class adminWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
		@Autowired
		private  DataSource dataSource;
		protected void configure(HttpSecurity http) throws Exception {
			 http.antMatcher("/admin/**").authorizeRequests().anyRequest().hasRole("ADMIN")
			.and()
				.formLogin().loginPage("/admin/signin").permitAll().failureUrl("/admin/signin?error").usernameParameter("email")
					.passwordParameter("password").successHandler(savedRequestAwareAuthenticationSuccessHandler())
	        .and()
	        	.logout().logoutUrl("/admin/logout").logoutSuccessUrl("/admin/signin?logout").permitAll().deleteCookies("JSESSIONID")
	        .and()
	        	.exceptionHandling().accessDeniedPage("/admin403").accessDeniedHandler(customAccessDeniedHandler())
	        .and()
	        	.rememberMe().tokenRepository(persistentTokenRepository()).tokenValiditySeconds(604800)
	        	.and().csrf().ignoringAntMatchers("/admin/logout");;
		}
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/**");
		}
		
		@Bean(name="customSavedRequestAwareAuthenticationSuccessHandlerAdmin")
		public  CustomSavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() { 
			CustomSavedRequestAwareAuthenticationSuccessHandler auth = new CustomSavedRequestAwareAuthenticationSuccessHandler();
			auth.setTargetUrlParameter("targetUrl");
			auth.setDefaultTargetUrl("/admin");
			//auth.setUseReferer(true);
			return auth;
		}
		
		@Bean
		public  PersistentTokenRepository persistentTokenRepository() {
			JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
			db.setDataSource(dataSource);
			return db;
		}
		@Bean
		public AccessDeniedHandler customAccessDeniedHandler(){
			CustomAccessDeniedHandlerImpl customAccessDeniedHandler = new CustomAccessDeniedHandlerImpl();
			customAccessDeniedHandler.setErrorPage("/admin403");
			return customAccessDeniedHandler;
		}
	}

	@Configuration      
	@Order(1) 
	public static class profileWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		@Autowired
		private  DataSource dataSource;
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/profile/**").authorizeRequests().anyRequest().hasRole("PROVIDER")			
			.and()
				.formLogin().loginPage("/profile/signin").permitAll().failureUrl("/profile/signin?error").usernameParameter("email")
					.passwordParameter("password").successHandler(savedRequestAwareAuthenticationSuccessHandler())
	        .and()
	        	.logout().logoutUrl("/profile/logout").logoutSuccessUrl("/").deleteCookies("JSESSIONID")
	        .and()
	        	.exceptionHandling().accessDeniedPage("/admin403").accessDeniedHandler(customAccessDeniedHandler())
	        .and()
	        	.rememberMe().tokenRepository(persistentTokenRepository()).tokenValiditySeconds(604800)
	        	.and().csrf().ignoringAntMatchers("/profile/logout");
	        
		        
		}
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/**");
		}
		@Bean
		public  PersistentTokenRepository persistentTokenRepository() {
			JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
			db.setDataSource(dataSource);
			return db;
		}
		@Bean(name="customSavedRequestAwareAuthenticationSuccessHandlerProfile")
		public  CustomSavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() { 
			CustomSavedRequestAwareAuthenticationSuccessHandler auth = new CustomSavedRequestAwareAuthenticationSuccessHandler();
			auth.setTargetUrlParameter("targetUrl");
			auth.setDefaultTargetUrl("/profile");
			//auth.setUseReferer(true);
			return auth;
		}
		
		@Bean
		public AccessDeniedHandler customAccessDeniedHandler(){
			CustomAccessDeniedHandlerImpl customAccessDeniedHandler = new CustomAccessDeniedHandlerImpl();
			customAccessDeniedHandler.setErrorPage("/admin403");
			return customAccessDeniedHandler;
		}
		
	}
	
	@Configuration      
	@Order
	public static class publicWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			 http.antMatcher("/**").authorizeRequests().anyRequest().permitAll()
			 .and().exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
	        
		        
		}
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/**");
		}
		@Bean
		public AccessDeniedHandler customAccessDeniedHandler(){
			CustomAccessDeniedHandlerImpl customAccessDeniedHandler = new CustomAccessDeniedHandlerImpl();
			customAccessDeniedHandler.setErrorPage("/admin403");
			return customAccessDeniedHandler;
		}
		
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	
}