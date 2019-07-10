package com.example.energia.servicedesk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ServiceDeskApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceDeskApplication.class, args);
	}

}
