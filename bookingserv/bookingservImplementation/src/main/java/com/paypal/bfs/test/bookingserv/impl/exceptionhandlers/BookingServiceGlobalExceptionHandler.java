package com.paypal.bfs.test.bookingserv.impl.exceptionhandlers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.paypal.bfs.test.bookingserv.impl.BookingResourceImpl;

/**
 * This Global Exception  is used for Booking service application
 * @author gprasanth
 *
 */
@ControllerAdvice
public class BookingServiceGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(BookingResourceImpl.class);

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		LOG.error("Method arguments Missing Exception occured {} for request {}", ex, request);
		return new ResponseEntity<>(new ConstructError(HttpStatus.BAD_REQUEST.getReasonPhrase(),
				"Validation Failed " + ex.getBindingResult().toString()), status);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		LOG.error("Input Message is not proper failed with cause{} exception {} for request {}",
				ex.getMostSpecificCause(), ex, request);
		return new ResponseEntity<>(
				new ConstructError(HttpStatus.BAD_REQUEST.getReasonPhrase(), "Request provide is not valid request"),
				headers, status);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(HttpServletRequest request,
			ConstraintViolationException cve) {
		LOG.error("SQL constraint Exception occured {} for request {}", cve, request);
		return new ResponseEntity<>(new ConstructError(HttpStatus.BAD_REQUEST.getReasonPhrase(),
				"Sql Constraint error, please check with administrator."), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleSQLConstraintViolationException(HttpServletRequest request,
			DataIntegrityViolationException cve) {
		LOG.error("SQL Data Integrity violation Exception occured {} for request {}", cve, request);
		return new ResponseEntity<>(new ConstructError(HttpStatus.BAD_REQUEST.getReasonPhrase(),
				"SQL Update or Insert failed due to Constraints present in table, please check with administrator."),
				HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(BookingServiceException.class)
	public final ResponseEntity<Object> handleBookingServiceExceptions(BookingServiceException ex, WebRequest request) {
		LOG.error("Exception details {} for request {}", ex, request);
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		if (ex.getStatusCode() != null) {
			status = ex.getStatusCode();
		}

		return new ResponseEntity<>(new ConstructError(status.getReasonPhrase(), ex.getMessage()), status);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		LOG.error("Exception details {} for request {}", ex, request);
		return new ResponseEntity<>(new ConstructError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
				"Error occured while trying process, Please try again. If problem is persistent check with administrator."),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
