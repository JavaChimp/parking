package com.javachimp.garage.rate.processor;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.javachimp.common.ApplicationContext;
import com.javachimp.common.naming.Registry;
import com.javachimp.common.naming.RegistryKeys;
import com.javachimp.garage.Application;
import com.javachimp.garage.rate.repository.EffectiveRate;
import com.javachimp.garage.rate.repository.RateRepository;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Application.class)
public class RateProcessorTest {

	private static RateRepository mock;
	
	@BeforeClass
	public static void init() {
		mock = Mockito.mock(RateRepository.class);
	}
	
	@After
	public void reset() {
		Mockito.reset(mock);
	}
	
	@Test
	public void testBeginDateLow() {
	
		ZonedDateTime startTime = ZonedDateTime.parse("2017-11-11T10:00:00Z");
		ZonedDateTime endTime = ZonedDateTime.parse("2017-11-11T17:00:00Z");
		
		EffectiveRate rate1 =
				new EffectiveRate(DayOfWeek.SATURDAY, "0000", "0700", 900);
		EffectiveRate rate2 =
				new EffectiveRate(DayOfWeek.SATURDAY, "0700", "0900", 1300);
		EffectiveRate rate3 =
				new EffectiveRate(DayOfWeek.SATURDAY, "1900", "1159", 900);
	
		PowerMockito.mockStatic(Application.class);
		ApplicationContext ctxMock = Mockito.mock(ApplicationContext.class);
		Registry registryMock = Mockito.mock(Registry.class);
		
		when(Application.getContext()).thenReturn(ctxMock);
		when(ctxMock.getRegistry()).thenReturn(registryMock);
		when(registryMock.find(RegistryKeys.RATE_REPOSITORY)).thenReturn(mock);
		List<EffectiveRate> rates = new ArrayList<>(3);
		rates.addAll(Arrays.asList(rate1, rate2, rate3));
		
		when(mock.getEffectiveRates(eq(DayOfWeek.SATURDAY)))
			.thenReturn(rates);
		
		EffectiveRate result = RateProcessor.getRate(startTime, endTime);
		Assert.assertNull(result);
		
		ArgumentCaptor<DayOfWeek> dow = ArgumentCaptor.forClass(DayOfWeek.class);
		verify(mock, times(1)).getEffectiveRates(dow.capture());
		
		Assert.assertEquals(DayOfWeek.SATURDAY, dow.getValue());
	}
	
	@Test
	public void testBeginDateHigh() {
	
		ZonedDateTime startTime = ZonedDateTime.parse("2017-11-11T10:00:00Z");
		ZonedDateTime endTime = ZonedDateTime.parse("2017-11-11T17:00:00Z");
		
		EffectiveRate rate1 =
				new EffectiveRate(DayOfWeek.SATURDAY, "0000", "0700", 900);
		EffectiveRate rate2 =
				new EffectiveRate(DayOfWeek.SATURDAY, "0700", "0900", 1300);
	
		PowerMockito.mockStatic(Application.class);
		ApplicationContext ctxMock = Mockito.mock(ApplicationContext.class);
		Registry registryMock = Mockito.mock(Registry.class);
		
		when(Application.getContext()).thenReturn(ctxMock);
		when(ctxMock.getRegistry()).thenReturn(registryMock);
		when(registryMock.find(RegistryKeys.RATE_REPOSITORY)).thenReturn(mock);
		List<EffectiveRate> rates = new ArrayList<>(2);
		rates.addAll(Arrays.asList(rate1, rate2));
		
		when(mock.getEffectiveRates(DayOfWeek.SATURDAY))
			.thenReturn(rates);
		
		EffectiveRate result = RateProcessor.getRate(startTime, endTime);
		Assert.assertNull(result);
		
		ArgumentCaptor<DayOfWeek> dow = ArgumentCaptor.forClass(DayOfWeek.class);
		verify(mock, times(1)).getEffectiveRates(dow.capture());
		
		Assert.assertEquals(DayOfWeek.SATURDAY, dow.getValue());
	}
	
	@Test
	public void testEndDateLow() {
	
		ZonedDateTime startTime = ZonedDateTime.parse("2017-11-11T10:00:00Z");
		ZonedDateTime endTime = ZonedDateTime.parse("2017-11-11T17:00:00Z");
		
		EffectiveRate rate1 =
				new EffectiveRate(DayOfWeek.SATURDAY, "0700", "0900", 1300);
		EffectiveRate rate2 =
				new EffectiveRate(DayOfWeek.SATURDAY, "1900", "1159", 900);
	
		PowerMockito.mockStatic(Application.class);
		ApplicationContext ctxMock = Mockito.mock(ApplicationContext.class);
		Registry registryMock = Mockito.mock(Registry.class);
		
		when(Application.getContext()).thenReturn(ctxMock);
		when(ctxMock.getRegistry()).thenReturn(registryMock);
		when(registryMock.find(RegistryKeys.RATE_REPOSITORY)).thenReturn(mock);
		List<EffectiveRate> rates = new ArrayList<>(2);
		rates.addAll(Arrays.asList(rate1, rate2));
		
		when(mock.getEffectiveRates(eq(DayOfWeek.SATURDAY)))
			.thenReturn(rates);
		
		EffectiveRate result = RateProcessor.getRate(startTime, endTime);
		Assert.assertNull(result);
		
		ArgumentCaptor<DayOfWeek> dow = ArgumentCaptor.forClass(DayOfWeek.class);
		verify(mock, times(1)).getEffectiveRates(dow.capture());
		
		Assert.assertEquals(DayOfWeek.SATURDAY, dow.getValue());
	}
	
	@Test
	public void testEndDateHigh() {
	
		ZonedDateTime startTime = ZonedDateTime.parse("2017-11-11T10:00:00Z");
		ZonedDateTime endTime = ZonedDateTime.parse("2017-11-11T17:00:00Z");
		
		EffectiveRate rate1 =
				new EffectiveRate(DayOfWeek.SATURDAY, "0000", "1100", 900);
		EffectiveRate rate2 =
				new EffectiveRate(DayOfWeek.SATURDAY, "0700", "1600", 1300);
	
		PowerMockito.mockStatic(Application.class);
		ApplicationContext ctxMock = Mockito.mock(ApplicationContext.class);
		Registry registryMock = Mockito.mock(Registry.class);
		
		when(Application.getContext()).thenReturn(ctxMock);
		when(ctxMock.getRegistry()).thenReturn(registryMock);
		when(registryMock.find(RegistryKeys.RATE_REPOSITORY)).thenReturn(mock);
		List<EffectiveRate> rates = new ArrayList<>(2);
		rates.addAll(Arrays.asList(rate1, rate2));
		
		when(mock.getEffectiveRates(DayOfWeek.SATURDAY))
			.thenReturn(rates);
		
		EffectiveRate result = RateProcessor.getRate(startTime, endTime);
		Assert.assertNull(result);
		
		ArgumentCaptor<DayOfWeek> dow = ArgumentCaptor.forClass(DayOfWeek.class);
		verify(mock, times(1)).getEffectiveRates(dow.capture());
		
		Assert.assertEquals(DayOfWeek.SATURDAY, dow.getValue());
	}
	
	@Test
	public void testOverlap() {
	
		ZonedDateTime startTime = ZonedDateTime.parse("2017-11-13T07:00:00Z");
		ZonedDateTime endTime = ZonedDateTime.parse("2017-11-13T17:00:00Z");
		
		EffectiveRate rate1 =
				new EffectiveRate(DayOfWeek.MONDAY, "0000", "0700", 900);
		EffectiveRate rate2 =
				new EffectiveRate(DayOfWeek.MONDAY, "0700", "0900", 1300);
		EffectiveRate rate3 =
				new EffectiveRate(DayOfWeek.MONDAY, "0900", "1900", 2700);
	
		PowerMockito.mockStatic(Application.class);
		ApplicationContext ctxMock = Mockito.mock(ApplicationContext.class);
		Registry registryMock = Mockito.mock(Registry.class);
		
		when(Application.getContext()).thenReturn(ctxMock);
		when(ctxMock.getRegistry()).thenReturn(registryMock);
		when(registryMock.find(RegistryKeys.RATE_REPOSITORY)).thenReturn(mock);
		List<EffectiveRate> rates = new ArrayList<>(3);
		rates.addAll(Arrays.asList(rate1, rate2, rate3));
		
		when(mock.getEffectiveRates(eq(DayOfWeek.MONDAY)))
			.thenReturn(rates);
		
		EffectiveRate result = RateProcessor.getRate(startTime, endTime);
		Assert.assertNull(result);
		
		ArgumentCaptor<DayOfWeek> dow = ArgumentCaptor.forClass(DayOfWeek.class);
		verify(mock, times(1)).getEffectiveRates(dow.capture());
		
		Assert.assertEquals(DayOfWeek.MONDAY, dow.getValue());
	}
	
	@Test
	public void testRangeIsJustRight() {
	
		ZonedDateTime startTime = ZonedDateTime.parse("2017-11-13T10:00:00Z");
		ZonedDateTime endTime = ZonedDateTime.parse("2017-11-13T17:00:00Z");
		
		EffectiveRate rate1 =
				new EffectiveRate(DayOfWeek.MONDAY, "0000", "0700", 900);
		EffectiveRate rate2 =
				new EffectiveRate(DayOfWeek.MONDAY, "0700", "0900", 1300);
		EffectiveRate rate3 =
				new EffectiveRate(DayOfWeek.MONDAY, "0900", "1900", 2700);
	
		PowerMockito.mockStatic(Application.class);
		ApplicationContext ctxMock = Mockito.mock(ApplicationContext.class);
		Registry registryMock = Mockito.mock(Registry.class);
		
		when(Application.getContext()).thenReturn(ctxMock);
		when(ctxMock.getRegistry()).thenReturn(registryMock);
		when(registryMock.find(RegistryKeys.RATE_REPOSITORY)).thenReturn(mock);
		List<EffectiveRate> rates = new ArrayList<>(3);
		rates.addAll(Arrays.asList(rate1, rate2, rate3));
		
		when(mock.getEffectiveRates(eq(DayOfWeek.MONDAY)))
			.thenReturn(rates);
		
		EffectiveRate result = RateProcessor.getRate(startTime, endTime);
		Assert.assertNotNull(result);
		Assert.assertEquals(2700L, result.getPrice().longValue());
		
		ArgumentCaptor<DayOfWeek> dow = ArgumentCaptor.forClass(DayOfWeek.class);
		verify(mock, times(1)).getEffectiveRates(dow.capture());
		
		Assert.assertEquals(DayOfWeek.MONDAY, dow.getValue());
	}
	
}