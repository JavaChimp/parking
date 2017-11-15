package com.javachimp.garage.rate.processor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import com.javachimp.common.naming.RegistryKeys;
import com.javachimp.garage.Application;
import com.javachimp.garage.rate.repository.EffectiveRate;
import com.javachimp.garage.rate.repository.RateRepository;

public class RateProcessor {
	
	public static EffectiveRate getRate(ZonedDateTime startTime, ZonedDateTime endTime) {

		DayOfWeek dow = startTime.getDayOfWeek();
		LocalTime low = startTime.toLocalTime();
		LocalTime high = endTime.toLocalTime();
		
		RateRepository rateRepo = Application.getContext()
											 .getRegistry()
											 .find(RegistryKeys.RATE_REPOSITORY);
		
		List<EffectiveRate> effectiveRates = rateRepo.getEffectiveRates(dow);
		
		Optional<EffectiveRate> rate = 
							  effectiveRates.stream()
				 							.filter(r -> (!low.isBefore(r.getStartTime()) && (!low.isAfter(r.getEndTime()))) &&  
														 (!high.isBefore(r.getStartTime()) && (!high.isAfter(r.getEndTime()))))
							   				.findAny();
		return rate.isPresent() ? rate.get() : null;
	}
	
}
