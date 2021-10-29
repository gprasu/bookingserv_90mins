package com.paypal.bfs.test.bookingserv.repo;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.paypal.bfs.test.bookingserv.api.model.Booking;
import com.paypal.bfs.test.bookingserv.helper.BookingHelper;
import com.paypal.bfs.test.bookingserv.impl.jpa.repo.BookingRepo;
import com.paypal.bfs.test.bookingserv.jpa.entity.BookingEntity;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookingServiceRepoTests {

	@Autowired
	private BookingRepo repository;

	@Test
	@Rollback(true)
	void addAndFindBookingInRepo() throws Exception {
		Booking booking = BookingHelper.getBookingObj();
		BookingEntity convertToEntity = BookingHelper.convertToEntity(booking);
		repository.save(convertToEntity);
		assertEquals(true, repository.existsById(convertToEntity.getId()));
	}

}
