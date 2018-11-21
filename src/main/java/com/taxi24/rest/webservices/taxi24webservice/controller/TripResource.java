package com.taxi24.rest.webservices.taxi24webservice.controller;

import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.taxi24.rest.webservices.taxi24webservice.exception.UserNotFoundException;
import com.taxi24.rest.webservices.taxi24webservice.model.Driver;
import com.taxi24.rest.webservices.taxi24webservice.controller.DriverRepository;
import com.taxi24.rest.webservices.taxi24webservice.model.Invoice;
import com.taxi24.rest.webservices.taxi24webservice.controller.InvoiceRepository;
import com.taxi24.rest.webservices.taxi24webservice.model.Trip;
import com.taxi24.rest.webservices.taxi24webservice.controller.TripRepository;



@RestController
public class TripResource {
	
	@Autowired
	private TripRepository tripRepository;
	
	@Autowired
	private DriverRepository driverRepository;
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	//create trip
	@PostMapping("/trips")
	public ResponseEntity<Object> createTrip(@Valid @RequestBody Trip trip) {	
		Trip savedTrip = tripRepository.save(trip);		
		
		//mark assigned driver as unavailable
		updateDriverStatus(savedTrip, 1);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedTrip.getId())
				.toUri();		
				
		return ResponseEntity.created(location).build();
	}
	
	// get all trips
	@GetMapping("/trips")
	public List<Trip> retrieveAllTrips() {
		return tripRepository.findAll();		
	}
	
	// get a specific trip by id
	@GetMapping("/trips/{id}")
	public Trip retrieveTrip(@PathVariable(value = "id") int id) {
		Optional<Trip> trip = tripRepository.findById(id);
		if(!trip.isPresent())
			throw new UserNotFoundException("id-" + id);
		return trip.get();
	}
		
	// get all active trips
	@GetMapping("/trips/active")
	public List<Trip> retrieveAllActiveTrips() {
		return tripRepository.findByTripStatus(0);		
	}
	
	// complete a trip	
	@PutMapping("/trips")
	//public @ResponseBody String completeTrip(@RequestBody Trip trip) {
	public @ResponseBody Invoice completeTrip(@RequestBody Trip trip) {
		Trip savedTrip = tripRepository.save(trip);	

		// mark driver as available on trip completion
		updateDriverStatus(savedTrip, 0);		
				
		// generate invoice
		Invoice generatedInvoice = generateInvoice(savedTrip);
		return generatedInvoice;
		//return "created invoice: " + generatedInvoice.toString();		
	}
		
	private void updateDriverStatus(Trip savedTrip, int driverStatus) {
		Integer tripDriverId = savedTrip.getDriverId();		
		Driver tripDriver = driverRepository.getOne(tripDriverId);		
		tripDriver.setDriverStatus(driverStatus);
		driverRepository.save(tripDriver);
	}
	
	private Invoice generateInvoice(Trip completedTrip) {
		long tripDuration = (completedTrip.getTripEndTime().getTime() - completedTrip.getTripStartTime().getTime())/1000;
		double tripCost = tripDuration * 4;
		Timestamp invoiceTime =  new Timestamp(System.currentTimeMillis());
		Invoice tripInvoice = new Invoice(completedTrip,  tripCost, invoiceTime);
		Invoice savedInvoice = invoiceRepository.save(tripInvoice);
		return savedInvoice;
	}
	
}
