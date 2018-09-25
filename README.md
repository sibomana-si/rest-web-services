# Taxi-Web-Service

A RESTful API that can be used to manage a fleet of drivers, and allocate drivers to passengers.

## Setup
- use the **_data.sql_** script to create the database and relevant tables
- import the project into your IDE
- update the **_application.properties_** file (src/main/resources/) to specify your database credentials (username and password)
- execute the file **_Taxi24WebServiceApplication.java_** to start the application

## Features
### Drivers
- get a specific driver by id
- get list of all drivers
- get list of all available drivers
- get list of all available drivers within a 3km distance of a specific location
- register a new driver

### Riders
- get a specific rider by id
- get a list of all riders
- get a list of the 3 closest drivers, for a specific rider
- register a new rider

### Trips
- register a new trip request
- get a list of all active trips
- record completed trip

### Invoices
- create a new invoice

