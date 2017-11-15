package com.javachimp.common.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javachimp.common.naming.Registry;
import com.javachimp.common.naming.RegistryKeys;
import com.javachimp.garage.Application;

public class MetricServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Registry reg = Application.getContext()
										.getRegistry();
		MetricRegistry metrics = reg.find(RegistryKeys.METRICS);						
		resp.setStatus(HttpServletResponse.SC_OK);
		
		try(OutputStream out = resp.getOutputStream()) {
			resp.setContentType("application/json");
			
			ObjectMapper mapper =new ObjectMapper();
			
			mapper.writerWithDefaultPrettyPrinter()
				  .writeValue(out, metrics);
		}		
	}
}
