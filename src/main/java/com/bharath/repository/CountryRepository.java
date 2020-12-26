package com.bharath.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bharath.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Serializable> {

}
