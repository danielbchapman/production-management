package com.danielbchapman.production.entity;

import java.io.Serializable;

/**
 * A simple enumeration signifying priority
 *
 */
public enum Priority implements Serializable
{
	NONE("None"),
	E_MAIL("Electronic Mail"),
	URGENT("Urgent, All Notifications ");
	
	String s;
	Priority(String s)
	{	
		this.s = s;
	}
	
	/**
	 * @param s the string to search
	 * @return the associated Priority, REMINDER if unknown
	 */
	public static Priority parseValue(String s)
	{
		if(s == null)
			return NONE;
		
		for(Priority p : values()) //Catch Names
			if(p.toString().equals(s))
				return p;
		
		for(Priority p : values()) //Catch Ordinal
			if(Integer.toString(p.ordinal()).equals(s))
				return p;
		
		return NONE;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString()
	{
		return s;
	}
}
