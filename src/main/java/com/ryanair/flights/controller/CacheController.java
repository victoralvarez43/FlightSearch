package com.ryanair.flights.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cacheManagament")
public class CacheController {

	private static Log logger = LogFactory.getLog(CacheController.class);

	@Autowired
	private CacheManager cacheManager;

	@GetMapping("/clean")
	public void cleanRouteCache() {
		cacheManager.getCacheNames().stream().map(cacheManager::getCache).forEach(c -> {
			c.clear();
			logger.info("Clean cache: " + c.getName());
		});
	}
}
