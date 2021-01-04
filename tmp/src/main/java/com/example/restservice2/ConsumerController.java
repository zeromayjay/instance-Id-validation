package com.example.restservice2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

import reactor.core.publisher.Mono;

import org.springframework.http.HttpHeaders;


@RestController
public class ConsumerController {

	WebClient client = WebClient.create("http://localhost:8082/getInformation");
	String InstanceID = "X-Service-2-deployment-id-8081";

	@SuppressWarnings("null")
	@GetMapping("/consume")
	public Mono<String> consume(HttpServletResponse var) {
		
		
		Mono<String> response;
		//= client.get().retrieve().bodyToMono(String.class).block();
		
		response = client.get().exchange().doOnSuccess(clientResponse -> 
		{
		String extract = clientResponse.headers().header("Instance-ID").get(0);var.addHeader("Instance-ID", InstanceID +", "+ extract);
		Collection<String> metaData = var.getHeaders("Instance-ID");
		System.out.println("the List: " + clientResponse.headers().header("Instance-ID").get(0));
		System.out.println("the List2: " + var.getHeaders("Instance-ID"));
		})
				.flatMap(clientResponse -> clientResponse.bodyToMono(String.class));
		
		
		//ClientResponse clientResponse = null;
		
		/**
		 * TODO: extract the list of the header response of the key "Instance-ID"
		 */
		
		return response;
		//return null;
		
	}
}