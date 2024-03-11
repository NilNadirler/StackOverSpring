package com.example.stack.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration                                                                                                                                                                                                                       
public class CorsConfiguration {

	@Bean
	public WebMvcConfigurer corsConfig() {
		 
		return new WebMvcConfigurer() {
			public void addCorsMapping(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedOrigins("http://localhost:4200")
				.allowedMethods("GET", "POST,PUT,DELETE")
				.allowedHeaders("*");
			}
		};
	}
}
