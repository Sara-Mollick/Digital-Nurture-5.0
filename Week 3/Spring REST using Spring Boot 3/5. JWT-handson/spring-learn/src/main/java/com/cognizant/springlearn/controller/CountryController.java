package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.Country;
import com.cognizant.springlearn.service.CountryService;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/countries")
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryService countryService;

    @GetMapping
    public List<Country> getAllCountries() {
        LOGGER.info("START - getAllCountries");
        return countryService.getAllCountries();
    }

    @GetMapping("/{code}")
    public Country getCountryByCode(@PathVariable String code) {
        LOGGER.info("START - getCountryByCode");
        Country country = countryService.getCountryByCode(code);
        if (country == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found");
        }
        return country;
    }

    @PostMapping
    public Country addCountry(@RequestBody @Valid Country country) {
        LOGGER.info("Start - addCountry");
        countryService.addCountry(country);
        LOGGER.info("End - addCountry");
        return country;
    }

    @PutMapping
    public Country updateCountry(@RequestBody @Valid Country country) {
        LOGGER.info("Start - updateCountry");
        // Simple update implementation for mock list
        Country existing = countryService.getCountryByCode(country.getCode());
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found");
        }
        existing.setName(country.getName());
        LOGGER.info("End - updateCountry");
        return existing;
    }

    @DeleteMapping("/{code}")
    public void deleteCountry(@PathVariable String code) {
        LOGGER.info("Start - deleteCountry");
        Country existing = countryService.getCountryByCode(code);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found");
        }
        countryService.getAllCountries().remove(existing);
        LOGGER.info("End - deleteCountry");
    }
}
