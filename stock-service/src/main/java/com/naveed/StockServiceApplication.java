package com.naveed;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableSwagger2
public class StockServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockServiceApplication.class, args);
	}
	
	
	@Bean
	public Docket swaggerConfig() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/stock/**"))
				.apis(RequestHandlerSelectors.basePackage("com.naveed"))
				.build()
				.apiInfo(apiDetails());
		
	}
	
	private ApiInfo apiDetails(){
		return new ApiInfo(
				"Stock Details API",
				"API for handling stocks",
				"1.0",
				"Free to use",
				new springfox.documentation.service.Contact("Naveed", "", "mohammednaveed01@gmail.com"),
				"API License",
				"http://www.naveed.com",
				Collections.emptyList()
				);
	}
	

}
