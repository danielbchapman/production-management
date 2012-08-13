package com.danielbchapman.production.web.services;

import java.util.Map;

import com.google.gson.Gson;

public class InvalidRequestException extends Exception
{

	private static final long serialVersionUID = 1L;

	public InvalidRequestException(String message, Class<?> clazz, Map<String, String> parameters)
	{
		super(createMessage(message, clazz, parameters));
	}

	public static String createMessage(String message, Class<?> clazz, Map<String, String> parameters)
	{
		StringBuilder b = new StringBuilder(1024);
		b.append(message);
		b.append("\nError for: ");
		b.append(clazz.getName());
		

		if(parameters != null && parameters.keySet().size() > 0)
		{
			b.append("\n Paramters");
			for(String s : parameters.keySet())
			{
				b.append("\n\t");
				b.append(s);
				b.append(", ");
				b.append(parameters.get(s));
			}
		}
			
		else
			b.append(" | No parameters found");

		return b.toString();
	}
	
	/**
	 * @return a new Error JSON message from the framework
	 */
	public String errorJson()
	{
		return new Gson().toJson(new JsonTemplate(true, getMessage()));
	}
}
