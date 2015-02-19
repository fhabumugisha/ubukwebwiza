package com.buseni.ubukwebwiza.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@Profile( "mysql" )
@Configuration
@EnableJpaRepositories( basePackages={"com.buseni.ubukwebwiza.administrator.repository","com.buseni.ubukwebwiza.vendor.repository","com.buseni.ubukwebwiza.contactus.repository"})
@PropertySource( {  "classpath:hibernate.properties" } )
@EnableTransactionManagement
public class PersistenceMySqlConfig {

	private final static String DRIVER_CLASSNAME = "datasource.driver.classname";
	private final static String DATASOURCE_URL = "datasource.url";
	private final static String DATASOURCE_USERNAME = "datasource.username";
	private final static String DATASOURCE_PASSWORD = "datasource.password";

	private final static String HIBERNATE_DIALECT = "hibernate.dialect";
	private final static String HIBERNATE_BATCH_SIZE = "hibernate.jdbc.batch_size";
	private final static String HIBERNATE_FETCH_SIZE = "hibernate.jdbc.fetch_size";
	private final static String HIBERNATE_ORDER_INSERTS = "hibernate.jdbc.order_inserts";
	private final static String HIBERNATE_ORDER_UPDATES = "hibernate.jdbc.order_updates";
	private final static String HIBERNATE_USE_UNICODES = "hibernate.connection.useUnicode";
	private final static String HIBERNATE_CHARSET = "hibernate.connection.charSet";
	private final static String HIBERNATE_DEFAULTNCHAR = "hibernate.connection.defaultNChar";
	private final static String HIBERNATE_HBM2DLL = "hibernate.hbm2ddl.auto";

	private final static String[] SCAN_PACKAGES = new String[] { "com.buseni.ubukwebwiza" };

	@Autowired
	Environment environment;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName( getRequired( DRIVER_CLASSNAME ) );
		dataSource.setUrl( getRequired( DATASOURCE_URL ) );
		dataSource.setUsername( getRequired( DATASOURCE_USERNAME ) );
		dataSource.setPassword( getRequired( DATASOURCE_PASSWORD ) );
		Properties connectionProperties = new Properties();
		//Parametrage d'un proxy http
		/*connectionProperties.put("http.proxyHost", "172.27.231.250");
		connectionProperties.put("http.proxyPort", "8080");
		dataSource.setConnectionProperties(connectionProperties);*/
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		 HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		    vendorAdapter.setGenerateDdl(true);
		    vendorAdapter.setShowSql(true);
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setPackagesToScan( SCAN_PACKAGES );
		entityManagerFactoryBean.setDataSource( dataSource() );
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
		entityManagerFactoryBean.setJpaProperties( hibProperties() );
		return entityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory( entityManagerFactory().getObject() );
		return transactionManager;
	}

	private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put( HIBERNATE_DIALECT, getRequired( HIBERNATE_DIALECT ) );
		properties.put( HIBERNATE_BATCH_SIZE, getRequired( HIBERNATE_BATCH_SIZE ) );
		properties.put( HIBERNATE_FETCH_SIZE, getRequired( HIBERNATE_FETCH_SIZE ) );
		properties.put( HIBERNATE_ORDER_INSERTS, getRequired( HIBERNATE_ORDER_INSERTS ) );
		properties.put( HIBERNATE_ORDER_UPDATES, getRequired( HIBERNATE_ORDER_UPDATES ) );
		properties.put( HIBERNATE_USE_UNICODES, getRequired( HIBERNATE_USE_UNICODES ) );
		properties.put( HIBERNATE_CHARSET, getRequired( HIBERNATE_CHARSET ) );
		properties.put( HIBERNATE_DEFAULTNCHAR, getRequired( HIBERNATE_DEFAULTNCHAR ) );
		if ( isProp( HIBERNATE_HBM2DLL ) ) {
			properties.put( HIBERNATE_HBM2DLL, getRequired( HIBERNATE_HBM2DLL ) );
		}
		return properties;
	}

	private String getRequired( String key ) {
		return this.environment.getRequiredProperty( key );
	}

	private boolean isProp( String key ) {
		return this.environment.getProperty( key ) != null;
	}

	/*
	 * private String getProp( String key ) { return
	 * this.environment.getProperty( key ); }
	 */

}
