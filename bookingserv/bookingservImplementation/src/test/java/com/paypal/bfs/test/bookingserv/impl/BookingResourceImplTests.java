package com.paypal.bfs.test.bookingserv.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.bfs.test.bookingserv.api.model.Booking;
import com.paypal.bfs.test.bookingserv.helper.BookingHelper;
import com.paypal.bfs.test.bookingserv.impl.jpa.repo.BookingRepo;
import com.paypal.bfs.test.bookingserv.jpa.entity.BookingEntity;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebClient
@WebMvcTest(controllers = BookingResourceImpl.class)
public class BookingResourceImplTests {

	private static final String BOOKING_RESOURCE_PATH = "/v1/bfs/";
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookingRepo repository;

	@Test
	void addBooking() throws Exception {
		Booking booking = BookingHelper.getBookingObj();
		BookingEntity convertToEntity = BookingHelper.convertToEntity(booking);
		when(repository.existsById(booking.getId().longValue())).thenReturn(false);
		when(repository.save(convertToEntity)).thenReturn(convertToEntity);
		mockMvc.perform(post(BOOKING_RESOURCE_PATH + "booking").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(booking))).andExpect(status().isCreated())
				.andExpect(jsonPath("id", is(booking.getId())));
	}

	@Test
	void addBookingvalidationCheck() throws Exception {
		Booking booking = BookingHelper.getBookingObj();
		booking.setFirstName(null);
		BookingEntity convertToEntity = BookingHelper.convertToEntity(booking);
		when(repository.existsById(booking.getId().longValue())).thenReturn(false);
		when(repository.save(convertToEntity)).thenReturn(convertToEntity);
		mockMvc.perform(post(BOOKING_RESOURCE_PATH + "booking").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(booking))).andExpect(status().isBadRequest());
	}

	@Test
	void duplicateBookingvalidationCheck() throws Exception {
		Booking booking = BookingHelper.getBookingObj();
		when(repository.existsById(booking.getId().longValue())).thenReturn(true);
		mockMvc.perform(post(BOOKING_RESOURCE_PATH + "booking").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(booking))).andExpect(status().isConflict());
	}

	@Test
	void bookingwithNoBody() throws Exception {
		mockMvc.perform(post(BOOKING_RESOURCE_PATH + "booking").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void bookingwithInvalidContentType() throws Exception {
		mockMvc.perform(post(BOOKING_RESOURCE_PATH + "booking")).andExpect(status().isUnsupportedMediaType());
	}

	@Test
	void getBookingbookinglist() throws Exception {
		Booking booking = BookingHelper.getBookingObj();
		when(repository.findAllByOrderByIdAsc()).thenReturn(List.of(BookingHelper.convertToEntity(booking)));
		mockMvc.perform(get(BOOKING_RESOURCE_PATH + "allbookings")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(booking.getId())));
	}

	@Test
	void getBookingbookinglistwithNoRecords() throws Exception {
		when(repository.findAll()).thenReturn(List.of());
		mockMvc.perform(get(BOOKING_RESOURCE_PATH + "allbookings")).andExpect(status().isOk());
	}

}
