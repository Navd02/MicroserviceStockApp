package com.naveed.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.security.config.BeanIds;

import com.naveed.service.CustomUserDetailsService;

import lombok.var;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	 @Autowired
	    private CustomUserDetailsService customUserDetailsService;


	    @Autowired
	    private BCryptPasswordEncoder bCryptPasswordEncoder;

	    @Override
	    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
	        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
	    }

	    @Override
	    @Bean(BeanIds.AUTHENTICATION_MANAGER)
	    protected AuthenticationManager authenticationManager() throws Exception {
	        return super.authenticationManager();
	    }
	
	    @Autowired
	    private JwtAuthenticationEntryPoint unauthorizedHandler;
	
	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
		 http.cors().and().csrf().disable()
		 .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
         .and()
         .authorizeRequests()
         .antMatchers(
                 "/",
                 "/favicon.ico",
                 "/**/*.png",
                 "/**/*.gif",
                 "/**/*.svg",
                 "/**/*.jpg",
                 "/**/*.html",
                 "/**/*.css",
                 "/**/*.js"
         ).permitAll();
	 }

}
