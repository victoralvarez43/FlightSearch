package com.ryanair.test;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.ryanair.flights.config.FlightSearchConfig;
import com.ryanair.flights.service.RouteSearchService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { FlightSearchConfig.class })
@WebAppConfiguration
public class RouteAPITest {

	@Autowired
	private RouteSearchService routeSearchService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ResourceLoader resourceLoader;

	@Value("${rest.ryanair.endpoint.routes}")
	private String endpoint;

	@Test
	public void testGetRoute() throws Exception {

		Resource resource = resourceLoader.getResource("classpath:/routes.json");
		MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
		server.expect(requestTo(endpoint)).andRespond(withSuccess(resource, MediaType.APPLICATION_JSON));

		Set<String> routes = routeSearchService.getRoutes("OPO", "ATH", 1);
		assertTrue(routes.contains("OPO-FRA-ATH"));
		assertTrue(routes.contains("OPO-PMO-ATH"));
		assertTrue(routes.contains("OPO-ATH"));
		assertTrue(routes.size() == 3);
	}

	@Test
	public void testGetRoute2Stops() throws Exception {

		Resource resource = resourceLoader.getResource("classpath:/routes.json");
		MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
		server.expect(requestTo(endpoint)).andRespond(withSuccess(resource, MediaType.APPLICATION_JSON));

		Set<String> routes = routeSearchService.getRoutes("OPO", "ATH", 2);
		
		assertTrue(routes.contains("OPO-FRA-ATH"));
		assertTrue(routes.contains("OPO-PMO-ATH"));
		assertTrue(routes.contains("OPO-ATH"));
		assertTrue(routes.contains("OPO-HAM-PMO-ATH"));
		assertTrue(routes.size() == 4);
	}
}
