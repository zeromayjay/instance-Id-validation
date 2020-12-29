package com.example.restservice3;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;


@RestController
public class ServiceController {

	WebClient client = WebClient.create("http://localhost:8083/getInformation");
	WebClient client2 = WebClient.create("http://localhost:8084/getInformation");
	
	String InstanceID = "X-service-3-deployment-id-8082";
	
	@Value("${application.name}")
    private String applicationName;

    @Value("${build.version}")
    private String buildVersion;

    @Value("${build.timestamp}")
    private String buildTimestamp;

    @Value("${git.commit.message.short}")
    private String commitMessage;

    @Value("${git.branch}")
    private String branch;

    @Value("${git.commit.id}")
    private String commitId;
	

	@GetMapping("/getInformation")
	public Mono<String> consume(HttpServletResponse var) {
		
		/*
		 * add a new header field to the header
		 */
		
		Mono<String> response;
		//= client.get().retrieve().bodyToMono(String.class).block();
		
		response = client.get().exchange().doOnSuccess(clientResponse -> 
		{
		String extract = clientResponse.headers().header("Instance-ID").get(1);var.addHeader("Instance-ID", InstanceID +", "+ extract);
		})
				.flatMap(clientResponse -> clientResponse.bodyToMono(String.class));
		
		System.out.println("the List: " + var.getHeaders("Instance-ID"));
		System.out.println("VERSION: " + buildTimestamp);
		
		
		return response;
		//return null;
	}
	
	
    @RequestMapping("/commitId")
    public Map<String, String> getCommitId() {
        Map<String, String> result = new HashMap<>();
        result.put("Commit message",commitMessage);
        result.put("Commit branch", branch);
        result.put("Commit id", commitId);
        return result;
    }
	
	
	
}