package com.javachimp.garage.rate.messages;

import java.time.ZonedDateTime;

public class RateRequest {
	
	private final ZonedDateTime startDate;
	private final ZonedDateTime endDate;
	
	public RateRequest(ZonedDateTime startDate, ZonedDateTime endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public ZonedDateTime getStartDate() {
		return startDate;
	}

	public ZonedDateTime getEndDate() {
		return endDate;
	}
}
