package com.ryanair.flights.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@EnableRetry
@ComponentScan(basePackages = { "com.ryanair.flights.*" })
@PropertySource("classpath:flightSearch.properties")
@Import({FligthSearchRestConfig.class, FlightSearchCacheConfig.class})
public class FlightSearchConfig implements WebMvcConfigurer {

}
