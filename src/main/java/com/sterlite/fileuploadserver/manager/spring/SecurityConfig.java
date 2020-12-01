package com.sterlite.fileuploadserver.manager.spring;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
@ComponentScan("com.sterlite.fileuploadserver.manager.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	//"/login","/dist/**","/bower_components/**","/plugins/**","/authenticateStaff","/customers/changepassword"
    
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()      
				.antMatchers("/fileupload","/liveliness").permitAll()   
               	.anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticateStaff")
                .permitAll()                   
                .and()
                .logout().permitAll().logoutUrl("/logout")
                .and()
                .csrf().disable()
                ;
    }
	
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web
           .ignoring()
           .antMatchers("/resources/**", "/dist/**", "/bower_components/**", "/plugins/**", "/images/**");
    }
	
	@Component
	public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	    @Override
	    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
	        redirectStrategy.sendRedirect(request, response, "home");
	    }
	}

	
}
