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
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.buseni.ubukwebwiza.account.service.UserAccountService;
import com.buseni.ubukwebwiza.filters.CustomSavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class MyMultiHttpSecurityConfig  {
	

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
	        	.logout().logoutUrl("/admin/logout").logoutSuccessUrl("/admin/signin?logout").permitAll()
	        	.deleteCookies("JSESSIONID")
	        .and()
	        	.exceptionHandling().accessDeniedPage("/admin403")
	        .and().sessionManagement().maximumSessions(1)
	             .sessionRegistry(sessionRegistry())
	             .expiredUrl("/admin/signin?expired")
	             .and()
	          .and()
	        	.rememberMe().tokenRepository(persistentTokenRepository()).tokenValiditySeconds(604800)
	        .and().csrf().disable();
		}
		@Bean(name = "sessionRegistry")
		public SessionRegistry sessionRegistry() {
			 return new SessionRegistryImpl();
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
		/*@Bean
		public AccessDeniedHandler customAccessDeniedHandler(){
			CustomAccessDeniedHandlerImpl customAccessDeniedHandler = new CustomAccessDeniedHandlerImpl();
			customAccessDeniedHandler.setErrorPage("/admin403");
			return customAccessDeniedHandler;
		}*/
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
	        	.exceptionHandling().accessDeniedPage("/admin403")
	        .and()
	        	.rememberMe().tokenRepository(persistentTokenRepository()).tokenValiditySeconds(604800)
	        	.and().csrf().disable();
	        
		        
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
		
		/*@Bean
		public AccessDeniedHandler customAccessDeniedHandler(){
			CustomAccessDeniedHandlerImpl customAccessDeniedHandler = new CustomAccessDeniedHandlerImpl();
			customAccessDeniedHandler.setErrorPage("/admin403");
			return customAccessDeniedHandler;
		}*/
		
	}
	
	@Configuration      
	@Order
	public static class publicWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			 http.antMatcher("/**").authorizeRequests().anyRequest().permitAll()
			 .and().exceptionHandling()
			 .and().csrf().disable();
	        
		        
		}
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/**");
		}
		/*@Bean
		public AccessDeniedHandler customAccessDeniedHandler(){
			CustomAccessDeniedHandlerImpl customAccessDeniedHandler = new CustomAccessDeniedHandlerImpl();
			customAccessDeniedHandler.setErrorPage("/admin403");
			return customAccessDeniedHandler;
		}*/
		
	}
	
	@Configuration      
	@Order(4)
	public static class apiWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
			  .antMatcher("/api/**").authorizeRequests().anyRequest().authenticated()	
			 .and()
			 .addFilter(new JWTAuthenticationFilter(authenticationManager()))
             .addFilter(new JWTAuthorizationFilter(authenticationManager()))
			 ;
	        
		        
		}
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/**");
		}
		/*@Bean
		public AccessDeniedHandler customAccessDeniedHandler(){
			CustomAccessDeniedHandlerImpl customAccessDeniedHandler = new CustomAccessDeniedHandlerImpl();
			customAccessDeniedHandler.setErrorPage("/admin403");
			return customAccessDeniedHandler;
		}*/
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	
}