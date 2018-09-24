package com.taxi24.rest.webservices.taxi24webservice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Integer>{
	List<Trip> findByTripStatus(Integer tripStatus);
}
