package com.example.restservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@Value("${spring.application.name}")
    private String applicationName;
    
    @Value("${properties.myown.version.new}")
    private String myownNew;
    
    @Value("${properties.myown.version.old}")
    private String myownOld;
    
    private String myown;
    

    /**
     * git-commit-id-plugin-properties
     */
    @Value("${git.commit.message.short}")
    private String commitMessage;

    @Value("${git.branch}")
    private String branch;
    
    @Value("${git.commit.id}")
    private String commitId;
    
    @Value("${git.commit.id.abbrev}")
    private String commitIdShort;
    
    @Value("${git.build.version}")
    private String buildV;
    
    @Value("${git.commit.time}")
    private String commitTime;

	
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
	
    @RequestMapping("/commitId")
    public Map<String, String> getCommitId() {
    	
    	getVerIdRandom();
    	
    	String idConcat = applicationName + "-" + myown + "-" + commitIdShort + "-" + commitTime ;
    	
        Map<String, String> result = new HashMap<>();
        result.put("Commit message",commitMessage);
        result.put("Commit branch", branch);
        result.put("Commit id", commitId);
        result.put("Commit id abbrevation", commitIdShort);
        result.put("Commit build version", buildV);
        result.put("myown prop", myown);
        result.put("application name", applicationName);
        result.put("id concatenated", idConcat);
        return result;
    }


	private void getVerIdRandom() {
		Random rand = new Random();
		// 1/3 val = false
		boolean val = rand.nextInt(5)==0;
		
		if (val != true) {
			myown = myownNew;
		}
		else {
			myown = myownOld;
		}
	}
	
}