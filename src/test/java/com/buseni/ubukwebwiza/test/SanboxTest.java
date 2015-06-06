package com.buseni.ubukwebwiza.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.buseni.ubukwebwiza.account.domain.UserAccount;
import com.buseni.ubukwebwiza.account.repository.UserAccountRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= TestConfig.class)
@WebAppConfiguration
//@Transactional

public class SanboxTest {
	

	
	@Autowired
	private UserAccountRepository userBaseRepository	;
	@Test
	public void addData(){
		
		UserAccount customerAccount =  new UserAccount("email@test.com", "password");
		//userBaseRepository.save(customerAccount);
		
	     
		
	}

}
