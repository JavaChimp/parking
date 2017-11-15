package com.javachimp.garage.jaxrs.mapping;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.javachimp.garage.rate.messages.RateResponse;

public class RateResponseSerializer extends StdSerializer<RateResponse> {

	private static final long serialVersionUID = -4059298095768575191L;

	public RateResponseSerializer() {
		super(RateResponse.class);
	}

	@Override
	public void serialize(RateResponse value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartObject("effective-rates");
		gen.writeStringField("beginDate", value.getStartDate().format(ISO_INSTANT));
		gen.writeStringField("endDate", value.getEndDate().format(ISO_INSTANT));
		gen.writeNumberField("price", value.getPrice());
		gen.writeEndObject();
	}
}
