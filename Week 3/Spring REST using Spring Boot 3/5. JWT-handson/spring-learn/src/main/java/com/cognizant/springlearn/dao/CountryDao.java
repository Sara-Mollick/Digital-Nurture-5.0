package com.cognizant.springlearn.dao;

import com.cognizant.springlearn.Country;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

@Repository
public class CountryDao {
    private static List<Country> countries;

    static {
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        countries = (List<Country>) context.getBean("countryList", ArrayList.class);
    }

    public List<Country> getAllCountries() {
        return countries;
    }

    public Country getCountryByCode(String code) {
        return countries.stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);
    }

    public void addCountry(Country country) {
        countries.add(country);
    }
}
