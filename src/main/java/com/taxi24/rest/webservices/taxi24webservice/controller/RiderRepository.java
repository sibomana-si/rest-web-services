package com.taxi24.rest.webservices.taxi24webservice.controller;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taxi24.rest.webservices.taxi24webservice.model.Rider;

public interface RiderRepository extends JpaRepository<Rider, Integer> {

}
