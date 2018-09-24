package com.taxi24.rest.webservices.taxi24webservice;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
public class DriverResource {
	
	@Autowired
	private DriverRepository driverRepository;
	
	// create a new driver
	@PostMapping("/drivers")
	public ResponseEntity<Object> createDriver(@Valid @RequestBody Driver driver) {
		Driver savedDriver = driverRepository.save(driver);		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedDriver.getId())
				.toUri();		
			
		return ResponseEntity.created(location).build();
	}
		
	// get all drivers
	@GetMapping("/drivers")
	public List<Driver> retrieveAllDrivers() {
		return driverRepository.findAll();		
	}
	
	// get a specific driver by id
	@GetMapping("/drivers/{id}")
	public Optional<Driver> retrieveDriver(@PathVariable int id) {
		Optional<Driver> driver = driverRepository.findById(id);
		
		if(!driver.isPresent())
			throw new UserNotFoundException("id-" + id);
		
		return driver;
	}
	
	// get all available drivers
	@GetMapping("/drivers/available")
	public List<Driver> retrieveAllAvailableDrivers() {
		return driverRepository.findByDriverStatus(0);		
	}
		
	// get all available drivers within a 3KM distance of provided location 
	@GetMapping("/drivers/nearby")
	public List<Driver> retrieveAllNearbyDrivers(@RequestParam Integer xLoc, @RequestParam Integer yLoc) {
		List<Driver> nearbyDrivers = new ArrayList<>();
		List<Driver> availableDrivers = driverRepository.findByDriverStatus(0);
		
		for(Driver driver : availableDrivers) {
			Double xDistance = Math.pow((driver.getxLocation() - xLoc), 2);
			Double yDistance = Math.pow((driver.getyLocation() - yLoc), 2);			
			Double driverDistance = Math.sqrt(xDistance + yDistance);
			
			if(driverDistance <= 3) {				
				nearbyDrivers.add(driver);
			}			
		}
		
		return nearbyDrivers;
	}
		
		
}
