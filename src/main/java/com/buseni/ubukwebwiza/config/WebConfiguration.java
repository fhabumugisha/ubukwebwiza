package com.buseni.ubukwebwiza.config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.buseni.ubukwebwiza.breadcrumbs.interceptor.NavigationPathInterceptor;
//@ActiveProfiles("embedded")
@Configuration
@ComponentScan(basePackages = {"com.buseni.ubukwebwiza"})
@PropertySource("classpath:application.properties")
@EnableWebMvc
@EnableWebSecurity
@EnableCaching
@Import({PersistenceMySqlConfig.class,  ViewConfiguration.class, MultiHttpSecurityConfig.class})
public class WebConfiguration extends WebMvcConfigurerAdapter{
	
	
	@Autowired
	private Environment env;
	// Maps resources path to webapp/resources
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/resources/**").addResourceLocations("/resources/")
			.setCachePeriod(2592000)
            .resourceChain(true)
            .addResolver(new GzipResourceResolver())
            .addResolver(new PathResourceResolver());
			
			//String workingDir = System.getProperty("user.dir");
			registry.addResourceHandler("/images/**")
					.addResourceLocations("file:"+env.getProperty("files.location"))
					.setCachePeriod(3600)
		            .resourceChain(true)
		            .addResolver(new GzipResourceResolver())
		       .addResolver(new PathResourceResolver());
		}
		
		@Override
		@Order(value=1)
		public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
			
			converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));	
			ByteArrayHttpMessageConverter batmcc = new ByteArrayHttpMessageConverter();
			batmcc.setSupportedMediaTypes(Arrays.asList(MediaType.IMAGE_PNG, MediaType.IMAGE_JPEG));			
			converters.add( batmcc);
			converters.add(new BufferedImageHttpMessageConverter());
			converters.add(new Jaxb2RootElementHttpMessageConverter());
			converters.add(new MappingJackson2HttpMessageConverter());
		}
		
		@Override
		public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
			PageableHandlerMethodArgumentResolver resolver =	new PageableHandlerMethodArgumentResolver();
			resolver.setFallbackPageable(new PageRequest(1, 12));
			resolver.setMaxPageSize(12);
			
			argumentResolvers.add(resolver);
		}
		@Bean
		public WebContentInterceptor webContentInterceptor() {
		    WebContentInterceptor interceptor = new WebContentInterceptor();
		    interceptor.setCacheSeconds(2592000);
		    interceptor.setUseExpiresHeader(true);
		    interceptor.setUseCacheControlHeader(true);
		    interceptor.setUseCacheControlNoStore(true);
		    return interceptor;
		}
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(new NavigationPathInterceptor());
			registry.addInterceptor(webContentInterceptor());
		}
		
	/*	@Bean(name="simpleMappingExceptionResolver")
	    public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
	        SimpleMappingExceptionResolver r =
	              new SimpleMappingExceptionResolver();

	        Properties mappings = new Properties();
	        mappings.setProperty("OldBusinessException", "businessException");
	        mappings.setProperty("DatabaseException", "databaseError");
	        mappings.setProperty("InvalidCreditCardException", "creditCardError");

	        r.setExceptionMappings(mappings);  // None by default
	        r.setDefaultErrorView("error");    // No default
	        r.setExceptionAttribute("ex");     // Default is "exception"
	       // r.setWarnLogCategory("example.MvcLogger");     // No default
	        return r;
	    }*/
		@Bean
		public static PropertySourcesPlaceholderConfigurer properties(){
		   PropertySourcesPlaceholderConfigurer pspc =
		      new PropertySourcesPlaceholderConfigurer();
		   Resource[] resources = new ClassPathResource[ ]
		      { new ClassPathResource( "application.properties" ) };
		  pspc.setLocations( resources );
		  pspc.setIgnoreUnresolvablePlaceholders( true );
		  return pspc;
		}
		@Override
		public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
			// Simple strategy: only path extension is taken into account
			configurer.favorPathExtension(false).ignoreAcceptHeader(true)
				.useJaf(false)
				.defaultContentType(MediaType.TEXT_HTML).
				mediaType("html", MediaType.TEXT_HTML).
				mediaType("xml", MediaType.APPLICATION_XML).
				mediaType("json", MediaType.APPLICATION_JSON).
				mediaType("png", MediaType.IMAGE_PNG).mediaType("jpeg", MediaType.IMAGE_JPEG)
				.mediaType("jpg", MediaType.IMAGE_JPEG);;
		}
		
		@Bean(name="filterMultipartResolver")
		public  CommonsMultipartResolver filterMultipartResolver() {
			CommonsMultipartResolver filterMultipartResolver = new CommonsMultipartResolver();
		    //filterMultipartResolver.setMaxUploadSize(MAXSIZE);
		    return filterMultipartResolver;
		}
		
		/*@Bean(name="multipartResolver")
		public  MultipartResolver multipartResolver() {
			MultipartResolver filterMultipartResolver = new StandardServletMultipartResolver();
		    return filterMultipartResolver;
		}*/
		
		
		 @Bean
		    public LocaleResolver localeResolver() {
		        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
		        return cookieLocaleResolver;
		    }
		
		@Bean
		public CacheManager cacheManager() {
			return new EhCacheCacheManager(ehCacheCacheManager().getObject());
		}
	 
		@Bean
		public EhCacheManagerFactoryBean ehCacheCacheManager() {
			EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
			cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
			cmfb.setShared(true);
			return cmfb;
		}
		
}
