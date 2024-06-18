package com.hackWeb.hackWeb.service;

import com.hackWeb.hackWeb.entity.Country;
import com.hackWeb.hackWeb.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    public List<Country> getAll(){
        return countryRepository.findAll();
    }
}
