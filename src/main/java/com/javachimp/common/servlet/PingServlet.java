package com.javachimp.common.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PingServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try(OutputStream out = resp.getOutputStream()) {
			resp.setContentType("text-plain");
			resp.setStatus(HttpServletResponse.SC_OK);
			out.write("pong".getBytes("UTF-8"));
		}
	}

}
