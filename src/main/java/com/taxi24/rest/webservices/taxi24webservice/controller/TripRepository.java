package com.taxi24.rest.webservices.taxi24webservice.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taxi24.rest.webservices.taxi24webservice.model.Trip;

public interface TripRepository extends JpaRepository<Trip, Integer>{
	List<Trip> findByTripStatus(Integer tripStatus);
}
