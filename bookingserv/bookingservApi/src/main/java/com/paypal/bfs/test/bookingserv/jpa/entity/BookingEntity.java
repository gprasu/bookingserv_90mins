package com.paypal.bfs.test.bookingserv.jpa.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Booking")
public class BookingEntity {

	@Id
	@Column(name = "booking_id")
	private Long id;

	@Column(name = "first_name", nullable = false)
	@Size(min = 1, max = 255)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	@Size(min = 1, max = 255)
	private String lastName;

	@Column(name = "date_of_birth", nullable = false)
	private Date dateOfBirth;

	@Column(name = "checkin_datetime", nullable = false)
	private Date checkinDatetime;

	@Column(name = "checkout_datetime", nullable = false)
	private Date checkoutDatetime;

	@Column(name = "totalprice", nullable = false)
	private Double totalprice;

	@Column(name = "deposit", nullable = false)
	private Double deposit;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private AddressEntity address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getCheckinDatetime() {
		return checkinDatetime;
	}

	public void setCheckinDatetime(Date checkinDatetime) {
		this.checkinDatetime = checkinDatetime;
	}

	public Date getCheckoutDatetime() {
		return checkoutDatetime;
	}

	public void setCheckoutDatetime(Date checkoutDatetime) {
		this.checkoutDatetime = checkoutDatetime;
	}

	public Double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Double totalprice) {
		this.totalprice = totalprice;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public AddressEntity getAddress() {
		return address;
	}

	public void setAddress(AddressEntity address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "BookingEntity [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth="
				+ dateOfBirth + ", checkinDatetime=" + checkinDatetime + ", checkoutDatetime=" + checkoutDatetime
				+ ", totalprice=" + totalprice + ", deposit=" + deposit + ", address=" + address + "]";
	}

}
