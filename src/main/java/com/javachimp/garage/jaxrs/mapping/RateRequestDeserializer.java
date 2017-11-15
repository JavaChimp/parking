package com.javachimp.garage.jaxrs.mapping;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.javachimp.garage.rate.messages.RateRequest;

public class RateRequestDeserializer extends StdDeserializer<RateRequest> {

	private static final long serialVersionUID = 6376837447909969856L;

	public RateRequestDeserializer() {
		super(RateRequest.class);
	}

	@Override
	public RateRequest deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
		
		ObjectCodec oc = p.getCodec();
		JsonNode node = oc.readTree(p);
		
		String startDate = node.findValue("beginDate").asText();
		String endDate = node.findValue("endDate").asText();
		
		RateRequest req = new RateRequest(
							ZonedDateTime.parse(startDate),		
							ZonedDateTime.parse(endDate));
		
		return req;
	}

}
