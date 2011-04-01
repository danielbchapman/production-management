package com.danielbchapman.production.entity;

import java.io.Serializable;

/**
 * A simple enumeration signifying priority
 *
 */
public enum Priority implements Serializable
{
	REMINDER("Reminder"),
	STANDARD("Standard"),
	BY_DATE("By Date"),
	URGENT("Urgent"),
	HIGH("High");
	
	String s;
	Priority(String s)
	{	
		this.s = s;
	}
	
	/**
	 * @param s the string to search
	 * @return the associated Priority, REMINDER if unknown
	 */
	public Priority parseValue(String s)
	{
		if(s == null)
			return REMINDER;
		
		for(Priority p : values())
			if(p.toString().equals(s))
				return p;
		
		return REMINDER;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString()
	{
		return s;
	}
}
