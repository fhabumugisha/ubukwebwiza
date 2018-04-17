package com.buseni.ubukwebwiza.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Profile( "mysql" )
@Configuration
@EnableJpaRepositories(basePackages={"com.buseni.ubukwebwiza.administrator.repository","com.buseni.ubukwebwiza.provider.repository",
		"com.buseni.ubukwebwiza.contactus.repository","com.buseni.ubukwebwiza.gallery.repository", "com.buseni.ubukwebwiza.account.repository"})
@PropertySource( {  "classpath:hibernate.properties"} )
@EnableTransactionManagement
public class DatasourceConfig {

	private final static String DRIVER_CLASSNAME = "datasource.driver.classname";
	private final static String DATASOURCE_URL = "datasource.url";
	private final static String DATASOURCE_USERNAME = "datasource.username";
	private final static String DATASOURCE_PASSWORD = "datasource.password";

	public static final Logger LOGGER = LoggerFactory.getLogger( DatasourceConfig.class );

	

	@Autowired
	Environment environment;

	@Bean
	@Profile("dev")
	public DataSource dataSource() {
		LOGGER.debug("Initializing dev datasource");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName( getRequired( DRIVER_CLASSNAME ) );
//		dataSource.setUrl( getRequired( DATASOURCE_URL ) );
//		dataSource.setUsername( getRequired( DATASOURCE_USERNAME ) );
//		dataSource.setPassword( getRequired( DATASOURCE_PASSWORD ) );
		
		dataSource.setUrl( System.getProperty(DATASOURCE_URL ) );
		dataSource.setUsername( System.getProperty( DATASOURCE_USERNAME ) );
		dataSource.setPassword( System.getProperty(DATASOURCE_PASSWORD ) );
		LOGGER.debug("Initializing dev datasource : " + dataSource.toString());
		return dataSource;
	}

//	@Bean
//	@Profile("demo")
//	public DataSource demoDataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//		dataSource.setDriverClassName( getRequired( "spring.datasource.driverClassName" ) );
//		dataSource.setUrl( getRequired( "spring.datasource.url" ) );
//		dataSource.setUsername( getRequired( "spring.datasource.username" ) );
//		dataSource.setPassword( getRequired( "spring.datasource.password" ) );
//		return dataSource;
//	}

	@Bean
	@Profile("default")
	public DataSource jndiDataSource() {
		final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        //dsLookup.setResourceRef(true);
        DataSource dataSource = dsLookup.getDataSource("jdbc/ubukwebwiza");
        return dataSource;
	}
	

//	 @Bean
//	 @Profile("embedded")
//	  public DataSource embeddedDataSource() {		 
//	    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();	    
//	    return builder.setType(EmbeddedDatabaseType.H2).build();
//	  }



	private String getRequired( String key ) {
		return this.environment.getRequiredProperty( key );
	}

	

	/*
	 * private String getProp( String key ) { return
	 * this.environment.getProperty( key ); }
	 */

}
