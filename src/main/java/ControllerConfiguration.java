

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.buseni.ubukwebwiza.controller.SiteController;

@Configuration 
public class ControllerConfiguration {

	
	@Bean
	public SiteController siteController() {
		return new SiteController();
	}

}
