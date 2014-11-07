package com.danielbchapman.production;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.NumberConverter;

public class SmartConvertCurrency extends NumberConverter
{

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent ui, String value)
	{
		if(value != null)
			value = value.replaceAll("$", "");

		return super.getAsObject(ctx, ui, value);
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent ui, Object value)
	{
		String newVal = super.getAsString(ctx, ui, value);
		if(newVal != null)
			newVal = newVal.replaceAll("$", "");

		return newVal;
	}
}
