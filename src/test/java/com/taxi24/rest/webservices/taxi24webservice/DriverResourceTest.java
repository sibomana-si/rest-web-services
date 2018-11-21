package com.taxi24.rest.webservices.taxi24webservice;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.taxi24.rest.webservices.taxi24webservice.controller.DriverResource;
import com.taxi24.rest.webservices.taxi24webservice.model.Driver;


@RunWith(SpringRunner.class)
@WebMvcTest(DriverResource.class)
public class DriverResourceTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DriverResource driverResource;
	

	@Test
	public void retrieveDriver() throws Exception {
		
		when(driverResource.retrieveDriver(1001)).thenReturn(new Driver("john", "doe"));
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/drivers/1001")
				.accept(MediaType.APPLICATION_JSON);
	    
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json("{firstName:john, lastName:doe}"));			
	}
	
	
	@Test
	public void retrieveAllDrivers() throws Exception {
		
		when(driverResource.retrieveAllDrivers()).thenReturn(Arrays.asList(new Driver("john", "doe"),
				new Driver("jane", "doe")));
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/drivers")
				.accept(MediaType.APPLICATION_JSON);
	    
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json("[{firstName:john, lastName:doe}, {firstName:jane, lastName:doe}]"));			
	} 

	
	@Test
	public void retrieveAllAvailableDrivers() throws Exception {
		
		when(driverResource.retrieveAllAvailableDrivers()).thenReturn(Arrays.asList(new Driver("will", "smith"),
				new Driver("anne", "frank")));
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/drivers/available")
				.accept(MediaType.APPLICATION_JSON);
	    
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json("[{firstName:will, lastName:smith}, {firstName:anne, lastName:frank}]"));			
	} 

	
	@Test
	public void retrieveAllNearbyDrivers() throws Exception {
		
		when(driverResource.retrieveAllNearbyDrivers(3, 4)).thenReturn(Arrays.asList(new Driver("michael", "jordan"),
				new Driver("serena", "williams")));
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/drivers/nearby")
				.param("xLoc", "3")
				.param("yLoc", "4")
				.accept(MediaType.APPLICATION_JSON);
	    
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json("[{firstName:michael, lastName:jordan}, {firstName:serena, lastName:williams}]"));			
	} 

	
	@Test
	public void createDriver() throws Exception {
		
		URI location = new URI("http://localhost/drivers/1001");
		when(driverResource.createDriver(any(Driver.class))).thenReturn(ResponseEntity.created(location).build());
		
		RequestBuilder request = MockMvcRequestBuilders
				.post("/drivers")
				.accept(MediaType.APPLICATION_JSON)			
				.content("{\"firstName\": \"george\",\"lastName\": \"bull\",\"xLocation\":23,\"yLocation\":31}")
				.contentType(MediaType.APPLICATION_JSON);
			
		mockMvc.perform(request)
				.andExpect(status().isCreated())
				.andExpect(header().string("location", containsString("/drivers/1001")));	
	} 
	
	
}
