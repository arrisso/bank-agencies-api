package com.bank.agencies.endpoint;

import com.bank.agencies.Application;
import com.bank.agencies.domain.AgencyGatewayResponse;
import com.bank.agencies.domain.AgencyResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = { Application.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AgenciesControllerIntegrationTest {

	private static final String API_BASE_URL = "/agencies";
	private static final String API_BASE_URL_GROUP = "/all_grouped_agencies";

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void shouldReturnOKWhenDoAGetRequest() {
		ResponseEntity<AgencyResponse[]> responseEntity = restTemplate.getForEntity(API_BASE_URL, AgencyResponse[].class);

		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertTrue(responseEntity.getBody().length > 0);
	}
	
	/*
	 * @Test public void shouldReturnOKWhenDoAGGetRequest() {
	 * ResponseEntity<AgencyResponse[]> responseEntity =
	 * restTemplate.getForEntity(API_BASE_URL_GROUP, AgencyResponse[].class);
	 * 
	 * Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	 * Assertions.assertTrue(responseEntity.getBody().length > 0); }
	 */
	

}
