package com.paypal.bfs.test.bookingserv.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.paypal.bfs.test.bookingserv.api.model.Booking;

public interface BookingResource {
	/**
	 * Create {@link Booking} resource
	 *
	 * @param booking the booking object
	 * @return the created booking
	 */
	@RequestMapping(value = "/v1/bfs/booking", method = RequestMethod.POST, produces = {
			"application/json" }, consumes = { "application/json" })
	ResponseEntity<Booking> create(@RequestBody Booking booking);

	@RequestMapping(value = "/v1/bfs/allbookings", method = RequestMethod.GET, produces = { "application/json" })
	ResponseEntity<List<Booking>> getAllBookings();

	// ----------------------------------------------------------
	// TODO - add a new operation for Get All the bookings resource.
	// ----------------------------------------------------------
}
