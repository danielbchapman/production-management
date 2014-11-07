package com.danielbchapman.production.web.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public abstract class AbstractJsonService extends HttpServlet implements Serializable
{
	private static final long serialVersionUID = 1L;
	public final static String CONTENT_JSON = "application/json";
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doIt(request, response);
	}
	
	protected final void doIt(HttpServletRequest request, HttpServletResponse response)
	{
		response.setContentType(CONTENT_JSON);
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		
		Enumeration<String> names = request.getParameterNames();
		
		while(names.hasMoreElements())
		{
			String name = names.nextElement();
			parameters.put(name, request.getParameter(name));
		}

		String json = null;
		
		try
		{
			json = process(parameters);
		}
		catch(InvalidRequestException e)
		{	
			json = e.errorJson();
		}
		
		
		try
		{
			response.getWriter().write(json);
			response.flushBuffer();
		}
		catch(IOException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doIt(request, response);
	}
	
	/**
	 * @param parameters
	 * @return a Gson object to write to the stream as a response
	 */
	/**
	 * @param parameters the parameters that come from either the post or get (both are accepted
	 * @return a String with the JSON response
	 * @throws InvalidRequestException if the request doesn't work and a standard object will be created.
	 */
	protected abstract String process(Map<String, String> parameters) throws InvalidRequestException;
	
	
}
