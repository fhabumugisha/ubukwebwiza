package com.buseni.ubukwebwiza.test;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.buseni.ubukwebwiza.config.WebConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes={WebConfiguration.class})
public abstract class AbstractTests {
	
	  /**
     * The database setup needs to happen only once, so normally we would use a JUnit method 
     * annotated with @BeforeClass
     * rather than @Before.  However, @BeforeClass methods must be static, and Spring can only
     *  inject the necessary DataSource  object into an instance variable.  
     *  So we use this static "flag" variable here, to ensure that the @Before method
     * only populates the database once.
     */
    protected static boolean databasePopulated = false;

    @Autowired
    DataSource dataSource;

    protected final String CURRENT_WORKING_DIRECTORY = this.getClass().getProtectionDomain()
    		.getCodeSource().getLocation().getFile();

    @Before
    public void before() throws Exception {
        if(!databasePopulated) {
            
            }
        }
    

}
