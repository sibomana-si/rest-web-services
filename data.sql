create database taxi24db;

use taxi24db;

create table drivers(
	id int(10) unsigned not null auto_increment, 
	first_name varchar(20) not null, 
	last_name varchar(20) not null, 
	x_location smallint(5) unsigned, 
	y_location smallint(5) unsigned, 
	driver_status tinyint(1) not null default 0, 
    	primary key(id),
	key(driver_status)
	);


create table riders(
	id int(10) unsigned not null auto_increment,
	first_name varchar(20) not null, 
	last_name varchar(20) not null,
	x_location smallint(5) unsigned, 
	y_location smallint(5) unsigned,
	primary key(id)
	);


create table trips(
	id int(10) unsigned not null auto_increment,
	driver_id int(10) unsigned not null,
	rider_id int(10) unsigned not null,
	trip_origin_x smallint(5) unsigned not null,
	trip_origin_y smallint(5) unsigned not null,
	trip_destination_x smallint(5) unsigned not null,
	trip_destination_y smallint(5) unsigned not null,
	trip_start_time Timestamp not null,
	trip_end_time Timestamp,
	trip_status tinyint(1) not null default 0,
	primary key(id),
	key(trip_status)
	);
	

create table invoices(
	id int(10) unsigned not null auto_increment,
	trip_id int(10) unsigned not null,
	invoice_amount decimal(9,2) not null,
	invoice_time Timestamp not null,
	primary key(id),
	key(trip_id)
	);


