package com.ryanair.test;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
import com.ryanair.flights.controller.FlightSearchController;
import com.ryanair.flights.model.Interconnection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { FlightSearchConfig.class })
@WebAppConfiguration
public class FlightSearchControllerTest {

	@Autowired
	private FlightSearchController flightSearchController;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ResourceLoader resourceLoader;

	@Value("${rest.ryanair.endpoint.routes}")
	private String endpoint;

	@Test
	public void testGetFlights() throws Exception {

		Resource resource = resourceLoader.getResource("classpath:/month.json");
		Resource resourceRoutes = resourceLoader.getResource("classpath:/routes.json");
		MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
		// routes
		server.expect(requestTo(endpoint)).andRespond(withSuccess(resourceRoutes, MediaType.APPLICATION_JSON));
		// months
		server.expect(requestTo("https://api.ryanair.com/timetable/3/schedules/HHN/CFU/years/2018/months/6"))
				.andRespond(withSuccess(resource, MediaType.APPLICATION_JSON));

		String init = "2018-06-08 12:30";
		String end = "2018-06-08 23:00";
		String arrivalDateTimeStr = "2018-06-08 21:20";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime departureDateTime = LocalDateTime.parse(init, formatter);
		LocalDateTime arrivalDateTime = LocalDateTime.parse(end, formatter);

		List<Interconnection> result = flightSearchController.flightSearch("HHN", "CFU", departureDateTime,
				arrivalDateTime, 1);
		assertTrue(result.get(0).getLegs().get(0).getArrivalDateTime()
				.compareTo(LocalDateTime.parse(arrivalDateTimeStr, formatter)) == 0);
		assertTrue(result.size() == 1);
	}

	@Test
	public void testGetFlights2() throws Exception {

		Resource resource = resourceLoader.getResource("classpath:/month.json");
		Resource resourceRoutes = resourceLoader.getResource("classpath:/routes.json");
		MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
		// routes
		server.expect(requestTo(endpoint)).andRespond(withSuccess(resourceRoutes, MediaType.APPLICATION_JSON));
		// months
		server.expect(requestTo("https://api.ryanair.com/timetable/3/schedules/HHN/CFU/years/2018/months/6"))
				.andRespond(withSuccess(resource, MediaType.APPLICATION_JSON));

		String init = "2018-06-07 12:30";
		String end = "2018-06-08 23:00";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime departureDateTime = LocalDateTime.parse(init, formatter);
		LocalDateTime arrivalDateTime = LocalDateTime.parse(end, formatter);

		List<Interconnection> result = flightSearchController.flightSearch("HHN", "CFU", departureDateTime,
				arrivalDateTime, 1);
		assertTrue(result.size() == 2);
	}

}
