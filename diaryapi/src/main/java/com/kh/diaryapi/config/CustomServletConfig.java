package com.kh.diaryapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kh.diaryapi.controller.formatter.LocalDateFormatter;

@Configuration
public class CustomServletConfig implements WebMvcConfigurer {
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new LocalDateFormatter());
	}

	@Override
	public void addCorsMappings(CorsRegistry registry){
	registry.addMapping("/**")
	.allowedOrigins("*")
	.allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "OPTIONS")
			.maxAge(3003).allowedHeaders("Authorization", "Cache-Control", "Content-Type");
	}
}
