package com.ryanair.flights.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Rest configuration.
 * 
 * @author victor
 *
 */
@Configuration
@PropertySource("classpath:restClient.properties")
public class FligthSearchRestConfig {

	@Value("${rest.endpoint.timeout.connect}")
	private int connectTimeout;

	@Value("${rest.endpoint.timeout.read}")
	private int readTimeout;

	@Bean
	public RestTemplate restTemplate() {
		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		// httpRequestFactory.setConnectionRequestTimeout(connectTimeout);
		httpRequestFactory.setConnectTimeout(connectTimeout);
		httpRequestFactory.setReadTimeout(readTimeout);

		return new RestTemplate(httpRequestFactory);
	}

}
