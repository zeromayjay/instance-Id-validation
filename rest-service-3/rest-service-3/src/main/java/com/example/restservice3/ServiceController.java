package com.example.restservice3;

import java.util.HashMap;
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

	WebClient client = WebClient.create("http://localhost:8083/getInformation");
	WebClient client2 = WebClient.create("http://localhost:8084/getInformation");
	
	String InstanceID = "X-service-3-deployment-id-8082";
	
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
    
    //this property is not working with GitHub
    @Value("${git.build.number.unique}")
    private String buildNumberUnique;

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
		
		return response;
		//return null;
	}
	
	
    @RequestMapping("/commitId")
    public Map<String, String> getCommitId() {
    	
    	getVerIdRandom();
    	
    	String idConcat = applicationName + "-" + myownNew + "-" + commitIdShort + "-" + commitTime ;
    	
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
		boolean val = rand.nextInt(3)==0;
		
		if (val != true) {
			myown = myownNew;
		}
		else {
			myown = myownOld;
		}
	}
	
	
	
}