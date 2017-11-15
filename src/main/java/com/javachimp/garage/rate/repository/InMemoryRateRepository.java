package com.javachimp.garage.rate.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javachimp.common.time.Days;

public class InMemoryRateRepository implements RateRepository  {
	
	private final Map<DayOfWeek, List<EffectiveRate>> repository = new ConcurrentHashMap<>(); 
	private final StringBuilder builder = new StringBuilder();
	private final String fileName;
	
	public InMemoryRateRepository(String fileName) {
		this.fileName = fileName;
	}
	
	public void init() {
		InputStream input = Thread.currentThread()
							  .getContextClassLoader()
							  .getResourceAsStream(fileName);
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
			reader.lines().forEach(l-> builder.append(l + "\n"));
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		String json = builder.toString();
		Rates rates = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			rates = (Rates) mapper.readValue(json, Rates.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (Rate rate : rates.getRates()) {
			for (String day : rate.getDays()) {
				DayOfWeek dow = Days.fromLiteral(day).dayOfWeek();
				EffectiveRate effectiveRate = 	
						new EffectiveRate(dow, rate.getTimes()[0],
										  rate.getTimes()[1],
										  rate.getPrice());	
									  
										  
				if(repository.containsKey(dow)) {
					repository.get(dow).add(effectiveRate);
				} else {
					List<EffectiveRate> repoValue = new ArrayList<>();
					repoValue.add(effectiveRate);
					repository.put(dow, repoValue);
				}
			}
		}
	}
		
	@Override
	public List<EffectiveRate> getEffectiveRates(DayOfWeek dow) {
		return repository.get(dow);
	}
}
