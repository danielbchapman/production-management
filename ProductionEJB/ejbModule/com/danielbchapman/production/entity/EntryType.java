package com.danielbchapman.production.entity;

import java.io.Serializable;

/**
 * A simple enumeration anticipating complex types
 *
 */
public enum EntryType implements Serializable
{
	PENDING("Pending"),
	CONFIRMED("Confirmed");
	
	String name;
	EntryType(String name)
	{
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString()
	{
		return name;
	}
}
