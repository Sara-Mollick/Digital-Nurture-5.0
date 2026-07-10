package com.cognizant.springlearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hands on 4: Country bean, loaded from Spring XML configuration (country.xml).
 */
public class Country {

    private static final Logger LOGGER = LoggerFactory.getLogger(Country.class);

    private String code;
    private String name;

    public Country() {
        LOGGER.debug("Inside Country Constructor.");
    }

    public String getCode() {
        LOGGER.debug("Inside getCode(). Value : {}", code);
        return code;
    }

    public void setCode(String code) {
        LOGGER.debug("Inside setCode(). Value : {}", code);
        this.code = code;
    }

    public String getName() {
        LOGGER.debug("Inside getName(). Value : {}", name);
        return name;
    }

    public void setName(String name) {
        LOGGER.debug("Inside setName(). Value : {}", name);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country [code=" + code + ", name=" + name + "]";
    }
}
