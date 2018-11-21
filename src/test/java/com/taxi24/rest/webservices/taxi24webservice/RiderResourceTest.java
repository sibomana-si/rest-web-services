package com.taxi24.rest.webservices.taxi24webservice;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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

import com.taxi24.rest.webservices.taxi24webservice.controller.RiderResource;
import com.taxi24.rest.webservices.taxi24webservice.model.Driver;
import com.taxi24.rest.webservices.taxi24webservice.model.Rider;

@RunWith(SpringRunner.class)
@WebMvcTest(RiderResource.class)
public class RiderResourceTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private RiderResource riderResource;
	
	@Test
	public void retrieveRider() throws Exception {
		
		when(riderResource.retrieveRider(2001)).thenReturn(new Rider("jane", "lee"));
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/riders/2001")
				.accept(MediaType.APPLICATION_JSON);
	    
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json("{firstName:jane, lastName:lee}"));			
	}

	
	@Test
	public void retrieveAllRiders() throws Exception {
		
		when(riderResource.retrieveAllRiders()).thenReturn(Arrays.asList(new Rider("dan", "yao"),
				new Rider("liz", "tyler")));
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/riders")
				.accept(MediaType.APPLICATION_JSON);
	    
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json("[{firstName:dan, lastName:yao}, {firstName:liz, lastName:tyler}]"));			
	}
	
	
	@Test
	public void retrieveNearbyDrivers() throws Exception {
		
		when(riderResource.retrieveNearbyDrivers(2001)).thenReturn(Arrays.asList(new Driver("will", "smith"),
				new Driver("anne", "frank"), new Driver("joan", "arc")));
		
		RequestBuilder request = MockMvcRequestBuilders
				.get("/riders/2001/nearby")
				.accept(MediaType.APPLICATION_JSON);
	    
		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(content().json("[{firstName:will, lastName:smith}"
						+ ",{firstName:anne, lastName:frank}"
						+ ",{firstName:joan, lastName:arc}]"));			
	}

	
	@Test
	public void createRider() throws Exception {
		
		URI location = new URI("http://localhost/riders/2001");
		when(riderResource.createRider(any(Rider.class))).thenReturn(ResponseEntity.created(location).build());
		
		RequestBuilder request = MockMvcRequestBuilders
				.post("/riders")
				.accept(MediaType.APPLICATION_JSON)			
				.content("{\"firstName\": \"ada\",\"lastName\": \"lovelace\",\"xLocation\":15,\"yLocation\":17}")
				.contentType(MediaType.APPLICATION_JSON);
			
		mockMvc.perform(request)
				.andExpect(status().isCreated())
				.andExpect(header().string("location", containsString("/riders/2001")));	
	}
	
	
}
