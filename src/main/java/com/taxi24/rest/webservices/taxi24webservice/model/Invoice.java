package com.taxi24.rest.webservices.taxi24webservice.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="invoices", schema="taxi24db")
public class Invoice {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JsonIgnore
	private Trip trip;
	
	private Double invoiceAmount;
	private Timestamp invoiceTime;
	
	protected Invoice() {
	}	
	
	public Invoice(Trip trip, Double invoiceAmount, Timestamp invoiceTime) {
		this.trip = trip;
		this.invoiceAmount = invoiceAmount;
		this.invoiceTime = invoiceTime;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Trip getTrip() {
		return trip;
	}
	
	public void setTrip(Trip trip) {
		this.trip = trip;
	}
	
	public Double getInvoiceAmount() {
		return invoiceAmount;
	}
	
	public void setInvoiceAmount(Double invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	
	public Timestamp getInvoiceTime() {
		return invoiceTime;
	}
	
	public void setInvoiceTime(Timestamp invoiceTime) {
		this.invoiceTime = invoiceTime;
	}

	@Override
	public String toString() {
		return "Invoice [id=" + id + ", invoiceAmount=" + invoiceAmount + ", invoiceTime=" + invoiceTime + "]";
	}
		

}
