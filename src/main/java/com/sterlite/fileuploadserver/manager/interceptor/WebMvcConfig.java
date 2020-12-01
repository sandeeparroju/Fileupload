package com.sterlite.fileuploadserver.manager.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

		/*@Override
	   public void addInterceptors(InterceptorRegistry registry) {
	      registry.addInterceptor(new CustomManagerInterceptor()).excludePathPatterns("/login","/dist/**","/bower_components/**","/plugins/**","/authenticateStaff","/customers/changepassword");
	   }*/
	  
		/*
		 * @Bean public MultipartResolver multipartResolver() { return new
		 * CommonsMultipartResolver(); }
		 */
}
