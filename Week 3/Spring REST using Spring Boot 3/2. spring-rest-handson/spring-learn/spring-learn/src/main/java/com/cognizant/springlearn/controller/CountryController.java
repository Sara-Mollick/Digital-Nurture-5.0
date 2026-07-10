package com.cognizant.springlearn.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springlearn.Country;
import com.cognizant.springlearn.service.CountryService;

@RestController
public class CountryController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

	private final CountryService countryService;

	public CountryController(CountryService countryService) {
		LOGGER.debug("Inside CountryController Constructor.");
		this.countryService = countryService;
	}

	@GetMapping("/country")
	public Country getCountryIndia() {
		LOGGER.info("START");
		ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
		Country country = context.getBean("in", Country.class);
		LOGGER.info("END");
		return country;
	}

	@GetMapping("/countries")
	@SuppressWarnings("unchecked")
	public List<Country> getAllCountries() {
		LOGGER.info("START");
		ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
		List<Country> countryList = (List<Country>) context.getBean("countryList", List.class);
		LOGGER.info("END");
		return countryList;
	}

	@GetMapping("/country/{code}")
	public Country getCountry(@PathVariable String code) {
		LOGGER.info("START");
		Country country = countryService.getCountry(code);
		LOGGER.info("END");
		return country;
	}

}
