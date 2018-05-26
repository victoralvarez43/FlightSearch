package com.ryanair.test;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.ryanair.flights.config.FlightSearchConfig;
import com.ryanair.flights.model.Month;
import com.ryanair.flights.service.MonthService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { FlightSearchConfig.class })
@WebAppConfiguration
public class MonthAPITest {

	@Autowired
	private MonthService monthService;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ResourceLoader resourceLoader;

	@Test
	public void testGetRoute() throws Exception {

		Resource resource = resourceLoader.getResource("classpath:/month.json");
		MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
		server.expect(requestTo("https://api.ryanair.com/timetable/3/schedules/ZZZ/XXX/years/2018/months/6"))
				.andRespond(withSuccess(resource, MediaType.APPLICATION_JSON));
		server.expect(requestTo("https://api.ryanair.com/timetable/3/schedules/ZZZ/XXX/years/2018/months/7"))
				.andRespond(withSuccess(resource, MediaType.APPLICATION_JSON));

		String init = "2018-06-08 12:30";
		String end = "2018-07-09 12:30";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime departureDateTime = LocalDateTime.parse(init, formatter);
		LocalDateTime arrivalDateTime = LocalDateTime.parse(end, formatter);

		Set<Month> result = monthService.getMonths("ZZZ", "XXX", departureDateTime, arrivalDateTime);
		assertTrue(result.size() == 2);
	}

	@Test
	public void testGetRoute2Years() throws Exception {

		Resource resource = resourceLoader.getResource("classpath:/month.json");
		MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
		server.expect(requestTo("https://api.ryanair.com/timetable/3/schedules/ZZZ/XXX/years/2018/months/12"))
				.andRespond(withSuccess(resource, MediaType.APPLICATION_JSON));
		server.expect(requestTo("https://api.ryanair.com/timetable/3/schedules/ZZZ/XXX/years/2019/months/1"))
				.andRespond(withSuccess(resource, MediaType.APPLICATION_JSON));

		String init = "2018-12-08 12:30";
		String end = "2019-01-09 12:30";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime departureDateTime = LocalDateTime.parse(init, formatter);
		LocalDateTime arrivalDateTime = LocalDateTime.parse(end, formatter);

		Set<Month> result = monthService.getMonths("ZZZ", "XXX", departureDateTime, arrivalDateTime);
		assertTrue(result.size() == 2);
	}
}
