package com.buseni.ubukwebwiza.api.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.buseni.ubukwebwiza.config.AuditorAwareImpl;

//@Profile( "mysql" )
@Configuration
@EnableJpaRepositories(basePackages = { "com.buseni.ubukwebwiza.administrator.repository",
		"com.buseni.ubukwebwiza.provider.repository", "com.buseni.ubukwebwiza.contactus.repository",
		"com.buseni.ubukwebwiza.gallery.repository", "com.buseni.ubukwebwiza.account.repository" })
@PropertySource({ "classpath:hibernate.properties" })
@EnableTransactionManagement
@EnableJpaAuditing
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
	
	private final static String HIBERNATE_SEARCH_DEFAULT_DIRECTORY_PROVIDER = "hibernate.search.default.directory_provider";
			private final static String HIBERNATE_SEARCH_DEFAULT_INDEX_BASE ="hibernate.search.default.indexBase";

	private final static String[] SCAN_PACKAGES = new String[] { "com.buseni.ubukwebwiza" };

	@Autowired
	Environment environment;

	@Autowired
	DataSource datasource;

	/*
	 * @Bean
	 * 
	 * @Profile("dev") public DataSource dataSource() { DriverManagerDataSource
	 * dataSource = new DriverManagerDataSource();
	 * 
	 * dataSource.setDriverClassName( getRequired( DRIVER_CLASSNAME ) );
	 * dataSource.setUrl( getRequired( DATASOURCE_URL ) );
	 * dataSource.setUsername( getRequired( DATASOURCE_USERNAME ) );
	 * dataSource.setPassword( getRequired( DATASOURCE_PASSWORD ) ); return
	 * dataSource; }
	 * 
	 * @Bean
	 * 
	 * @Profile("default") public DataSource jndiDataSource() { final
	 * JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
	 * //dsLookup.setResourceRef(true); DataSource dataSource =
	 * dsLookup.getDataSource("jdbc/ubukwebwiza"); return dataSource; }
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(true);
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setPackagesToScan(SCAN_PACKAGES);
		entityManagerFactoryBean.setDataSource(datasource);
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
		entityManagerFactoryBean.setJpaProperties(hibProperties());
		return entityManagerFactoryBean;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public AuditorAware<String> auditorProvider() {
		return new AuditorAwareImpl();
	}

	private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put(HIBERNATE_DIALECT, getRequired(HIBERNATE_DIALECT));
		properties.put(HIBERNATE_BATCH_SIZE, getRequired(HIBERNATE_BATCH_SIZE));
		properties.put(HIBERNATE_FETCH_SIZE, getRequired(HIBERNATE_FETCH_SIZE));
		properties.put(HIBERNATE_ORDER_INSERTS, getRequired(HIBERNATE_ORDER_INSERTS));
		properties.put(HIBERNATE_ORDER_UPDATES, getRequired(HIBERNATE_ORDER_UPDATES));
		properties.put(HIBERNATE_USE_UNICODES, getRequired(HIBERNATE_USE_UNICODES));
		properties.put(HIBERNATE_CHARSET, getRequired(HIBERNATE_CHARSET));
		properties.put(HIBERNATE_DEFAULTNCHAR, getRequired(HIBERNATE_DEFAULTNCHAR));
		properties.put(HIBERNATE_SEARCH_DEFAULT_DIRECTORY_PROVIDER, getRequired(HIBERNATE_SEARCH_DEFAULT_DIRECTORY_PROVIDER));
		properties.put(HIBERNATE_SEARCH_DEFAULT_INDEX_BASE, getRequired(HIBERNATE_SEARCH_DEFAULT_INDEX_BASE));
		if (isProp(HIBERNATE_HBM2DLL)) {
			properties.put(HIBERNATE_HBM2DLL, getRequired(HIBERNATE_HBM2DLL));
		}
		return properties;
	}

	private String getRequired(String key) {
		return this.environment.getRequiredProperty(key);
	}

	private boolean isProp(String key) {
		return this.environment.getProperty(key) != null;
	}

	/*
	 * private String getProp( String key ) { return
	 * this.environment.getProperty( key ); }
	 */

}
