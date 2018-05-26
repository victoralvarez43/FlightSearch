package com.ryanair.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import com.ryanair.flights.config.FlightSearchConfig;
import com.ryanair.flights.utils.ExceptionUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { FlightSearchConfig.class })
@WebAppConfiguration
public class UtilsTest {

	@Test
	public void testUtils() {
		HttpStatusCodeException br = new HttpClientErrorException(HttpStatus.BAD_REQUEST);
		HttpStatusCodeException bl = new HttpClientErrorException(HttpStatus.BANDWIDTH_LIMIT_EXCEEDED);
		HttpStatusCodeException su = new HttpClientErrorException(HttpStatus.SERVICE_UNAVAILABLE);
		
		assertTrue(!ExceptionUtils.isRetry(br));
		assertTrue(ExceptionUtils.isRetry(bl));
		assertTrue(ExceptionUtils.isRetry(su));
	}
	
}
