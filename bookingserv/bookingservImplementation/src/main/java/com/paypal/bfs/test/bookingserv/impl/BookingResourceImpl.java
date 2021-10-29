package com.paypal.bfs.test.bookingserv.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import com.paypal.bfs.test.bookingserv.api.BookingResource;
import com.paypal.bfs.test.bookingserv.api.model.Address;
import com.paypal.bfs.test.bookingserv.api.model.Booking;
import com.paypal.bfs.test.bookingserv.impl.exceptionhandlers.BookingServiceException;
import com.paypal.bfs.test.bookingserv.impl.jpa.repo.BookingRepo;
import com.paypal.bfs.test.bookingserv.jpa.entity.AddressEntity;
import com.paypal.bfs.test.bookingserv.jpa.entity.BookingEntity;

/**
 * This controller is used for Adding or retrieving Booking information
 * @author gprasanth
 *
 */
@RestController
public class BookingResourceImpl implements BookingResource {

	@Autowired
	private BookingRepo repository;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
	private final static SimpleDateFormat yymmdd = new SimpleDateFormat("yyyy-MM-dd");

	private static final Logger LOG = LoggerFactory.getLogger(BookingResourceImpl.class);

	/**
	 *This Method is used to Add entry of the Booking in to DB
	 */
	@Override
	public ResponseEntity<Booking> create(@RequestBody Booking booking) {
		LOG.debug("Create new Booking request came to the service {}", booking);
		validate(booking);
		if (repository.existsById(booking.getId().longValue())) {
			LOG.warn("Attempt to add duplicate Booking Id {}", booking);
			throw new BookingServiceException("Booking Id " + booking.getId() + " is already present.",
					HttpStatus.CONFLICT);
		}
		repository.save(convertToEntity(booking));
		LOG.info("Created new Booking request came to the service {}", booking);
		return ResponseEntity.status(HttpStatus.CREATED).body(booking);
	}

	/**
	 *This Method is used to retrieve Bookings from DB
	 */
	@Override
	public ResponseEntity<List<Booking>> getAllBookings() {
		List<BookingEntity> records = repository.findAllByOrderByIdAsc();
		LOG.debug("Returned the booking records of size {}, record {}", records.size(), records);
		return ResponseEntity.status(HttpStatus.OK)
				.body(records.stream().map(BookingResourceImpl::convertToObj).collect(Collectors.toList()));
	}

	private static Booking convertToObj(BookingEntity entity) {
		Booking booking = new Booking();
		booking.setId(entity.getId().intValue());
		booking.setFirstName(entity.getFirstName());
		booking.setLastName(entity.getLastName());
		booking.setCheckoutDatetime(entity.getCheckinDatetime());
		booking.setCheckinDatetime(entity.getCheckinDatetime());
		booking.setDeposit(entity.getDeposit());
		booking.setTotalprice(entity.getTotalprice());
		booking.setDateOfBirth(yymmdd.format(entity.getDateOfBirth()));
		Address address = new Address();
		address.setCity(entity.getAddress().getCity());
		address.setLine1(entity.getAddress().getLine1());
		address.setLine2(entity.getAddress().getLine2());
		address.setState(entity.getAddress().getState());
		address.setZipCode(entity.getAddress().getZipCode());
		booking.setAddress(address);
		return booking;

	}

	private BookingEntity convertToEntity(Booking booking) {
		BookingEntity entity = new BookingEntity();
		entity.setId(Long.valueOf(booking.getId()));
		entity.setFirstName(booking.getFirstName());
		entity.setLastName(booking.getLastName());
		entity.setCheckoutDatetime(booking.getCheckinDatetime());
		entity.setCheckinDatetime(booking.getCheckinDatetime());
		entity.setDeposit(booking.getDeposit());
		entity.setTotalprice(booking.getTotalprice());
		entity.setDateOfBirth(toDate(booking.getDateOfBirth()));
		AddressEntity adentity = new AddressEntity();
		adentity.setCity(booking.getAddress().getCity());
		adentity.setLine1(booking.getAddress().getLine1());
		adentity.setLine2(booking.getAddress().getLine2());
		adentity.setState(booking.getAddress().getState());
		adentity.setZipCode(booking.getAddress().getZipCode());
		entity.setAddress(adentity);
		return entity;

	}

	private Date toDate(String date) {
		try {
			return new Date(yymmdd.parse(date).getTime());
		} catch (ParseException e) {
			throw new BookingServiceException("valid date_of_birth format is yyyy-MM-dd", HttpStatus.BAD_REQUEST);
		}
	}

	private void validate(Booking booking) {
		try (InputStream schemaStream = getClass().getResourceAsStream("/v1/schema/booking.json")) {
			JsonNode json = objectMapper.convertValue(booking, JsonNode.class);
			JsonSchema schema = schemaFactory.getSchema(schemaStream);
			Set<ValidationMessage> validate = schema.validate(json);

			if (!CollectionUtils.isEmpty(validate)) {
				String error = validate.stream().map(i -> i.getMessage().replace("$.", ""))
						.filter(i -> !i.contains("integer found, string expected")) // issue with json validator library
						.collect(Collectors.joining(System.lineSeparator()));
				if (error != null && error.length() > 0) {
					throw new BookingServiceException(error, HttpStatus.BAD_REQUEST);
				}
			}

		} catch (IOException e) {
			throw new BookingServiceException("Error while validation json schema", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
