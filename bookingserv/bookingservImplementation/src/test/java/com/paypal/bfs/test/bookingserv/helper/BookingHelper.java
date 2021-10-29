package com.paypal.bfs.test.bookingserv.helper;

import java.util.Date;

import com.paypal.bfs.test.bookingserv.api.model.Address;
import com.paypal.bfs.test.bookingserv.api.model.Booking;
import com.paypal.bfs.test.bookingserv.jpa.entity.AddressEntity;
import com.paypal.bfs.test.bookingserv.jpa.entity.BookingEntity;

public class BookingHelper {
	
	public static BookingEntity convertToEntity(Booking booking) {
		BookingEntity entity = new BookingEntity();
		entity.setId(Long.valueOf(booking.getId()));
		entity.setFirstName(booking.getFirstName());
		entity.setLastName(booking.getLastName());
		entity.setCheckoutDatetime(booking.getCheckinDatetime());
		entity.setCheckinDatetime(booking.getCheckinDatetime());
		entity.setDeposit(booking.getDeposit());
		entity.setTotalprice(booking.getTotalprice());
		entity.setDateOfBirth(new Date());
		AddressEntity adentity = new AddressEntity();
		adentity.setCity(booking.getAddress().getCity());
		adentity.setLine1(booking.getAddress().getLine1());
		adentity.setLine2(booking.getAddress().getLine2());
		adentity.setState(booking.getAddress().getState());
		adentity.setZipCode(booking.getAddress().getZipCode());
		entity.setAddress(adentity);
		return entity;

	}

	public static Booking getBookingObj() {
		Booking booking = new Booking();
		booking.setId(1234);
		booking.setFirstName("first Name");
		booking.setLastName("last Name");
		booking.setCheckoutDatetime(new Date());
		booking.setCheckinDatetime(new Date());
		booking.setDeposit(10.0);
		booking.setTotalprice(100.0);
		booking.setDateOfBirth("1996-07-21");
		Address address = new Address();
		address.setCity("bangalore");
		address.setLine1("line1");
		address.setLine2("line2");
		address.setState("karnataka");
		address.setZipCode("560064");
		booking.setAddress(address);
		return booking;

	}

}
