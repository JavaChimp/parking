package com.javachimp.garage.rate.processor;

import java.time.ZonedDateTime;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.javachimp.garage.Application;
import com.javachimp.garage.rate.repository.EffectiveRate;

public class RateProcessorITest {

	
	@BeforeClass
	public static void init() {
		Application.init();
	}
	
	@Test
	public void testBeginDateLow() {
	
		ZonedDateTime startTime = ZonedDateTime.parse("2017-11-11T01:00:00Z");
		ZonedDateTime endTime = ZonedDateTime.parse("2017-11-11T17:00:00Z");
		
		EffectiveRate result = RateProcessor.getRate(startTime, endTime);
		Assert.assertNull(result);

	}
	
	@Test
	public void testBeginDateHigh() {
	
		ZonedDateTime startTime = ZonedDateTime.parse("2017-11-11T22:00:00Z");
		ZonedDateTime endTime = ZonedDateTime.parse("2017-11-11T23:00:00Z");
		
		EffectiveRate result = RateProcessor.getRate(startTime, endTime);
		Assert.assertNull(result);
	}
	
	@Test
	public void testEndDateLow() {
	
		ZonedDateTime startTime = ZonedDateTime.parse("2017-11-11T00:00:00Z");
		ZonedDateTime endTime = ZonedDateTime.parse("2017-11-11T00:59:59Z");
		
		EffectiveRate result = RateProcessor.getRate(startTime, endTime);
		Assert.assertNull(result);
	}
	
	@Test
	public void testEndDateHigh() {
	
		ZonedDateTime startTime = ZonedDateTime.parse("2017-11-13T06:00:00Z");
		ZonedDateTime endTime = ZonedDateTime.parse("2017-11-13T08:00:00Z");
		
		EffectiveRate result = RateProcessor.getRate(startTime, endTime);
		Assert.assertNull(result);
		
	}
	
	@Test
	public void testOverlap() {
	
		ZonedDateTime startTime = ZonedDateTime.parse("2017-11-13T02:00:00Z");
		ZonedDateTime endTime = ZonedDateTime.parse("2017-11-13T06:00:00Z");
		
		EffectiveRate result = RateProcessor.getRate(startTime, endTime);
		Assert.assertNull(result);
	}
	
	@Test
	public void testRangeIsJustRight() {
	
		ZonedDateTime startTime = ZonedDateTime.parse("2017-11-13T10:00:00Z");
		ZonedDateTime endTime = ZonedDateTime.parse("2017-11-13T17:00:00Z");
		
		EffectiveRate result = RateProcessor.getRate(startTime, endTime);
		Assert.assertNotNull(result);
		Assert.assertEquals(1500L, result.getPrice().longValue());
		
	}
	
}