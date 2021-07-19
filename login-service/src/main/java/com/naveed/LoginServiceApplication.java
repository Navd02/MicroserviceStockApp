package com.naveed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class LoginServiceApplication {
	
	 @Bean
	    BCryptPasswordEncoder bCryptPasswordEncoder(){
	        return new BCryptPasswordEncoder();
	    }

	public static void main(String[] args) {
		SpringApplication.run(LoginServiceApplication.class, args);
	}

}
