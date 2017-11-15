package com.javachimp.garage.jaxrs.resources;

import java.time.ZonedDateTime;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.javachimp.common.naming.RegistryKeys;
import com.javachimp.garage.Application;
import com.javachimp.garage.rate.messages.RateRequest;
import com.javachimp.garage.rate.messages.RateResponse;
import com.javachimp.garage.rate.processor.RateProcessor;
import com.javachimp.garage.rate.repository.EffectiveRate;

@Path("/garage/rate")
public class RateResource {

	private static Logger logger = LogManager.getLogger(RateResource.class);
	private final MetricRegistry metrics = Application.getContext().getRegistry()
											  .find(RegistryKeys.METRICS);
	private final Counter counter = metrics.counter("garage.rate.counter");
	private final Timer timer = metrics.timer("garage.rate.timer");
	
	
	@GET
	@Consumes({MediaType.APPLICATION_JSON, 
			   MediaType.APPLICATION_XML})
	@Produces({MediaType.APPLICATION_JSON, 
		       MediaType.APPLICATION_XML})
	public Response getEffectiveRate(RateRequest req) {
		
		final Timer.Context timerCtx = timer.time();
		try {
			counter.inc();
			logger.trace(()-> "Entering getEffectiveRate...");
			
			final ZonedDateTime startTime = req.getStartDate();
			final ZonedDateTime endTime = req.getEndDate();
			
			EffectiveRate rate = RateProcessor.getRate(startTime, endTime);
			
			if (rate == null) {
				logger.debug(String.format("Rate not found for %s to %s.", startTime, endTime));
				logger.trace(()-> "Exiting getEffectiveRate with 404 code");
				return Response.status(Status.NOT_FOUND.getStatusCode(), "Rates for that timefame are unvailable.").build();
			} else {
				logger.debug(() -> "Rate of" + rate.getPrice() + 
											"found for" + startTime + " to " + endTime);
				RateResponse res = new RateResponse(startTime, endTime, rate.getPrice());
				logger.trace(()-> "Exiting getEffectiveRate with 200 code");
				return Response.ok(res).build();
			}
		} finally {
			timerCtx.stop();
		}
	}
}
