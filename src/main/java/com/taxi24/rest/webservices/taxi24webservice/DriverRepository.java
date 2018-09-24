package com.taxi24.rest.webservices.taxi24webservice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Integer>{
	
	List<Driver> findByDriverStatus(Integer driverStatus);
	
}
