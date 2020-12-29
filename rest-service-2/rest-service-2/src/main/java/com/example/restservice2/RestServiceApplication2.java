package com.example.restservice2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
public class RestServiceApplication2 {

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication2.class, args);
	}

}
