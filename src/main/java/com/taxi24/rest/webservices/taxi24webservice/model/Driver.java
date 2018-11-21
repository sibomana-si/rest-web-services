package com.taxi24.rest.webservices.taxi24webservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;


@Entity
@Table(name="drivers", schema="taxi24db")
public class Driver {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min=2, message="First Name should have at least 2 characters")
	private String firstName;

	@Size(min=2, message="Last Name should have at least 2 characters")
	private String lastName;
	
	private Integer xLocation;
	private Integer yLocation;
	
	private Integer driverStatus = 0;
	
	
	protected Driver() {
	}
		
	public Driver(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Integer getId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public Integer getxLocation() {
		return xLocation;
	}

	public void setxLocation(Integer xLocation) {
		this.xLocation = xLocation;
	}

	public Integer getyLocation() {
		return yLocation;
	}

	public void setyLocation(Integer yLocation) {
		this.yLocation = yLocation;
	}

	public Integer getDriverStatus() {
		return driverStatus;
	}

	public void setDriverStatus(Integer driverStatus) {
		this.driverStatus = driverStatus;
	}	

	@Override
	public String toString() {
		return "Driver [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}
		
}
