package com.javachimp.garage.rate.repository;

import java.time.DayOfWeek;
import java.util.List;

import com.javachimp.common.lifecycle.LifeCycle;

public interface RateRepository extends LifeCycle {

	public abstract List<EffectiveRate> getEffectiveRates(DayOfWeek dow);

}