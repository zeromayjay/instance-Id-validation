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


import java.net.URI;

import com.example.restservice.RestServiceApplication;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestServiceApplication.class)
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
	public void testRetrieveMessage() throws JSONException {
	    
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(
				"localhost:8081/consume",
				HttpMethod.GET, entity, String.class);
		
		String expected = "{}";
		
		JSONAssert.assertEquals(expected, response.getBody(), false);
		
	}


}
