package com.bharath.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bharath.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, Serializable> {

	List<City> findByStateId(Integer stateId);
}
