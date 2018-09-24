package com.taxi24.rest.webservices.taxi24webservice;

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


@RestController
public class TripResource {
	
	@Autowired
	private TripRepository tripRepository;
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	//create trip
	@PostMapping("/trips")
	public ResponseEntity<Object> createTrip(@Valid @RequestBody Trip trip) {	
		Trip savedTrip = tripRepository.save(trip);		
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
	public Optional<Trip> retrieveTrip(@PathVariable int id) {
		Optional<Trip> trip = tripRepository.findById(id);
		if(!trip.isPresent())
			throw new UserNotFoundException("id-" + id);
		return trip;
	}
		
	// get all active trips
	@GetMapping("/trips/active")
	public List<Trip> retrieveAllActiveTrips() {
		return tripRepository.findByTripStatus(0);		
	}
	
	// complete a trip	
	@PutMapping("/trips")
	public @ResponseBody String completeTrip(@RequestBody Trip trip) {
		Trip savedTrip = tripRepository.save(trip);	
		
		long tripDuration = (savedTrip.getTripEndTime().getTime() - savedTrip.getTripStartTime().getTime())/1000;
		double tripCost = tripDuration * 4;
		Timestamp invoiceTime =  new Timestamp(System.currentTimeMillis());
		Invoice tripInvoice = new Invoice(trip,  tripCost, invoiceTime);
		Invoice savedInvoice = invoiceRepository.save(tripInvoice);
		return "created invoice: " + savedInvoice.toString();		
	}
			
}
