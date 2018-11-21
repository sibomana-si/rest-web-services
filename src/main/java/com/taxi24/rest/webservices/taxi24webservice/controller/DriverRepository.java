package com.taxi24.rest.webservices.taxi24webservice.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taxi24.rest.webservices.taxi24webservice.model.Driver;

public interface DriverRepository extends JpaRepository<Driver, Integer>{
	
	List<Driver> findByDriverStatus(Integer driverStatus);
	
}
