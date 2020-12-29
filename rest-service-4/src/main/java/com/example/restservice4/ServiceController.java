package com.example.restservice4;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

import reactor.core.publisher.Mono;


@RestController
public class ServiceController {

	WebClient client = WebClient.create("http://localhost:8080/getInformation");
	String InstanceID = "X-service-4-deployment-id-8083";
	

	@GetMapping("/getInformation")
	public Mono<String> consume(HttpServletResponse var) {
		
		/*
		 * add a new header field to the header
		 */
	
		
		Mono<String> response;
		//= client.get().retrieve().bodyToMono(String.class).block();
		
		response = client.get().exchange().doOnSuccess(clientResponse -> 
		{var.addHeader("Instance-ID", clientResponse.headers().header("Instance-ID").get(0));
		String extract = clientResponse.headers().header("Instance-ID").get(0);var.addHeader("Instance-ID", InstanceID +", "+ extract);
		System.out.println("the List: " + clientResponse.headers().header("Instance-ID").get(0));})
				.flatMap(clientResponse -> clientResponse.bodyToMono(String.class));
		
		//String extract = var.getHeader(instanceID);
		//var.addHeader("instance-ID", instanceID + extract);
		
		System.out.println("the List: " + var.getHeaders("Instance-ID"));

		return response;
		//return "test";
		
	}
}