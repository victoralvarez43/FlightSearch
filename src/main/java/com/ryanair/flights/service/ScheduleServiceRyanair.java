package com.ryanair.flights.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service()
@Qualifier("scheduleServiceRyanair")
public class ScheduleServiceRyanair implements ScheduleService {

}
