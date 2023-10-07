package com.escuela.maestromicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class MaestroMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaestroMicroserviceApplication.class, args);
	}

}
