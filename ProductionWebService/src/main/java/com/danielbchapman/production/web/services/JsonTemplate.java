package com.danielbchapman.production.web.services;

import lombok.Data;

@Data
public class JsonTemplate
{
	private boolean hasError = false;
	private String errorMessage = null;
	
	public JsonTemplate()
	{
	}
	
	public JsonTemplate(boolean error, String errorMessage)
	{
		this.hasError = error;
		this.errorMessage = errorMessage;
	}
}
