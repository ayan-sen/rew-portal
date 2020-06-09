package com.rew.portal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {
	@Value("${ui.context-path}")
	private String uiContextPath;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE").allowedOrigins("*");
				
	}
	
	 /*@Override
     public void addResourceHandlers(ResourceHandlerRegistry registry) {
             registry.addResourceHandler("/resources/**")
                     .addResourceLocations("/public", "/static");
                     
     }*/
}