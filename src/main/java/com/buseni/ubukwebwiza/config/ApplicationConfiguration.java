package com.buseni.ubukwebwiza.config;

import java.nio.charset.Charset;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableJpaRepositories
@ComponentScan
@PropertySource("classpath:application.properties")
@EnableWebMvc
@EnableTransactionManagement
@Import({ViewConfiguration.class, ControllerConfiguration.class})
public class ApplicationConfiguration extends WebMvcConfigurerAdapter{
	// Maps resources path to webapp/resources
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		}
		
		@Override
		public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
			//converters.add(jsonHttpMessageConverter());
			converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		}
		
		 @Bean
		  public DataSource dataSource() {
			 
		    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		    
		    return builder.setType(EmbeddedDatabaseType.H2).build();
		  }

		 
		 
		  @Bean
		  public EntityManagerFactory entityManagerFactory() {

		    HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		    vendorAdapter.setGenerateDdl(true);

		    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		    factory.setJpaVendorAdapter(vendorAdapter);
		    factory.setPackagesToScan("com.buseni.ubukwebwiza.core.domain");
		    factory.setDataSource(dataSource());
		    factory.afterPropertiesSet();

		    return factory.getObject();
		  }

		  @Bean
		  public PlatformTransactionManager transactionManager() {

		    JpaTransactionManager txManager = new JpaTransactionManager();
		    txManager.setEntityManagerFactory(entityManagerFactory());
		    return txManager;
		  }

}
