package com.paypal.bfs.test.bookingserv.impl.exceptionhandlers;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ConstructError {
	private String errorMessage;
	private String errorDescription;
	private Date timestamp;

	public ConstructError(String errorMessage, String errorDescription) {
		super();
		this.timestamp = new Date();
		this.errorMessage = errorMessage;
		this.errorDescription = errorDescription;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "ErrorResponse [errorMessage=" + errorMessage + ", errorDescription=" + errorDescription + ", timestamp="
				+ timestamp + "]";
	}

}
