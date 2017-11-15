package com.javachimp.garage.rate.repository;

import java.time.DayOfWeek;
import java.time.LocalTime;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class EffectiveRate {
	
	private final DayOfWeek day;
	private final LocalTime startTime;
	private final LocalTime endTime;
	private final Integer price;
	
	public EffectiveRate(DayOfWeek day, String startTime, String endTime, Integer price) {
		super();
		this.day = day;
		this.startTime = LocalTime.of(Integer.valueOf(startTime.substring(0,2)),
			  	 		   			  Integer.valueOf(startTime.substring(2)));
		this.endTime = LocalTime.of(Integer.valueOf(endTime.substring(0,2)),
		   			  				Integer.valueOf(endTime.substring(2)));;
		this.price = price;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public Integer getPrice() {
		return price;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder
				.reflectionToString(this);
	}
	
	@Override
	public boolean equals(Object other) {
		return EqualsBuilder
				.reflectionEquals(this, other, false);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder
				.reflectionHashCode(this);
	}
				
}
