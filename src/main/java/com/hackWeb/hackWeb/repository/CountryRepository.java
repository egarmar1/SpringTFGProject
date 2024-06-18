package com.hackWeb.hackWeb.repository;

import com.hackWeb.hackWeb.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country,Integer> {
}
