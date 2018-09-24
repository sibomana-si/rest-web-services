package com.taxi24.rest.webservices.taxi24webservice;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
public class RiderResource {
	
	@Autowired
	private RiderRepository riderRepository;
	
	@Autowired
	private DriverRepository driverRepository;
		
	// create a new rider
	@PostMapping("/riders")
	public ResponseEntity<Object> createRider(@Valid @RequestBody Rider rider) {
		Rider savedRider = riderRepository.save(rider);		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedRider.getId())
				.toUri();		
				
		return ResponseEntity.created(location).build();
	}
		
	// get all riders
	@GetMapping("/riders")
	public List<Rider> retrieveAllRiders() {
		return riderRepository.findAll();		
	}
	
	// get a specific rider by id
	@GetMapping("/riders/{id}")
	public Optional<Rider> retrieveRider(@PathVariable int id) {
		Optional<Rider> rider = riderRepository.findById(id);
		
		if(!rider.isPresent())
			throw new UserNotFoundException("id-" + id);
		
		return rider;
	}
	
	// get nearby drivers
	@GetMapping("/riders/{id}/nearby")
	public List<Driver> retrieveNearbyDrivers(@PathVariable int id) {
		List<Driver> nearbyDrivers = new ArrayList<>();
		PriorityQueue<DriverProximity>  driverProximityQueue = new PriorityQueue<>();
		
		Optional<Rider> rider = riderRepository.findById(id);		
		if(!rider.isPresent())
			throw new UserNotFoundException("id-" + id);
		
		List<Driver> availableDrivers = driverRepository.findByDriverStatus(0);		
		
		for(Driver availableDriver: availableDrivers) {
			Double xDistance = Math.pow((availableDriver.getxLocation() - rider.get().getxLocation()), 2);
			Double yDistance = Math.pow((availableDriver.getyLocation() - rider.get().getyLocation()), 2);
			Double driverProximity = Math.sqrt(xDistance + yDistance);			
			driverProximityQueue.add(new DriverProximity(availableDriver, driverProximity));
		}
				
		
		for (int i = 0; i < availableDrivers.size(); i++) {
			if(i >= 3) break;
			nearbyDrivers.add(driverProximityQueue.poll().getDriver());			
		}				
		
		return nearbyDrivers;
	}
	
			
}
