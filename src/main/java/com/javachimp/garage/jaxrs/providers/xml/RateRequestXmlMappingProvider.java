package com.javachimp.garage.jaxrs.providers.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.javachimp.garage.jaxrs.mapping.RateRequestDeserializer;
import com.javachimp.garage.jaxrs.mapping.RateResponseSerializer;
import com.javachimp.garage.rate.messages.RateRequest;
import com.javachimp.garage.rate.messages.RateResponse;

@Provider
@Consumes(MediaType.APPLICATION_XML)
@Produces(MediaType.APPLICATION_XML)
public class RateRequestXmlMappingProvider implements MessageBodyReader<RateRequest>,
													  MessageBodyWriter<RateResponse> {

	private static final XmlMapper mapper;
	
	static {
		SimpleModule  module = new SimpleModule("RateRequest-Xml", new Version(1,0,0,"SNAPSHOT", "com.spothero", "spothero"));
		module.addDeserializer(RateRequest.class, new RateRequestDeserializer());
		module.addSerializer(RateResponse.class, new RateResponseSerializer());
		mapper = new XmlMapper();
		mapper.registerModule(module);
	}
	
	public RateRequestXmlMappingProvider() {
		super();
	}
	
	@Override
	public boolean isReadable(Class<?> type, Type genericType,
							  Annotation[] annotations, MediaType mediaType) {
		
		return (type.isAssignableFrom(RateRequest.class))
				&& (MediaType.APPLICATION_XML_TYPE.equals(mediaType));
	}

	@Override
	public RateRequest readFrom(Class<RateRequest> type, Type genericType,
								Annotation[] annotations, MediaType mediaType,
								MultivaluedMap<String, String> httpHeaders, InputStream entityStream) {
		RateRequest req = null;
		
		try {
			req = mapper.readValue(entityStream, RateRequest.class);
		} catch (IOException e) {
			//This is a terrible solution, exceptions should never be swallowed.
			//This should wrapped by a runtime exception and then thrown
			//so it can be passed to an exception handler that converts the exception
			//into an HTTP status code.  This is just a hack to save time.
			e.printStackTrace();
		}
		
		return req;
	}
	
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		return (type.isAssignableFrom(RateResponse.class))
				&& (MediaType.APPLICATION_XML_TYPE.equals(mediaType));
	}

	@Override
	public void writeTo(RateResponse t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
						MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) {
		
		try {
			mapper.writeValue(entityStream, t);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
