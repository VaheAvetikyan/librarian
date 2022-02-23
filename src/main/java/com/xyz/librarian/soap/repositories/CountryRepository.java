package com.xyz.librarian.soap.repositories;

import com.xyz.librarian.soap.countries.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {
    Country findCountryByName(String name);
}
