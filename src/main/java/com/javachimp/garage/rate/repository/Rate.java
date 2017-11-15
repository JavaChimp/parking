package com.javachimp.garage.rate.repository;

public class Rate {
	
	private String[] days;
	private String[] times;
	private Integer price;

	public Rate() {
		super();
	}

	public String[] getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days.split(",");
	}

	public String[] getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times.split("-");
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
	
}
