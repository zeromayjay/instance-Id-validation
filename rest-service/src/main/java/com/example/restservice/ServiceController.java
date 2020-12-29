package com.example.restservice;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class ServiceController {

	/*
	 * currently not in use!
	 */
	WebClient client = WebClient.create("http://localhost:8080/getInformation");
	String InstanceID = "X-Service-1-deployment-id-8080";
	
	@GetMapping("/getInformation")
	public Service getInformation(HttpServletResponse var) {
		
		/*
		 * add a new header field to the header
		 */
		var.addHeader("Instance-ID", InstanceID);
		
		/*
		 * currently not in use!
		 */
		Mono<String> response;
		//= client.get().retrieve().bodyToMono(String.class).block();
		
		response = client.get().exchange().doOnSuccess(clientResponse -> 
		{var.addHeader("Instance-ID", clientResponse.headers().header("Instance-ID").get(0));})
				.flatMap(clientResponse -> clientResponse.bodyToMono(String.class));
		
		
		System.out.println("the List of service 8080: " + var.getHeaders("Instance-ID"));
		
		return new Service(1, "service_1_test");

	}
}