package com.example.restservice;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.util.Collection;

import com.example.restservice2.RestServiceApplication2;
import com.example.restservice.*;
import com.example.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestServiceApplication2.class)
@WebAppConfiguration
public class RestServiceApplicationTests {
	
	@LocalServerPort
	private int port;
	
	TestRestTemplate restTemplate = new TestRestTemplate();
	
	HttpHeaders headers = new HttpHeaders();
	

	@Test
	void contextLoads() {
	}
	
	@Test
	public void testGetRequest() throws JSONException {
	    
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:8081/consume",
				HttpMethod.GET, entity, String.class);
		
		String expected = "{\"id\":1,\"name\":\"service_1_test\"}";
		
		assertEquals(expected, response.getBody());
		
	}
	
	@Test
	public void getCorrectList() {
		
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:8081/consume",
				HttpMethod.GET, entity, String.class);
		
		response.getHeaders().get("Instance-ID");
		
		String expected = "[X-Service-2-deployment-id-8081, X-service-3-deployment-id-8082, X-service-4-deployment-id-8083, X-Service-1-deployment-id-8080]";
		
		assertEquals(expected, response.getHeaders().get("Instance-ID").toString());
		
	}


}