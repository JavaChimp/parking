package com.javachimp.garage.rate.messages;

import java.time.ZonedDateTime;

public class RateResponse {
	
	private final ZonedDateTime startDate;
	private final ZonedDateTime endDate;
	private final Integer price;
	
	public RateResponse(ZonedDateTime startDate, ZonedDateTime endDate, Integer price) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
	}

	public ZonedDateTime getStartDate() {
		return startDate;
	}

	public ZonedDateTime getEndDate() {
		return endDate;
	}
	
	public Integer getPrice() {
		return price;
	}
}
