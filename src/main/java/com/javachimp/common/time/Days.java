package com.javachimp.common.time;

import java.time.DayOfWeek;
import java.util.Arrays;



public enum Days {
	
	SUNDAY(DayOfWeek.SUNDAY, "sun"),
	MONDAY(DayOfWeek.MONDAY, "mon"),
	TUESDAY(DayOfWeek.TUESDAY, "tue"),
	WEDNESDAY(DayOfWeek.WEDNESDAY, "wed"),
	THURSDAY(DayOfWeek.THURSDAY, "thu"),
	FRIDAY(DayOfWeek.FRIDAY, "fri"),
	SATURDAY(DayOfWeek.SATURDAY, "sat");
	
	private final DayOfWeek dayOfWeek;
	private final String dayOfWeekLiteral;
	
	private Days(DayOfWeek dayOfWeek, String dayOfWeekLiteral) {
		this.dayOfWeek = dayOfWeek;
		this.dayOfWeekLiteral = dayOfWeekLiteral;
	}
	
	public static Days fromLiteral(final String literal) {  	
		return Arrays.asList(Days.values()).stream()
									.filter(l -> l.dayOfWeekLiteral.equalsIgnoreCase(literal))
									.findFirst().get();
	}
	
	public DayOfWeek dayOfWeek() {
		return dayOfWeek;
	}

}
