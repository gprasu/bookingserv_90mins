package com.paypal.bfs.test.bookingserv.impl.exceptionhandlers;

import org.springframework.http.HttpStatus;

/**
 * This Exception  is used for Booking service application
 * @author gprasanth
 *
 */
public class BookingServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final HttpStatus statusCode;

	public BookingServiceException() {
		super();
		this.statusCode = null;
	}

	public BookingServiceException(String message) {
		super(message);
		this.statusCode = null;
	}

	public BookingServiceException(String message, HttpStatus statusCode) {
		super(message);
		this.statusCode = statusCode;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

}
