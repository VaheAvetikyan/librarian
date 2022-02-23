package com.xyz.librarian.soap.repositories;

import com.xyz.librarian.soap.countries.Country;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class CountryRepository {
    private static final Map<String, Country> countries = new HashMap<>();

    @PostConstruct
    public void initData() {
        Country armenia = new Country();
        armenia.setName("Armenia");
        armenia.setCapital("Yerevan");
        armenia.setIsoCode("ARM");
        armenia.setPopulation(46704314);

        countries.put(armenia.getName(), armenia);

        Country spain = new Country();
        spain.setName("Spain");
        spain.setCapital("Madrid");
        spain.setIsoCode("ESP");
        spain.setPopulation(46704314);

        countries.put(spain.getName(), spain);

        Country poland = new Country();
        poland.setName("Poland");
        poland.setCapital("Warsaw");
        poland.setIsoCode("POL");
        poland.setPopulation(38186860);

        countries.put(poland.getName(), poland);

        Country uk = new Country();
        uk.setName("United Kingdom");
        uk.setCapital("London");
        uk.setIsoCode("GBR");
        uk.setPopulation(63705000);

        countries.put(uk.getName(), uk);
    }

    public Country findCountryByName(String name) {
        Assert.notNull(name, "The country's name must not be null");
        return countries.get(name);
    }
}
