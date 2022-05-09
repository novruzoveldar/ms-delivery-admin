package com.guavapay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsDeliveryAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsDeliveryAdminApplication.class, args);
	}

}
