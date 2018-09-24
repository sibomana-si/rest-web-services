package com.taxi24.rest.webservices.taxi24webservice;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="trips", schema="taxi24db")
public class Trip {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer riderId;		
	private Integer driverId;
	private Integer tripOrigin_x;
	private Integer tripOrigin_y;
	private Integer tripDestination_x;
	private Integer tripDestination_y;
	private Timestamp tripStartTime;
	private Timestamp tripEndTime;
	private Integer tripStatus = 0;
	
	@OneToOne(mappedBy="trip")
	@JsonIgnore
	private Invoice invoice;
	
	protected Trip() {
	}		
	
	public Trip(Integer riderId, Integer driverId, Integer tripOrigin_x, Integer tripOrigin_y,
			Integer tripDestination_x, Integer tripDestination_y, Timestamp tripStartTime) 
	{
		this.riderId = riderId;
		this.driverId = driverId;
		this.tripOrigin_x = tripOrigin_x;
		this.tripOrigin_y = tripOrigin_y;
		this.tripDestination_x = tripDestination_x;
		this.tripDestination_y = tripDestination_y;
		this.tripStartTime = tripStartTime;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getRiderId() {
		return riderId;
	}
	
	public void setRiderId(Integer riderId) {
		this.riderId = riderId;
	}
	
	public Integer getDriverId() {
		return driverId;
	}
	
	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}
	
	public Integer getTripOrigin_x() {
		return tripOrigin_x;
	}
	
	public void setTripOrigin_x(Integer tripOrigin_x) {
		this.tripOrigin_x = tripOrigin_x;
	}
	
	public Integer getTripOrigin_y() {
		return tripOrigin_y;
	}
	
	public void setTripOrigin_y(Integer tripOrigin_y) {
		this.tripOrigin_y = tripOrigin_y;
	}
	
	public Integer getTripDestination_x() {
		return tripDestination_x;
	}
	
	public void setTripDestination_x(Integer tripDestination_x) {
		this.tripDestination_x = tripDestination_x;
	}
	
	public Integer getTripDestination_y() {
		return tripDestination_y;
	}
	
	public void setTripDestination_y(Integer tripDestination_y) {
		this.tripDestination_y = tripDestination_y;
	}
	
	public Timestamp getTripStartTime() {
		return tripStartTime;
	}
	
	public void setTripStartTime(Timestamp tripStartTime) {
		this.tripStartTime = tripStartTime;
	}
	
	public Timestamp getTripEndTime() {
		return tripEndTime;
	}
	
	public void setTripEndTime(Timestamp tripEndTime) {
		this.tripEndTime = tripEndTime;
	}
	
	public Integer getTripStatus() {
		return tripStatus;
	}
	
	public void setTripStatus(Integer tripStatus) {
		this.tripStatus = tripStatus;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	@Override
	public String toString() {
		return "Trip [id=" + id + ", tripOrigin_x=" + tripOrigin_x + ", tripOrigin_y=" + tripOrigin_y
				+ ", tripDestination_x=" + tripDestination_x + ", tripDestination_y=" + tripDestination_y
				+ ", tripStartTime=" + tripStartTime + ", tripEndTime=" + tripEndTime + "]";
	}
		
}
