package com.cognizant.springlearn.service;

import com.cognizant.springlearn.Country;
import com.cognizant.springlearn.dao.CountryDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CountryService {

    @Autowired
    private CountryDao countryDao;

    @Transactional(readOnly = true)
    public List<Country> getAllCountries() {
        return countryDao.getAllCountries();
    }

    @Transactional(readOnly = true)
    public Country getCountryByCode(String code) {
        return countryDao.getCountryByCode(code);
    }

    @Transactional
    public void addCountry(Country country) {
        countryDao.addCountry(country);
    }
}
