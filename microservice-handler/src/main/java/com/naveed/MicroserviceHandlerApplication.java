package com.naveed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableZuulProxy
@EnableFeignClients
@CrossOrigin
public class MicroserviceHandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceHandlerApplication.class, args);
	}

}
