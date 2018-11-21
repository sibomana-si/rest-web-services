package com.taxi24.rest.webservices.taxi24webservice.controller;

import com.taxi24.rest.webservices.taxi24webservice.model.Driver;

public class DriverProximity implements Comparable<DriverProximity>{
	
	private final Driver driver;
	private final Double proximity;
	
	public DriverProximity(Driver driver, Double proximity) {
		this.driver = driver;
		this.proximity = proximity;		
	}

	public Driver getDriver() {
		return driver;
	}

	public Double getProximity() {
		return proximity;
	}

	@Override
	public int compareTo(DriverProximity that) {
		if (this.getProximity() < that.getProximity()) {
			return -1;
		} else if(this.getProximity() > that.getProximity()) {
			return 1;
		} else {
			return 0;
		}
	}
	
	@Override
	public String toString() {
		return "DriverDistance [driverId=" + driver.getId() + ", proximity=" + proximity + "]";
	}	

}
